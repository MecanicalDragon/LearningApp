package net.medrag.PaymentService.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ConditionalOnProperty(
        name = "payment.service.tests-disabled",
        havingValue = "true",
        matchIfMissing = true)
@Configuration
@EnableSwagger2
public class OpenAPIDocumentationConfig {

    private static final String VERSION = "1.0";
    private static final String LICENSE = "Apache 2.0";
    private static final String LICENSE_URL = "http://www.apache.org/licenses/LICENSE-2.0.html";
    private static final String TITLE = "PaymentService";
    private static final String DESCRIPTION = "Money management";
    private static final String AUTHOR = "Stanislav Tretyakov";
    private static final String EMAIL = "gaffstranger@gmail.com";
    private static final String AUTHOR_URL = "https://github.com/MecanicalDragon";

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(TITLE)
                .description(DESCRIPTION)
                .license(LICENSE)
                .licenseUrl(LICENSE_URL)
                .version(VERSION)
                .contact(new Contact(AUTHOR, AUTHOR_URL, EMAIL))
                .build();
    }

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("net.medrag.PaymentService.controller"))
                .build();
    }

}

