package poli.meets.authservice.web;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {


    @GetMapping
    public ResponseEntity<String> test() {

        return ResponseEntity.ok("done");
    }
}
