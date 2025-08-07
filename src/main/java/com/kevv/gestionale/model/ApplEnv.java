package com.kevv.gestionale.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "appl_env")
@IdClass(ApplEnvId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ApplEnv {


    @Id
    @Column(name = "appl", length = 15)
    private String appl;


    @Id
    @Column(name = "webctx", length = 30)
    private String webctx;

    @Id
    @Column(name = "param", length = 15)
    private String param;


    @Column(name = "value", length = 100)
    private  String value;


    @Column(name = "descr", length = 100)
    private String descr;


    @ManyToOne
    @JoinColumn(name = "appl", referencedColumnName = "code", insertable = false, updatable = false)
    private ApplMenu applMenu;


    @ManyToOne
    @JoinColumn(name = "webctx", referencedColumnName = "context", insertable = false, updatable = false)
    private Webctx webctxObj;

}

