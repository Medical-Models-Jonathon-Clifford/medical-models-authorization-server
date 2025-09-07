package org.jono.medicalmodelsauthorizationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories(
        basePackages = "org.jono.medicalmodelsauthorizationserver.repository.jdbc"
)
public class MedicalModelsAuthorizationServerApplication {

    public static void main(final String[] args) {
        SpringApplication.run(MedicalModelsAuthorizationServerApplication.class, args);
    }

}
