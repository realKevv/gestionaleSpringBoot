package com.kevv.gestionale.repository;

import com.kevv.gestionale.model.Compy;
import com.kevv.gestionale.model.CompyId;
import com.kevv.gestionale.model.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, String> {

//    @Query("select c from Connection c where c.connid = :connid")
//    Optional<Connection> findByConnid(@Param("connid") String connid);
}
