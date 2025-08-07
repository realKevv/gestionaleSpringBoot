package com.kevv.gestionale.repository;


import com.kevv.gestionale.model.ApplEnv;
import com.kevv.gestionale.model.ApplEnvId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplEnvRepository extends JpaRepository<ApplEnv,ApplEnvId> {

    Optional<ApplEnv> findByParam(String param);

    List<ApplEnv> findByAppl(String appl);
}
