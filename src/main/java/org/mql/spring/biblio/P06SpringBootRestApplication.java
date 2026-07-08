package org.mql.spring.biblio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:beans.xml"})
public class P06SpringBootRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(P06SpringBootRestApplication.class, args);
	}

}
