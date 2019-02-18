package com.example.common.swagger2.config;

import com.example.common.swagger2.properties.Swagger2Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = {"swagger2.enabled"}, havingValue = "true")
public class Swagger2Configuration {
	
	@Autowired
	private Swagger2Properties swagger2Properties;
	
	@Bean
	public Docket buildDocket() {

		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(buildApiInfo())
				.groupName("api")
				.genericModelSubstitutes(DeferredResult.class)
				.useDefaultResponseMessages(false)
				//.globalOperationParameters(paramList)
				.forCodeGeneration(true)
				.select()
				.apis(RequestHandlerSelectors.basePackage(swagger2Properties.getBasePackage()))
				.paths(PathSelectors.any())
				.build();
	}
	
	private ApiInfo buildApiInfo() {
		return new ApiInfoBuilder()
				.title(swagger2Properties.getApiInfo().getTitle())
				.contact(new Contact(swagger2Properties.getApiInfo().getName(),
									 swagger2Properties.getApiInfo().getUrl(),
									 swagger2Properties.getApiInfo().getEmail()))
				.version(swagger2Properties.getApiInfo().getVersion()).build();
	}
}
