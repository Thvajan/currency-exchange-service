package com.learning.currencyexchangeservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.currencyexchangeservice.model.CurrencyExchange;
import com.learning.currencyexchangeservice.repository.CurrencyExchangeRepository;

@RestController
@RequestMapping("currency-exchange")
public class CurrencyExchangeController {

	@Autowired
	CurrencyExchangeRepository currencyExchangeRepository;

	@Autowired
	private Environment environment;

	@GetMapping("/from/{from}/to/{to}")
	public CurrencyExchange getExchangeValue(@PathVariable String from, @PathVariable String to) {
//		CurrencyExchange currencyExchange = new CurrencyExchange(100L, from, to, BigDecimal.valueOf(50));
		CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);
		if (currencyExchange == null) {
			throw new RuntimeException("Unable to find data for " + from + " to " + to);
		}
		currencyExchange.setEnvironment(environment.getProperty("local.server.port"));

		return currencyExchange;
	}

}
