package pl.example.GameListApp.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.example.GameListApp.configuration.SecurityConstants;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JWTTokenValidatorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            System.out.println("gen");
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
            String jwt = Jwts.builder().setIssuer("security").setSubject("JWT Token")
                    .claim("username", authentication.getName())
                    .claim("authorities", authentication.getAuthorities())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + 300000))
                    .signWith(key).compact();
            response.setHeader(SecurityConstants.JWT_HEADER, jwt);
        } else {
            String header = request.getHeader(SecurityConstants.JWT_HEADER);
            System.out.println(header);
            if (header != null && header.startsWith("Bearer ")) {
                try {
                    SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

                    Claims claims = Jwts.parserBuilder()
                            .setSigningKey(key)
                            .build()
                            .parseClaimsJws(header)
                            .getBody();
                    String username = String.valueOf(claims.get("username"));
                    String authorities = (String) claims.get("authorities");
                    Authentication authenticationInValidation = new UsernamePasswordAuthenticationToken(username, null,
                            AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                    SecurityContextHolder.getContext().setAuthentication(authenticationInValidation);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }


        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
