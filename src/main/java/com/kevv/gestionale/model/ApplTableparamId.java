package com.kevv.gestionale.model;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ApplTableparamId implements Serializable {

        @Column(name = "appl")
        private String appl;

        @Column(name = "webctx")
        private String webctx;

        @Column(name = "param")
        private  String param;

}
