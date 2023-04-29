package poli.meets.gateway.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Mono;


@FeignClient(name = "auth-service", configuration = ReactiveFeignConfiguration.class)
public interface AuthClient {

    @PostMapping("/auth/validate-token")
    Mono<ResponseEntity<String>> validateToken();
}
