package com.ducninh.freshfood.controller;

import com.ducninh.freshfood.dto.ChangePasswordDTO;
import com.ducninh.freshfood.dto.UserDTO;
import com.ducninh.freshfood.exception.LogicException;
import com.ducninh.freshfood.model.User;
//import com.ducninh.freshfood.service.impl.EmailServiceImpl;
import com.ducninh.freshfood.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServiceImpl userService;

//    @Autowired
//    private EmailServiceImpl emailService;

    @GetMapping
    public ResponseEntity<List<User>> findByAll(){
        return new ResponseEntity(userService.findByAll(), HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePasswordUser(@RequestBody ChangePasswordDTO changePasswordDTO) throws LogicException {
        logger.info("Controller info about change password user :{}", changePasswordDTO);

        userService.changePassword(changePasswordDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Long id) throws LogicException {
        logger.info("Controller info about get detail user :{}", id);

        UserDTO user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UserDTO userDTO) throws LogicException {
        logger.info("Controller info about update user :{}", userDTO);

        userService.update(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws LogicException {
        logger.info("Controller info about delete user :{}", id);

        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
