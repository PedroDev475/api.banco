package controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "repository")
@EntityScan(basePackages = "entity")
@SpringBootApplication(scanBasePackages = {"controller", "service", "repository"})
public class ContaTesteSwaggerAplication {
    public static void main(String[] args){
        SpringApplication.run(ContaTesteSwaggerAplication.class, args);
    }

}
