package com.kevv.gestionale.repository;

import com.kevv.gestionale.model.ModParam;
import com.kevv.gestionale.model.ModParamId;
import com.kevv.gestionale.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModParamRepository extends JpaRepository<ModParam, ModParamId> {


        List<ModParam> findByModule(Module module);
}
