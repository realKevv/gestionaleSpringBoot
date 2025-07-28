
package com.kevv.gestionale.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * Entity JPA per la tabella company.
 * Rappresenta una azienda.
 *
 * @author Francesco
 */
@Entity
@Table(name = "company")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Company implements Serializable, Comparable<Company> {

    @Id
    @Column(name = "compid", length = 10, nullable = false)
    private String compid;

    @Column(name = "descr", length = 50)
    private String descr;


    @Override
    public int compareTo(Company other) {
        return this.compid.compareTo(other.compid);
    }
}