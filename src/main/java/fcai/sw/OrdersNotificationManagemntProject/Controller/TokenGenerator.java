package fcai.sw.OrdersNotificationManagemntProject.Controller;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.security.SecureRandom;
import java.util.Base64;
@Component
public class TokenGenerator {
    private final String SECRET_KEY;

    TokenGenerator(){
        SECRET_KEY = generateSecretKey(512);
    }
    public  String generateSecretKey(int keyLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[keyLength];
        secureRandom.nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }
    public String generateToken(String username) {
        // 10 days
        long EXPIRATION_TIME = 864_000_000;
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, this.SECRET_KEY)
                .compact();
    }
    public String extractUsername(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(this.SECRET_KEY).build()
                    .parseClaimsJws(token);
            return claims.getBody().getSubject();
        } catch (Exception e) {
            // Handle the exception appropriately (log it or return null)
            e.printStackTrace();
            return null;
        }
    }
}
