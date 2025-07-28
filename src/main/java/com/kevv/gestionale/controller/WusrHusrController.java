package com.kevv.gestionale.controller;

import com.kevv.gestionale.model.User;
import com.kevv.gestionale.model.WusrHusr;
import com.kevv.gestionale.model.WusrHusrId;
import com.kevv.gestionale.repository.UserRepository;
import com.kevv.gestionale.repository.WusrHusrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/wusrhusr")
public class WusrHusrController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WusrHusrRepository repository;

    @GetMapping
    public List<WusrHusr> getAll() {
        return repository.findAll();
    }

    @GetMapping("/user/{userid}")
    public ResponseEntity<List<WusrHusr>> getWusrHusrByUserId(@PathVariable String userid) {
        List<WusrHusr> list = repository.findByWusr_Userid(userid);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{host}/{husr}")
    public ResponseEntity<WusrHusr> getById(@PathVariable String host, @PathVariable String husr) {
        WusrHusrId id = new WusrHusrId(host, husr);
        Optional<WusrHusr> opt = repository.findById(id);
        if (opt.isPresent()){
            return ResponseEntity.ok().body(opt.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public WusrHusr create(@RequestBody WusrHusr wusrHusr) {
        return repository.save(wusrHusr);
    }

    @PutMapping("/{host}/{husr}")
    public WusrHusr update(@PathVariable String host,
                           @PathVariable String husr,
                           @RequestBody WusrHusr updated) {
        WusrHusrId id = new WusrHusrId(host, husr);
        if (repository.existsById(id)) {
            updated.setId(id);
            return repository.save(updated);
        } else {
            throw new RuntimeException("Entity not found");
        }
    }

    @DeleteMapping("/{host}/{husr}")
    public ResponseEntity<Void> delete(@PathVariable String host,
                                       @PathVariable String husr) {
        WusrHusrId id = new WusrHusrId(host, husr);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/user/{wusr}/addrange")
    public List<WusrHusr> addRange(
            @PathVariable String wusr,
            @RequestParam String host,
            @RequestParam int startIndex,
            @RequestParam int endIndex) {

        User user = userRepository.findById(wusr)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<WusrHusr> added = new ArrayList<>();

        System.out.println(wusr);
        System.out.println(host);
        System.out.println(startIndex);
        System.out.println(endIndex);

        for (int i = startIndex; i <= endIndex; i++) {
            String husr = "web" + String.format("%03d", i);
            WusrHusrId id = new WusrHusrId(host, husr);
            System.out.println(husr);
            if (!repository.existsById(id)) {
                WusrHusr newEntry = new WusrHusr();
                newEntry.setId(id);
                newEntry.setWusr(user);
                repository.save(newEntry);
                added.add(newEntry);
            }
        }

        return added;
    }

    // NUOVO ENDPOINT PER LA CONFIGURAZIONE COMPLETA
    @PostMapping("/user/{wusr}/configure")
    public ResponseEntity<Map<String, Object>> configureUser(
            @PathVariable String wusr,
            @RequestParam String host,
            @RequestParam int startIndex,
            @RequestParam int endIndex,
            @RequestParam(required = false) String aziendaMin,
            @RequestParam(required = false) String aziendaMax,
            @RequestParam(required = false, defaultValue = "2024") int annoMin,
            @RequestParam(required = false, defaultValue = "2024") int annoMax,
            @RequestParam(required = false) String deviceStampa,
            @RequestParam(required = false, defaultValue = "nessuno") String tipoSpooling,
            @RequestParam(required = false, defaultValue = "50") int lineePagina,
            @RequestParam(required = false, defaultValue = "80") int colonnePagina,
            @RequestParam(required = false) String menuHome) {

        try {
            // Verifica che l'utente esista
            User user = userRepository.findById(wusr)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            List<WusrHusr> configured = new ArrayList<>();

            System.out.println("Configurazione per utente: " + wusr);
            System.out.println("Host: " + host);
            System.out.println("Range: " + startIndex + " - " + endIndex);
            System.out.println("Azienda: " + aziendaMin + " - " + aziendaMax);
            System.out.println("Anno: " + annoMin + " - " + annoMax);
            System.out.println("Device Stampa: " + deviceStampa);
            System.out.println("Tipo Spooling: " + tipoSpooling);
            System.out.println("Layout: " + lineePagina + "x" + colonnePagina);
            System.out.println("Menu Home: " + menuHome);

            // Crea/aggiorna gli utenti host con tutti i parametri
            for (int i = startIndex; i <= endIndex; i++) {
                String husr = "web" + String.format("%03d", i);
                WusrHusrId id = new WusrHusrId(host, husr);

                WusrHusr entry;
                if (repository.existsById(id)) {
                    // Aggiorna esistente
                    entry = repository.findById(id).get();
                } else {
                    // Crea nuovo
                    entry = new WusrHusr();
                    entry.setId(id);
                    entry.setWusr(user);
                }

                // Imposta tutti i parametri di configurazione
                // Nota: questi setter dipendono dalla tua classe WusrHusr
                // Dovrai adattarli ai campi effettivi della tua entità

                // Se la tua entità WusrHusr ha questi campi, decommentali:
                // entry.setAziendaMin(aziendaMin);
                // entry.setAziendaMax(aziendaMax);
                // entry.setAnnoMin(annoMin);
                // entry.setAnnoMax(annoMax);
                // entry.setDeviceStampa(deviceStampa);
                // entry.setTipoSpooling(tipoSpooling);
                // entry.setLineePagina(lineePagina);
                // entry.setColonnePagina(colonnePagina);
                // entry.setMenuHome(menuHome);

                repository.save(entry);
                configured.add(entry);
            }

            // Prepara la risposta
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Configurazione completata con successo");
            response.put("count", configured.size());
            response.put("configured", configured);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Errore durante la configurazione: " + e.getMessage());
            errorResponse.put("count", 0);

            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}