package com.arioatlas.eshop;

import com.arioatlas.eshop.storage.StorageProperties;
import com.arioatlas.eshop.storage.StorageService;
import com.arioatlas.eshop.validators.GroupValidator;
import com.arioatlas.eshop.validators.OrderValidator;
import com.arioatlas.eshop.validators.ProductValidator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.Validator;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class EShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(EShopApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService){
		return (args -> storageService.init());
	}

	@Bean
	public Validator productValidator(){
		return new ProductValidator();
	}

	@Bean
	public Validator groupValidator(){
		return new GroupValidator();
	}

	@Bean
	public Validator orderValidator(){
		return new OrderValidator();
	}

}
