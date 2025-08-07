package com.kevv.gestionale.repository;


import com.kevv.gestionale.model.ApplTableparam;
import com.kevv.gestionale.model.ApplTableparamId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplTableparamRepository extends JpaRepository<ApplTableparam, ApplTableparamId> {

        List<ApplTableparam> findById_Appl(String appl);


        List<ApplTableparam> findById_ApplAndId_Webctx(String appl, String webctx);

    @Query("SELECT a FROM ApplTableparam a JOIN FETCH a.webctxTableparam WHERE a.id.appl = :appl")
    List<ApplTableparam> findByApplWithWebctxData(@Param("appl") String appl);

    @Query("SELECT a FROM ApplTableparam a JOIN FETCH a.webctxTableparam WHERE a.id.appl = :appl AND a.id.webctx = :webctx")
    List<ApplTableparam> findByApplAndWebctxWithWebctxData(@Param("appl") String appl, @Param("webctx") String webctx);
}

