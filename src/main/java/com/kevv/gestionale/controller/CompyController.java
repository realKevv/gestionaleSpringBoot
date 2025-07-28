package com.kevv.gestionale.controller;

import com.kevv.gestionale.model.Compy;
import com.kevv.gestionale.model.CompyId;
import com.kevv.gestionale.model.Connection; // Assicurati di importare Connection
import com.kevv.gestionale.repository.CompyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/compy")
@CrossOrigin(origins = "*")
public class CompyController {

    @Autowired
    private CompyRepository compyRepository;

    // --- ENDPOINT GET ALL ---
    // GET /api/compy
    @GetMapping
    public List<Compy> getAll() {
        return compyRepository.findAll();
    }

    // --- ENDPOINT GET BY ID ---
    // GET /api/compy/{comp}/{year}
    @GetMapping("/{comp}/{year}")
    public ResponseEntity<Compy> getById(@PathVariable String comp, @PathVariable String year) {
        CompyId id = new CompyId();
        id.setComp(comp);
        id.setYear(year);

        Optional<Compy> compy = compyRepository.findById(id);
        return compy.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/by-comp/{comp}")
    public ResponseEntity<List<Compy>> getCompiesByComp(@PathVariable String comp) {
        List<Compy> compies = compyRepository.findById_Comp(comp);

        if (compies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(compies, HttpStatus.OK);
    }

    @PostMapping
    public Compy create(@RequestBody Compy compy) {
        return compyRepository.save(compy);
    }

    @PutMapping("/{comp}/{year}")
    public ResponseEntity<Compy> updateOrCreate(@PathVariable String comp, @PathVariable String year, @RequestBody Compy updatedCompy) {
        CompyId newId = new CompyId();
        newId.setComp(comp);
        newId.setYear(year);

        if (!newId.equals(updatedCompy.getId())) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }

        return compyRepository.findById(newId)
                .map(existingCompy -> {

                    return ResponseEntity.ok(compyRepository.save(existingCompy)); // Restituisce 200 OK
                })
                .orElseGet(() -> {
                    updatedCompy.setId(newId);

                    // Salva il nuovo record
                    return new ResponseEntity<>(compyRepository.save(updatedCompy), HttpStatus.CREATED); // Restituisce 201 CREATED
                });
    }


    @DeleteMapping("/{comp}/{year}")
    public ResponseEntity<Void> delete(@PathVariable String comp, @PathVariable String year) {
        CompyId id = new CompyId();
        id.setComp(comp);
        id.setYear(year);

        if (compyRepository.existsById(id)) {
            compyRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // Restituisce 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // Restituisce 404 Not Found
        }
    }
}