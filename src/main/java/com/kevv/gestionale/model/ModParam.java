package com.kevv.gestionale.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "mod_param")
@Data
public class ModParam {

    @EmbeddedId
    private ModParamId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("module")   // mappa il campo roleid di UserRoleId
    @JoinColumn(name = "module")
    private Module module;

    @Column
    private String descr;
    @Column
    private String regexpr;
    @Column
    private String tip;
    @Column
    private String value;
}
