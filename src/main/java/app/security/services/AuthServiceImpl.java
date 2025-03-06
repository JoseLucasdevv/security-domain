package app.security.services;

import app.security.Enum.TypeRole;
import app.security.MapperDTO.UserMapper;
import app.security.domain.Role;
import app.security.domain.User;
import app.security.exceptions.Exception;
import app.security.infra.security.UserAuthenticated;
import app.security.repository.RoleRepository;
import app.security.repository.UserRepository;
import app.security.services.validations.CreateUserValidation;
import app.security.types.AuthDTO;
import app.security.types.RegisterDTO;
import app.security.types.UserDTOIn;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CreateUserValidation createUserValidation;
    @Autowired
    private UserService userService;
    @Override
    public void register(RegisterDTO form,TypeRole role) {

        createUserValidation.RegisterValidation(form);

        String encryptedPassword = new BCryptPasswordEncoder().encode(form.password());
        User persistUser = UserMapper.DtoToUser(new UserDTOIn(form.name(),form.username(),encryptedPassword));
        Role roleUser = new Role();
        roleUser.setName(role);
        roleUser.setUsers(List.of(persistUser));

        persistUser.setRole(roleUser);


        roleRepository.save(roleUser);
        userService.saveUser(persistUser);
    }

    @Override
    public String auth(AuthDTO form) throws BadCredentialsException {
        var user = userRepository.findByUsername(form.username());
        if (user == null)throw new Exception("this user doesn't exists");
        var usernamePassword = new UsernamePasswordAuthenticationToken(form.username(), form.password());

        var auth = authenticationManager.authenticate(usernamePassword);

        return jwtService.generateToken((UserAuthenticated) auth.getPrincipal());

    }
}
