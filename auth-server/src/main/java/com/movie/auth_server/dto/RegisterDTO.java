package com.movie.auth_server.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {


    @NotBlank(message = "Username can not be empty.")
    private String userName;

    @NotBlank(message = "Email can not be empty.")
    @Email
    private String email;

    @NotBlank(message = "Password can not be empty.")
    private String password;

    @NotBlank(message = "First name can not be empty.")
    private String userFirstName;

    @NotBlank(message = "Last name can not be empty.")
    private String userLastName;

}
