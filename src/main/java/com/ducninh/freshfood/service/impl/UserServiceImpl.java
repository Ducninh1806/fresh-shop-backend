package com.ducninh.freshfood.service.impl;

import com.ducninh.freshfood.dto.ChangePasswordDTO;
import com.ducninh.freshfood.dto.UserDTO;
import com.ducninh.freshfood.exception.ErrorCode;
import com.ducninh.freshfood.exception.LogicException;
import com.ducninh.freshfood.model.User;
import com.ducninh.freshfood.repository.UserRepository;
import com.ducninh.freshfood.security.SecurityUtil;
import com.ducninh.freshfood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public List<User> findByAll() {
//        List<User> users = ;
        return userRepository.findAll();
    }

    @Override
    public UserDTO findById() {
        return null;
    }

    @Override
    public void create() {

    }

    @Override
    public void update(UserDTO userDTO) {

    }


    @Override
    public void delete(Long id) {

    }

    @Override
    public void changePassword(ChangePasswordDTO changePasswordDTO) throws LogicException {
        Long userId = SecurityUtil.getCurrentUserIdLogin();
        Optional<User> userOptional = userRepository.findById(userId);

        if (!userOptional.isPresent()){
            throw new LogicException(ErrorCode.RECORD_NOT_FOUND);
        }
        User user = userOptional.get();
        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())){
            throw new LogicException(ErrorCode.CURRENT_PASSWORD_INCORRECT);
        }
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(user);
    }
}
