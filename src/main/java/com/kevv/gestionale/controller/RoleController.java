package com.kevv.gestionale.controller;

import com.kevv.gestionale.model.Role;
import com.kevv.gestionale.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/roles")
@CrossOrigin(origins = "*")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @GetMapping("/{roleid}")
    public ResponseEntity<Role> getRoleById(@PathVariable String roleid) {
        return roleRepository.findById(roleid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{roleid}")
    public ResponseEntity<Role> updateRole(@PathVariable String roleid, @RequestBody Role roleDetails) {
        return roleRepository.findById(roleid).map(existingRole -> {
            Optional.ofNullable(roleDetails.getDescr()).ifPresent(existingRole::setDescr);
            Role updatedRole = roleRepository.save(existingRole);
            return ResponseEntity.ok(updatedRole);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role newRole) {
        if (newRole.getRoleid() == null || newRole.getRoleid().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        if (roleRepository.existsById(newRole.getRoleid())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Role savedRole = roleRepository.save(newRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRole);
    }

}





