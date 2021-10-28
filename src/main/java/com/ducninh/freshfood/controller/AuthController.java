package com.ducninh.freshfood.controller;

import com.ducninh.freshfood.dto.*;
import com.ducninh.freshfood.exception.ErrorCode;
import com.ducninh.freshfood.exception.LogicException;
import com.ducninh.freshfood.model.ERole;
import com.ducninh.freshfood.model.Role;
import com.ducninh.freshfood.model.User;
import com.ducninh.freshfood.repository.RoleRepository;
import com.ducninh.freshfood.repository.UserRepository;
import com.ducninh.freshfood.security.jwt.JwtProvider;
import com.ducninh.freshfood.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("signin")
    public ResponseEntity authenticateUser (@Valid @RequestBody LoginDTO loginDTO) throws LogicException {
        logger.info("Controller info about authenticate User :{}", loginDTO);

        Authentication authentication =
                authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping("signup")
    public ResponseEntity registerUser(@Valid @RequestBody SignUpDTO signUpDTO) {
        logger.info("Controller info about register User :{}", signUpDTO);

        if (userRepository.existsByUsername(signUpDTO.getUsername())) {
            return new ResponseEntity(new MessageResponse("Fail -> Username is already taken!"), HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpDTO.getEmail())) {
            return new ResponseEntity(new MessageResponse("Fail -> Email is already taken"), HttpStatus.BAD_REQUEST);
        }

        User user = new User(signUpDTO.getFirstName(), signUpDTO.getLastName(), signUpDTO.getUsername(), signUpDTO.getEmail(), passwordEncoder.encode(signUpDTO.getPassword()));
        if (signUpDTO.getRoles() == null ) {
            signUpDTO.setRoles(Collections.singleton("user"));
        }
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

    @PostMapping("/forgot-password")
    public ResponseEntity forgotPasswordUser(@RequestBody ForgotPasswordDTO forgotPasswordDTO) throws LogicException {
        logger.info("Controller info about forgot Password User :{}", forgotPasswordDTO);
        userService.forgotPassword(forgotPasswordDTO);
        return new ResponseEntity(HttpStatus.OK);
    }
}
