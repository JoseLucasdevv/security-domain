package app.security.services;


import app.security.infra.security.UserAuthenticated;
import app.security.utils.GenerateExpirationDate;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Service;
import java.time.*;


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
                    .withExpiresAt(GenerateExpirationDate.genExpirationDate(1L))
                    .sign(algorithm);

        } catch (JWTCreationException e) {
            throw new JWTCreationException("fail to create AccessToken:" + e.getMessage(),e.getCause());
        }

    }

    @Override
    public Instant extractExpiresAt(String token){
        try{
            return JWT.decode(token).getExpiresAt().toInstant();
        } catch (JWTDecodeException e) {
            throw new JWTDecodeException("cannot decode token");
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
        }catch(JWTVerificationException e){
            throw new JWTVerificationException("cannot verify token:" + e.getMessage());
        }


    }


}
