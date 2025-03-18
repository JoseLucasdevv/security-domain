package app.security.infra.security;



import app.security.repository.TokenBlackListRepository;
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
    TokenBlackListRepository tokenBlackListRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserDetails userDetails;
    @Autowired
    JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var auth = request.getHeader("Authorization");
        var token = getToken(auth);
    if(token != null){
        var userName = jwtService.validateToken(token);
        var extractClaims = jwtService.extractClaims(token);
        var emailConfirmed = extractClaims.get("Email-Confirmed").asBoolean();
        request.setAttribute("Email-Confirmed",emailConfirmed);

        var userFromRepository = this.userRepository.findByUsername(userName);
        tokenBlackListRepository.findByTokenBlackList(token).ifPresent(t-> {
        if(userFromRepository.getAccessTokenBlackList().contains(t)){
            new ServletException("this token in blackList");
        };
    });

    UserAuthenticated user = userDetails.loadUserByUsername(userName);

    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    authentication.setDetails(extractClaims);
    SecurityContextHolder.getContext().setAuthentication(authentication);
}
        filterChain.doFilter(request, response);
    }


    String getToken(String auth){
        if(auth == null) return null;

        return auth.replace("Bearer ", "");
    }
}
