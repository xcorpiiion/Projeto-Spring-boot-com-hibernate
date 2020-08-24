package br.com.estudo.projetoweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.estudo.projetoweb.services.S3Service;

@SpringBootApplication
public class ProjetoWebApplication implements CommandLineRunner {

	@Autowired
	private S3Service s3Service;
	
    public static void main(String[] args) {
        SpringApplication.run(ProjetoWebApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    	s3Service.uploadFile("C:\\Users\\vinicius.cruz\\Pictures\\Camera Roll\\Akashi.png");
    }
}
