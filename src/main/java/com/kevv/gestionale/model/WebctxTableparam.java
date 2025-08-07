package com.kevv.gestionale.model;


import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "webctx_tableparam")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class WebctxTableparam {


    @EmbeddedId
    private WebctxGenParamId id;


    @Column(name = "descr", nullable = false, length = 50)
    private String descr;


    @Column(name = "obsolete", nullable = false)
    private boolean obsolete;

}
