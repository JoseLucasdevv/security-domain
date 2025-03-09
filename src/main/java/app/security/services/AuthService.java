package app.security.services;

import app.security.Enum.TypeRole;
import app.security.types.AuthDTO;
import app.security.types.RegisterDTO;
import app.security.types.ResponseAuthentication;


public interface AuthService {
     void register(RegisterDTO form, TypeRole role);

    ResponseAuthentication auth(AuthDTO form);
}
