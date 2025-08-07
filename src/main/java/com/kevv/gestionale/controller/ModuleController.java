package com.kevv.gestionale.controller;


import com.kevv.gestionale.model.Module;
import com.kevv.gestionale.repository.ModuleRepository;
import jakarta.persistence.Embeddable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/module")
public class ModuleController {

    @Autowired
    private ModuleRepository moduleRepository;


    @GetMapping
    public ResponseEntity<List<Module>> getallModule() {
        return ResponseEntity.ok().body(moduleRepository.findByRefModIsNull());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Module> updateModule(@PathVariable String id, @RequestBody Module module) {
        Optional<Module> existingModule = moduleRepository.findById(id);
        if (existingModule.isPresent()) {
            Module updatedModule = existingModule.get();
            updatedModule.setDescr(updatedModule.getDescr());
            moduleRepository.save(updatedModule);
            return ResponseEntity.ok(updatedModule);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}