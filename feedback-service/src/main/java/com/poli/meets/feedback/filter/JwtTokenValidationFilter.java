package com.poli.meets.feedback.filter;

import com.poli.meets.feedback.client.AuthClient;
import com.poli.meets.feedback.client.FeignClientInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@AllArgsConstructor
public class JwtTokenValidationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String TOKEN_TYPE = "Bearer ";

    private final AuthClient authClient;

    private final FeignClientInterceptor feignClientInterceptor;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String token = getTokenFromRequest(request);

        feignClientInterceptor.setJwtTokenStorage(token);


        ResponseEntity<String> responseEntity = authClient.validateToken();

            if (Objects.equals(responseEntity.getBody(), "validated")) {
                chain.doFilter(request, response);
            } else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }

    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION_HEADER);
        if (token != null && token.startsWith(TOKEN_TYPE)) {
            return token.substring(7);
        }
        return null;
    }

}