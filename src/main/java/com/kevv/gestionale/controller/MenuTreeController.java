package com.kevv.gestionale.controller;


import com.kevv.gestionale.model.MenuTree;
import com.kevv.gestionale.repository.MenuTreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Menu_tree")
public class MenuTreeController {


    private final MenuTreeRepository menuTreeRepository;

    @Autowired
    public MenuTreeController(MenuTreeRepository menuTreeRepository) {
        this.menuTreeRepository = menuTreeRepository;
    }


    @GetMapping
    public List<MenuTree> getAllMenuTree() {
        return menuTreeRepository.findAll();
    }

//    @GetMapping("/submenu/{parentCode}")
//    public List<MenuTree> getSubMenusByParentCode(@PathVariable String parentCode) {
//        return menuTreeRepository.findById_Parent_Code(parentCode);
//    }

    @DeleteMapping("/{applMenuCode}")
    public ResponseEntity<Void> deleteMenu(@PathVariable String applMenuCode) {
        menuTreeRepository.deleteById_Applmenu_Code(applMenuCode);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/parent/{parentCode}")
    public List<MenuTree> getMenusByParent(@PathVariable String parentCode) {
        return menuTreeRepository.findById_Parent_Code(parentCode);
    }



}