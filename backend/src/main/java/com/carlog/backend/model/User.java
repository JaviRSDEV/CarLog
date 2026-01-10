package com.carlog.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")
public class User {

    @Id
    private String dni;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = true)
    private String phone;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "must_change_psswd")
    private boolean mustChangePsswd;

    @ManyToOne(fetch = FetchType.LAZY) // Carga el taller solo si se lo pides
    @JoinColumn(name = "workshop_id")  // Nombre de la columna en la tabla SQL
    @JsonIgnoreProperties({"employees", "hibernateLazyInitializer", "handler"}) // EVITA BUCLE INFINITO
    private Workshop workshop;
}
