package com.practice.OneYear.service.userService;

import com.practice.OneYear.dto.userDTOS.UserInputDTO;
import com.practice.OneYear.dto.userDTOS.UserOutputDTO;
import com.practice.OneYear.entity.enums.UserRole;

import java.util.List;

public interface UserService {
    List<UserOutputDTO> allUsers();
    UserOutputDTO addUser(UserInputDTO userInputDTO, UserRole userRole);
}
