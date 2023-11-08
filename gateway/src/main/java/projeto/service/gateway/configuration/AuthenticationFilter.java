package projeto.service.gateway.configuration;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GatewayFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (this.verifyRoute(path)) {

            if (!exchange.getRequest().getHeaders().containsKey("Authorization")) {
                return this.error(exchange, HttpStatusCode.valueOf(401));
            }

            String token = this.getTokenFromHeader(exchange.getRequest());

            if (!tokenService.isValid(token)) {
                return this.error(exchange, HttpStatusCode.valueOf(401));
            }

            String id = tokenService.getIdFromToken(token);
            exchange.getRequest().mutate().header("user_id", id).build();
        }

        return chain.filter(exchange);
    }

    public String getTokenFromHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0).split(" ")[1];
    }

    public Mono<Void> error(ServerWebExchange exchange, HttpStatusCode code) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(code);
        return response.setComplete();
    }

    public boolean verifyRoute(String path) {
        Set<String> routes = Set.of("/api/users/", "/api/users/login");
        return !routes.contains(path);
    }
}
