package com.mca.project.repository;

import com.mca.project.model.Hperson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HpersonRepo extends JpaRepository<Hperson, Long> {
    Hperson findByEmail(String email);
}
