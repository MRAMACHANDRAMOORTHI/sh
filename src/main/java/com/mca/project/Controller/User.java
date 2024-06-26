package com.mca.project.Controller;

import com.mca.project.model.*;
import com.mca.project.repository.*;
import com.mca.project.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Controller
public class User {

    private static final String UPLOAD_DIR = "uploads/";

    private final EmailService emailService;
    private final SubdomainRepo subdomainRepo;
    private final DomainRepo domainRepo;
    private final PersonRepo personRepo;
    private final HpersonRepo hpersonRepo;
    private final Collab2Repo collab2Repo;
    private final CompanyRepo companyRepo;

    @Autowired
    public User(EmailService emailService,
                SubdomainRepo subdomainRepo,
                DomainRepo domainRepo,
                PersonRepo personRepo,
                HpersonRepo hpersonRepo,
                Collab2Repo collab2Repo,
                CompanyRepo companyRepo) {
        this.emailService = emailService;
        this.subdomainRepo = subdomainRepo;
        this.domainRepo = domainRepo;
        this.personRepo = personRepo;
        this.hpersonRepo = hpersonRepo;
        this.collab2Repo = collab2Repo;
        this.companyRepo = companyRepo;
    }

    // Home page mapping
    @GetMapping("/")
    public String index() {
        return "index"; // Assuming you have an index.html or index.jsp in your templates
    }

    // About page mapping
    @GetMapping("/about")
    public String about() {
        return "about";
    }

    // Services page mapping
    @GetMapping("/service")
    public String service() {
        return "service";
    }

    // Collaboration page mapping
    @GetMapping("/collab")
    public String collab() {
        return "collab";
    }

    // Additional collaboration page mapping (if needed)
    @GetMapping("/collab2")
    public String collab2() {
        return "collab2";
    }

    // Why Us page mapping
    @GetMapping("/why")
    public String why() {
        return "why";
    }

    // Login page mapping
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Signup page mapping with domain and subdomain options
    @GetMapping("/sign")
    public String sign(Model model) {
        List<Dom> dom = domainRepo.findAll();
        List<SubDom> sub = subdomainRepo.findAll();
        model.addAttribute("domain", dom);
        model.addAttribute("subdom", sub);
        model.addAttribute("person", new Person());
        return "signup";
    }

    // Thank you page mapping
    @GetMapping("/thank")
    public String thank() {
        return "thank";
    }

    // login page mapping
    @GetMapping("/hlogin")
    public String hlogin() {
        return "hlogin";
    }

    // signup page mapping
    @GetMapping("/hsignup")
    public String hsignup() {
        return "hsignup";
    }

    // Processing signup form for regular users
    @PostMapping("/signup")
    public String signup(@ModelAttribute("person") Person person) {
        Person savedPerson = personRepo.findByEmail(person.getEmail());
        if (savedPerson == null) {
            String e = person.getEmail();
            String subject = "Skillhub Account Creation";
            String text = "Account creation with Skillhub is successful";
            emailService.sendEmail(e, subject, text);
            personRepo.save(person);
            return "redirect:/login"; // Redirect to login page after successful signup
        } else {
            return "redirect:/signup"; // Handle case where email already exists
        }
    }

    // Processing signup form for Hiring Persons
    @PostMapping("/hsignup")
    public String hsignup(@ModelAttribute Hperson hperson, Model model) {
        Hperson h = hpersonRepo.findByEmail(hperson.getEmail());
        if (h == null) {
            String e = hperson.getEmail();
            String subject = "Skillhub Account Creation";
            String text = "You successfully Create a Account in SkillHub";
            emailService.sendEmail(e, subject, text);
            hpersonRepo.save(hperson);
            return "hlogin"; // Redirect to hospital login page after successful signup
        } else {
            model.addAttribute("msg", "Email already in use");
            return "hsignup"; // Handle case where email already exists
        }
    }

    // Saving collaboration details to the database
    @PostMapping("/collab2")
    public String collab2(@ModelAttribute Collab2 collab2) {
        collab2Repo.save(collab2);
        return "thank"; // Redirect to thank you page after saving collaboration details
    }

    // Processing login form for regular users
    @PostMapping("/log")
    public String log(@ModelAttribute Person person, Model model) {
        Person person1 = personRepo.findByEmail(person.getEmail());
        if (person1 != null && person1.getPassword().equals(person.getPassword())) {
            model.addAttribute("person", person1);
            Dom d = person1.getDomain();
            List<Company> com = companyRepo.findByDomain(d);
            model.addAttribute("company", com);
            return "sucess"; // Redirect to success page after successful login
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login"; // Redirect back to login page with error message
        }
    }

