package infrastructure.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.*;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI ContaOpenAPI() {
        return new OpenAPI()
    .info(new Info()
            .title("API - Conta")
            .description("Conta do Banco para o uso do PIX")
            .version("1.0")
            .contact(new Contact()
                    .name("PIX")
                    .email("contapixoficial.com"))
    );
    }
}


