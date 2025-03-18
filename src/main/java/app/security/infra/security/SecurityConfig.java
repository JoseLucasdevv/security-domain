package app.security.infra.security;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    @Autowired
    AuthorizationManagerFactory authorizationManagerFactory;
    @Autowired
    FilterValidateJWT filterValidateJWT;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.
                        // auth Users
                        requestMatchers(HttpMethod.POST,"api/register").permitAll().
                        requestMatchers(HttpMethod.POST,"api/auth").permitAll().
                        requestMatchers(HttpMethod.POST,"api/refresh_token").permitAll().
                        requestMatchers(HttpMethod.GET,"api/confirm-email").permitAll()
                        //Just authenticated.
                        .requestMatchers(HttpMethod.GET,"api/resend-email").authenticated().
                        requestMatchers(HttpMethod.POST,"api/logOut").authenticated()
                        // TeacherConsumer
                        .requestMatchers(HttpMethod.GET,"api/teacher/users/**").access(authorizationManagerFactory.emailConfirmedAndRole("TEACHER"))
                        .requestMatchers(HttpMethod.GET,"api/teacher/user/**").access(authorizationManagerFactory.emailConfirmedAndRole("TEACHER"))
                        .requestMatchers(HttpMethod.POST,"api/teacher/workout/save/**").access(authorizationManagerFactory.emailConfirmedAndRole("TEACHER"))
                        .requestMatchers(HttpMethod.PUT,"api/teacher/workout/update/**").access(authorizationManagerFactory.emailConfirmedAndRole("TEACHER"))
                        .requestMatchers(HttpMethod.GET,"api/teacher/workouts/**").access(authorizationManagerFactory.emailConfirmedAndRole("TEACHER"))
                        .requestMatchers(HttpMethod.GET,"api/teacher/workout/**").access(authorizationManagerFactory.emailConfirmedAndRole("TEACHER"))
                        .requestMatchers(HttpMethod.DELETE,"api/teacher/workout/delete/**").access(authorizationManagerFactory.emailConfirmedAndRole("TEACHER"))

                        //User Consumer
                        .requestMatchers(HttpMethod.GET,"api/user/resource/workouts").access(authorizationManagerFactory.emailConfirmedAndRole("USER"))


                ).addFilterBefore(filterValidateJWT, UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling(exh -> exh.authenticationEntryPoint(
                (request, response, ex) -> {

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,ex.getMessage());


}
));
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return  authenticationConfiguration.getAuthenticationManager();
    }


}
