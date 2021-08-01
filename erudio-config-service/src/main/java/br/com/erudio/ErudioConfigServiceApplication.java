package br.com.erudio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ErudioConfigServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErudioConfigServiceApplication.class, args);
	}

}
