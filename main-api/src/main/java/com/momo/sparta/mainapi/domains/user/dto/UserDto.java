package com.momo.sparta.mainapi.domains.user.dto;

import com.momo.sparta.commonmysqldb.entity.UserRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    @NotBlank
    @Size(min = 2, max = 255)
    String name;

    @NotBlank
    @Email
    @Size(max = 128)
    String email;

    UserRoleEnum role;

    Boolean isLoginned = false;
}
