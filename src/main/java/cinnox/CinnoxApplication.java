package cinnox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CinnoxApplication {

	public static void main(String[] args) {
		System.getProperties().setProperty("server.port", "3000");
		SpringApplication.run(CinnoxApplication.class, args);
	}

}
