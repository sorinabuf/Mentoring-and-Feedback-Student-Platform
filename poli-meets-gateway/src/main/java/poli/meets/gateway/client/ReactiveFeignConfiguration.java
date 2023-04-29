package poli.meets.gateway.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ReactiveFeignConfiguration {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector())
                .build();
    }
}