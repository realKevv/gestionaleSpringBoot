package com.kevv.gestionale.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "webctx_param")
@IdClass(WebctxGenParamId.class)
@Data

public class WebctxParam {

    @Id
    @Column(name = "webctx", nullable = false, length = 30)
    private String webctx;

    @Id
    @Column(name = "param", nullable = false, length = 15)
    private String param;

    @Column(name = "descr", nullable = false, length = 50)
    private String descr;

    @Column(name = "required", nullable = false)
    private boolean required;

    @Column(name = "regexpr", length = 100)
    private String regexpr;

    @Column(name = "tip", length = 200)
    private String tip;

    @Column(name = "obsolete", nullable = false)
    private boolean obsolete;
}