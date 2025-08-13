package com.kevv.gestionale.repository;

import com.kevv.gestionale.model.ApplMenuUserIn;
import com.kevv.gestionale.model.ApplMenuUserKeyId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ApplMenuUserInRepository extends JpaRepository<ApplMenuUserIn, ApplMenuUserKeyId> {

    List<ApplMenuUserIn> findByUserUserid(String userid);

    List<ApplMenuUserIn> findByApplMenuCode(String applMenuCode);


    // metodo per cancellare tutte le assegnazioni di una applicazione
    @Modifying
    @Transactional
    void deleteByApplMenuCode(String applMenuCode);
}