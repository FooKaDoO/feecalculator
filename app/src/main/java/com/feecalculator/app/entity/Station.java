package com.feecalculator.app.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@NoArgsConstructor
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Setter
    private String name;

    @Setter
    private Integer wmocode;

    @Setter
    private Double airtemperature;

    @Setter
    private Double windspeed;

    @Setter
    private String phenomenon;

    @Setter
    private Long timestamp;
}
