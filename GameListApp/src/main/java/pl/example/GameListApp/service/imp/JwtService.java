package pl.example.GameListApp.service.imp;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.example.GameListApp.component.CustomUserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static pl.example.GameListApp.configuration.SecurityConstants.JWT_KEY;

@Service
public class JwtService {


    public String extractUsername(String token) {
        return extractClaim(token, claims -> claims.getSubject());
    }

    public String generateToken(CustomUserDetails customUserDetails) {
        return generateToken(new HashMap<>(), customUserDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, CustomUserDetails customUserDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(customUserDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails customUserDetails) {
        final String username = extractUsername(token);
        return (username.equals(customUserDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, claims -> claims.getExpiration());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
