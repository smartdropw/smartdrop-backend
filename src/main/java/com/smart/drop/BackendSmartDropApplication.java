package com.smart.drop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@EnableJpaAuditing
@SpringBootApplication
public class BackendSmartDropApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendSmartDropApplication.class, args);
    }

    @Bean
    @SuppressWarnings("unused")
    public CommandLineRunner printSwaggerUrl(Environment env) {
        return unused -> {
            String port = env.getProperty("server.port", "8787");
            String swaggerUrl = "http://localhost:" + port + "/swagger-ui/index.html";
            System.out.println("\n" +
                "╔════════════════════════════════════════════════════╗\n" +
                "║         🚀 SmartDrop Backend iniciado 🚀          ║\n" +
                "╠════════════════════════════════════════════════════╣\n" +
                "║  📖 Swagger UI disponible en:                      ║\n" +
                "║  " + String.format("%-45s", swaggerUrl) + "║\n" +
                "║                                                    ║\n" +
                "║  📋 OpenAPI JSON:                                  ║\n" +
                "║  http://localhost:" + port + "/v3/api-docs" + "                         ║\n" +
                "╚════════════════════════════════════════════════════╝\n");
        };
    }

}
