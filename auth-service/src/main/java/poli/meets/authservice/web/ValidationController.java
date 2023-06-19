package poli.meets.authservice.web;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poli.meets.authservice.service.UserUtilsService;
import poli.meets.authservice.service.dtos.UserDetailsDTO;


@RestController
@AllArgsConstructor
public class ValidationController {
    private final UserUtilsService userUtilsService;

    @PostMapping("/validate-token")
    public ResponseEntity<String> validateToken() {

        return ResponseEntity.ok("validated");
    }

    @GetMapping("/current-user-details")
    public ResponseEntity<UserDetailsDTO> getCurrentUserDetails(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userUtilsService.getCurrentUserDetails(token));
    }
}
