package com.learning.currencyexchangeservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircuitBreakerController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${custom.callmsg}")
	String message;

	@GetMapping("/random")
	@Retry(name = "random", fallbackMethod = "fallbackResponse")
	public String randomMethod() {
		logger.info(message);
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:801//random",
				String.class);
		return forEntity.getBody();
	}
	
	public String fallbackResponse(Exception exception) {
		return "Random service is down. Sorry for the Inconvenience";
	}
}
