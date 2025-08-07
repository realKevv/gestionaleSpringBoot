package com.kevv.gestionale.controller;

import com.kevv.gestionale.model.ApplEnv;
import com.kevv.gestionale.model.ApplEnvId;
import com.kevv.gestionale.model.ApplMenu;
import com.kevv.gestionale.model.ApplTableparamId;
import com.kevv.gestionale.repository.ApplEnvRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/appl_env")
public class ApplEnvController {

    private final ApplEnvRepository applEnvRepository;

    @Autowired
    public ApplEnvController(ApplEnvRepository applEnvRepository) {
        this.applEnvRepository = applEnvRepository;
    }

    @GetMapping
    public List<ApplEnv> getAllApplEnv() {
        return applEnvRepository.findAll();
    }

    // Nuovo endpoint per recuperare le variabili di ambiente per un'applicazione specifica
    @GetMapping("/{appl}")
    public List<ApplEnv> getApplEnvByAppl(@PathVariable String appl) {
        return applEnvRepository.findByAppl(appl);
    }


    @PostMapping
    public ResponseEntity<ApplEnv> createApplEnv(@RequestBody ApplEnv applEnv) {
        ApplEnv newApplEnv = applEnvRepository.save(applEnv);
        return new ResponseEntity<>(newApplEnv, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ApplEnv> updateApplEnv(@RequestBody ApplEnv updateApplEnv) {
        ApplEnvId id = new ApplEnvId(updateApplEnv.getAppl(), updateApplEnv.getWebctx(), updateApplEnv.getParam());
        Optional<ApplEnv> existingApplEnv = applEnvRepository.findById(id);

        if (existingApplEnv.isPresent()) {
            ApplEnv applEnvToUpdate = existingApplEnv.get();
            applEnvToUpdate.setValue(updateApplEnv.getValue());
            applEnvToUpdate.setDescr(updateApplEnv.getDescr());
            ApplEnv savedApplEnv = applEnvRepository.save(applEnvToUpdate);
            return new ResponseEntity<>(savedApplEnv, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{param}")
    public ResponseEntity<Void> removeParam(@PathVariable String param) {
        Optional<ApplEnv> applEnvToDelete = applEnvRepository.findByParam(param);

        if (applEnvToDelete.isPresent()) {
            applEnvToDelete.get().setAppl(applEnvToDelete.get().getAppl());
            applEnvToDelete.get().setWebctx(applEnvToDelete.get().getWebctx());
            applEnvRepository.delete(applEnvToDelete.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
