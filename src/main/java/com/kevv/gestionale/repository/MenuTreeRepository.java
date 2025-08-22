package com.kevv.gestionale.repository;

import com.kevv.gestionale.model.MenuTree;
import com.kevv.gestionale.model.MenuTreeId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuTreeRepository extends JpaRepository<MenuTree, MenuTreeId> {

    // Tutti i figli di un parent
    List<MenuTree> findById_Parent_Code(String parentCode);

    // Controllo se un menu ha figli (isFolder)
    boolean existsById_Parent_Code(String parentCode);

    // Cancella un menu
    @Transactional
    void deleteById_Applmenu_Code(String applMenuCode);

    // Recupera un menu tramite applmenu.code
    @Query("SELECT m FROM MenuTree m WHERE m.id.applmenu.code = :code")
    Optional<MenuTree> findByApplmenuCode(@Param("code") String code);
}
