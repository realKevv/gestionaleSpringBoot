package com.kevv.gestionale.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * Classe per la chiave primaria composta dell'entità ApplParam.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplParamId implements Serializable {

    private String appl;
    private String webctx;
    private String param;
}
