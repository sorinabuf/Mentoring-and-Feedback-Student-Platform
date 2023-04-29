package poli.meets.coreservice.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "auth-service")
public interface AuthClient {

    @PostMapping("/auth/validate-token")
    ResponseEntity<String> validateToken();
}
