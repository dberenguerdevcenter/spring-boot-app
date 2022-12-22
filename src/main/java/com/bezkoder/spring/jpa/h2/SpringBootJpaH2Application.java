package com.bezkoder.spring.jpa.h2;

import com.bezkoder.spring.jpa.h2.vault.ConfigDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootJpaH2Application implements CommandLineRunner {

    @Autowired
    ConfigDto configDto;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJpaH2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Vault Config -> " + configDto.getUsername());
    }
}
