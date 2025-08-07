package com.kevv.gestionale.controller;

import com.kevv.gestionale.model.ApplTableparam;
import com.kevv.gestionale.model.ApplTableparamId;
import com.kevv.gestionale.repository.ApplTableparamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/appl-tableparam")
public class ApplTableparamController {

    @Autowired
    private ApplTableparamRepository repository;

    // Ottieni tutti i parametri
    @GetMapping
    public List<ApplTableparam> getAllParams() {
        return repository.findAll();
    }

    // Ottieni un parametro specifico per appl, webctx e param
    @GetMapping("/{appl}/{webctx}/{param}")
    public ResponseEntity<ApplTableparam> getParamById(@PathVariable String appl,
                                                       @PathVariable String webctx,
                                                       @PathVariable String param) {
        ApplTableparamId id = new ApplTableparamId(appl, webctx, param);

        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Ottieni tutti i parametri per una specifica applicazione
    @GetMapping("/by-appl/{appl}")
    public List<ApplTableparam> getParamsByAppl(@PathVariable String appl) {
        return repository.findById_Appl(appl);
    }

    // Ottieni tutti i parametri per una specifica applicazione e webctx
    @GetMapping("/by-appl/{appl}/webctx/{webctx}")
    public List<ApplTableparam> getParamsByApplAndWebctx(@PathVariable String appl,
                                                         @PathVariable String webctx) {
        return repository.findById_ApplAndId_Webctx(appl, webctx);
    }

    // Crea un nuovo parametro
    @PostMapping
    public ApplTableparam createParam(@RequestBody ApplTableparam newParam) {
        return repository.save(newParam);
    }

    // Aggiorna il valore di un parametro esistente
    @PutMapping("/{appl}/{webctx}/{param}")
    public ResponseEntity<ApplTableparam> updateParamValue(@PathVariable String appl,
                                                           @PathVariable String webctx,
                                                           @PathVariable String param,
                                                           @RequestBody ApplTableparam updatedParam) {
        ApplTableparamId id = new ApplTableparamId(appl, webctx, param);

        return repository.findById(id)
                .map(existingParam -> {
                    // Aggiorna solo il valore, come richiesto dall\'utente
                    existingParam.setValue(updatedParam.getValue());
                    ApplTableparam savedParam = repository.save(existingParam);
                    return ResponseEntity.ok(savedParam);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Elimina un parametro
    @DeleteMapping("/{appl}/{webctx}/{param}")
    public ResponseEntity<Void> deleteParam(@PathVariable String appl,
                                            @PathVariable String webctx,
                                            @PathVariable String param) {
        ApplTableparamId id = new ApplTableparamId(appl, webctx, param);

        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}