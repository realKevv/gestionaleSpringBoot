package com.kevv.gestionale.repository;

import com.kevv.gestionale.model.Compy;

import com.kevv.gestionale.model.Compy;
import com.kevv.gestionale.model.CompyId;
import com.kevv.gestionale.model.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompyRepository extends JpaRepository<Compy, CompyId> {

    Optional<Compy> findByConn(Connection conn);

    // QUESTO È IL METODO CORRETTO per cercare per la parte 'comp' della chiave composita
    List<Compy> findById_Comp(String compId); // <-- **USA QUESTO NOME DI METODO**
}