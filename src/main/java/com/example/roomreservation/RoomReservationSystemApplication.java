package com.example.roomreservation;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class RoomReservationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomReservationSystemApplication.class, args);
	}

}
