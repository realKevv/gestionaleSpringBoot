package com.kevv.gestionale.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kevv.gestionale.model.ModuleType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "module")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Module {

    @Id
    @Column(name = "code", length = 15, nullable = false)
    private String code;

    @Column(name = "descr", length = 100, nullable = false)
    private String descr;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ModuleType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_mod", referencedColumnName = "code", foreignKey = @ForeignKey(name = "module_refmod_fk"))
    private Module refMod;

//    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private List<ModParam> modParams = new ArrayList<>();


}
