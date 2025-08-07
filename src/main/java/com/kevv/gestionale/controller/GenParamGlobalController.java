package com.kevv.gestionale.controller;


import com.kevv.gestionale.model.GenParam;
import com.kevv.gestionale.repository.GenParamMenuRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;


@RestController
@RequestMapping("/gen-param")
public class GenParamGlobalController {


        @Autowired
    private GenParamMenuRepository genParamMenuRepository;



        @GetMapping
    public List<GenParam> getAllGenParams() {
            return genParamMenuRepository.findAll();
        }



        @GetMapping("/{param}")
    public ResponseEntity<GenParam> getGenParamById(@PathVariable String param) {
            Optional<GenParam> genParam = genParamMenuRepository.findById(param);
            return genParam.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }


    @PutMapping("/{param}")
    public ResponseEntity<GenParam> updateGenParam(@PathVariable String param, @RequestBody GenParam updatedGenParam) {
        Optional<GenParam> existingGenParam = genParamMenuRepository.findById(param);

        if (existingGenParam.isPresent()) {
            GenParam genParam = existingGenParam.get();
            genParam.setDescr(updatedGenParam.getDescr());
            genParam.setRegexpr(updatedGenParam.getRegexpr());
            genParam.setTip(updatedGenParam.getTip());
            genParam.setValue(updatedGenParam.getValue());
            return ResponseEntity.ok(genParamMenuRepository.save(genParam));
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping
    public GenParam createGenParam(@RequestBody GenParam newGenParam) {
            return genParamMenuRepository.save(newGenParam);
    }
}