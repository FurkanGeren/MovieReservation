package com.movie.auth_server.service;

import com.movie.auth_server.dto.LoginDTO;
import com.movie.auth_server.dto.RegisterDTO;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    void login(LoginDTO loginDTO, HttpServletResponse response);

    void register(RegisterDTO registerDTO, HttpServletResponse response);
}
