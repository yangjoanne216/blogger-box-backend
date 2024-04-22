package com.duaphine.blogger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Blogger dox backend",
				description = "Blogger dox endpoints and apis",
				contact = @Contact(name = "Yang", email = "yang.yang2@dauphine.eu"),
				version = "1.0.0"
		)
)
public class BloggerBoxBackendApplication {

	public static void main(String[] args) {

		SpringApplication.run(BloggerBoxBackendApplication.class, args);
	}

}
