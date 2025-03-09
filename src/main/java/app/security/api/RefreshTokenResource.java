package app.security.api;

import app.security.exceptions.HashError;
import app.security.services.RefreshTokenService;
import app.security.types.RefreshTokenIn;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RequiredArgsConstructor
@RequestMapping("/api/refresh_token")
@RestController
public class RefreshTokenResource {

private final RefreshTokenService refreshTokenService;

@PostMapping
public ResponseEntity<?> RefreshToken(@Valid @RequestBody RefreshTokenIn input){
    try{
        return ResponseEntity.ok().body(refreshTokenService.verify(input));
    }catch (Exception e) {
        HashMap<String,String> objectHashError = HashError.createHashErrorOutput(e.getMessage());

        return ResponseEntity.badRequest().body(objectHashError);
    }

}

}
