package com.kevv.gestionale.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "webctx")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Webctx implements Serializable {

    @Id
    @Column(name = "context")
    private String context;
}
