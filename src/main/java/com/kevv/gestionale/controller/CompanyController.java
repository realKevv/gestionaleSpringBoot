package com.kevv.gestionale.controller;

import com.kevv.gestionale.model.Company;
import com.kevv.gestionale.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/companies")
@CrossOrigin(origins = "*")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;


    @GetMapping
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @GetMapping("/{compid}")
    public Optional<Company> getCompanyById(@PathVariable String compid) {
        return companyRepository.findById(compid);
    }

    @PutMapping("/{compid}")
    public Company updateCompany(@PathVariable String compid, @RequestBody Company updatedCompany) {
        return companyRepository.findById(compid)
                .map(existingCompany -> {
                    existingCompany.setDescr(updatedCompany.getDescr());
                    return companyRepository.save(existingCompany);
                })
                .orElseThrow(() -> new RuntimeException("Azienda non trovata con compid: " + compid));
    }

    @PostMapping
    public ResponseEntity<Company> createNewCompany(@RequestBody Company newCompany) {
        if (newCompany.getCompid() != null && companyRepository.existsById(newCompany.getCompid())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Company savedCompany = companyRepository.save(newCompany);
        return new ResponseEntity<>(savedCompany, HttpStatus.CREATED);
    }
}

