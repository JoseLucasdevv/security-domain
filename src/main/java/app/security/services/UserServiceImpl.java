package app.security.services;


import app.security.Enum.TypeRole;
import app.security.domain.Role;
import app.security.domain.User;
import app.security.repository.RoleRepository;
import app.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User saveUser(User user) {
        log.info("save new user {} in database",user.getName());
        return this.userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("save new Role {} in database",role.getName());
        return this.roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String userName, TypeRole roleName) {
        log.info("Add new Role {} to User {}",userName,roleName);
    User user = this.userRepository.findByUsername(userName);
    Role role = this.roleRepository.findByname(roleName);
    user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        log.info("get user {}",username );
        return this.userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("get all Users");
        return this.userRepository.findAll();
    }
}
