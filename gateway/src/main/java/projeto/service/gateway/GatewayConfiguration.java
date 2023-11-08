package projeto.service.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import projeto.service.gateway.configuration.AuthenticationFilter;

@Configuration
public class GatewayConfiguration {

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                    .route(route -> route.path("/api/users/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("http://localhost:8081/"))
                    .build();
    }
}
