package com.cibertec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CoinTrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoinTrackApplication.class, args);
	}

}
