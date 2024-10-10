package com.JMDF.flux.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class ServiceGetStep {

    @Autowired
    private WebClient.Builder webClientBuilder;

    private final WebClient webClient;

    private int maxRetries = 3; // Número máximo de reintentos
    private long retryDelay = 2000;

    public ServiceGetStep(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8084/webhook").build(); // Cambia la URL según sea necesario
    }


    public Mono<String> getStepOne(String requestBody) {
        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8080/getStep")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getStepTwo(String requestBody) {
        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8081/getStep")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getStepThree(String requestBody) {
        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8082/getStep")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class);
    }

    private void callWebhook(String message) {
        webClientBuilder.build().post()
                .uri("http://localhost:8084/webhook") // Cambia esta URL si es necesario
                .bodyValue(message)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(response -> System.out.println("Respuesta del webhook: " + response),
                        error -> System.err.println("Error al llamar al webhook: " + error.getMessage()));
    }

    public Mono<String> orchestrateSteps(String requestBody) {
        Mono<String> stepOne = getStepOne(requestBody).onErrorReturn("Fallback Step 1: Error en el servicio");
        Mono<String> stepTwo = getStepTwo(requestBody).onErrorReturn("Fallback Step 2: Error en el servicio");
        Mono<String> stepThree = getStepThree(requestBody).onErrorReturn("Fallback Step 3: Error en el servicio");


        callWebhook("MENSAJE DESDE EL ORQUEESTADOR");

        return stepOne
                .zipWith(stepTwo, (s1, s2) -> "Step 1: " + s1 + " - Step 2: " + s2)
                .zipWith(stepThree, (s12, s3) -> s12 + " - Step 3: " + s3)
                .retryWhen(Retry.fixedDelay(maxRetries, Duration.ofMillis(retryDelay)));

    }
}
