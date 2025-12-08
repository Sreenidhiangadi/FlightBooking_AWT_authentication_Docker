package com.flightapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

@Slf4j
@Component
@Order(-2)
public class GlobalErrorHandler implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        log.error("Unhandled error: {}", ex.getMessage(), ex);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        String path = exchange.getRequest().getPath().value();
        String message = ex.getMessage() != null ? ex.getMessage() : "Unexpected error";
        String body = "{"
                + "\"timestamp\":\"" + Instant.now() + "\","
                + "\"path\":\"" + escape(path) + "\","
                + "\"status\":" + status.value() + ","
                + "\"error\":\"" + status.getReasonPhrase() + "\","
                + "\"message\":\"" + escape(message) + "\""
                + "}";

        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);

        var response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        var buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(buffer));
    }

    private String escape(String value) {
        return value.replace("\"", "\\\"");
    }
}