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

    List<Compy> findByConn(Connection conn);

    List<Compy> findById_Comp(String compId);
}