package com.kevv.gestionale.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "appl_tableparam")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ApplTableparam {

    @EmbeddedId
    private  ApplTableparamId id;

    @Column(name = "value", nullable = false, length = 200)
    private String value;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "webctx", referencedColumnName = "webctx", insertable = false, updatable = false),
            @JoinColumn(name = "param", referencedColumnName = "param", insertable = false, updatable = false)
    })
    private WebctxTableparam webctxTableparam;



}
