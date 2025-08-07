package com.kevv.gestionale.repository;

import com.kevv.gestionale.model.WebctxGenParamId;
import com.kevv.gestionale.model.WebctxParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebctxParamRepository extends JpaRepository<WebctxParam, WebctxGenParamId> {




}