    // Processing login form for hiring persons
    @PostMapping("/hlog")
    public String hlog(@ModelAttribute Hperson hperson, Model model) {
        Hperson hperson1 = hpersonRepo.findByEmail(hperson.getEmail());
        if (hperson1 != null && hperson1.getPassword().equals(hperson.getPassword())) {
            model.addAttribute("hperson", hperson1);
            return "redirect:/main"; // Redirect to main page after hospital login
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "hlogin"; // Redirect back to hospital login page with error message
        }
    }

    // Displaying main page with domain details
    @GetMapping("/main")
    public String main(Model model) {
        List<Dom> domain = domainRepo.findAll();
        model.addAttribute("domain", domain);
        return "main"; // Display main page with domain information
    }

    // Backend processing based on selected domain
    @PostMapping("/backend")
    public String backend(@RequestParam("domains") String dom, Model model) {
        List<Dom> d = domainRepo.findAll();
        Dom domain = domainRepo.findByName(dom);
        List<SubDom> subdom = null;
        if (domain != null) {
            subdom = subdomainRepo.findDistinctByDomain(domain);
            model.addAllAttributes(Map.of(
                    "domain", d,
                    "subdom", subdom
            ));
        } else {
            model.addAttribute("noResultsFound", true); // Handle case where domain is not found
        }
        return "main"; // Display main page with updated domain and subdomain details
    }

    // Frontend processing based on selected subdomain
    @PostMapping("/front")
    public String front(@RequestParam("d") Long subdomainId, Model model) {
        List<Person> persons = personRepo.findBySubdomainId(subdomainId);
        if (persons == null) {
            return "error"; // Handle case where persons for subdomain are not found
        }
        model.addAttribute("person", persons);
        return "front"; // Display frontend page with persons related to selected subdomain
    }

    // Displaying form to add a company with domain options
    @GetMapping("/addCompany")
    public String showAddCompanyForm(Model model) {
        List<Dom> domains = domainRepo.findAll();
        model.addAttribute("domains", domains);
        model.addAttribute("company", new Company());
        return "addcompany"; // Display form to add a company with domain options
    }

    // Saving company details to the database
    @PostMapping("/saveCompany")
    public String saveCompany(@ModelAttribute("company") Company company) {
        companyRepo.save(company);
        return "redirect:/addCompany"; // Redirect to addCompany page after saving company details
    }

    // Method to show domain form (if needed)
    @GetMapping("/domain_form")
    public String showDomainForm() {
        return "domain_form"; // Display domain form page
    }

    // Method to save domain and subdomain details to the database
    @PostMapping("/saveDomain")
    public String saveDomain(
            @RequestParam("subdomName") String subdomName,
//            @RequestParam("subdomainLogo") MultipartFile imgLink,
            @RequestParam("domainName") String domainName,
//            @RequestParam("domainLogo") MultipartFile domainLogoFile,
            RedirectAttributes redirectAttributes) {

        Dom existingDomain = domainRepo.findByName(domainName);

        if (existingDomain == null) {
            Dom newDomain = new Dom();
            newDomain.setName(domainName);
//            newDomain.setLogo(uploadFile(domainLogoFile));
            existingDomain = domainRepo.save(newDomain);
        }

        SubDom subDom = new SubDom();
        subDom.setName(subdomName);
//      subDom.setImg_link(uploadFile(imgLink));
        subDom.setDomain(existingDomain);

        subdomainRepo.save(subDom);

        redirectAttributes.addFlashAttribute("message", "SubDom saved successfully!");
        return "redirect:/domain_form"; // Redirect back to domain form
    }

    // Method to upload file and return the file path
//    private String uploadFile(MultipartFile file) {
//        try {
//            String filename = StringUtils.cleanPath(file.getOriginalFilename());
//            Path uploadPath = Paths.get(UPLOAD_DIR);
//            if (!Files.exists(uploadPath)) {
//                Files.createDirectories(uploadPath);
//            }
//            Path filePath = uploadPath.resolve(filename);
//            Files.copy(file.getInputStream(), filePath);
//            return filePath.toString();
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to store file", e);
//        }
//    }

}
