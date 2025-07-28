package com.kevv.gestionale.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="log_wctxparam")
public class LogWctxParam {

        @EmbeddedId
        private  LogWctxParamId id;

        @Column(name = "detail")
        private String detail;
}




