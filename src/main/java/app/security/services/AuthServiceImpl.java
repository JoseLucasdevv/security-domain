package app.security.services;

import app.security.Enum.TypeRole;
import app.security.MapperDTO.UserMapper;
import app.security.domain.*;
import app.security.exceptions.Exception;
import app.security.infra.security.UserAuthenticated;
import app.security.mailProvider.SendEmailService;
import app.security.repository.*;
import app.security.services.validations.CreateUserValidation;
import app.security.dto.*;
import app.security.utils.GenerateExpirationDate;
import app.security.utils.GenerateKeyEncoded;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SendEmailService sendEmailService;
    @Autowired
    private EmailConfirmationTokenRepository emailConfirmationTokenRepository;
    @Autowired
    private TokenBlackListRepository tokenBlackListRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    RefreshTokenService refreshTokenService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CreateUserValidation createUserValidation;
    @Autowired
    private UserService userService;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    @Override
    public void register(RegisterDTO form,TypeRole role) {

        createUserValidation.RegisterValidation(form);

        String encryptedPassword = this.passwordEncoder.encode(form.password());
        User persistUser = UserMapper.DtoToUser(new UserDTOIn(form.name(),form.username(), form.email(), encryptedPassword));
        Role roleUser = new Role();
        roleUser.setName(role);
        roleUser.setUsers(List.of(persistUser));
        persistUser.setRole(roleUser);
        EmailConfirmationToken emailConfirmationToken = new EmailConfirmationToken();
        emailConfirmationToken.setUser(persistUser);
        String tokenEmailConfirm = GenerateKeyEncoded.getKeyEncodedToken();
        emailConfirmationToken.setToken(tokenEmailConfirm);
        emailConfirmationToken.setExpiresAt(GenerateExpirationDate.genExpirationDate(2L));
        roleRepository.save(roleUser);
        userService.saveUser(persistUser);
        emailConfirmationTokenRepository.save(emailConfirmationToken);
        sendEmailService.sendConfirmationLink(tokenEmailConfirm);
    }

    @Override
    public ResponseAuthentication auth(AuthDTO form) throws BadCredentialsException {
        var user = userRepository.findByUsername(form.username());

        if (user == null) throw new Exception("this user doesn't exists", HttpStatus.NOT_FOUND);
            UserDTO userDto = UserMapper.UserToDTO(user);

        var usernamePassword = new UsernamePasswordAuthenticationToken(form.username(), form.password());

        var auth = authenticationManager.authenticate(usernamePassword);
        String refreshTokenGenerated = refreshTokenService.generateRefreshToken();
        Instant RefreshTokenExpiresAt = refreshTokenService.getExpiresAtRefreshToken();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiresAt(RefreshTokenExpiresAt);
        refreshToken.setToken(refreshTokenGenerated);
        refreshToken.setCreatedAt(Instant.now());
        refreshTokenRepository.save(refreshToken);
        String accessToken = jwtService.generateToken((UserAuthenticated) auth.getPrincipal());
        Instant accessTokenExpiresAt = jwtService.extractExpiresAt(accessToken);
        user.getRefreshTokens().removeIf((r)->  r.getExpiresAt().isBefore(Instant.now()));
        userRepository.save(user);
        return new ResponseAuthentication(accessToken,accessTokenExpiresAt, refreshTokenGenerated,refreshToken.getId(),refreshToken.getExpiresAt(),userDto,"Bearer");
    }

    @Override
    public void logOut(LogOutIn logOut) {
        String username = jwtService.validateToken(logOut.accessToken());
        User user =  this.userRepository.findByUsername(username);
        TokenBlackList tokenBlackList = new TokenBlackList();
        tokenBlackList.setTokenBlackList(logOut.accessToken());
        tokenBlackList.setUser(user);
        user.getAccessTokenBlackList().add(tokenBlackList);
        user.getRefreshTokens().stream().filter(r -> r.getToken().equals(logOut.refreshToken())).findFirst().orElseThrow(() -> new Exception("can't find this refreshToken",HttpStatus.NOT_FOUND));
        user.getRefreshTokens().removeIf(r->r.getToken().equals(logOut.refreshToken()));
        user.getAccessTokenBlackList().removeIf((t)->  jwtService.extractExpiresAt(t.getTokenBlackList()).isBefore(Instant.now()));
        this.tokenBlackListRepository.save(tokenBlackList);
        this.userRepository.save(user);

    }


}
