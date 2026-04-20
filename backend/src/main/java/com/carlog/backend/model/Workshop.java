package com.carlog.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="workshop")
public class Workshop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workshopId;

    @Column(name = "name", nullable = false)
    private String workshopName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone", nullable = false)
    private String workshopPhone;

    @Column(name = "email")
    private String workshopEmail;

    @Column(name = "icon")
    private String icon;

    @OneToMany(mappedBy = "workshop", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<User> employees;

    @OneToMany(mappedBy = "workshop")
    @JsonIgnoreProperties("workshop")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Vehicle> vehicles;
}
