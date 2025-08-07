package com.kevv.gestionale.controller;

import com.kevv.gestionale.model.Compy;
import com.kevv.gestionale.model.CompyId;
import com.kevv.gestionale.model.Connection; // Assicurati che questo import sia presente
import com.kevv.gestionale.repository.CompyRepository;
import com.kevv.gestionale.repository.ConnectionRepository; // <-- NUOVO: Importa il ConnectionRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/compy")
@CrossOrigin(origins = "*")
public class CompyController {

    @Autowired
    private CompyRepository compyRepository;

    @Autowired
    private ConnectionRepository connectionRepository; // <-- NUOVO: Inietta il ConnectionRepository

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

    @PostMapping // Questo endpoint è meno comune per Compy con ID composito, ma lo manteniamo
    public Compy create(@RequestBody Compy compy) {
        // Per un POST, l'ID composito e l'oggetto Connection (o il suo ID) dovrebbero essere nel corpo della richiesta
        if (compy.getId() == null || compy.getId().getComp() == null || compy.getId().getYear() == null || compy.getConn() == null || compy.getConn().getConnid() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Compy ID (comp, year) and Connection ID (connid) are required for creation.");
        }

        // Recupera l'oggetto Connection completo dal database usando il connid fornito
        Connection connection = connectionRepository.findById(compy.getConn().getConnid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Connection not found with ID: " + compy.getConn().getConnid()));

        compy.setConn(connection); // Imposta l'oggetto Connection gestito
        return compyRepository.save(compy);
    }

    @PutMapping("/{comp}/{year}")
    public ResponseEntity<Compy> updateOrCreate(@PathVariable String comp, @PathVariable String year, @RequestBody Compy updatedCompy) {
        CompyId newId = new CompyId();
        newId.setComp(comp);
        newId.setYear(year);

        // 1. Verifica che l'ID nel path corrisponda all'ID nel corpo della richiesta
        if (!newId.equals(updatedCompy.getId())) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }

        // 2. Verifica che il connid sia fornito nel corpo della richiesta (tramite l'oggetto conn)
        if (updatedCompy.getConn() == null || updatedCompy.getConn().getConnid() == null || updatedCompy.getConn().getConnid().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Connection ID (connid) is required.");
        }

        // 3. Recupera l'oggetto Connection completo dal database usando il connid fornito
        Connection connection = connectionRepository.findById(updatedCompy.getConn().getConnid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Connection not found with ID: " + updatedCompy.getConn().getConnid()));

        // 4. Cerca l'entità esistente nel database
        return compyRepository.findById(newId)
                .map(existingCompy -> {
                    // Se l'entità esiste, aggiorna il suo oggetto Connection
                    existingCompy.setConn(connection); // <-- MODIFICATO: Usa setConn()
                    // Se ci fossero altri campi in Compy, aggiornali qui
                    // existingCompy.setOtherField(updatedCompy.getOtherField());
                    return ResponseEntity.ok(compyRepository.save(existingCompy)); // Restituisce 200 OK
                })
                .orElseGet(() -> {
                    // Se l'entità non esiste, crea una nuova
                    updatedCompy.setId(newId); // Imposta l'ID composito dalla URL
                    updatedCompy.setConn(connection); // <-- MODIFICATO: Imposta l'oggetto Connection gestito
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
