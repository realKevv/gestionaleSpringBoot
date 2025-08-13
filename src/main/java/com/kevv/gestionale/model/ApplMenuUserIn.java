package com.kevv.gestionale.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "applmenu_users_in")
@IdClass(ApplMenuUserKeyId.class)

public class ApplMenuUserIn {

    @Id
    @Column(length = 15)
    private String applmenu;

    @Id
    @Column(length = 15)
    private String user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applmenu", insertable = false, updatable = false)
    private ApplMenu applMenu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", insertable = false, updatable = false)
    private User userEntity;

    // Costruttore custom che accetta le entità e setta anche le chiavi (utile in controller)
    public ApplMenuUserIn(ApplMenu applMenu, User userEntity) {
        this.applmenu = applMenu.getCode();
        this.user = userEntity.getUserid();
        this.applMenu = applMenu;
        this.userEntity = userEntity;
    }
}