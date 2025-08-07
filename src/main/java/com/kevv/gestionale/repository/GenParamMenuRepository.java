package com.kevv.gestionale.repository;


import com.kevv.gestionale.model.GenParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenParamMenuRepository extends JpaRepository<GenParam, String> {


}
