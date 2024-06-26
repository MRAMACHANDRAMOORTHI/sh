package com.mca.project.model;

import jakarta.persistence.*;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String email;
    private String mobile;
    private String password;
    private String gender;
    private String linkedin;
    private String portfolio;

    @ManyToOne
    @JoinColumn(name = "domain_id")
    private Dom domain;

    @ManyToOne
    @JoinColumn(name = "subdomain_id")
    private SubDom subdomain;

    // Constructors, getters, and setters

    public Person() {
        // default constructor
    }

    public Person(String name, String email, String mobile, String password, String gender, String linkedin, String portfolio, Dom domain, SubDom subdomain) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.gender = gender;
        this.linkedin = linkedin;
        this.portfolio = portfolio;
        this.domain = domain;
        this.subdomain = subdomain;
    }

    // Getters and setters for all fields

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

    public Dom getDomain() {
        return domain;
    }

    public void setDomain(Dom domain) {
        this.domain = domain;
    }

    public SubDom getSubdomain() {
        return subdomain;
    }

    public void setSubdomain(SubDom subdomain) {
        this.subdomain = subdomain;
    }
}
