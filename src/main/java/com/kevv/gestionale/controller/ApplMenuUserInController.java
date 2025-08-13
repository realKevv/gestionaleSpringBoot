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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/applmenuUser")
public class ApplMenuUserInController {

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

    // Lista menu assegnati a un utente
    @GetMapping("/user/{userid}")
    public ResponseEntity<List<String>> getAssignedMenus(@PathVariable String userid) {
        List<ApplMenuUserIn> assignments = applMenuUserInRepository.findByUser(userid);
        List<String> menuCodes = assignments.stream()
                .map(ApplMenuUserIn::getApplmenu)
                .collect(Collectors.toList());
        return ResponseEntity.ok(menuCodes);
    }

    // Lista utenti assegnati a un menu
    @GetMapping("/application/{applMenuCode}")
    public ResponseEntity<?> getAssignedUsers(@PathVariable String applMenuCode) {
        try {
            List<ApplMenuUserIn> assignments = applMenuUserInRepository.findByApplmenu(applMenuCode);
            List<String> userIds = assignments.stream()
                    .map(ApplMenuUserIn::getUser)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(userIds);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore nel recupero degli utenti assegnati: " + e.getMessage());
        }
    }

    // Tutti gli utenti
    @GetMapping("/users/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> allUsers = userRepository.findAll();
            return ResponseEntity.ok(allUsers);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore nel recupero degli utenti: " + e.getMessage());
        }
    }

    // Rimuove l'assegnazione utente-menu
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

    @PostMapping("/assign")
    public ResponseEntity<?> assignMenuToUser(@RequestBody AssignmentRequest request) {
        try {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("Utente non trovato con ID " + request.getUserId()));
            ApplMenu menu = applMenuRepository.findById(request.getApplMenuCode())
                    .orElseThrow(() -> new RuntimeException("Menu non trovato con codice: " + request.getApplMenuCode()));

            ApplMenuUserIn assignment = new ApplMenuUserIn(menu, user);

            ApplMenuUserIn savedAssignment = applMenuUserInRepository.save(assignment);
            return ResponseEntity.ok(savedAssignment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore nell'assegnazione: " + e.getMessage());
        }
    }


}