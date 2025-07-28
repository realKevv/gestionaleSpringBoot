package com.kevv.gestionale.repository;

import com.kevv.gestionale.model.GenParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenParamRepository extends JpaRepository<GenParam, String> {
    // Qui puoi aggiungere query personalizzate se vuoi
}
