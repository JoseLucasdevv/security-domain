package app.security.api;


import app.security.services.ResendEmailService;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ResendEmailResource {
    @Autowired
    private ResendEmailService resendEmailService;
    @GetMapping("/resend-email")
    public ResponseEntity<?> resendEmail(HttpServletRequest request) {
    try{
        var auth = SecurityContextHolder.getContext().getAuthentication();

        Boolean emailConfirm = (Boolean) request.getAttribute("Email-Confirmed");

        resendEmailService.resendEmailConfirmation(emailConfirm,auth.getName());
        return ResponseEntity.ok().build();
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    }

}
