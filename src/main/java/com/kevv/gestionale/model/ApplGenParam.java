//package com.kevv.gestionale.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Table(name = "appl_gen_param")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@IdClass(ApplGenParamId.class)
//public class ApplGenParam {
//
//    @Id
//    @Column(name = "appl_code", length = 20, nullable = false)
//    private String applCode;
//
//    @Id
//    @Column(name = "param", length = 15, nullable = false)
//    private String param;
//
//    @Column(name = "value", length = 200)
//    private String value;
//
//    // Relazione con GenParam per ottenere descrizione, regex, tip
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "param", referencedColumnName = "param", insertable = false, updatable = false)
//    private GenParam genParam;
//
//    // Relazione con ApplMenu
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "appl_code", referencedColumnName = "code", insertable = false, updatable = false)
//    private ApplMenu applMenu;
//}