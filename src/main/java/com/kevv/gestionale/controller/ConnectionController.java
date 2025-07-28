package com.kevv.gestionale.controller;

import com.kevv.gestionale.model.Compy;
import com.kevv.gestionale.model.Connection;
import com.kevv.gestionale.repository.CompyRepository;
import com.kevv.gestionale.repository.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @GetMapping("/{connid}/compy")
    public ResponseEntity<Compy> getConmpy(@PathVariable String connid) {
        System.out.println("ARRIVA: " + connid);
        System.out.println(connectionRepository.findById(connid));

        Optional<Compy> compy = compyRepository.findByConn(connectionRepository.findById(connid).get());
        if (compy.isPresent()){
            System.out.println(compy.get().getId().getComp());
            System.out.println(compy.get().getId().getYear());

            return ResponseEntity.ok().body(compy.get());

        }
        else{
            return ResponseEntity.notFound().build();

        }


//        return connectionRepository.findById(connid)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
    }


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
        if (url == null || url.isEmpty()) {
            return ResponseEntity.badRequest().body("Campo 'url' mancante o vuoto nel JSON");
        }
        System.out.println(body.get("url"));

//        try {
//            Class.forName("com.informix.jdbc.IfxDriver");
//
//            java.sql.Connection conn = DriverManager.getConnection(url);
//            return ResponseEntity.ok("✅ Connessione riuscita con successo a " + url);
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            return ResponseEntity.status(500).body("❌ Connessione fallita: " + ex.getMessage());
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }

        try {
            Class.forName("com.informix.jdbc.IfxDriver");
//            Connection connection = DriverManager.getConnection("jdbc:informix-sqli://192.168.89.253:9088/web0708:informixserver=cdaplus;user=informix;password=info0");
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