package com.huda.money_track.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    // Replace this with a secure key in a real application, ideally fetched from environment variables
    public static final String SECRET = "6452510fd79de82bdb73038302c88bd79e774677a7d4457433725c7243e1cbd34cd9f859b5b5682bf5915a12e00bb2c4875041f9a80d3cfbb0fe2dfc7906276787bf1755ddd41887281fb7393b7b066540d706853af75c857b05a298f0306e647184973b5c106f68051a8cd4197fc2581846c9863974083345e64de465e08b03d4a95fdab61b9978857eaf34094f6ac9d4bc255fd9e73cc675df7df1040e9b722b0b3f433925ccb99444e0397c01ce50118484ef249d296b6d1dd2e709b20af324ce2ebf1ea0cd2fbe8ac62ef964578685f22c1461cb2cc203eabc6c0220aeeb76e6683697d7a807815792ecbeddb0af5ae24798e867c0a1113cfe283900f989";

    // Generate token with given user name
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, email);
    }

    // Create a JWT token with specified claims and subject (email)
    private String createToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // Token valid for 30 minutes
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Get the signing key for JWT token
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Extract the email from the token
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract the expiration date from the token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract a claim from the token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Check if the token is expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Validate the token against user details and expiration
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        // UserDetails only have username and password, we will use email as UserDetail's username because email is unique
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
