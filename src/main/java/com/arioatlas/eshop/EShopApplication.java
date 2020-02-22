package com.arioatlas.eshop;

import com.arioatlas.eshop.cahce.Cache;
import com.arioatlas.eshop.cahce.RedisCache;
import com.arioatlas.eshop.storage.StorageProperties;
import com.arioatlas.eshop.storage.StorageService;
import com.arioatlas.eshop.validators.GroupValidator;
import com.arioatlas.eshop.validators.OrderValidator;
import com.arioatlas.eshop.validators.ProductValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.http.MediaType;
import org.springframework.plugin.core.OrderAwarePluginRegistry;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.validation.Validator;
import redis.clients.jedis.Jedis;

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

	@Value("${redis.host}")
	private String redisHost;
	@Value("${redis.port}")
	private int redisPort;
	@Value("${redis.password}")
	private String redisPassword;

	@Bean
	public Jedis redisCliFactory(){
		Jedis jedis = new Jedis(redisHost, redisPort);
		jedis.auth(redisPassword);
		return jedis;
	}

	@Bean
	@Autowired
	public Cache cacheObject(ObjectMapper objectMapper){
		return new RedisCache(objectMapper, redisCliFactory());
	}

}
