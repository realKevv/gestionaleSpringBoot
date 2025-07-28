package com.kevv.gestionale.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity JPA per la tabella gen_param.
 * Rappresenta un parametro generale.
 */
@Entity
@Table(name = "gen_param")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GenParam extends EntityDTO<GenParam> {

    @Id
    @Column(name = "param", length = 15, nullable = false)
    private String param;

    @Column(name = "descr", length = 50)
    private String descr;

    @Column(name = "regexpr", length = 100)
    private String regexpr;

    @Column(name = "tip", length = 200)
    private String tip;

    @Column(name = "value", length = 200)
    private String value;

    @Transient
    public int getValueLength() {
        if (value == null) return 0;
        int maxLength = 0;
        String[] lines = value.split("\n");
        for (String line : lines) {
            if (line.length() > maxLength) maxLength = line.length();
        }
        return maxLength;
    }

    @Transient
    public int getValueRows() {
        if (value == null || value.isEmpty()) return 0;
        return value.split("\n").length;
    }

    @Override
    public int compareTo(GenParam other) {
        return this.param.compareTo(other.getParam());
    }
}