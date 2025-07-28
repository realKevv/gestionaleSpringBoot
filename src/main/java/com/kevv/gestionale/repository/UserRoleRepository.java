package com.kevv.gestionale.repository;

import com.kevv.gestionale.model.UserRole;
import com.kevv.gestionale.model.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    List<UserRole> findByIdRoleid(String roleid);

    // Cancella tutte le UserRole dove la parte roleid della chiave composta è uguale a quella passata
    void deleteByIdRoleid(String roleid);
}