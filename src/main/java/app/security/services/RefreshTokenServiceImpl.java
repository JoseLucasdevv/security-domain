package app.security.services;

import app.security.domain.RefreshToken;
import app.security.domain.User;
import app.security.exceptions.Exception;
import app.security.infra.security.UserAuthenticated;
import app.security.infra.security.UserDetails;
import app.security.repository.RefreshTokenRepository;
import app.security.types.RefreshTokenIn;
import app.security.types.RefreshTokenOut;
import app.security.utils.GenerateExpirationDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
@RequiredArgsConstructor
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserDetails userDetails;

    @Override
    public String generateRefreshToken(){
        try{
            return UUID.randomUUID().toString();
        } catch (app.security.exceptions.Exception e) {
            throw new Exception("can't generate a refresh Token");
        }

    }

    public Instant getExpiresAtRefreshToken(){
        return GenerateExpirationDate.genExpirationDate(5L);
    }

    @Override
    public RefreshTokenOut verify(RefreshTokenIn refreshTokenIn) {
        RefreshToken refreshTokenAlreadyExists = refreshTokenRepository.findByToken(refreshTokenIn.refreshToken()).orElseThrow(()-> new Exception("refreshToken doesn't exists"));

        Instant refreshTokenExpiresAt = refreshTokenAlreadyExists.getExpiresAt();
        if(refreshTokenExpiresAt.isBefore(Instant.now())) throw new Exception("RefreshToken expired");
        User user = refreshTokenAlreadyExists.getUser();

        org.springframework.security.core.userdetails.UserDetails userAuth = userDetails.loadUserByUsername(user.getUsername());
        String newAccessToken = jwtService.generateToken((UserAuthenticated) userAuth);
        Instant newAccessTokenExpiresAt = jwtService.extractExpiresAt(newAccessToken);
        user.getRefreshTokens().removeIf(w->w.getExpiresAt().isBefore(Instant.now()));
        refreshTokenAlreadyExists.setUser(user);
        refreshTokenRepository.save(refreshTokenAlreadyExists);

        return new RefreshTokenOut(newAccessToken
                ,newAccessTokenExpiresAt
                ,refreshTokenAlreadyExists.getToken()
                ,refreshTokenExpiresAt
                ,"Bearer");
    }


}
