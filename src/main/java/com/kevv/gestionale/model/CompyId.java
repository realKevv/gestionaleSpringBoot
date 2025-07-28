package com.kevv.gestionale.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data; // Usa Data per Getter, Setter, Equals, HashCode, ToString
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data // Include @Getter, @Setter, @EqualsAndHashCode, @ToString
@NoArgsConstructor
@AllArgsConstructor
public class CompyId implements Serializable {

    @Column(name = "comp")
    private String comp;

    @Column(name = "year")
    private String year;
}