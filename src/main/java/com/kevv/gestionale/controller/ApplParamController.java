package com.kevv.gestionale.controller;

import com.kevv.gestionale.model.ApplParam;
import com.kevv.gestionale.model.ApplParamId;
import com.kevv.gestionale.repository.ApplParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("menu/appl-params")
public class ApplParamController {

    private final ApplParamRepository applParamRepository;

    @Autowired
    public ApplParamController(ApplParamRepository applParamRepository) {
        this.applParamRepository = applParamRepository;
    }

    @GetMapping
    public List<ApplParam> getAllApplParams() {
        return applParamRepository.findAllWithRelations();
    }

    @GetMapping("/{appl}/{webctx}/{param}")
    public ResponseEntity<ApplParam> getApplParamById(
            @PathVariable String appl,
            @PathVariable String webctx,
            @PathVariable String param) {

        Optional<ApplParam> applParam = applParamRepository.findByIdWithRelations(appl, webctx, param);

        return applParam.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ApplParam> createApplParam(@RequestBody ApplParam applParam) {
        ApplParam savedApplParam = applParamRepository.save(applParam);

        // Ricarica con le relazioni per la risposta completa
        Optional<ApplParam> reloadedParam = applParamRepository.findByIdWithRelations(
                savedApplParam.getAppl(),
                savedApplParam.getWebctx(),
                savedApplParam.getParam()
        );

        return new ResponseEntity<>(
                reloadedParam.orElse(savedApplParam),
                HttpStatus.CREATED
        );
    }
    @PutMapping("/{appl}/{webctx}/{param}")
    public ResponseEntity<ApplParam> updateApplParam(
            @PathVariable String appl,
            @PathVariable String webctx,
            @PathVariable String param,
            @RequestBody ApplParam applParamDetails) {

        ApplParamId id = new ApplParamId(appl, webctx, param);
        Optional<ApplParam> existingApplParam = applParamRepository.findById(id);

        if (existingApplParam.isPresent()) {
            ApplParam updatedApplParam = existingApplParam.get();

            // Aggiorna SOLO il valore - le chiavi primarie e altri campi rimangono invariati
            updatedApplParam.setValue(applParamDetails.getValue());

            applParamRepository.save(updatedApplParam);

            // Restituisce il parametro aggiornato con tutte le relazioni
            Optional<ApplParam> result = applParamRepository.findByIdWithRelations(appl, webctx, param);
            return ResponseEntity.ok(result.orElse(updatedApplParam));

        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{appl}/{webctx}/{param}")
    public ResponseEntity<Void> deleteApplParam(
            @PathVariable String appl,
            @PathVariable String webctx,
            @PathVariable String param) {

        ApplParamId id = new ApplParamId(appl, webctx, param);
        if (applParamRepository.existsById(id)) {
            applParamRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}