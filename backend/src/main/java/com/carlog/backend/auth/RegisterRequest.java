package com.carlog.backend.auth;

import com.carlog.backend.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
        private String dni;
        private String name;
        private String email;
        private String password;
        private Role role;
}
