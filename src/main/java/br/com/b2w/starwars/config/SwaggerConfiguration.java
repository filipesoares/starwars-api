package br.com.b2w.starwars.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("br.com.b2w.starwars.rest"))
          .paths(PathSelectors.regex("/planets.*"))
          .build()
          .apiInfo(apiInfo());
    }
	
	@SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
        return new ApiInfo(
                "StarWars REST API",
                "API de planetas do universo starwars",
                "v1",
                "Terms of service",
                "Filipe Oliveira - <filipesomstd@gmail.com>",
                "", 
                "");     
    }
    
}
