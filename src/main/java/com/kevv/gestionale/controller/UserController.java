package com.kevv.gestionale.controller;

import com.kevv.gestionale.model.*;
import com.kevv.gestionale.repository.CompyRepository; // <<< AGGIUNGI QUESTO IMPORT
import com.kevv.gestionale.repository.RoleRepository;
import com.kevv.gestionale.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; // Assicurati di avere questo import
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private EntityManager entityManager; // Utile per operazioni avanzate, ma non strettamente necessaria qui
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CompyRepository compyRepository; // <<< AGGIUNTO: Necessario per recuperare le entità Compy


    @PutMapping("/resetPassword/{userid}")
    public ResponseEntity<String> resetPassword(@PathVariable String userid) {
        Optional<User> optionalUser = userRepository.findById(userid);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente non trovato con id " + userid);
        }
        User user = optionalUser.get();
        String newPassword = generateRandomPassword(8);
        String hashedPassword = md5(newPassword);
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return ResponseEntity.ok("Reset password completato. Nuova password: " + newPassword);
    }

    public String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }


    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PutMapping("/resetLogin/{userid}")
    @Transactional // Aggiunto @Transactional se la logica di reset coinvolge più operazioni DB
    private ResponseEntity<String> resetLogin(@PathVariable String userid) {
        try {
            User user = userRepository.findById(userid)
                    .orElseThrow(() -> new RuntimeException("Utente non trovato"));
            user.setIsLogged(false);
            userRepository.save(user);
            return ResponseEntity.ok("Login resettato con successo");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante il reset login: " + e.getMessage()); // Dettaglio l'errore
        }
    }

    @GetMapping("/{userid}")
    public ResponseEntity<User> getUser(@PathVariable String userid) {
        Optional<User> tmpUser = userRepository.findById(userid);
        return tmpUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{userid}")
    @Transactional // IMPORTANTE: le operazioni su collezioni gestite da JPA devono essere transazionali
    public ResponseEntity<User> updateUser(
            @PathVariable String userid,
            @RequestBody Map<String, Object> updates
    ) {
        System.out.println("Received updates for user " + userid + ": " + updates);

        return userRepository.findById(userid)
                .map(user -> {
                    // --- Gestione userRoles ---
                    if (updates.containsKey("userRoles")) {
                        List<Map<String, String>> rolesList = (List<Map<String, String>>) updates.get("userRoles");
                        Set<Role> newRoles = new HashSet<>();
                        for (Map<String, String> roleMap : rolesList) {
                            String roleidKey = roleMap.get("roleid");
                            if (roleidKey != null) {
                                // Recupera l'entità Role gestita dal DB
                                Optional<Role> roleTmp = roleRepository.findById(roleidKey);
                                roleTmp.ifPresent(newRoles::add);
                            }
                        }
                        user.setUserRoles(newRoles); // Imposta la nuova collezione di ruoli
                    }

                    // --- Gestione userCompies ---
                    if (updates.containsKey("userCompies")) {
                        // Il frontend invia un List di Map che rappresentano UserCompy con l'ID composito
                        List<Map<String, LinkedHashMap<String, String>>> compiesList =
                                (List<Map<String, LinkedHashMap<String, String>>>) updates.get("userCompies");

                        // Crea una nuova lista temporanea per le associazioni
                        List<UserCompy> newUserCompies = new ArrayList<>();

                        for (Map<String, LinkedHashMap<String, String>> compyMap : compiesList) {
                            LinkedHashMap<String, String> idMap = compyMap.get("id");
                            if (idMap != null) {
                                String compId = idMap.get("comp");
                                String year = idMap.get("year");

                                if (compId != null && year != null) {
                                    // 1. Crea l'ID composto per cercare Compy
                                    CompyId searchCompyId = new CompyId(compId, year);

                                    // 2. Recupera l'entità Compy gestita dal DB (NECESSARIO!)
                                    Optional<Compy> compyOptional = compyRepository.findById(searchCompyId);

                                    if (compyOptional.isPresent()) {
                                        Compy associatedCompy = compyOptional.get();

                                        // 3. Crea il nuovo UserCompy
                                        UserCompy newUserCompy = new UserCompy();
                                        // Popola l'ID di UserCompy
                                        newUserCompy.setId(new UserCompyId(userid, compId, year));
                                        // Collega le entità gestite
                                        newUserCompy.setUser(user); // L'utente che stiamo aggiornando
                                        newUserCompy.setCompany(associatedCompy); // L'azienda recuperata dal DB

                                        newUserCompies.add(newUserCompy);
                                    } else {
                                        System.err.println("Compy non trovata nel DB per ID: comp=" + compId + ", year=" + year);
                                        // Qui puoi decidere se lanciare un errore, ignorare, ecc.
                                    }
                                }
                            }
                        }
                        // Aggiorna la collezione dell'utente. Hibernate gestirà aggiunte/rimozioni.
                        // Questo imposterà la nuova collezione e rimuoverà le vecchie associazioni non presenti.
                        user.getUserCompies().clear(); // Rimuove tutte le associazioni esistenti
                        user.getUserCompies().addAll(newUserCompies); // Aggiunge le nuove
                    }

                    // --- Aggiornamento degli altri campi semplici ---
                    if (updates.containsKey("userid")) {
                        Object obj = updates.get("userid");
                        if (obj instanceof String val) user.setUserid(val); // Normalmente l'ID non si cambia in un PUT
                    }
                    if (updates.containsKey("surname")) {
                        Object obj = updates.get("surname");
                        if (obj instanceof String val) user.setSurname(val);
                    }
                    if (updates.containsKey("name")) {
                        Object obj = updates.get("name");
                        if (obj instanceof String val) user.setName(val);
                    }
                    if (updates.containsKey("password")) {
                        Object obj = updates.get("password");
                        // Dovresti hashare la password qui, non impostarla direttamente
                        if (obj instanceof String val) user.setPassword(md5(val));
                    }
                    if (updates.containsKey("usehost")) {
                        Object obj = updates.get("usehost");
                        if (obj instanceof Boolean val) user.setUsehost(val);
                    }
                    if (updates.containsKey("isLogged")) {
                        Object obj = updates.get("isLogged");
                        if (obj instanceof Boolean val) user.setIsLogged(val);
                    }
                    if (updates.containsKey("lastLogin")) {
                        Object obj = updates.get("lastLogin");
                        if (obj instanceof String val) {
                            try {
                                OffsetDateTime odt = OffsetDateTime.parse(val);
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                user.setLastLogin(Timestamp.valueOf(odt.format(formatter)));
                            } catch (Exception e) {
                                System.err.println("Errore parsing lastLogin: " + e.getMessage());
                            }
                        }
                    }
                    if (updates.containsKey("fromIp")) {
                        Object obj = updates.get("fromIp");
                        if (obj instanceof String val) user.setFromIp(val);
                    }
                    if (updates.containsKey("pentahoEnabled")) {
                        Object obj = updates.get("pentahoEnabled");
                        if (obj instanceof Boolean val) user.setPentahoEnabled(val);
                        System.out.println("pentahoEnabled tipo: " + (obj != null ? obj.getClass() : "null"));
                    }
                    if (updates.containsKey("email")) {
                        Object obj = updates.get("email");
                        if (obj instanceof String val) user.setEmail(val);
                    }
                    if (updates.containsKey("lastChangePsw")) {
                        Object obj = updates.get("lastChangePsw");
                        if (obj instanceof String val) {
                            try {
                                OffsetDateTime odt = OffsetDateTime.parse(val);
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                user.setLastChangePsw(Timestamp.valueOf(odt.format(formatter)));
                            } catch (Exception e) {
                                System.err.println("Errore parsing lastChangePsw: " + e.getMessage());
                            }
                        }
                    }
                    if (updates.containsKey("cdaposEnabled")) {
                        Object obj = updates.get("cdaposEnabled");
                        if (obj instanceof Boolean val) user.setCdaposEnabled(val);
                    }
                    if (updates.containsKey("hostUser")) {
                        Object obj = updates.get("hostUser");
                        if (obj instanceof String val) user.setHostUser(val);
                    }

                    User savedUser = userRepository.save(user); // Salva l'utente con tutte le modifiche
                    return ResponseEntity.ok(savedUser);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody Map<String, Object> userData) {
        try {
            User newUser = new User();

            // L'ID utente dovrebbe essere generato dal sistema o validato se fornito
            if (userData.containsKey("userid")) {
                newUser.setUserid((String) userData.get("userid"));
            } else {
                return ResponseEntity.badRequest().body(null); // O genera un ID qui
            }

            // Mappa gli altri campi dal JSON
            if (userData.containsKey("surname")) {
                Object val = userData.get("surname");
                if (val instanceof String) newUser.setSurname((String) val);
            }
            if (userData.containsKey("name")) {
                Object val = userData.get("name");
                if (val instanceof String) newUser.setName((String) val);
            }
            if (userData.containsKey("password")) {
                Object val = userData.get("password");
                // Hasha sempre la password!
                if (val instanceof String) newUser.setPassword(md5((String) val));
            }
            if (userData.containsKey("usehost")) {
                Object val = userData.get("usehost");
                if (val instanceof Boolean) newUser.setUsehost((Boolean) val);
            }
            if (userData.containsKey("isLogged")) {
                Object val = userData.get("isLogged");
                if (val instanceof Boolean) newUser.setIsLogged((Boolean) val);
            }
            if (userData.containsKey("lastLogin")) {
                Object val = userData.get("lastLogin");
                if (val instanceof String) {
                    try {
                        OffsetDateTime odt = OffsetDateTime.parse((String) val);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        newUser.setLastLogin(Timestamp.valueOf(odt.format(formatter)));
                    } catch (Exception e) {
                        System.err.println("Errore parsing lastLogin in createUser: " + e.getMessage());
                    }
                }
            }
            if (userData.containsKey("fromIp")) {
                Object val = userData.get("fromIp");
                if (val instanceof String) newUser.setFromIp((String) val);
            }
            if (userData.containsKey("pentahoEnabled")) {
                Object val = userData.get("pentahoEnabled");
                if (val instanceof Boolean) newUser.setPentahoEnabled((Boolean) val);
            }
            if (userData.containsKey("email")) {
                Object val = userData.get("email");
                if (val instanceof String) newUser.setEmail((String) val);
            }
            if (userData.containsKey("lastChangePsw")) {
                Object val = userData.get("lastChangePsw");
                if (val instanceof String) {
                    try {
                        OffsetDateTime odt = OffsetDateTime.parse((String) val);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        newUser.setLastChangePsw(Timestamp.valueOf(odt.format(formatter)));
                    } catch (Exception e) {
                        System.err.println("Errore parsing lastChangePsw in createUser: " + e.getMessage());
                    }
                }
            }
            if (userData.containsKey("cdaposEnabled")) {
                Object val = userData.get("cdaposEnabled");
                if (val instanceof Boolean) newUser.setCdaposEnabled((Boolean) val);
            }
            if (userData.containsKey("hostUser")) {
                Object val = userData.get("hostUser");
                if (val instanceof String) newUser.setHostUser((String) val);
            }

            // --- Gestione userRoles per la creazione ---
            if (userData.containsKey("userRoles")) {
                List<Map<String, String>> rolesList = (List<Map<String, String>>) userData.get("userRoles");
                Set<Role> newRoles = new HashSet<>();
                for (Map<String, String> roleMap : rolesList) {
                    String roleidKey = roleMap.get("roleid");
                    if (roleidKey != null) {
                        Optional<Role> roleTmp = roleRepository.findById(roleidKey);
                        roleTmp.ifPresent(newRoles::add);
                    }
                }
                newUser.setUserRoles(newRoles);
            }

            // --- Gestione userCompies per la creazione ---
            if (userData.containsKey("userCompies")) {
                List<Map<String, LinkedHashMap<String, String>>> compiesList =
                        (List<Map<String, LinkedHashMap<String, String>>>) userData.get("userCompies");
                List<UserCompy> newUserCompies = new ArrayList<>();

                for (Map<String, LinkedHashMap<String, String>> compyMap : compiesList) {
                    LinkedHashMap<String, String> idMap = compyMap.get("id");
                    if (idMap != null) {
                        String compId = idMap.get("comp");
                        String year = idMap.get("year");

                        if (compId != null && year != null) {
                            CompyId searchCompyId = new CompyId(compId, year);
                            Optional<Compy> compyOptional = compyRepository.findById(searchCompyId);

                            if (compyOptional.isPresent()) {
                                Compy associatedCompy = compyOptional.get();
                                UserCompy uc = new UserCompy();
                                uc.setId(new UserCompyId(newUser.getUserid(), compId, year));
                                uc.setUser(newUser);
                                uc.setCompany(associatedCompy);
                                newUserCompies.add(uc);
                            } else {
                                System.err.println("Compy non trovata nel DB per ID (create user): comp=" + compId + ", year=" + year);
                            }
                        }
                    }
                }
                newUser.setUserCompies(newUserCompies); // Imposta la collezione di UserCompy
            }

            User savedUser = userRepository.save(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser); // 201 Created

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @DeleteMapping("/{id}")
    @Transactional // Aggiunto @Transactional per l'operazione di delete
    public ResponseEntity<Object> deleteUser(@PathVariable String id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.noContent().build(); // 204 No Content
                })
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found
    }
}