package com.kevv.gestionale.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @PostMapping("/assign")
    public ResponseEntity<ApplMenuUserIn> assignMenuToUser(@RequestBody AssignmentRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Utente non trovato con ID  "+ request.getUserId()));
        ApplMenu menu = applMenuRepository.findById(request.getApplMenuCode())
                .orElseThrow(() -> new RuntimeException("Menu non trovato con codice: " + request.getApplMenuCode()));


        ApplMenuUserKeyId key = new ApplMenuUserKeyId(menu.getCode(), user.getUserid());
        ApplMenuUserIn assignment = new ApplMenuUserIn(key, menu, user);

        ApplMenuUserIn savedAssignment = applMenuUserInRepository.save(assignment);
        return  ResponseEntity.ok(savedAssignment);
    }

    @GetMapping("/user/{userid}")
    public ResponseEntity<List<String>> getAssignedMenus(@PathVariable String userid) {
        //  metodo di ricerca
        List<ApplMenuUserIn> assignments = applMenuUserInRepository.findByUserUserid(userid);

        List<String> menuCodes = assignments.stream()
                .map(a -> a.getApplMenu().getCode())
                .collect(Collectors.toList());

        return ResponseEntity.ok(menuCodes);
    }

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

    @GetMapping("/users/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> allUsers = userRepository.findAll();
            return ResponseEntity.ok(allUsers);
        } catch (Exception e) {
            // Log dell'errore per debugging
            System.err.println("Errore nel recupero di tutti gli utenti: " + e.getMessage());
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore nel recupero degli utenti: " + e.getMessage());
        }
    }

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
}
