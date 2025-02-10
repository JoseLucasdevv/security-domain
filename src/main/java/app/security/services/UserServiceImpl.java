package app.security.services;


import app.security.Enum.TypeRole;
import app.security.domain.User;
import app.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public User saveUser(User user) {
        log.info("save new user {} in database",user.getName());
        return this.userRepository.save(user);
    }


    @Override
    public User getUser(String username) {
        log.info("get user {}",username );
        return this.userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers(int pageNumber) {
        Pageable page = PageRequest.of(pageNumber - 1 ,10);
        log.info("get all Users");

        return this.userRepository.getAllByRoleNameUser(TypeRole.USER,page).stream().toList();
    }
}
