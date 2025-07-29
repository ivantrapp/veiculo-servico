package com.servico.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ProjetoBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetoBackendApplication.class, args);
    }

}
