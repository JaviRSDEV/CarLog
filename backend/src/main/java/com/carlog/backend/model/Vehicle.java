package com.carlog.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plate", unique = true, nullable = false)
    private String plate;

    @Column(name = "Brand")
    private String brand;

    @Column(name = "Model")
    private String model;

    @Column(name = "Kilometers")
    private Long kilometers;

    @Column(name = "Engine")
    private String engine;

    @Column(name = "Horsepower")
    private int horsePower;

    @Column(name = "Torque")
    private int torque;

    @Column(name = "Tires")
    private String tires;

    @Column(name = "Image")
    private String image;

    @Column(name = "LastMaintenance")
    LocalDate lastMaintenance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workshop_id")
    @JsonIgnoreProperties({"vehicles", "workshop", "hibernateLazyInitializer", "handler"})
    private Workshop workshop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @JsonIgnoreProperties({"vehicles", "workshop", "hibernateLazyInitializer", "handler"})
    private User owner;
}
