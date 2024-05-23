package uz.com.employee_position.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import uz.com.employee_position.model.entity.EmployeeEntity;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.access.expiry}")
    private Long accessTokenExpiry;

    @Value("${jwt.refresh.expiry}")
    private Long refreshTokenExpiry;

    public String generateAccessToken(EmployeeEntity employeeEntity){
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setSubject(employeeEntity.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + accessTokenExpiry))
                .addClaims(Map.of("authorities", getAuthorities(employeeEntity.getAuthorities())))
                .compact();
    }
    public String generateRefreshToken(EmployeeEntity employeeEntity){
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setSubject(employeeEntity.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + refreshTokenExpiry))
                .compact();
    }
    public Jws<Claims> extractToken(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
    }
    private List<String> getAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }
}
