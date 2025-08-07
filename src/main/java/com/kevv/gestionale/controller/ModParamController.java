package com.kevv.gestionale.controller;

import com.kevv.gestionale.model.ModParam;
import com.kevv.gestionale.model.ModParamId;
import com.kevv.gestionale.repository.ModParamRepository;
import com.kevv.gestionale.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/modparam")

public class ModParamController {

 @Autowired
 private ModParamRepository repository;

 @Autowired
 private ModuleRepository moduleRepository;

 @GetMapping
    public List<ModParam> getAll() {
        return repository.findAll();
 }

 @GetMapping("/{module}/{param}")
    public ResponseEntity<Optional<ModParam>> getById(@PathVariable String module, @PathVariable String param) {
     ModParamId id = new ModParamId();

     id.setModule(module);
     id.setParam(param);

     return ResponseEntity.ok().body(repository.findById(id));
 }

    @PutMapping("/{module}/{param}")
    public ResponseEntity<ModParam> update(
            @PathVariable String module,
            @PathVariable String param,
            @RequestBody ModParam modParam) {

        ModParamId id = new ModParamId(module, param);

        Optional<ModParam> existingModParamOptional = repository.findById(id);

        if (existingModParamOptional.isPresent()) {
            ModParam paramToUpdate = existingModParamOptional.get();

            paramToUpdate.setDescr(modParam.getDescr()); // Aggiorna la descrizione
            paramToUpdate.setValue(modParam.getValue()); // Aggiorna il valore

            return ResponseEntity.ok().body(repository.save(paramToUpdate));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

 @DeleteMapping("/{module}/{param}")
    public void delete(@PathVariable String module, @PathVariable String param) {
        ModParamId id = new ModParamId();
        id.setParam(param);
        id.setModule(module);
        repository.deleteById(id);
 }

 @GetMapping("/{module}")
    public ResponseEntity<List<ModParam>> moduleParam(@PathVariable String module) {

     return ResponseEntity.ok().body(repository.findByModule(moduleRepository.findById(module).get()));

 }


}
