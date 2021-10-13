package com.ducninh.freshfood.controller;

import com.ducninh.freshfood.dto.ChangePasswordDTO;
import com.ducninh.freshfood.dto.UserDTO;
import com.ducninh.freshfood.exception.LogicException;
import com.ducninh.freshfood.model.User;
import com.ducninh.freshfood.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<List<User>> findByAll(){
        return new ResponseEntity(userService.findByAll(), HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePasswordUser(@RequestBody ChangePasswordDTO changePasswordDTO) throws LogicException {
        userService.changePassword(changePasswordDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

}
