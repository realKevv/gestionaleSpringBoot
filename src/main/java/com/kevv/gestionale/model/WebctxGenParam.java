package com.kevv.gestionale.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "webctx_genparam")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class WebctxGenParam {

    @EmbeddedId
    private WebctxGenParamId id;

    @Column(name = "required", nullable = false)
    private boolean required;

    @Column(name = "obsolete", nullable = false)
    private boolean obsolete;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "param", referencedColumnName = "param", insertable = false, updatable = false)
    private GenParam genParam;


}
