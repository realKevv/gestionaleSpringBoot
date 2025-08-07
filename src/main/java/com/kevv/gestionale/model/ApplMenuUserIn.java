package com.kevv.gestionale.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "applmenu_users_in")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class ApplMenuUserIn {

    @EmbeddedId
    private ApplMenuUserKeyId id;


    @ManyToOne
    @MapsId("applmenu")
    @JoinColumn( name = "applmenu", referencedColumnName = "code")
    private ApplMenu applMenu;

    @ManyToOne
    @MapsId("user")
    @JoinColumn(name = "user", referencedColumnName = "userid")
    private User user;


}
