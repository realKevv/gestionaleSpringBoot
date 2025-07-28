package com.kevv.gestionale.repository;

import com.kevv.gestionale.model.LogLaunch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface LogLaunchRepository extends JpaRepository<LogLaunch, Integer> {

    @Query("SELECT l FROM LogLaunch l " +
            "WHERE l.startTime >= :startTime AND l.startTime <= :endTime " +
            "AND (:applCode IS NULL OR l.applCode = :applCode) " +
            "AND (:type IS NULL OR l.type = :type) " +
            "AND (:webUser IS NULL OR l.webuser = :webUser) " +
            "AND (:fromIp IS NULL OR l.fromIp = :fromIp) " +
            "AND (:hostUser IS NULL OR l.hostuser = :hostUser) " +
            "AND (:company IS NULL OR l.company = :company) " +
            "AND (:year IS NULL OR l.year = :year) " +
            "AND (:fromLogid IS NULL OR l.logid >= :fromLogid)")
    List<LogLaunch> search(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("applCode") String applCode,
            @Param("type") String type,
            @Param("webUser") String webUser,
            @Param("fromIp") String fromIp,
            @Param("hostUser") String hostUser,
            @Param("company") String company,
            @Param("year") String year,
            @Param("fromLogid") Integer fromLogid
    );

    @Query("SELECT DISTINCT l.type FROM LogLaunch l")
    List<String> findDistinctTypes();
}