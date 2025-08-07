package com.kevv.gestionale.repository;


import com.kevv.gestionale.model.WebctxGenParamId;
import com.kevv.gestionale.model.WebctxTableParamId;
import com.kevv.gestionale.model.WebctxTableparam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebctxTableparamRepository extends JpaRepository<WebctxTableparam, WebctxTableParamId> {


    boolean existsById(WebctxGenParamId id);

    List<WebctxTableparam> findById_Webctx(String webctx);
}
