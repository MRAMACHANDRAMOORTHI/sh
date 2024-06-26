package com.mca.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
@Entity
public class Hperson {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String mobile;
    private String password;
    private String domain;
    private String company;


    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hperson(String name, String email, String mobile, String password, String domain,String company) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.domain = domain;
        this.company=company;
    }


    public Hperson() {

    }




}
