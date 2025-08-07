package com.kevv.gestionale.repository;

import com.kevv.gestionale.model.UserCompy;
import com.kevv.gestionale.model.UserCompyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCompyRepository extends JpaRepository<UserCompy, UserCompyId> { // <-- CAMBIATO DA 'class' A 'interface'

    List<UserCompy> findByIdCompAndIdYear(String comp, String year); // <-- RIMOSSO IL CORPO DEL METODO
}
