package com.kevv.gestionale.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data; // Usa Data per Getter, Setter, Equals, HashCode, ToString
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "compy")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Compy implements Serializable {

    @EmbeddedId
    private CompyId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "conn", referencedColumnName = "connid")
    private Connection conn;

    // Ignora la relazione inversa a UserCompy per prevenire loop di serializzazione.
    // Cambiato a List<UserCompy> per coerenza con User.java
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<UserCompy> userCompies = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("comp")
    @JoinColumn(name = "comp", referencedColumnName = "compid")
    @ToString.Exclude
    @JsonIgnore
    private Company company;

    // Getter di convenienza per accedere ai campi dell'ID
    public String getComp() {
        return id != null ? id.getComp() : null;
    }

    public String getYear() {
        return id != null ? id.getYear() : null;
    }

}