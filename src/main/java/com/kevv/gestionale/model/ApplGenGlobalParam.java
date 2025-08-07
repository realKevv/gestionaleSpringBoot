package com.kevv.gestionale.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gen-param-global")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class ApplGenGlobalParam {


    @Id
    private String param;


    @Column
    private String descr;

    @Column
    private String regexpr;

    @Column
    private String tip;


    @Column
    private String value;

}
