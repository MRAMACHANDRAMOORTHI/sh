package com.mca.project.model;

import jakarta.persistence.*;

@Entity
public class SubDom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;
    public String img_link;

    @ManyToOne
    private Dom domain;

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

    public String getImg_link() {
        return img_link;
    }

    public void setImg_link(String img_link) {
        this.img_link = img_link;
    }

    public Dom getDomain() {
        return domain;
    }

    public void setDomain(Dom domain) {
        this.domain = domain;
    }

    // Constructors, getters, and setters
    // Omitted for brevity
}
