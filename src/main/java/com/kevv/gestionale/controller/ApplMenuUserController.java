package com.kevv.gestionale.controller;

import com.kevv.gestionale.model.ApplMenu;
import com.kevv.gestionale.model.ApplMenuUserIn;
import com.kevv.gestionale.model.ApplMenuUserKeyId;
import com.kevv.gestionale.model.User;
import com.kevv.gestionale.repository.ApplMenuRepository;
import com.kevv.gestionale.repository.ApplMenuUserInRepository;
import com.kevv.gestionale.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/applmenuUser")
@CrossOrigin(origins = "*")
public class ApplMenuUserController {

    @Autowired
    private ApplMenuUserInRepository applMenuUserInRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplMenuRepository applMenuRepository;

    @Data
    public static class AssignmentRequest {
        private String userId;
        private String applMenuCode;
    }

    // Esistente: assegna singolo con body { userId, applMenuCode }
    @PostMapping("/assign")
    public ResponseEntity<ApplMenuUserIn> assignMenuToUser(@RequestBody AssignmentRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Utente non trovato con ID  "+ request.getUserId()));
        ApplMenu menu = applMenuRepository.findById(request.getApplMenuCode())
                .orElseThrow(() -> new RuntimeException("Menu non trovato con codice: " + request.getApplMenuCode()));

        ApplMenuUserKeyId key = new ApplMenuUserKeyId(menu.getCode(), user.getUserid());

        // evita duplicati
        if (applMenuUserInRepository.existsById(key)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        ApplMenuUserIn assignment = new ApplMenuUserIn(key, menu, user);
        ApplMenuUserIn savedAssignment = applMenuUserInRepository.save(assignment);
        return ResponseEntity.ok(savedAssignment);
    }

    // Nuovo: aggiungi singolo tramite path (comodo dal frontend)
    @PostMapping("/application/{applMenuCode}/add/{userid}")
    public ResponseEntity<?> addUserToApplication(@PathVariable String applMenuCode, @PathVariable String userid) {
        try {
            User user = userRepository.findById(userid)
                    .orElseThrow(() -> new RuntimeException("Utente non trovato: " + userid));
            ApplMenu menu = applMenuRepository.findById(applMenuCode)
                    .orElseThrow(() -> new RuntimeException("Applicazione non trovata: " + applMenuCode));

            ApplMenuUserKeyId key = new ApplMenuUserKeyId(menu.getCode(), user.getUserid());
            if (applMenuUserInRepository.existsById(key)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Assegnazione già esistente");
            }
            ApplMenuUserIn assignment = new ApplMenuUserIn(key, menu, user);
            applMenuUserInRepository.save(assignment);
            return ResponseEntity.ok("Utente assegnato");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: " + e.getMessage());
        }
    }

    // Esistente: recupera menu assegnati ad un utente
    @GetMapping("/user/{userid}")
    public ResponseEntity<List<String>> getAssignedMenus(@PathVariable String userid) {
        List<ApplMenuUserIn> assignments = applMenuUserInRepository.findByUserUserid(userid);
        List<String> menuCodes = assignments.stream()
                .map(a -> a.getApplMenu().getCode())
                .collect(Collectors.toList());
        return ResponseEntity.ok(menuCodes);
    }

    // Esistente: recupera utenti assegnati a un'applicazione
    @GetMapping("/application/{applMenuCode}")
    public ResponseEntity<?> getAssignedUsers(@PathVariable String applMenuCode) {
        try {
            List<ApplMenuUserIn> assignments = applMenuUserInRepository.findByApplMenuCode(applMenuCode);
            List<String> userIds = assignments.stream()
                    .map(a -> a.getUser().getUserid())
                    .collect(Collectors.toList());
            return ResponseEntity.ok(userIds);
        } catch (Exception e) {
            System.err.println("Errore nel recupero utenti assegnati per l'applicazione " + applMenuCode + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore nel recupero degli utenti assegnati: " + e.getMessage());
        }
    }

    // Esistente: recupera tutti gli utenti
    @GetMapping("/users/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> allUsers = userRepository.findAll();
            return ResponseEntity.ok(allUsers);
        } catch (Exception e) {
            System.err.println("Errore nel recupero di tutti gli utenti: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore nel recupero degli utenti: " + e.getMessage());
        }
    }

    // Esistente: rimuove singolo tramite body { userId, applMenuCode }
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeUserFromMenu(@RequestBody AssignmentRequest request) {
        try {
            ApplMenuUserKeyId key = new ApplMenuUserKeyId(request.getApplMenuCode(), request.getUserId());
            applMenuUserInRepository.deleteById(key);
            return ResponseEntity.ok("Utente rimosso con successo dall'applicazione");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Errore nella rimozione: " + e.getMessage());
        }
    }

    // Nuovo: rimuovi singolo tramite path (comodo dal frontend)
    @DeleteMapping("/application/{applMenuCode}/remove/{userid}")
    public ResponseEntity<?> removeUserFromApplication(@PathVariable String applMenuCode, @PathVariable String userid) {
        try {
            ApplMenuUserKeyId key = new ApplMenuUserKeyId(applMenuCode, userid);
            if (!applMenuUserInRepository.existsById(key)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assegnazione non trovata");
            }
            applMenuUserInRepository.deleteById(key);
            return ResponseEntity.ok("Utente rimosso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: " + e.getMessage());
        }
    }

    // NUOVO: salva l'elenco completo di userIds per una application (sostituisce le assegnazioni esistenti)
    @PostMapping("/application/{applMenuCode}/save")
    @Transactional
    public ResponseEntity<?> saveUsersForApplication(@PathVariable String applMenuCode, @RequestBody List<String> userIds) {
        try {
            // verifica esistenza applicazione
            ApplMenu menu = applMenuRepository.findById(applMenuCode)
                    .orElseThrow(() -> new RuntimeException("Applicazione non trovata: " + applMenuCode));

            // cancella assegnazioni esistenti per questa applicazione
            applMenuUserInRepository.deleteByApplMenuCode(applMenuCode);

            // crea nuove assegnazioni
            List<ApplMenuUserIn> toSave = new ArrayList<>();
            for (String userid : userIds) {
                User user = userRepository.findById(userid)
                        .orElseThrow(() -> new RuntimeException("Utente non trovato: " + userid));
                ApplMenuUserKeyId key = new ApplMenuUserKeyId(applMenuCode, userid);
                ApplMenuUserIn assignment = new ApplMenuUserIn(key, menu, user);
                toSave.add(assignment);
            }
            applMenuUserInRepository.saveAll(toSave);
            return ResponseEntity.ok("Assegnazioni aggiornate");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: " + e.getMessage());
        }
    }
}