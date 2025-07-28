package com.kevv.gestionale;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;

@SpringBootApplication
public class GestionaleApplication {

/*	@Bean

		public ObjectMapper getObejectMap() {
			return new ObjectMapper().findAndRegisterModules().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		}*/


	public static void main(String[] args) {
		SpringApplication.run(GestionaleApplication.class, args);


	}

}
