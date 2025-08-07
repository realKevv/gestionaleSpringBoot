package com.kevv.gestionale.repository;


import com.kevv.gestionale.model.WebctxGenParam;
import com.kevv.gestionale.model.WebctxGenParamId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebctxGenParamRepository extends JpaRepository<WebctxGenParam, WebctxGenParamId> {

    //metodi jpa


}
