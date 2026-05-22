package com.momo.sparta.mainapi.domains.user.dto;

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
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto implements Serializable {

    @NotBlank
    @Size(min = 2, max = 128)
    String name;

    @NotBlank
    @Email
    @Size(min = 6, max = 128)
    String email;

    @NotBlank
    @Size(min = 6, max = 255)
    String passwd;

}
