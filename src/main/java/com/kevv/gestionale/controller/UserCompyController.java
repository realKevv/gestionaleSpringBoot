package com.kevv.gestionale.controller;

import com.kevv.gestionale.model.Compy;
import com.kevv.gestionale.model.CompyId;
import com.kevv.gestionale.model.User;
import com.kevv.gestionale.model.UserCompy;
import com.kevv.gestionale.model.UserCompyId;
import com.kevv.gestionale.repository.CompyRepository;
import com.kevv.gestionale.repository.UserRepository;
import com.kevv.gestionale.repository.UserCompyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin(origins = "*")
public class UserCompyController {

    @Autowired
    private UserCompyRepository userCompyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompyRepository compyRepository;


    @GetMapping("/{comp}/{year}/assigned-users")
    public ResponseEntity<List<User>> getAssignedUsers(@PathVariable String comp, @PathVariable String year) {
        List<UserCompy> userCompies = userCompyRepository.findByIdCompAndIdYear(comp, year);

        List<User> assignedUsers = userCompies.stream()
                .map(UserCompy::getUser)
                .collect(Collectors.toList());

        return ResponseEntity.ok(assignedUsers);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/{comp}/{year}/assign-user/{userId}")
    public ResponseEntity<UserCompy> assignUserToCompany(
            @PathVariable String comp,
            @PathVariable String year,
            @PathVariable String userId) { // userId è String, coerente con il tuo modello User

        // 1. Verifica se l'azienda (Compy) esiste
        CompyId compyId = new CompyId(comp, year);
        Compy company = compyRepository.findById(compyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Azienda non trovata con comp: " + comp + " e year: " + year));

        // 2. Verifica se l'utente esiste
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato con ID: " + userId));

        UserCompyId userCompyId = new UserCompyId();
        userCompyId.setUserid(userId);
        userCompyId.setComp(comp);
        userCompyId.setYear(year);

        if (userCompyRepository.existsById(userCompyId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "L'utente è già assegnato a questa azienda per l'anno specificato.");
        }

        // 5. Crea e salva la nuova associazione
        UserCompy userCompy = new UserCompy();
        userCompy.setId(userCompyId);
        userCompy.setUser(user);
        userCompy.setCompany(company);

        UserCompy savedUserCompy = userCompyRepository.save(userCompy);
        return new ResponseEntity<>(savedUserCompy, HttpStatus.CREATED); // Restituisce 201 CREATED
    }

    @DeleteMapping("/{comp}/{year}/unassign-user/{userId}")
    public ResponseEntity<Void> unassignUserFromCompany(
            @PathVariable String comp,
            @PathVariable String year,
            @PathVariable String userId) { // userId è String

        UserCompyId userCompyId = new UserCompyId();
        userCompyId.setUserid(userId);
        userCompyId.setComp(comp);
        userCompyId.setYear(year);

        if (!userCompyRepository.existsById(userCompyId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Assegnazione non trovata per utente " + userId + " e azienda " + comp + "/" + year);
        }

        userCompyRepository.deleteById(userCompyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
    }
}
