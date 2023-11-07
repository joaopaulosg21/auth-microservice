package projeto.service.user.configuration;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import projeto.service.user.model.User;

@Service
public class TokenService {

    @Value("${jwt.expiration}")
    private String exp;

    @Value("${jwt.secret}")
    private String secret;


    public String generate(User user) {
        Date expiration = new Date(new Date().getTime() + Long.valueOf(exp));

        return Jwts.builder()
                    .setIssuedAt(new Date())
                    .signWith(Keys.hmacShaKeyFor(secret.getBytes()),SignatureAlgorithm.HS256)
                    .setExpiration(expiration)
                    .setSubject(Long.toString(user.getId()))
                    .compact();
    }
}
