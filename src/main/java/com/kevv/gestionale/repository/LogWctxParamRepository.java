package com.kevv.gestionale.repository;

import com.kevv.gestionale.model.LogWctxParam;
import com.kevv.gestionale.model.LogWctxParamId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Repository
public interface LogWctxParamRepository  extends JpaRepository<LogWctxParam, LogWctxParamId>{

    @Query("SELECT l FROM LogWctxParam l WHERE "
            + "(:webctx IS NULL OR l.id.webctx = :webctx) "
            + "AND (:param IS NULL OR l.id.param = :param) "
            + "AND (:type IS NULL OR l.id.type = :type) "
            + "AND (:action IS NULL OR l.id.action = :action) "
            + "AND ("
            + "    (:startDate IS NULL OR l.id.date >= :startDate) "
            + "    AND (:endDate IS NULL OR l.id.date <= :endDate)"
            + ") "
            + "AND (:time IS NULL OR l.id.time = :time)")

    List<LogWctxParam> search(
            @Param("webctx") String webctx,
            @Param("param") String param,
            @Param("type") String type,
            @Param("action") String action,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("time") Time time
    );


    @Query("SELECT DISTINCT l.id.webctx FROM LogWctxParam l")
    List<String> findDistinctWebctx();

    @Query("SELECT DISTINCT l.id.type FROM LogWctxParam l")
    List<String> findDistinctTypeParam();

    @Query("SELECT DISTINCT l.id.action FROM LogWctxParam l")
    List<String> findDistinctAction();
}