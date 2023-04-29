package poli.meets.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import poli.meets.gateway.client.AuthClient;
import poli.meets.gateway.client.FeignClientInterceptor;
import poli.meets.gateway.error.ForbiddenException;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationPrefilter implements GatewayFilter, Ordered {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final WebClient webClient;

    public AuthenticationPrefilter(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION_HEADER);

        return webClient.post()
                .uri("http://localhost:8081/auth/validate-token")
                .header("Authorization", token)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new ForbiddenException("Invalid token")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new RuntimeException("Auth service error")))
                .toBodilessEntity()
                .flatMap(response -> chain.filter(exchange));

    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}