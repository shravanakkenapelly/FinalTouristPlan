package com.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

//RouteValidator for validate endpoints
@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/admin/register",
            "/admin/authenticate",
            "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
