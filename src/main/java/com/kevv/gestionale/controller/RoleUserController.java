package com.kevv.gestionale.controller;

import com.kevv.gestionale.model.User;
import com.kevv.gestionale.model.UserRole;
import com.kevv.gestionale.model.UserRoleId;
import com.kevv.gestionale.repository.UserRepository;
import com.kevv.gestionale.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roleusers")
@CrossOrigin(origins = "*")
public class RoleUserController {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    // 1) Prendere gli utenti assegnati a un ruolo
    @GetMapping("/{roleid}")
    public List<User> getUsersByRole(@PathVariable String roleid) {
        List<UserRole> userRoles = userRoleRepository.findByIdRoleid(roleid);
        return userRoles.stream()
                .map(ur -> userRepository.findById(ur.getId().getUserid()).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    // 2) Aggiornare utenti assegnati a un ruolo (lista intera)
    @PutMapping("/{roleid}")
    public ResponseEntity<String> updateUsersByRole(
            @PathVariable String roleid,
            @RequestBody List<String> userids) {

        // Elimina tutte le assegnazioni correnti per questo ruolo
        userRoleRepository.deleteByIdRoleid(roleid);

        // Salva tutte le nuove assegnazioni
        userids.forEach(userid -> {
            UserRoleId id = new UserRoleId(userid, roleid);
            UserRole ur = new UserRole();
            ur.setId(id);
            userRoleRepository.save(ur);
        });

        return ResponseEntity.ok("Assegnazioni aggiornate");
    }
}
