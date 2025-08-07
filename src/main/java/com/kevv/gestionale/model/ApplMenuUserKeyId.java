package com.kevv.gestionale.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplMenuUserKeyId implements Serializable {

    private static final long serialVersionUID = 1L;

    private String applmenu;

    private String user;
}