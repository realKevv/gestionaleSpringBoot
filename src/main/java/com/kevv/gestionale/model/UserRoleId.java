package com.kevv.gestionale.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleId implements Serializable {
    private String userid;
    private String roleid;


    // equals, hashCode, getters, setters
}