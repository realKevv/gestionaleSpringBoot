package com.kevv.gestionale.model;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appl_menu")
public class ApplMenu implements Serializable {

    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "descr")
    private String descr;

    @Column(name = "type")
    private String type;

    @Column(name = "is_web")
    private boolean isWeb;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "webctx", referencedColumnName = "context")
    private Webctx webCtx;

    @Column(name = "alias_mod")
    private String aliasMod;

    @Column(name = "maxrow")
    private short maxRow;

    @Column(name = "maxcol")
    private short maxCol;

    @Column(name = "standard")
    private boolean standard;





}
