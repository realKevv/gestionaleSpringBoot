package com.kevv.gestionale.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "log_launch")
@Data                   // Genera getter, setter, toString, equals, hashCode
@NoArgsConstructor       // Genera costruttore vuoto
@AllArgsConstructor      // Genera costruttore con tutti i campi
 public class LogLaunch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer logid;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "applcode")
    private String applCode;

    @Column(name = "type")
    private String type;

    @Column(name = "webuser")
    private String webuser;

    @Column(name = "from_ip")
    private String fromIp;

    @Column(name = "hostuser")
    private String hostuser;

    @Column(name = "company")
    private String company;

    @Column(name = "year")
    private String year;

    @Column(name = "from_logid")
    private Integer fromLogid;

    @Column(name = "end_time")
    private LocalDateTime endTime;
}
