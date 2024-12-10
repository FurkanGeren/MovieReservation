package com.movie.gateway_server.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Component
public class JwtFilter {


    @Value("${jwt.secret}")
    private String secretKey;

    public String extractTokenFromCookie(ServerWebExchange exchange) {
        return exchange.getRequest().getCookies().getFirst("movie_auth_cookie") != null ?
                Objects.requireNonNull(exchange.getRequest().getCookies().getFirst("movie_auth_cookie")).getValue() : null;
    }


    public boolean validateToken(String token) {
        try {
            String userName = extractUserName(token);
            return userName != null && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public void isInvalid(String token) {
        this.isTokenExpired(token);
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);

        Object roles = claims.get("role");
        if (roles instanceof List<?>) {
            return ((List<?>) roles).stream()
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .toList();
        }
        return null;
    }
}