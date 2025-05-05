package com.anurag.parallelapicalls.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class WebClientUtil {
    private final WebClient webClient;

    @Autowired
    public WebClientUtil(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public <T> Mono<T> get(String url, Class<T> responseType, Map<String, String> headers) {
        return webClient
                .get()
                .uri(url)
                .headers(httpHeaders -> httpHeaders.setAll(headers))
                .retrieve()
                .bodyToMono(responseType)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    System.err.println("GET Error: " + ex.getStatusCode() + " - " + ex.getResponseBodyAsString());
                    return Mono.error(ex);
                });
    }

    public <T, R> Mono<T> post(String url, R body, Class<T> responseType, Map<String, String> headers, MediaType mediaType) {
        return webClient
                .post()
                .uri(url)
                .headers(httpHeaders -> httpHeaders.setAll(headers))
                .contentType(mediaType)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(responseType)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    System.err.println("POST Error: " + ex.getStatusCode() + " - " + ex.getResponseBodyAsString());
                    return Mono.error(ex);
                });
    }

    public <T, R> Mono<T> put(String url, R body, Class<T> responseType, Map<String, String> headers) {
        return webClient
                .put()
                .uri(url)
                .headers(httpHeaders -> httpHeaders.setAll(headers))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(responseType)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    System.err.println("PUT Error: " + ex.getStatusCode() + " - " + ex.getResponseBodyAsString());
                    return Mono.error(ex);
                });
    }

    public <T> Mono<T> delete(String url, Class<T> responseType, Map<String, String> headers) {
        return webClient
                .delete()
                .uri(url)
                .headers(httpHeaders -> httpHeaders.setAll(headers))
                .retrieve()
                .bodyToMono(responseType)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    System.err.println("DELETE Error: " + ex.getStatusCode() + " - " + ex.getResponseBodyAsString());
                    return Mono.error(ex);
                });
    }
}
