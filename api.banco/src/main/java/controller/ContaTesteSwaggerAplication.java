package controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication(scanBasePackages = {"controller", "service", "repository"})
public class ContaTesteSwaggerAplication {
    public static void main(String[] args){
        SpringApplication.run(ContaTesteSwaggerAplication.class, args);
    }

}
