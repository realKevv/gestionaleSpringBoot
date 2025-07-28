package com.kevv.gestionale.controller;

import com.kevv.gestionale.model.GenParam;
import com.kevv.gestionale.repository.GenParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.DriverManager;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/genparams")
@CrossOrigin(origins = "*")
public class GenParamController {

    @Autowired
    private GenParamRepository genParamRepository;

    // GET /genparams -> prendi tutti i parametri
    @GetMapping
    public List<GenParam> getAllGenParams() {
     //   DriverManager
        return genParamRepository.findAll();
    }

    // GET /genparams/{param} -> prendi un parametro per id
    @GetMapping("/{param}")
    public Optional<GenParam> getGenParamById(@PathVariable String param) {
        return genParamRepository.findById(param);
    }

    // POST /genparams -> crea un nuovo parametro
    @PostMapping
    public GenParam createGenParam(@RequestBody GenParam genParam) {
        return genParamRepository.save(genParam);
    }

    // PUT /genparams/{param} -> aggiorna un parametro esistente
    @PutMapping("/{param}")
    public GenParam updateGenParam(@PathVariable String param, @RequestBody GenParam genParamDetails) {
        return genParamRepository.findById(param)
                .map(genParam -> {
                    genParam.setDescr(genParamDetails.getDescr());
                    genParam.setRegexpr(genParamDetails.getRegexpr());
                    genParam.setTip(genParamDetails.getTip());
                    genParam.setValue(genParamDetails.getValue());
                    return genParamRepository.save(genParam);
                })
                .orElseGet(() -> {
                    genParamDetails.setParam(param);
                    return genParamRepository.save(genParamDetails);
                });
    }

    // DELETE /genparams/{param} -> elimina un parametro
    @DeleteMapping("/{param}")
    public void deleteGenParam(@PathVariable String param) {
        genParamRepository.deleteById(param);
    }
}
