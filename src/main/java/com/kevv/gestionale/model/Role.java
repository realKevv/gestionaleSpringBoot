package com.kevv.gestionale.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Una istanza di questa classe rappresenta un ruolo cdapweb.
 *
 * @author Francesco Frasca
 */
//@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends EntityDTO<Role> {

    /**
     * Identificativo ruolo.
     */
    @Id
    @Column(name = "roleid", length = 15, nullable = false)
    private String roleid;

    /**
     * Descrizione.
     */
    @Column(name = "descr", length = 50)
    private String descr;

//    @JsonManagedReference
//    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)

    @JsonIgnore
    @ManyToMany(mappedBy = "userRoles")
    private Set<User> userRoles = new HashSet<>();



    @Override
    public int compareTo(Role otherRole) {
        return roleid.compareTo(otherRole.getRoleid());
    }
}
