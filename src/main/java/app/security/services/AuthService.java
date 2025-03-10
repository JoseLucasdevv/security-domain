package app.security.services;

import app.security.Enum.TypeRole;
import app.security.dto.AuthDTO;
import app.security.dto.LogOutIn;
import app.security.dto.RegisterDTO;
import app.security.dto.ResponseAuthentication;


public interface AuthService {
     void register(RegisterDTO form, TypeRole role);

    ResponseAuthentication auth(AuthDTO form);

    void logOut(LogOutIn logOut);
}
