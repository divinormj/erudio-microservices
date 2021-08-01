package br.com.erudio.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.model.Book;
import br.com.erudio.proxy.CambioProxy;
import br.com.erudio.repository.BookRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Book endpoint")
@RestController
@RequestMapping("/book-service")
public class BookController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private BookRepository repository;
	
	@Autowired
	private CambioProxy proxy;
	
	@Operation(summary = "Find a specific Book by id")
	@GetMapping(value = "/{id}/{currency}")
	public Book getBook(@PathVariable("id") Long id, @PathVariable("currency") String currency) {
		final var book = repository.getById(id);
		if(Objects.isNull(book)) {
			throw new RuntimeException("Book not found");
		}
		final var port = environment.getProperty("local.server.port");
		final var cambio = proxy.getCambio(book.getPrice(), "USD", currency);
		final var enviroment = String.format("Book port: %s - Cambio port: %s", port, cambio.getEnvironment());
		book.setEnviroment(enviroment);
		book.setPrice(cambio.getConvertedValue());
		return book;
	}
}
