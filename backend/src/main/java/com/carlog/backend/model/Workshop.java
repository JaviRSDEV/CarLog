package com.carlog.backend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="workshop")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "workshopId")
public class Workshop implements Serializable {

    private static final long serialVersionUID = 1L;

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
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<User> employees;

    @OneToMany(mappedBy = "workshop")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Vehicle> vehicles;
}
