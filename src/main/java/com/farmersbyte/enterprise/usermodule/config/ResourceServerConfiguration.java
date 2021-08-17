package com.farmersbyte.enterprise.usermodule.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(ResourceServerSecurityConfigurer config) {
		config.resourceId("farmer-bytes").stateless(true);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.cors().and()
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/oauth/**","/management/**","/configuration/**","/resources/**",
					"/swagger**","/swagger-resources/**","/swagger-ui/**","/webjars/**","/password/forgot/**",
					"/api/v2/**").permitAll()
			.antMatchers("/api/v1/**").authenticated();
		
	}
}
