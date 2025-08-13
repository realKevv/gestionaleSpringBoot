package com.kevv.gestionale.repository;

import com.kevv.gestionale.model.ApplMenuUserIn;
import com.kevv.gestionale.model.ApplMenuUserKeyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository

public interface ApplMenuUserInRepository extends JpaRepository<ApplMenuUserIn, ApplMenuUserKeyId> {

    List<ApplMenuUserIn> findByUser(String user);

    List<ApplMenuUserIn> findByApplmenu(String applmenu);
}