package com.kevv.gestionale.controller;


import com.kevv.gestionale.model.ApplMenu;
import com.kevv.gestionale.repository.ApplMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/applMenu")
@CrossOrigin(origins = "*")


 
public class ApplMenuController {

    private final ApplMenuRepository applMenuRepository;

    @Autowired
    public ApplMenuController(ApplMenuRepository applMenuRepository) {
        this.applMenuRepository = applMenuRepository;
    }

    @GetMapping
    public List<ApplMenu> getallApplMenus() {
        return applMenuRepository.findAll();
    }

    @GetMapping("/applications")
    public List<ApplMenu> getAllApplications() {
        return applMenuRepository.findByType("A");
    }


    @GetMapping("/{code}")
    public ResponseEntity<ApplMenu> GetApplMenuCode(@PathVariable String code) {
        Optional<ApplMenu> applMenu = applMenuRepository.findById(code);
        return applMenu.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()
                );
    }

    @PostMapping
    public ResponseEntity<ApplMenu> createApplMenu(@RequestBody ApplMenu newMenu) {
        try {
            ApplMenu savedMenu = applMenuRepository.save(newMenu);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMenu);
        } catch (Exception e) {
            System.err.println("Errore durante il salvataggio del menu: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{code}")
    public ResponseEntity<ApplMenu> updateApplMenu(@PathVariable String code, @RequestBody ApplMenu updateMenu) {
        if (!code.equals(updateMenu.getCode())) {
            return ResponseEntity.badRequest().build();
        }
        if (!applMenuRepository.existsById(code)) {
            return ResponseEntity.notFound().build();
        }


        try {
            ApplMenu saveMenu = applMenuRepository.save(updateMenu);
            return ResponseEntity.ok(saveMenu);
        } catch (Exception e) {
            System.err.println("Errore durante l\'aggiornamento:  " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteApplMenu(@PathVariable String code) {
        if (!applMenuRepository.existsById(code)) {
            return ResponseEntity.notFound().build();
        }
        try {
            applMenuRepository.deleteById(code);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            System.err.println("Errore durante l\'eliminazione: " + e.getMessage());
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{code}/duplicate")
    public ResponseEntity<ApplMenu> duplicateApplMenu(@PathVariable String code, @RequestBody String newCode) {
        Optional<ApplMenu> originalMenuOpt = applMenuRepository.findById(code);
        if(originalMenuOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ApplMenu originalMenu = originalMenuOpt.get();
        if (!"A".equals(originalMenu.getType())) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }
        if(applMenuRepository.existsById(newCode)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        ApplMenu duplicatedMenu= new ApplMenu(
                newCode,
                originalMenu.getDescr() + "  (copia)",
                originalMenu.getType(),
                originalMenu.isWeb(),
                originalMenu.getWebCtx(),
                originalMenu.getAliasMod(),
                originalMenu.getMaxRow(),
                originalMenu.getMaxCol(),
                originalMenu.isStandard()
        );
        try {
            ApplMenu savedDuplicateMenu = applMenuRepository.save(duplicatedMenu);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDuplicateMenu);
        }   catch(Exception e) {
            System.err.println("errore durante la duplicazione:  " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

