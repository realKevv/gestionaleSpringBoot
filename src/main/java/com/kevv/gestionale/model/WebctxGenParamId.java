package com.kevv.gestionale.model;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor


public class WebctxGenParamId extends WebctxTableParamId implements Serializable {

    @Column(name = "webctx", length = 15)
    private  String webctx;

    @Column(name = "param", length = 15)
    private String param;

    @Override
    public boolean equals(Object o ) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebctxGenParamId that = (WebctxGenParamId)  o;
        return Objects.equals(webctx, that.webctx) && Objects.equals(param, that.param);
    }

        @Override
    public int hashCode() {
        return Objects.hash(webctx, param);
        }

}
