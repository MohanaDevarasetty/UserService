package com.farmersbyte.enterprise.usermodule.config;

import com.authy.AuthyApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CommonConfig {
	
	@Value(value = "${authy.api-key}")
    private String API_KEY;

	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	@Bean
    public AuthyApiClient client() {
        return new AuthyApiClient(API_KEY);
    }
}
