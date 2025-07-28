package com.kevv.gestionale.controller;

import com.kevv.gestionale.model.User;
import com.kevv.gestionale.model.WebgenHost;
import com.kevv.gestionale.repository.UserRepository;
import com.kevv.gestionale.repository.WebgenHostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/webgenHosts")
@RequiredArgsConstructor
public class WebgenHostController {

    private final WebgenHostRepository webgenHostRepository;
    private final UserRepository userRepository;

    @PostMapping("/{userId}")
    public ResponseEntity<?> createWebgenHost(
            @PathVariable String userId,
            @RequestBody WebgenHost webgenHost) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (webgenHostRepository.findByUser(user).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("WebgenHost for userId " + userId + " already exists.");
        }

        webgenHost.setUser(user);
        WebgenHost saved = webgenHostRepository.save(webgenHost);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<WebgenHost> getWebgenHostByUserId(@PathVariable String userId) {
        return userRepository.findById(userId)
                .flatMap(user -> webgenHostRepository.findByUser(user))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tipoSpo")
    public ResponseEntity<List<String>> getTipoSpoOptions() {
        List<String> options = List.of("automatico", "nessuno", "manuale");
        return ResponseEntity.ok(options);
    }



    @PutMapping("/{userId}")
    public ResponseEntity<WebgenHost> updateWebgenHost(
            @PathVariable String userId,
            @RequestBody WebgenHost updatedWebgenHost) {

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) return ResponseEntity.notFound().build();

        Optional<WebgenHost> webgenHost = webgenHostRepository.findByUser(user.get());
        if (webgenHost.isEmpty()) return ResponseEntity.notFound().build();

        WebgenHost existing = webgenHost.get();
        existing.setAziMin(updatedWebgenHost.getAziMin());
        existing.setAziMax(updatedWebgenHost.getAziMax());
        existing.setAnnoMin(updatedWebgenHost.getAnnoMin());
        existing.setAnnoMax(updatedWebgenHost.getAnnoMax());
        existing.setDevStampa(updatedWebgenHost.getDevStampa());
        existing.setTipoSpo(updatedWebgenHost.getTipoSpo());
        existing.setNumLinee(updatedWebgenHost.getNumLinee());
        existing.setNumCol(updatedWebgenHost.getNumCol());
        existing.setMenuHome(updatedWebgenHost.getMenuHome());

        WebgenHost saved = webgenHostRepository.save(existing);
        return ResponseEntity.ok(saved);
    }
}
    // ✅ CANCELLAZIONE
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteWebgenHost(@PathVariable String userid) {
//        if (webgenHostRepository.existsById(id)) {
//            webgenHostRepository.deleteById(id);
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.notFound().build();
//    }

