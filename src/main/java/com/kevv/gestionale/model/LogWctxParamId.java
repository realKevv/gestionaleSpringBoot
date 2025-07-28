package com.kevv.gestionale.model;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Embeddable


public class LogWctxParamId implements Serializable {

        @Column(name = "date")
            private Date date;

        @Column(name = "time")
            private Time time;

        @Column(name = "webctx")
            private String webctx;

        @Column(name = "param")
            private String param;

        @Column(name = "type")
            private String type;

        @Column(name = "action")
            private String action;

}



