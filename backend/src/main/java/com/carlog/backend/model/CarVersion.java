package com.carlog.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "car_version")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", nullable = false)
    private CarModel carModel;

    @Column(name = "version_name")
    private String versionName;

    @Column(name = "engine_code")
    private String engineCode;

    @Column(name = "engine_type")
    private String engineType;

    @Column(name = "fuel_type")
    private String fuelType;

    @Column(name = "power_cv")
    private Integer powerCv;

    @Column(name = "torque_nm")
    private Integer torque;

    @Column(name = "year_start")
    private Integer yearStart;

    @Column(name = "year_end")
    private Integer yearEnd;

}
