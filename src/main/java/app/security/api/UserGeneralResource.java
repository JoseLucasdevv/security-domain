package app.security.api;

import app.security.dto.UserUpdateDTO;
import app.security.exceptions.Exception;
import app.security.services.EmailService;
import app.security.services.UserService;
import app.security.services.UserServiceImpl;
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
    public ResponseEntity<Void> verifyCode(@RequestBody String code){
        this.emailService.verifyCode(code);
        return ResponseEntity.ok().build();
    }



}
