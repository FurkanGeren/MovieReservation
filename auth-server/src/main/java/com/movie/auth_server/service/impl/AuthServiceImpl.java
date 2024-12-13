package com.movie.auth_server.service.impl;

import com.movie.auth_server.dto.LoginDTO;
import com.movie.auth_server.dto.RegisterDTO;
import com.movie.auth_server.entity.Role;
import com.movie.auth_server.entity.User;
import com.movie.auth_server.jwt.JwtService;
import com.movie.auth_server.repository.UserRepository;
import com.movie.auth_server.service.AuthService;
import com.movie.auth_server.utiliy.CookieUtility;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Log4j2
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

//    @PostConstruct
//    public void init() {
//        Set<Role> roles = new HashSet<>();
//        roles.add(Role.USER);
//        roles.add(Role.ADMIN);
//        User admin = User.builder()
//                .username("user")
//                .userPassword(passwordEncoder.encode("user"))
//                .email("user@movie.com")
//                .isActive(true)
//                .userFirstName("Furkan")
//                .userLastName("Geren")
//                .roles(roles)
//                .build();
//        userRepository.save(admin);
//    }


    @Override
    public void login(LoginDTO loginDTO, HttpServletResponse response) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUserName(),
                            loginDTO.getPassword()
                    )
            );

            User user = userRepository.findByUsername(loginDTO.getUserName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            String jwtToken = jwtService.generateToken(user);
            log.info("Jwt token: {}", jwtToken);
            Cookie jwtCookie = CookieUtility.createCookie(jwtToken, 86400, "movie_auth_cookie");
            response.addCookie(jwtCookie);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Authentication failed", e);
        }
    }

    @Override
    public void register(RegisterDTO registerDTO, HttpServletResponse response) {
        Set<Role> authorities = new HashSet<>();
        authorities.add(Role.USER);
        User user = User.builder()
                .userFirstName(registerDTO.getUserFirstName())
                .userLastName(registerDTO.getUserLastName())
                .username(registerDTO.getUserName())
                .email(registerDTO.getEmail())
                .userPassword(passwordEncoder.encode(registerDTO.getPassword()))
                .isActive(true)
                .roles(authorities)
                .build();

        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        Cookie jwtCookie = CookieUtility.createCookie(jwtToken, 86400, "movie_auth_cookie");
        response.addCookie(jwtCookie);
    }

}
