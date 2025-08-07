package com.kevv.gestionale.controller;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "webctx")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebctxApplMenu {

    @Id
    @Column(name = "context")
    private String context;
}
