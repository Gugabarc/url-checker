package br.com.mirandalabs.urlchecker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JacksonConfig {
	
    @Bean
    public ObjectMapper objectMapper() {
    	ObjectMapper objMapper = new ObjectMapper();
    	objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    	objMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
    	
        return objMapper;
    }
}