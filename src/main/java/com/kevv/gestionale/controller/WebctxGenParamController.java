package com.kevv.gestionale.controller;


import com.kevv.gestionale.model.WebctxGenParam;
import com.kevv.gestionale.model.WebctxGenParamId;
import com.kevv.gestionale.repository.WebctxGenParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/webctx-genparam")


public class WebctxGenParamController {

    @Autowired
    private WebctxGenParamRepository webctxGenParamRepository;

    @GetMapping
    public List<WebctxGenParam> findAll(@RequestParam(required = false) String webctx) {
        if (webctx != null && !webctx.trim().isEmpty()) {
            // Se webctx è fornito, filtra i parametri per quel contesto
            return webctxGenParamRepository.findAll()
                    .stream()
                    .filter(p -> webctx.equals(p.getId().getWebctx()))
                    .collect(Collectors.toList());
        }
        // Altrimenti, restituisce tutti i parametri
        return webctxGenParamRepository.findAll();
    }


    @GetMapping("/{webctx}/{param}")
    public ResponseEntity<WebctxGenParam> getWebctxGenParamById(@PathVariable String webctx, @PathVariable String param) {
        WebctxGenParamId id = new WebctxGenParamId(webctx, param);
        Optional<WebctxGenParam> webctxGenParam = webctxGenParamRepository.findById(id);
        if (webctxGenParam.isPresent()) {
            return ResponseEntity.ok(webctxGenParam.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

