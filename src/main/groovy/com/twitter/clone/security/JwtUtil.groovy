package com.twitter.clone.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

import java.util.function.Function

@Component
class JwtUtil {
    @Value('${spring.security.jwt.secret}')
    private String secret

    String extractUsername(String token) {
        return extractClaim(token) { claims -> claims.getSubject() }
    }

    Date extractExpiration(String token) {
        return extractClaim(token) { claims -> claims.getExpiration() } as Date
    }

    Object extractClaim(String token, Function<Claims, ?> claimsResolver) {
        final Claims claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody()
    }

    Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date())
    }

    String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>()
        return createToken(claims, username)
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret).compact()
    }

    Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token)
        return username.equals(userDetails.username) && !isTokenExpired(token)
    }
}
