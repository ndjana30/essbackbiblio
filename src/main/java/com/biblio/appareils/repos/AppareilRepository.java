package com.biblio.appareils.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblio.appareils.models.Appareils;

public interface AppareilRepository extends JpaRepository<Appareils, Long> {
    
}
