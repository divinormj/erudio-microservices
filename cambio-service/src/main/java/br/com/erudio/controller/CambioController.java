package br.com.erudio.controller;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.model.Cambio;
import br.com.erudio.repository.CambioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Cambio endpoint")
@RestController
@RequestMapping("/cambio-service")
@Slf4j
public class CambioController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CambioRepository repository;

	@Operation(summary = "Find a specific Cambio by from to")
	@GetMapping("/{amount}/{from}/{to}")
	public Cambio getCambio(
			@PathVariable("amount") BigDecimal amount, 
			@PathVariable("from") String from, 
			@PathVariable("to") String to) {
		log.info("getCambio is called with - {}, {} and {}", amount, from, to);
		final var cambio = repository.findByFromAndTo(from, to);
		if(Objects.isNull(cambio)) {
			new RuntimeException("Currency Unsupported");
		}
		final var port = environment.getProperty("local.server.port");
		cambio.setEnvironment(port);
		cambio.calcConvertedValue(amount);
		return cambio;
	}
	
}
