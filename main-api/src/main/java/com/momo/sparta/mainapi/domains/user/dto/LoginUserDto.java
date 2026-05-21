package com.momo.sparta.mainapi.domains.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDto {

    @NotBlank
    @Email
    @Size(max = 128)
    String email;

    @NotBlank
    @Size(min = 6, max = 255)
    String passwd;

}
