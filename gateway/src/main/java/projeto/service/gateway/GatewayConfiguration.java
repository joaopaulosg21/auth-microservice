package projeto.service.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                    .route(route -> route.path("/api/users/**")
                        .uri("http://localhost:8081/"))
                    .route(route -> route.path("/api/consumer/**")
                        .uri("http://localhost:8082/"))
                    .build();
    }
}
