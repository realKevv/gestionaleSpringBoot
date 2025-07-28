package com.kevv.gestionale.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data; // Usiamo Data per Getter, Setter, Equals, HashCode, ToString
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data // Include @Getter, @Setter, @EqualsAndHashCode, @ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends EntityDTO<User> { // Assumo EntityDTO è una classe base che gestisci a parte

    @Id
    @Column(name = "userid", length = 15, nullable = false)
    private String userid;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "surname", length = 20)
    private String surname;

    @Column(name = "usehost")
    private Boolean usehost;

    @Column(name = "is_logged")
    private Boolean isLogged;

    @Column(name = "last_login")
    private Timestamp lastLogin;

    @Column(name = "from_ip", length = 15)
    private String fromIp;

    @Column(name = "pentaho_enabled")
    private Boolean pentahoEnabled;

    @Column(name = "email", length = 200)
    private String email;

    @Column(name = "last_change_psw")
    private Timestamp lastChangePsw;

    @Column(name = "cdapos_enabled")
    private Boolean cdaposEnabled;

    @Column(name = "host_user", length = 40)
    private String hostUser;

    @Column(name = "password", length = 32)
    private String password;

    // Relazione con UserCompy. FetchType.LAZY per evitare caricamenti eccessivi.
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserCompy> userCompies = new ArrayList<>();

    // Metodo per aggiungere un UserCompy.
    public void addUserCompy(UserCompy compy){
        if (this.userCompies == null) {
            this.userCompies = new ArrayList<>();
        }
        this.userCompies.add(compy);
    }

    // Relazione ManyToMany per i ruoli.
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "user_roles",
            joinColumns = { @JoinColumn(name = "userid") },
            inverseJoinColumns = { @JoinColumn(name = "roleid") }
    )
    private Set<Role> userRoles = new HashSet<>();

    @Override
    public int compareTo(User otherUser) {
        return userid.compareTo(otherUser.getUserid());
    }

    public void addRole(Role role) {
        this.userRoles.add(role);
        if (role != null && role.getUserRoles() != null) {
            role.getUserRoles().add(this);
        }
    }
    public void removeRole(Role role) {
        this.userRoles.remove(role);
        if (role != null && role.getUserRoles() != null) {
            role.getUserRoles().remove(this);
        }
    }
}