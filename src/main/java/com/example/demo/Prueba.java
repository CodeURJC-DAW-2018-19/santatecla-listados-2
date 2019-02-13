package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Prueba {
    @Id
    int columna;
    @Column
    int yup;

    public Prueba(){}
}

