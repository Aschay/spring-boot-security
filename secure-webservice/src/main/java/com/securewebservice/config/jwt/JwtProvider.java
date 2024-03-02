package com.securewebservice.config.jwt;

import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	@Value("${app.jwtSecret}")
	private String jwtSecret;
	// https://www.csfieldguide.org.nz/en/interactives/rsa-key-generator/ base 64
	// size 256

	public boolean validateToken(String token, UserDetails userDetails) {
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
		JwtParser parser = Jwts.parser().verifyWith(key).build();
		Claims claims = parser.parseSignedClaims(token).getPayload();
		String extractedUsername = (String) claims.getSubject();
		return (extractedUsername.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
		JwtParser parser = Jwts.parser().verifyWith(key).build();
		Claims claims = parser.parseSignedClaims(token).getPayload();
		return claims.getExpiration();
	}

	public String generateToken(String username) {
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
		Instant now = Instant.now();
		Instant expiration = now.plusSeconds(3600);
		Date expDate = Date.from(expiration);
		String token = Jwts.builder()
				               .subject(username)
				               .expiration(expDate)
				               .issuedAt(Date.from(now))
				               .signWith(key)
				               .compact();
		System.out.println("JWT Token: " + token);
		return token;
	}

	public String extractUsername(String token) {
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
		JwtParser parser = Jwts.parser().verifyWith(key).build();
		Claims claims = parser.parseSignedClaims(token).getPayload();
		String extractedUsername = claims.getSubject();
		return extractedUsername;

	}

}
