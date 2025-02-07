package app.security.services;

import app.security.types.AuthDTO;
import app.security.types.RegisterDTO;

public interface AuthService {
     void register(RegisterDTO form);

     String auth(AuthDTO form);
}
