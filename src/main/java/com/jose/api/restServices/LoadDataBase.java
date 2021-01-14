package com.jose.api.restServices;

import java.util.Date;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean; 

import com.jose.api.models.Servicios;
import com.jose.api.repositories.ServicesRepository;

import ch.qos.logback.classic.Logger; 

//@Configuration
public class LoadDataBase {
	private static final Logger log=(Logger) LoggerFactory.getLogger(LoadDataBase.class);
	
	@Bean
	CommandLineRunner initDataBase(ServicesRepository repository) {
		return args->{
			log.info("Preoloadind "+repository.save(new Servicios("zamba", "jose", "baño", "s/c", "9711533508", 123, new Date())));
			log.info("Preoloading "+repository.save(new Servicios("med", "jose", "baño", "s/c", "9711533508", 143, new Date())));
		};
	}
}
