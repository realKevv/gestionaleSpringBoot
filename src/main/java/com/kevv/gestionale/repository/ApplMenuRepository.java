package com.kevv.gestionale.repository;

import com.kevv.gestionale.model.ApplMenu;
import com.kevv.gestionale.model.ApplMenuUserKeyId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplMenuRepository extends JpaRepository<ApplMenu, String> {

    List<ApplMenu> findByType(String type);
}