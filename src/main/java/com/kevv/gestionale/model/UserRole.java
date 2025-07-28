package com.kevv.gestionale.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_roles")
@Getter
@Setter
public class UserRole {

    @EmbeddedId
    private UserRoleId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userid")   // mappa il campo userid di UserRoleId
    @JoinColumn(name = "userid")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleid")   // mappa il campo roleid di UserRoleId
    @JoinColumn(name = "roleid")
    private Role role;

    public UserRole() {}

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
        this.id = new UserRoleId();
        this.id.setUserid(user.getUserid());
        this.id.setRoleid(role.getRoleid());
    }
}
