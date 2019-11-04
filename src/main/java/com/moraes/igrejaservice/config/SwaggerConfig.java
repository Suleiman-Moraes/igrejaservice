package com.moraes.igrejaservice.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.moraes.igrejaservice.api.security.jwt.JwtTokenUtil;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SuppressWarnings("deprecation")
@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public Docket greetingApi() {
		String swaggerToken;
		try {
			final UserDetails userDetails = userDetailsService.loadUserByUsername("root");
			swaggerToken = jwtTokenUtil.generateToken(userDetails);
		} catch (Exception e) {
			swaggerToken = "";
		}
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.moraes.igrejaservice.api.controller"))
				.paths(PathSelectors.any()).build().apiInfo(metaData())
				.globalOperationParameters(Collections.singletonList(new ParameterBuilder().name("Authorization")
						.modelRef(new ModelRef("string")).parameterType("header").required(true).hidden(true)
						.defaultValue(swaggerToken).build()));

	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder().title("Igreja Spring Boot REST API").description("\"Web Service do Igreja\"")
				.version("1.0.0").license("Apache License Version 2.0")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"").build();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}
