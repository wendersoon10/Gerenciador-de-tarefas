
package com.projeto.gerenciadordetarefas.infra;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI gerenciadorDeTarefasOpenAPI() {
        String esquemaSeguranca = "bearer-jwt";

        return new OpenAPI()
                .info(new Info()
                        .title("Gerenciador de Tarefas API")
                        .version("1.0.0")
                        .description(
                                "API REST para gerenciamento de usuários e tarefas, "
                                        + "com autenticação JWT e controle de acesso."
                        )
                )
                .components(new Components()
                        .addSecuritySchemes(esquemaSeguranca,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Informe o token JWT de acesso.")
                        )
                )
                .addSecurityItem(
                        new SecurityRequirement().addList(esquemaSeguranca)
                );
    }
}
