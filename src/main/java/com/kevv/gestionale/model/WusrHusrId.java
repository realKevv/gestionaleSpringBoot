
package com.kevv.gestionale.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WusrHusrId implements Serializable {
    private String host;
    private String husr;
}