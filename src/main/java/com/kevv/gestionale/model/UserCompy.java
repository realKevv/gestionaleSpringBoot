package com.kevv.gestionale.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data; // Usa Data per Getter, Setter, Equals, HashCode, ToString
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_compy")
@Data // Include @Getter, @Setter, @EqualsAndHashCode, @ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserCompy {

    @EmbeddedId
    private UserCompyId id;

    // Ignora la relazione inversa a User per prevenire loop di serializzazione.
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userid")
    @JoinColumn(name = "user")
    @JsonIgnore
    private User user;

    // Non ignorare Compy per includere i suoi dettagli. FetchType.EAGER carica l'oggetto Compy.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "comp", referencedColumnName = "comp", insertable = false, updatable = false),
            @JoinColumn(name = "year", referencedColumnName = "year", insertable = false, updatable = false)
    })
    private Compy company;

    public String getComp() {
        return company != null ? company.getComp() : (id != null ? id.getComp() : null);
    }

    public String getYear() {
        return company != null ? company.getYear() : (id != null ? id.getYear() : null);
    }

    public Connection getConn() {
        return company != null ? company.getConn() : null;
    }
}