package com.mca.project.repository;

import com.mca.project.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepo extends JpaRepository<Person,Long> {
    
    List<Person> findBySubdomainId(Long subdomainId);

    Person findByEmail(String email);
}


