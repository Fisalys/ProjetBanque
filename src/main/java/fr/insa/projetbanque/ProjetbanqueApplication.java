package fr.insa.projetbanque;

import fr.insa.projetbanque.DTO.CompteDTO;
import fr.insa.projetbanque.exeption.ProcessExeption;
import fr.insa.projetbanque.services.CompteService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjetbanqueApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetbanqueApplication.class, args);
	}



}
