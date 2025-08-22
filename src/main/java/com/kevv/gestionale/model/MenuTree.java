package com.kevv.gestionale.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "menu_tree")
@Getter
@Setter
public class MenuTree {

    @EmbeddedId
    private MenuTreeId id;

    @Column(name = "row", nullable = false)
    private int row = 1;

    @Column(name = "col", nullable = false)
    private int col = 1;

    @Column(name = "ismain", nullable = false)
    private boolean isMain = false;

    @Transient
    private boolean Folder;
}
