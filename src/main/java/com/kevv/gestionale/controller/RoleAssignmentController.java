package com.kevv.gestionale.controller;

import com.kevv.gestionale.model.Role;
import com.kevv.gestionale.model.User;
import com.kevv.gestionale.repository.RoleRepository;
import com.kevv.gestionale.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RoleAssignmentController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Data
    static class UserIdRequest {
        private String userid;
    }

    @GetMapping("/{roleId}/users")
    public ResponseEntity<Set<User>> getUsersByRoleId(@PathVariable String roleId) {
        Optional<Role> roleOptional = roleRepository.findById(roleId);
        if (roleOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ruolo non trovato: " + roleId);
        }


        return ResponseEntity.ok(roleOptional.get().getUserRoles());
    }

    @PostMapping("/{roleId}/users")
    @Transactional // Fondamentale per le modifiche alle collezioni
    public ResponseEntity<String> assignUserToRole(@PathVariable String roleId, @RequestBody UserIdRequest request) {
        String userId = request.getUserid();

        if (userId == null || userId.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("L'ID utente è richiesto nel corpo della richiesta.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato con ID: " + userId));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ruolo non trovato con ID: " + roleId));

        if (user.getUserRoles().contains(role)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("L'utente " + userId + " è già assegnato al ruolo " + roleId);
        }


        user.addRole(role);

        // 5. Salva l'utente per persistere la modifica della collezione userRoles
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("Utente " + userId + " assegnato al ruolo " + roleId + " con successo.");
    }

    @DeleteMapping("/{roleId}/users/{userId}")
    @Transactional // Fondamentale per le modifiche alle collezioni
    public ResponseEntity<String> removeUserFromRole(@PathVariable String roleId, @PathVariable String userId) {
        // 1. Recupera Utente e Ruolo dal database
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato con ID: " + userId));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ruolo non trovato con ID: " + roleId));

        // 2. Controlla se l'utente è effettivamente assegnato a questo ruolo prima di provare a rimuoverlo
        if (user.getUserRoles().contains(role)) {
            // Rimuovi il ruolo dall'utente (il metodo removeRole in User.java gestisce entrambi i lati della relazione)
            // Assicurati che User.java abbia il metodo public void removeRole(Role role)
            user.removeRole(role);

            // 3. Salva l'utente per persistere la modifica della collezione userRoles
            userRepository.save(user);

            return ResponseEntity.ok("Utente " + userId + " rimosso dal ruolo " + roleId + " con successo.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("L'utente " + userId + " non è assegnato al ruolo " + roleId + ".");
        }
    }
}
