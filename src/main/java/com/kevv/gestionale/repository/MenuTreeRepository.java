package com.kevv.gestionale.repository;



import com.kevv.gestionale.model.MenuTree;
import com.kevv.gestionale.model.MenuTreeId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuTreeRepository extends JpaRepository<MenuTree, MenuTreeId> {


    List<MenuTree> findById_Parent_Code(String parentCode);


    @Transactional
    void deleteById_Applmenu_Code(String applMenuCode);

    @Query(value = "SELECT * FROM menu_tree WHERE parent = :parentCode", nativeQuery = true)
    List<MenuTree> findByParent(@Param("parentCode") String parentCode);



}
