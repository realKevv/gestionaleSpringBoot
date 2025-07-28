package com.kevv.gestionale.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "wusr_husr")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WusrHusr  {

    @EmbeddedId
    private WusrHusrId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wusr")
    @JsonIgnore
//    @JsonBackReference
    private User wusr;


    @Column(name = "husr_uid", nullable = false, length = 10)
    private String husrUid = "-1";

    @Column(name = "ismain", nullable = false)
    private Boolean isMain = true;
}
