package com.ducninh.freshfood.service;

import com.ducninh.freshfood.dto.ChangePasswordDTO;
import com.ducninh.freshfood.dto.UserDTO;
import com.ducninh.freshfood.exception.LogicException;
import com.ducninh.freshfood.model.User;

import java.util.List;

public interface UserService {

    List<User> findByAll();
    UserDTO findById();
    void create();
    void update(UserDTO userDTO);
    void delete(Long id);

    void changePassword(ChangePasswordDTO changePasswordDTO) throws LogicException;
}
