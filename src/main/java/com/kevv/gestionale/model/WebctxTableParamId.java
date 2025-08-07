package com.kevv.gestionale.model;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor

public class WebctxTableParamId {


        @Column(name = "webctx")
        private String webctx;

        @Column(name = "param")
        private String param;
}
