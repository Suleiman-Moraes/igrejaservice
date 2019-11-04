package com.moraes.igrejaservice;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

@SpringBootApplication(scanBasePackages = { "com.moraes.igrejaservice" })
public class IgrejaserviceApplication extends SpringBootServletInitializer{
	
	public static void main(String[] args) {
		SpringApplication.run(IgrejaserviceApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(IgrejaserviceApplication.class);
	}
	
	@Bean
	public LocaleResolver localeResolver() { 
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}
}
