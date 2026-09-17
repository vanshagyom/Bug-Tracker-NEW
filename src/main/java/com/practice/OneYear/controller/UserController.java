package com.practice.OneYear.controller;

import com.practice.OneYear.dto.userDTOS.UserInputDTO;
import com.practice.OneYear.dto.userDTOS.UserOutputDTO;
import com.practice.OneYear.entity.enums.UserRole;
import com.practice.OneYear.service.userService.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserOutputDTO>> getAllUsers(){
        System.out.println("User Controller Reached ");
        return ResponseEntity.ok(userService.allUsers());
    }

    @PostMapping
    public ResponseEntity<UserOutputDTO> addUser(@Valid @RequestBody UserInputDTO userInputDTO,
                                                 @RequestParam UserRole userRole){
        return ResponseEntity.ok(userService.addUser(userInputDTO,userRole));
    }

}
