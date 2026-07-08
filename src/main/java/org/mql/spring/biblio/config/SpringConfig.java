package org.mql.spring.biblio.config;

import org.mql.spring.biblio.models.Author;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
	public SpringConfig() {
	}
	
	@Bean
	public Author a1() {
		return new Author(201, "Brendan Eich", 1961);
	}
	
	@Bean
	public Author a2() {
		return new Author(202, "John Backus", 1924);
	}
	
	@Bean
	public String title() {
		return "Liste des auteurs trouvés";
	}
}
