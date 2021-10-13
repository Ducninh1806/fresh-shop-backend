package com.ducninh.freshfood.dto;

import com.ducninh.freshfood.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private Instant birthDay;
    private String address;
    private String avatar;
    private String email;
    private String phoneNumber;
    private Integer status;
    private Set<Role> roles = new HashSet<>();
}
