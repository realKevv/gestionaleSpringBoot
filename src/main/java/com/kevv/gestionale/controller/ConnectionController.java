package com.kevv.gestionale.controller;

import com.kevv.gestionale.model.Compy;
import com.kevv.gestionale.model.Connection;
import com.kevv.gestionale.repository.CompyRepository;
import com.kevv.gestionale.repository.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/connections")
@CrossOrigin(origins = "*")
public class ConnectionController {

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private CompyRepository compyRepository;

    @GetMapping
    public List<Connection> getAllConnections() {
        return connectionRepository.findAll();
    }

    // --- METODO getCompy CORRETTO (nome e tipo di ritorno) ---
    @GetMapping("/{connid}/compy") // <-- QUI IL PATH CORRETTO
    public ResponseEntity<List<Compy>> getCompy(@PathVariable String connid) { // <-- QUI IL NOME DEL METODO CORRETTO
        System.out.println("ARRIVA: " + connid);

        Optional<Connection> connectionOptional = connectionRepository.findById(connid);

        if (connectionOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Connection connection = connectionOptional.get();
        System.out.println("Connessione trovata: " + connection.getConnid());

        List<Compy> companies = compyRepository.findByConn(connection);

        if (companies.isEmpty()) {
            System.out.println("Nessuna azienda trovata per la connessione: " + connid);
            return ResponseEntity.ok(List.of());
        } else {
            System.out.println("Trovate " + companies.size() + " aziende per la connessione: " + connid);
            companies.forEach(c -> System.out.println("  - Azienda: " + c.getId().getComp() + ", Anno: " + c.getId().getYear()));
            return ResponseEntity.ok().body(companies);
        }
    }
    // --- FINE METODO getCompy CORRETTO ---


    @GetMapping("/{connid}")
    public ResponseEntity<Connection> getConnection(@PathVariable String connid) {
        System.out.println("ARRIVA: " + connid);

        Optional<Connection> connection = connectionRepository.findById(connid);
        if (connection.isPresent()){
            return ResponseEntity.ok().body(connection.get());
        }
        else{
            return ResponseEntity.notFound().build();

        }
    }


    @PutMapping("/{connid}")
    public ResponseEntity<Connection> updateConnection(@PathVariable String connid, @RequestBody Connection updated) {
        Optional<Connection> optionalConnection = connectionRepository.findById(connid);
        if (optionalConnection.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Connection existing = optionalConnection.get();

        if (updated.getDescr() != null) {
            existing.setDescr(updated.getDescr());
        }
        if (updated.getHost() != null) {
            existing.setHost(updated.getHost());
        }
        if (updated.getSez() != null) {
            existing.setSez(updated.getSez());
        }
        if (updated.getSub() != null) {
            existing.setSub(updated.getSub());
        }
        if (updated.getUrl() != null) {
            existing.setUrl(updated.getUrl());
        }

        connectionRepository.save(existing);

        return ResponseEntity.ok(existing);
    }


    @PostMapping
    public ResponseEntity<Connection> createConnection(@RequestBody Connection newConnection) {
        Connection saved = connectionRepository.save(newConnection);
        return ResponseEntity.ok(saved);
    }


    @PostMapping("/test")
    public ResponseEntity<String> testConnectionByBody(@RequestBody Map<String, String> body) {
        String url = body.get("url");
        if (url == null || url == null) { // piccolo fix qui: == null || url.isEmpty()
            return ResponseEntity.badRequest().body("Campo 'url' mancante o vuoto nel JSON");
        }
        System.out.println(body.get("url"));

        try {
            Class.forName("com.informix.jdbc.IfxDriver");
            java.sql.Connection connection = DriverManager.getConnection(url);
            System.out.println("OKKKK");
            return ResponseEntity.ok("✅ Connessione riuscita con successo a " + url);
        }
        catch (Exception ex){
            ex.printStackTrace();
            System.out.println("ERRORE NELLA CONNESSIONE");
            return ResponseEntity.status(500).body("❌ Connessione fallita: " + ex.getMessage());
        }
    }
}