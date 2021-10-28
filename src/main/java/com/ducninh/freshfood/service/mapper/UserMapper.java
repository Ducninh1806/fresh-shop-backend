package com.ducninh.freshfood.service.mapper;

import com.ducninh.freshfood.dto.UserDTO;
import com.ducninh.freshfood.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public void entityToDTO (User user, UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setAddress(user.getAddress());
        userDTO.setAvatar(user.getAvatar());
        userDTO.setEmail(user.getEmail());
        userDTO.setBirthDay(user.getBirthDay());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setStatus(user.getStatus());

    }

    public void dtoToEntity (User user, UserDTO userDTO) {
        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAddress(userDTO.getAddress());
        user.setAvatar(userDTO.getAvatar());
        user.setEmail(userDTO.getEmail());
        user.setBirthDay(userDTO.getBirthDay());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setStatus(userDTO.getStatus());

    }

}
