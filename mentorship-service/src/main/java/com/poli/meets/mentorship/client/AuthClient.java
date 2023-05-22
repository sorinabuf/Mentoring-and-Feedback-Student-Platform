package com.poli.meets.mentorship.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "auth-service")
public interface AuthClient {

    @PostMapping("/auth/validate-token")
    ResponseEntity<String> validateToken();

    @GetMapping("/auth/api/current-user")
    ResponseEntity<String> getCurrentUser(@RequestHeader("Authorization") String token);
}
