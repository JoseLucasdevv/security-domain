package app.security.infra.security;

import app.security.domain.User;

import app.security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetails implements UserDetailsService {
private final UserService userService;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUser(username);
        if(user == null){
            throw new UsernameNotFoundException("user not found");
        }
        return new UserAuthenticated(user);

    }
}
