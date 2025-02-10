package app.security.services;

import app.security.Enum.TypeRole;
import app.security.domain.Role;
import app.security.domain.User;
import app.security.infra.security.UserAuthenticated;
import app.security.repository.RoleRepository;
import app.security.types.AuthDTO;
import app.security.types.RegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
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
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Override
    public void register(RegisterDTO form) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(form.password());
        User persistUser = new User();
        persistUser.setName(form.name());
        persistUser.setPassword(encryptedPassword);
        persistUser.setUsername(form.username());
        Role roleUser = new Role();
        roleUser.setName(TypeRole.ADMIN);
        roleUser.setUsers(List.of(persistUser));
        persistUser.setRole(roleUser);
        roleRepository.save(roleUser);
        userService.saveUser(persistUser);
    }

    @Override
    public String auth(AuthDTO form) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(form.username(), form.password());
        var auth = authenticationManager.authenticate(usernamePassword);


        return jwtService.generateToken((UserAuthenticated) auth.getPrincipal());

    }
}
