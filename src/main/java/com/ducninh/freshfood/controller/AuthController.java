package com.ducninh.freshfood.controller;

import com.ducninh.freshfood.dto.JwtResponse;
import com.ducninh.freshfood.dto.LoginDTO;
import com.ducninh.freshfood.dto.MessageResponse;
import com.ducninh.freshfood.dto.SignUpDTO;
import com.ducninh.freshfood.exception.ErrorCode;
import com.ducninh.freshfood.exception.LogicException;
import com.ducninh.freshfood.model.ERole;
import com.ducninh.freshfood.model.Role;
import com.ducninh.freshfood.model.User;
import com.ducninh.freshfood.repository.RoleRepository;
import com.ducninh.freshfood.repository.UserRepository;
import com.ducninh.freshfood.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("signin")
    public ResponseEntity authenticateUser (@Valid @RequestBody LoginDTO loginDTO) throws LogicException {
        try {
            Authentication authentication =
                    authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateJwtToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
        } catch (Exception e){
            throw new LogicException(ErrorCode.FORBIDDEN);
        }


//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Authoriazition", String.format("Bearer %s", jwt));
//        return new ResponseEntity<>(Collections.singletonMap("id_token", jwt), httpHeaders, HttpStatus.OK);

    }

    @PostMapping("signup")
    public ResponseEntity registerUser(@Valid @RequestBody SignUpDTO signUpDTO) {
        if (userRepository.existsByUsername(signUpDTO.getUsername())) {
            return new ResponseEntity(new MessageResponse("Fail -> Username is already taken!"), HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpDTO.getEmail())) {
            return new ResponseEntity(new MessageResponse("Fail -> Email is already taken"), HttpStatus.BAD_REQUEST);
        }

        User user = new User(signUpDTO.getFirstName(), signUpDTO.getLastName(), signUpDTO.getUsername(), signUpDTO.getEmail(), passwordEncoder.encode(signUpDTO.getPassword()));
        Set<String> strRoles = signUpDTO.getRoles();
        Set<Role> roles = new HashSet();
        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(adminRole);
                    break;
                case "super_admin":
                    Role superAdminRole = roleRepository.findByName(ERole.ROLE_SUPER_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(superAdminRole);
                    break;
                default:
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(userRole);
            }
        });

        user.setRoles(roles);
        userRepository.save(user);

        return new ResponseEntity(new MessageResponse("User registered successfully!"), HttpStatus.OK);
    }
}
