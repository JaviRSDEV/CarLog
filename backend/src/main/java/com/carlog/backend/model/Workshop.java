package com.carlog.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private long workshopId;

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
    @JsonIgnore // ¡IMPORTANTE! Para que al pedir un taller no te devuelva los usuarios, y los usuarios el taller...
    private List<User> employees;
}
