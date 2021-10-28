package com.ducninh.freshfood.service;

import com.ducninh.freshfood.dto.ChangePasswordDTO;
import com.ducninh.freshfood.dto.ForgotPasswordDTO;
import com.ducninh.freshfood.dto.UserDTO;
import com.ducninh.freshfood.exception.LogicException;
import com.ducninh.freshfood.model.User;

import java.util.List;

public interface UserService {

    List<User> findByAll();
    UserDTO findById(Long id) throws LogicException;
    void update(UserDTO userDTO) throws LogicException;
    void delete(Long id) throws LogicException;

    void changePassword(ChangePasswordDTO changePasswordDTO) throws LogicException;
    void forgotPassword(ForgotPasswordDTO forgotPasswordDTO) throws LogicException;
}
