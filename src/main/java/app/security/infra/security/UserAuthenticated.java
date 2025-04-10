package app.security.infra.security;

import app.security.Enum.TypeRole;
import app.security.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class UserAuthenticated implements UserDetails {
    private final User user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        TypeRole role = user.getRole().getName();
        if(role == TypeRole.ADMIN){
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        if(role == TypeRole.TEACHER){
            return List.of(new SimpleGrantedAuthority("ROLE_TEACHER"));
        }

        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }


    public Boolean getEmailConfirm(){
        return user.getEmailConfirmed();
    }

    public String getUid() {
        return user.getUid();
    }

    public String getEmail(){
        return user.getEmail();
    }

    public String getName(){
        return user.getName();
    }

    public String getRole(){
        return user.getRole().getName().toString();
    }

}
