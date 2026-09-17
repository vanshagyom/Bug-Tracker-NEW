package com.practice.OneYear.mapper.userMapper;

import com.practice.OneYear.dto.userDTOS.UserInputDTO;
import com.practice.OneYear.dto.userDTOS.UserOutputDTO;
import com.practice.OneYear.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public User toEntityFromInputDTO(UserInputDTO userInputDTO){
        if (userInputDTO== null){
            return null;
        }
        return User.builder()
                .name(userInputDTO.getUserName())
                .email(userInputDTO.getUserEmail())
//                .userRole(userInputDTO.getUserRole())
                .created_at(LocalDateTime.now())
                .build();
    }

    public UserOutputDTO toOutputDTOfromEntity(User user){
        if (user == null){
            return null;
        }
        return UserOutputDTO.builder()
                .UserName(user.getName())
                .userRole(user.getUserRole())
                .userId(user.getUserId())
                .userCreatedAt(user.getCreated_at())
                .UserEmail(user.getEmail())
                .build();
    }

}
