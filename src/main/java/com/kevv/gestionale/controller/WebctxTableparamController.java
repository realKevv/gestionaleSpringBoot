package com.kevv.gestionale.controller;


import com.kevv.gestionale.model.WebctxGenParamId;
import com.kevv.gestionale.model.WebctxTableparam;
import com.kevv.gestionale.repository.WebctxTableparamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/webctx-Tableparam")
public class WebctxTableparamController {

        @Autowired
        private WebctxTableparamRepository repository;

    @GetMapping
    public List<WebctxTableparam> getAllParams() {
            return repository.findAll();
        }



    @GetMapping("/{webctx}/{param}")
    public ResponseEntity<WebctxTableparam> getParamById(@PathVariable String webctx, @PathVariable String param) {
        WebctxGenParamId id = new WebctxGenParamId(webctx, param);

        return repository.findById(id)
                .map(parametro-> ResponseEntity.ok(parametro))
                .orElse(ResponseEntity.notFound().build());
    }



    @GetMapping("/by-webctx/{webctx}")
    public List<WebctxTableparam> getParamsByWebctx(@PathVariable String webctx) {
        return repository.findById_Webctx(webctx);
    }

    @PostMapping
    public WebctxTableparam createParam(@RequestBody WebctxTableparam newParam) {
        return repository.save(newParam);
    }






}






