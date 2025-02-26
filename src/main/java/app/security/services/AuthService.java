package app.security.services;

import app.security.Enum.TypeRole;
import app.security.types.AuthDTO;
import app.security.types.RegisterDTO;

public interface AuthService {
     void register(RegisterDTO form, TypeRole role);

     String auth(AuthDTO form);
}
