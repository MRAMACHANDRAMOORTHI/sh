package com.mca.project.service;

import com.mca.project.model.Dom;
import com.mca.project.model.SubDom;
import com.mca.project.repository.SubdomainRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubdomainService {
    @Autowired
    private SubdomainRepo subdomainRepo;

    public List<SubDom> findDistinctSubdomainsByDomain(Dom domain) {
        return subdomainRepo.findDistinctByDomain(domain);
    }
}
