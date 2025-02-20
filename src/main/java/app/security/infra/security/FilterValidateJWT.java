package app.security.infra.security;

import app.security.repository.UserRepository;
import app.security.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FilterValidateJWT extends OncePerRequestFilter {
    @Autowired
    UserDetails userDetails;
    @Autowired
    JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        var auth = request.getHeader("Authorization");
        var token = getToken(auth);
if(token != null){
    var userName = jwtService.validateToken(token);

    org.springframework.security.core.userdetails.UserDetails user = userDetails.loadUserByUsername(userName);
    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);

}

        filterChain.doFilter(request, response);
    }


    String getToken(String auth){
        if(auth == null) return null;

        return auth.replace("Bearer ", "");
    }
}
