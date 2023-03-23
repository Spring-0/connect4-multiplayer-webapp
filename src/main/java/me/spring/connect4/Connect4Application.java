package me.spring.connect4;

import me.spring.connect4.config.WebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "me.spring.connect4")
@EnableJpaRepositories
@Import(WebConfiguration.class)
public class Connect4Application {

	public static void main(String[] args) {
		SpringApplication.run(Connect4Application.class, args);
	}

}
