package org.example.backendchapterdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BackendChapterDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendChapterDemoApplication.class, args);
	}

}
