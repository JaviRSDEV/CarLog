package com.carlog.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "car_brands")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
}
