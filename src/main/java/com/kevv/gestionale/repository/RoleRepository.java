package com.kevv.gestionale.repository;

import com.kevv.gestionale.model.Role;
import com.kevv.gestionale.model.User;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface RoleRepository extends JpaRepository<Role, String > {

}
