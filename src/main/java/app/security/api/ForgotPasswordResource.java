package app.security.api;


import app.security.dto.EmailForgotPasswordIn;
import app.security.dto.ResetPasswordIn;
import app.security.exceptions.HashError;
import app.security.services.ForgotPasswordService;
import app.security.services.ResetPasswordService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class ForgotPasswordResource {
    private final ResetPasswordService resetPasswordService;
    private final ForgotPasswordService forgotPasswordService;
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody EmailForgotPasswordIn input){
        try{
        forgotPasswordService.send(input);
        return ResponseEntity.ok().body("we sent the email to your email address");
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam("token") String token, @Valid @RequestBody ResetPasswordIn newPassword){
        try{
            System.out.println(newPassword.newPassword());
                resetPasswordService.resetPassword(token,newPassword.newPassword());
                return ResponseEntity.ok().body("você atualizou sua senha com sucesso");
        }
        catch(Exception e){
            HashMap<String,String> objectHashError = HashError.createHashErrorOutput(e.getMessage());
            return ResponseEntity.badRequest().body(objectHashError);
        }
    }
}
