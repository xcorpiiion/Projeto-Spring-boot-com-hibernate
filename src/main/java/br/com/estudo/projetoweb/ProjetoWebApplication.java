package br.com.estudo.projetoweb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjetoWebApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProjetoWebApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
