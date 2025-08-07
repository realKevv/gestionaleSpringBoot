package com.kevv.gestionale.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entità che mappa la tabella `appl_param` con una chiave primaria composta.
 */
@Entity
@Table(name = "appl_param")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ApplParamId.class) // Specifica la classe per la chiave composta
public class ApplParam {

    @Id
    @Column(name = "appl", length = 15, nullable = false)
    private String appl;

    @Id
    @Column(name = "webctx", length = 30, nullable = false)
    private String webctx;

    @Id
    @Column(name = "param", length = 15, nullable = false)
    private String param;

    @Column(name = "value", length = 500)
    private String value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "webctx", referencedColumnName = "webctx", insertable = false, updatable = false),
            @JoinColumn(name = "param", referencedColumnName = "param", insertable = false, updatable = false)
    })
    private WebctxParam webctxParam;

}
