package app.security.infra.security;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.function.Supplier;

public class EmailConfirmedAndRoleAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final String requiredRole;


    public EmailConfirmedAndRoleAuthorizationManager(String requiredRole) {
        this.requiredRole = requiredRole;
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        Authentication auth = authentication.get();

        if (auth == null || !auth.isAuthenticated()) {
            return new AuthorizationDecision(false);
        }


        UserAuthenticated userDetails = (UserAuthenticated) auth.getPrincipal();


        boolean isEmailConfirmed = userDetails.getEmailConfirm();


        if (!isEmailConfirmed) {
            return new AuthorizationDecision(false);
        }

        boolean hasRequiredRole = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals("ROLE_" + this.requiredRole));

        return new AuthorizationDecision(hasRequiredRole);
    }
}