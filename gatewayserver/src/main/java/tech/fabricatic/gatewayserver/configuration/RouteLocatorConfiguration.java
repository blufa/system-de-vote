package tech.fabricatic.gatewayserver.configuration;

import java.util.Date;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RouteLocatorConfiguration {
    
    @Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
	    return builder.routes()
	        .route(p -> p
	            .path("/fabricatic/votingserver/**")
	            .filters(f -> f.rewritePath("/fabricatic/votingserver/(?<segment>.*)","/${segment}")
	            				.addResponseHeader("X-Response-Time",new Date().toString()))
	            .uri("lb://VOTINGSERVER")).build();
	}

}
