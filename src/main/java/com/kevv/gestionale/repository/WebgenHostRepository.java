package com.kevv.gestionale.repository;
import aj.org.objectweb.asm.commons.Remapper;
import com.kevv.gestionale.model.User;
import com.kevv.gestionale.model.WebgenHost;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WebgenHostRepository extends JpaRepository<WebgenHost, String> {
    Optional<WebgenHost> findByUser(User user);
//    Optional<WebgenHost> findByUser(User user);



}