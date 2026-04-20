package com.carlog.backend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="vehicles")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

    @ElementCollection
    @CollectionTable(
            name = "vehicle_images",
            joinColumns = @JoinColumn(name = "vehicle_id")
    )

    @Column(name = "Image")
    private List<String> images = new ArrayList<>();

    @Column(name = "LastMaintenance")
    LocalDate lastMaintenance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workshop_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Workshop workshop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pending_workshop_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Workshop pendingWorkshop;


}
