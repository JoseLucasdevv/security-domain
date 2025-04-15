package app.security.api;

import app.security.domain.ChangeEmailToken;
import app.security.dto.ChangeEmailDTO;
import app.security.dto.UserUpdateDTO;
import app.security.services.EmailService;
import app.security.services.UserService;
import app.security.services.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserGeneralResource {
    private final EmailService emailService;
    private final UserService userService;

    @DeleteMapping("/user")
    public ResponseEntity<Void> deleteMyAccount(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        this.userService.deleteUserByUsername(username);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/user")
    public ResponseEntity<UserServiceImpl.ResponseUserUpdateValidationResource> updateMyAccount(@RequestBody UserUpdateDTO userUpdateDTO){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserServiceImpl.ResponseUserUpdateValidationResource response =  this.userService.updateUserByUsername(username,userUpdateDTO);
        //this.userService.updateUserByUsername(username);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/user/send-code")
    public ResponseEntity<Void> sendCodeVerification(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        this.emailService.sendVerificationCode(username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/verify-code")
    public ResponseEntity<String> verifyCode(@RequestParam String code){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        ChangeEmailToken changeEmailToken = this.emailService.verifyCode(code,username);
        return ResponseEntity.ok().body("TokenGenerated: " + changeEmailToken.getToken());
    }
    @PostMapping("/user/update-email")
    public ResponseEntity<Void> emailSenderChangeEmail(@RequestParam String token,@Valid @RequestBody ChangeEmailDTO contentBody){
        this.emailService.emailSenderChangeEmail(token,contentBody);
        return ResponseEntity.ok().build();
    }



    @GetMapping("/user/change-email")
    public ResponseEntity<String> changeEmail(@RequestParam("token") String token){
        this.userService.changeEmail(token);
        return ResponseEntity.ok().body("Ooookay you did it, now you change the email from your account :))");
    }



}
