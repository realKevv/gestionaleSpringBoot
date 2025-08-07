package com.kevv.gestionale.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.*;


@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Data
@Setter
public class ModParamId implements Serializable {
    private String module;
    private String param;
}
