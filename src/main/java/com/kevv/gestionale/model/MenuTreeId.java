package com.kevv.gestionale.model;



import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor


public class MenuTreeId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "applmenu", referencedColumnName = "code")
    private ApplMenu applmenu;

    @ManyToOne
    @JoinColumn(name = "parent", referencedColumnName = "code")
    private ApplMenu parent;


}

