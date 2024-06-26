package com.mca.project.repository;

import com.mca.project.model.Company;
import com.mca.project.model.Dom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepo extends JpaRepository<Company, Long> {
    List<Company> findByDomain(Dom d);
}
