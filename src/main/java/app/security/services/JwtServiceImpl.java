package app.security.services;


import app.security.infra.security.UserAuthenticated;
import app.security.utils.GenerateExpirationDate;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.stereotype.Service;
import java.time.*;
import java.util.Map;


@Service
public class JwtServiceImpl implements JwtService{
    String secretKey = "123791823791283sdakspoKOPASKAKSO";


    @Override
    public String generateToken(UserAuthenticated user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
                
            return JWT.create()
                    .withClaim("Role",user.getRole())
                    .withClaim("Email-Confirmed",user.getEmailConfirm())
                    .withClaim("Email",user.getEmail())
                    .withClaim("Name",user.getName())
                    .withClaim("uid",user.getUid())
                    .withIssuer("security-domain")
                    .withSubject(user.getUsername())
                    .withExpiresAt(GenerateExpirationDate.genExpirationDate(30L))
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

    public Map<String, Claim> extractClaims(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
            return JWT.require(algorithm)
                    .withIssuer("security-domain")
                    .build()
                    .verify(token)
                    .getClaims();


        }catch(JWTVerificationException e){
            throw new JWTVerificationException("cannot verify token:" + e.getMessage());
        }



    }
}
