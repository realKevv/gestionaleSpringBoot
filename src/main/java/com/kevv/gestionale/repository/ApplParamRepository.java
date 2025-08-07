package com.kevv.gestionale.repository;

import com.kevv.gestionale.model.ApplParam;
import com.kevv.gestionale.model.ApplParamId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository per l'entità ApplParam, che utilizza la chiave composta ApplParamId.
 */
@Repository
public interface ApplParamRepository extends JpaRepository<ApplParam, ApplParamId> {

    @Query("SELECT ap FROM ApplParam ap LEFT JOIN FETCH ap.webctxParam")
    List<ApplParam> findAllWithRelations();

    /**
     * Trova un parametro specifico con la relazione WebctxParam caricata
     */
    @Query("SELECT ap FROM ApplParam ap LEFT JOIN FETCH ap.webctxParam " +
            "WHERE ap.appl = :appl AND ap.webctx = :webctx AND ap.param = :param")
    Optional<ApplParam> findByIdWithRelations(@Param("appl") String appl,
                                              @Param("webctx") String webctx,
                                              @Param("param") String param);
}
