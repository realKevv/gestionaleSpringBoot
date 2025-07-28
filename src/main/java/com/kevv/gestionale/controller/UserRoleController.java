    package com.kevv.gestionale.controller;

    import com.kevv.gestionale.model.UserRole;
    import com.kevv.gestionale.model.UserRoleId;
    import com.kevv.gestionale.repository.UserRoleRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestParam;
    import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/userroles")
    public class UserRoleController {

        @Autowired
        private UserRoleRepository userRoleRepository;

        @PostMapping("/assign")
        public ResponseEntity<String> assignRoleToUser(
                @RequestParam String userid,
                @RequestParam String roleid
        ) {
            UserRoleId id = new UserRoleId();
            id.setUserid(userid);
            id.setRoleid(roleid);

            UserRole userRole = new UserRole();
            userRole.setId(id);

            userRoleRepository.save(userRole);

            return ResponseEntity.ok("Ruolo assegnato correttamente");
        }
    }