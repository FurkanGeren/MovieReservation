package com.movie.auth_server.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @NotBlank(message = "Username can not be empty.")
    private String userName;

    @NotBlank(message = "Password can not be empty.")
    private String password;

}