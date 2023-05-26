package poli.meets.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.reactive.function.client.WebClient;
import poli.meets.gateway.filter.AuthenticationPrefilter;

@Configuration
public class SpringCloudConfig {


    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder,
                                      AuthenticationPrefilter authenticationPrefilter) {
        return builder.routes()
                .route(r -> r.path("/auth/api/**")
                        .uri("lb://AUTH-SERVICE"))
                .route(r -> r.path("/core/**")
                        .filters(f -> f.filter(authenticationPrefilter))
                        .uri("lb://CORE-SERVICE"))
                .route(r -> r.path("/auth/validate-token")
                        .filters(f -> f.filter(authenticationPrefilter))
                        .uri("lb://AUTH-SERVICE"))
                .route(r -> r.path("/mentorship")
                        .filters(f -> f.filter(authenticationPrefilter))
                        .uri("lb://MENTORSHIP-SERVICE"))
                .build();
    }

    @Bean
    public AuthenticationPrefilter authGatewayPrefilter(WebClient webClient) {
        return new AuthenticationPrefilter(webClient);
    }

}