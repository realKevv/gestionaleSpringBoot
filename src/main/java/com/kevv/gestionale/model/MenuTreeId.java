package com.kevv.gestionale.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class MenuTreeId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "applmenu", referencedColumnName = "code")
    private ApplMenu applmenu;

    @ManyToOne
    @JoinColumn(name = "parent", referencedColumnName = "code")
    private ApplMenu parent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuTreeId)) return false;
        MenuTreeId that = (MenuTreeId) o;
        return Objects.equals(applmenu, that.applmenu) &&
                Objects.equals(parent, that.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applmenu, parent);
    }
}
