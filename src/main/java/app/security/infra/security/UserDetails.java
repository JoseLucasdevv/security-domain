package app.security.infra.security;

import app.security.domain.User;

import app.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetails implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserAuthenticated loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.userRepository.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("user not found");
        }
        return new UserAuthenticated(user);

    }
}
