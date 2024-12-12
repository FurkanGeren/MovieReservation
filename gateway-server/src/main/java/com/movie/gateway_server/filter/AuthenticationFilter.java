package com.movie.gateway_server.filter;


import com.movie.gateway_server.configuration.RouterValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@RefreshScope
@Component
@Log4j2
public class AuthenticationFilter implements GatewayFilter {

    private final JwtFilter jwtUtil;
    private final RouterValidator routerValidator;

    // private static final Logger log = LogManager.getLogger(AuthenticationFilter.class);


    @Autowired
    public AuthenticationFilter(JwtFilter jwtUtil, RouterValidator routerValidator) {
        this.jwtUtil = jwtUtil;
        this.routerValidator = routerValidator;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String token = jwtUtil.extractTokenFromCookie(exchange);

        log.info("token: {}", token);

        if (routerValidator.isSecured.test(request)) {
            if (token != null && token.startsWith("Bearer ")) token = token.substring(7);

            try {
                jwtUtil.isInvalid(token);
                List<String> roles = jwtUtil.extractRoles(token);

                log.info("Roles: {}", roles);

                // Rollere göre erişim kontrolü
                if (request.getPath().toString().contains("/reservation") && !roles.contains("USER")) {
                    log.info("User girdi");
                    return onError(exchange);
                } else if (request.getPath().toString().contains("/add/") && !roles.contains("ADMIN")) {
                    return onError(exchange);
                }
                String userId = jwtUtil.extractUserName(token);

                ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                        .header("X-User-Name", userId)
                        .build();

                exchange = exchange.mutate().request(mutatedRequest).build();

            } catch (Exception e) {
                return onError(exchange);
            }
        }

        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }
}
