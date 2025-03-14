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
    FilterValidateJWT filterValidateJWT;
    private AuthenticationConfiguration authenticationConfiguration;

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

                        // TeacherConsumer
                        .requestMatchers(HttpMethod.GET,"api/teacher/users/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.GET,"api/teacher/user/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.POST,"api/teacher/workout/save/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.PUT,"api/teacher/workout/update/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.GET,"api/teacher/workouts/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.GET,"api/teacher/workout/**").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.DELETE,"api/teacher/workout/delete/**").hasRole("TEACHER")

                        //User Consumer
                        .requestMatchers(HttpMethod.GET,"api/user/resource/workouts").hasRole("USER").

                        requestMatchers(HttpMethod.POST,"api/logOut").hasRole("USER")

                ).addFilterBefore(filterValidateJWT, UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling(exh -> exh.authenticationEntryPoint(
                (request, response, ex) -> {

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Fail authenticate something wrong with token");

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
