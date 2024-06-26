package com.mca.project.repository;

import com.mca.project.model.Dom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainRepo extends JpaRepository<Dom, Long> {
    Dom findByName(String name);
}
