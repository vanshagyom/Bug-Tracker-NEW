package com.practice.OneYear.dto.userDTOS;

import com.practice.OneYear.entity.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInputDTO {

    @NotBlank(message = "User's name can't be blank")
    private String userName;

    @NotBlank(message = "User's email can't be blank")
    private String userEmail;

}
