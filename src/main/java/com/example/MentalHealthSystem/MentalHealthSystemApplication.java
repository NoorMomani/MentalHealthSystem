package com.example.MentalHealthSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
public class MentalHealthSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(MentalHealthSystemApplication.class, args);
	}
}
