package projeto.service.gateway.configuration;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

@Service
public class TokenService {
    @Value("${jwt.expiration}")
    private String exp;

    @Value("${jwt.secret}")
    private String secret;

    public boolean isValid(String token) {
        if(Objects.isNull(token)) {
            return false;
        }
        try {
            Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(token);
            return true;
        }catch(Exception e) {
            System.out.println(token);
            e.printStackTrace();
            return false;
        }
    }

    public String getIdFromToken(String token) {
        String id = Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(token).getBody().getSubject();
        return id;
    }
}
