package com.kevv.gestionale.repository;

import com.kevv.gestionale.model.GenParam; // Assumendo che esista una classe GenParam
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenParamRepository extends JpaRepository<GenParam, String> {
    // Il repository per i parametri generali
}
