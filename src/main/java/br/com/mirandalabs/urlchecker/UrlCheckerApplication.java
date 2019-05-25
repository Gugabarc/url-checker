package br.com.mirandalabs.urlchecker;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableRabbit
@EnableCaching
public class UrlCheckerApplication {

	public static void main(String[] args) {

		SpringApplication.run(UrlCheckerApplication.class, args);
	}

}
