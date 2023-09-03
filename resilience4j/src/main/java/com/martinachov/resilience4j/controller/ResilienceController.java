package com.martinachov.resilience4j.controller;

import com.martinachov.resilience4j.dto.Response;
import com.martinachov.resilience4j.service.ResilienceService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ResilienceController {

    private static final String RESILIENCE4J_INSTANCE_NAME = "circuit-breaker";
    private static final String FALLBACK_METHOD = "fallback";
    private final ResilienceService resilienceService;

    @GetMapping(value = "/timeDelay/{delay}")
    @CircuitBreaker(name =RESILIENCE4J_INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
    public ResponseEntity<Response> timeDelay(@PathVariable int delay){
        String data = resilienceService.getTimeDelayData(delay);
        return ResponseEntity.ok(
                Response.builder()
                        .data(data)
                        .code(HttpStatus.OK.value())
                        .build());
    }

    @GetMapping(value = "/error")
    @CircuitBreaker(name =RESILIENCE4J_INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD)
    public ResponseEntity<Response> error() {
        String error = resilienceService.getError();
        return ResponseEntity.ok(
                Response.builder()
                        .data(error)
                        .build());
    }

    public ResponseEntity<Response> fallback(Throwable throwable){
        return ResponseEntity.ok(Response.builder()
                .data("Response from Fallback !!")
                .code(HttpStatus.OK.value())
                .build());
    }
}
