package com.kevv.gestionale.model;

import jakarta.persistence.*;
import lombok.*;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "webgen_host")
public class WebgenHost {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    @Id
    private String id; // This will be the same as user.userid

    @OneToOne
    @MapsId // This tells JPA that the ID of this entity is the same as the user's ID
    @JoinColumn(name = "user", referencedColumnName = "userid",
            foreignKey = @ForeignKey(name = "webgenhost_userid_fk"),
            nullable = false)
    private User user;


//    @Id
//    @ManyToOne
//    @JoinColumn(name = "user", referencedColumnName = "userid",
//            foreignKey = @ForeignKey(name = "webgenhost_userid_fk"),
//            nullable = false)
//    private User user;

    @Column(name = "azi_min", columnDefinition = "INT DEFAULT 1")
    @Builder.Default
    private Integer aziMin = 1;

    @Column(name = "azi_max", columnDefinition = "INT DEFAULT 99")
    @Builder.Default
    private Integer aziMax = 99;

    @Column(name = "anno_min", columnDefinition = "INT DEFAULT 0")
    @Builder.Default
    private Integer annoMin = 0;

    @Column(name = "anno_max", columnDefinition = "INT DEFAULT 99")
    @Builder.Default
    private Integer annoMax = 99;

    @Column(name = "dev_stampa", length = 6, columnDefinition = "VARCHAR(6) DEFAULT '      '")
    @Builder.Default
    private String devStampa = "      ";

    @Column(name = "tipo_spo", length = 1, columnDefinition = "VARCHAR(1) DEFAULT 'A'")
    @Builder.Default
    private String tipoSpo = "A";

    @Column(name = "num_linee", columnDefinition = "INT DEFAULT 66")
    @Builder.Default
    private Integer numLinee = 66;

    @Column(name = "num_col", columnDefinition = "INT DEFAULT 132")
    @Builder.Default
    private Integer numCol = 132;

    @Column(name = "menu_home", length = 10, columnDefinition = "VARCHAR(10) DEFAULT '----------'")
    @Builder.Default
    private String menuHome = "----------";
}
