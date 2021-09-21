package org.example.backendchapterdemo;

import org.example.backendchapterdemo.util.KafkaConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@EnableFeignClients
@EnableKafka
public class BackendChapterDemoApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(BackendChapterDemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BackendChapterDemoApplication.class, args);
	}

	@KafkaListener(groupId = "customerId", topics = KafkaConstants.CUSTOMER_TOPIC)
	public void customerListener(String message) {
		LOGGER.info("Customer Kafka message consumed >> " + message);
	}

	@KafkaListener(groupId = "userId", topics = KafkaConstants.USER_TOPIC)
	public void userListener(String message) {
		LOGGER.info("User Kafka message consumed >> " + message);
	}
}
