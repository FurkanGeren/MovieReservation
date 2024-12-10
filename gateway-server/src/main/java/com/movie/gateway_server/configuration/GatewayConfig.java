package com.movie.gateway_server.configuration;

import com.movie.gateway_server.filter.AuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final AuthenticationFilter filter;

    public GatewayConfig(AuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-server", r -> r.path("/api/auth/**")
                        .filters(f -> f.stripPrefix(1).filter(filter))
                        .uri("lb://AUTH-SERVER"))
                .route("movie-management-server", r -> r.path("/api/movie-management/**")
                        .filters(f -> f.stripPrefix(1).filter(filter))
                        .uri("lb://MOVIE-MANAGEMENT-SERVER"))
                .build();
    }
}
