package com.kevv.gestionale.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor


public class ApplEnvId  implements Serializable {

    private String appl;
    private String webctx;
    private String param;


}
