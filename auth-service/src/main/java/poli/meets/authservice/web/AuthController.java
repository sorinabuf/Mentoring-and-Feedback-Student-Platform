package poli.meets.authservice.web;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import poli.meets.authservice.model.User;
import poli.meets.authservice.security.JwtTokenUtil;
import poli.meets.authservice.service.dtos.ChangePasswordDTO;
import poli.meets.authservice.service.dtos.LoginRequest;
import poli.meets.authservice.service.dtos.LoginResponse;
import poli.meets.authservice.service.UserService;
import poli.meets.authservice.service.UserUtilsService;
import poli.meets.authservice.service.dtos.UserRegisterDTO;
import poli.meets.authservice.web.error.ForbiddenException;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserService userService;

    private final UserUtilsService userUtilsService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());


        if (!userUtilsService.isActivated(userDetails.getUsername())) {
            throw new ForbiddenException("Account is not activated");
        }

        String jwtToken = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(jwtToken));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO user) {
        try {
            User savedUser = userUtilsService.register(user);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<Boolean> changePassword(@RequestHeader("Authorization") String token,
                                            @RequestBody ChangePasswordDTO changePasswordDTO) {
        return ResponseEntity.ok(userUtilsService.changePassword(
                jwtTokenUtil.extractUsername(token.substring(7)), changePasswordDTO));
    }


    @GetMapping("/activate")
    public ResponseEntity<?> activate(@RequestParam("token") String token) {
        try {
            userUtilsService.activateUser(token);
            return ResponseEntity.ok("User activated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/is-activated")
    public ResponseEntity<Boolean> isActivated(@RequestParam String username) {
        return ResponseEntity.ok(userUtilsService.isActivated(username));
    }

    @GetMapping("/current-user")
    public ResponseEntity<String> getCurrentUser(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(jwtTokenUtil.extractUsername(token.substring(7)));
    }

    @GetMapping("/is-admin")
    public ResponseEntity<Boolean> isCurrentUserAdmin(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userUtilsService.isCurrentUserAdmin(
                jwtTokenUtil.extractUsername(token.substring(7))));
    }

}