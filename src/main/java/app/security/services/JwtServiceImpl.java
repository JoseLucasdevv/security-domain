package app.security.services;

import app.security.infra.security.UserAuthenticated;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
@Service
public class JwtServiceImpl implements JwtService{
    String secretKey = "123791823791283sdakspoKOPASKAKSO";


    @Override
    public String generateToken(UserAuthenticated user){

        try{
            Algorithm algorithm = Algorithm.HMAC256(this.secretKey);

            return JWT.create()
                    .withIssuer("security-domain")
                    .withSubject(user.getUsername())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String validateToken(String token) {


        try{
            Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
            return JWT.require(algorithm)
                    .withIssuer("security-domain")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
