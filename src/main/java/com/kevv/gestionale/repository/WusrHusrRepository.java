package com.kevv.gestionale.repository;

import com.kevv.gestionale.model.User;
import com.kevv.gestionale.model.WusrHusr;
import com.kevv.gestionale.model.WusrHusrId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WusrHusrRepository extends JpaRepository<WusrHusr, WusrHusrId> {


    List<WusrHusr> findByWusr(User wusr);

    List<WusrHusr> findByWusr_Userid(String userid);
}
