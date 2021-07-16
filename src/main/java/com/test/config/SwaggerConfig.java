package com.test.config;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * SwaggerConfig
 *
 * @author chengz
 * @date 2020/10/16
 */
@Configuration
@EnableSwagger2
@ConfigurationProperties(prefix = "swagger")
@Data
public class SwaggerConfig {
    private static final Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);

    private Boolean enable;

    private String title;
    private String description;
    private String version;

    private String license;
    private String licenseUrl;
    private String termsOfServiceUrl;

    private String host;
    private String basePath;
    private String basePackage;

    private ContactInfo contact;

    @Data
    public static class ContactInfo {
        private String name;
        private String email;
        private String url;
    }


    @Bean
    public Docket api() {
        logger.info("init swagger:basePath={}", basePath);
        //设置全局参数token 设置后每个接口都会需要
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("token").description("Token").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        pars.add(tokenPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enable)
                .pathMapping(basePath)
                .apiInfo(this.apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(pars).host(host);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(termsOfServiceUrl)
                .contact(new Contact(contact.getName(), contact.getUrl(), contact.getEmail()))
                .version(version)
                .build();
    }
}


