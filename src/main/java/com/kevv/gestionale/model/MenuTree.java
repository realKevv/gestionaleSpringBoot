package com.kevv.gestionale.model;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "menu_tree")
@Data

public class MenuTree {

    @EmbeddedId
    private MenuTreeId id;

    private short row;

    private short col;

    private boolean ismain;

}






