package com.duty.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@EnableFeignClients
public class DutyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DutyServiceApplication.class, args);
	}

}
