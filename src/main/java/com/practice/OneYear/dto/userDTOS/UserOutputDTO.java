package com.practice.OneYear.dto.userDTOS;

import com.practice.OneYear.entity.enums.UserRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserOutputDTO {

    private Long userId;

    private String UserName;

    private String UserEmail;

    private UserRole userRole;

    private LocalDateTime userCreatedAt;

}
