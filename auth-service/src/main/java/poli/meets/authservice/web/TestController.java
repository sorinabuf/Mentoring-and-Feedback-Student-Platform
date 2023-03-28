package poli.meets.authservice.web;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {

    @GetMapping("/get")
    public ResponseEntity<String> register() {

        return ResponseEntity.ok("done");
    }
}
