package com.mca.project.repository;

import com.mca.project.model.Dom;
import com.mca.project.model.SubDom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubdomainRepo extends JpaRepository<SubDom, Long> {

    List<SubDom> findDistinctByDomain(Dom domain);

}