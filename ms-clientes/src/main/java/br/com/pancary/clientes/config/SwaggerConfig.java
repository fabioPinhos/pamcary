package br.com.pancary.clientes.config;

import br.com.pancary.clientes.bean.Cliente;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
public class SwaggerConfig {

    private static final Contact DEFAULT_CONTACT = new Contact("PANCARY", "https://github.com/fabioPinhos/pancary", "grupoaplicacoes@semparar.net");
    private static final ApiInfo DEFAULT_API_INFO = new ApiInfoBuilder()
            .title("Clientes API")
            .description("O teste tem como objetivo avaliar o conhecimento técnico e pontos fortes do desenvolvedor")
            .version("1.0")
            .contact(DEFAULT_CONTACT)
            .build();

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.pancary.clientes.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(DEFAULT_API_INFO);
    }




}
