package com.JMDF.flux.Api;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/webhook")
public class WebHookController {

    private static final Logger logger = LoggerFactory.getLogger(WebHookController.class);


    @PostMapping
    public ResponseEntity<String> receiveWebhook(@RequestBody(required = false) String payload) {
        logger.info("recibido el mensaje del orquestador");
        // Opcional: Puedes procesar el payload si es necesario
        return new ResponseEntity<>("Webhook recibido correctamente", HttpStatus.OK);
    }

}
