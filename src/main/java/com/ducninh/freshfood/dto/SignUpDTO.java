package com.ducninh.freshfood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    @NotBlank
    private String password;

    private Set<String> roles;
}
