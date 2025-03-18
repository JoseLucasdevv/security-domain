package app.security.infra.security;

import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationManagerFactory {

    public AuthorizationManager<RequestAuthorizationContext> emailConfirmedAndRole(String role) {
        return new EmailConfirmedAndRoleAuthorizationManager(role);
    }
}