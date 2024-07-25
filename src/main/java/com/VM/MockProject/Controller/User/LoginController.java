package com.VM.MockProject.Controller.User;

import com.VM.MockProject.Config.JWT.JwtTokenProvider;
import com.VM.MockProject.Service.CustomUserDetails;
import com.VM.MockProject.form.MessageResponse;
import com.VM.MockProject.form.login.LoginRequest;
import com.VM.MockProject.form.login.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value ="/api/v1/auth")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
try {
    // Xác thực thông tin người dùng Request lên
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsernameOrEmail(),
                    loginRequest.getPassword()
            )
    );

    // Nếu không xảy ra exception tức là thông tin hợp lệ
    // Set thông tin authentication vào Security Context
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // Trả về jwt cho người dùng.
    String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    String fullName = userDetails.getFullName();
    Integer id = userDetails.getUserId();
    return ResponseEntity.ok(new LoginResponse(jwt, fullName, id));
} catch (AuthenticationException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username/email or password");
}
    }

    @GetMapping("/logout")
    public MessageResponse logout(Authentication authentication) {
        // You can add any additional logout logic here if needed
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // Clear authentication details
            SecurityContextHolder.clearContext();
            return new MessageResponse("Logout successfully");
        }
        return new MessageResponse("Auth null"); // Optionally return a message
    }
}
