package com.kevv.gestionale.repository;


import com.kevv.gestionale.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, String> {


    List<Module> findByRefModIsNull();


}
