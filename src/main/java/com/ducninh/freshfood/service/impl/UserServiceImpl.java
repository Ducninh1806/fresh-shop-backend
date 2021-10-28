package com.ducninh.freshfood.service.impl;

import com.ducninh.freshfood.dto.ChangePasswordDTO;
import com.ducninh.freshfood.dto.ForgotPasswordDTO;
import com.ducninh.freshfood.dto.UserDTO;
import com.ducninh.freshfood.exception.ErrorCode;
import com.ducninh.freshfood.exception.LogicException;
import com.ducninh.freshfood.model.User;
import com.ducninh.freshfood.repository.UserRepository;
import com.ducninh.freshfood.security.SecurityUtil;
import com.ducninh.freshfood.service.UserService;
import com.ducninh.freshfood.service.mapper.UserMapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findByAll() {
        logger.info("Service info about findAll user");

//        List<User> users = ;
        return userRepository.findAll();
    }

    @Override
    public UserDTO findById(Long id) throws LogicException {
        logger.info("Service info about detail user :{}", id);
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new LogicException(ErrorCode.RECORD_NOT_EXISTED);
        }
        return modelMapper.map(user.get(), UserDTO.class);
    }

    @Override
    public void update(UserDTO userDTO) throws LogicException {
        logger.info("Service info about update user :{}", userDTO);

        Optional<User> userOptional = userRepository.findById(userDTO.getId());
        if (!userOptional.isPresent()){
            throw new LogicException(ErrorCode.RECORD_NOT_EXISTED);
        }
        User user = userOptional.get();
        user.setEmail(userDTO.getEmail());
        user.setAddress(userDTO.getAddress());
        user.setAvatar(userDTO.getAvatar());
        user.setBirthDay(userDTO.getBirthDay());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        userRepository.save(user);
    }


    @Override
    public void delete(Long id) throws LogicException {
        logger.info("Service info about delete user :{}", id);

        Optional<User> user = userRepository.findById(id);
        if ( !user.isPresent()){
            throw new LogicException(ErrorCode.RECORD_NOT_EXISTED);
        }
        userRepository.delete(user.get());
    }

    @Override
    public void changePassword(ChangePasswordDTO changePasswordDTO) throws LogicException {
        logger.info("Service info about change password user :{}", changePasswordDTO);

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

    @Override
    public void forgotPassword(ForgotPasswordDTO forgotPasswordDTO) throws LogicException {
        logger.info("Service info about forgot password user :{}", forgotPasswordDTO);

        Optional<User> userOptional = userRepository.findByUsername(forgotPasswordDTO.getUsername());
        if (!userOptional.isPresent()) {
            throw new LogicException(ErrorCode.RECORD_NOT_EXISTED);
        }
        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(forgotPasswordDTO.getNewPassword()));
        userRepository.save(user);
    }
}
