package com.kevv.gestionale.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entity JPA per la tabella connections.
 */
@Entity
@Table(name = "connections")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Connection implements Comparable<Connection> {

    @Id
    @Column(name = "connid", length = 15, nullable = false)
    private String connid;

    @Column(name = "descr", length = 50)
    private String descr;

    @Column(name = "host", length = 30)
    private String host;

    @Column(name = "sez")
    private Integer sez;

    @Column(name = "sub")
    private Integer sub;

    @Column(name = "url", length = 200)
    private String url;

    @Override
    public int compareTo(Connection other) {
        return this.connid.compareTo(other.connid);
    }
}
