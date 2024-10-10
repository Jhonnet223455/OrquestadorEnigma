package com.JMDF.flux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class FluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(FluxApplication.class, args);
	}

}
