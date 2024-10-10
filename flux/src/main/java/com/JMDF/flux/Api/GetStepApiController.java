package com.JMDF.flux.Api;

import com.JMDF.flux.Service.ServiceGetStep;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orques/steps")
public class GetStepApiController {

    @Autowired
    private ServiceGetStep serviceGetStep;


    @PostMapping("/orchestration")
    public Mono<String> startOrchestration(@RequestBody String requestBody) {
        return serviceGetStep.orchestrateSteps(requestBody)
                .doOnSuccess(response -> {
                    // Opcional: Puedes hacer algo después de que se llame al webhook
                    System.out.println("Webhook llamado exitosamente: " );
                });
    }




}
