package com.kevv.gestionale.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data; // Usa Data per Getter, Setter, Equals, HashCode, ToString
import lombok.NoArgsConstructor;

@Embeddable
@Data // Include @Getter, @Setter, @EqualsAndHashCode, @ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserCompyId implements Serializable {

    @Column(name = "user")
    private String userid;

    @Column(name = "comp")
    private String comp;

    @Column(name = "year")
    private String year;

    // `equals` e `hashCode` sono già gestiti da @Data.
    // Non è necessario sovrascriverli manualmente se @Data è presente.
}