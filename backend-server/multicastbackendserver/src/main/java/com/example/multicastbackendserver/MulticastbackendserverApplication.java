package com.example.multicastbackendserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class MulticastbackendserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(MulticastbackendserverApplication.class, args);
	}

}
