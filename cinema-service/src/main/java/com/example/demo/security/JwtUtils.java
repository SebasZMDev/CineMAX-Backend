package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

	@Value("${jwt.secret}")
	private String secret;

	private Key getKey() {
		byte[] bytes = Decoders.BASE64.decode(java.util.Base64.getEncoder().encodeToString(secret.getBytes()));
		return Keys.hmacShaKeyFor(bytes);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
	}

	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}


	public boolean isTokenValid(String token) {
	    try {
	        Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
	        return !isExpired(token);
	    } catch (JwtException | IllegalArgumentException e) {
	        return false;
	    }
	}

	public Long extractUserId(String token) {
	    Claims claims = Jwts.parserBuilder().setSigningKey(getKey()).build()
	            .parseClaimsJws(token).getBody();
	    Object id = claims.get("usuarioId");
	    if (id instanceof Integer) return ((Integer) id).longValue();
	    if (id instanceof Long) return (Long) id;
	    return null;
	}

	public String extractRol(String token) {
	    return Jwts.parserBuilder().setSigningKey(getKey()).build()
	            .parseClaimsJws(token).getBody().get("rol", String.class);
	}

	private boolean isExpired(String token) {
	    return Jwts.parserBuilder().setSigningKey(getKey()).build()
	            .parseClaimsJws(token).getBody().getExpiration().before(new Date());
	}

}