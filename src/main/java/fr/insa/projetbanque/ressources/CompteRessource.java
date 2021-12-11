package fr.insa.projetbanque.ressources;

import fr.insa.projetbanque.DTO.CompteDTO;
import fr.insa.projetbanque.exeption.ProcessExeption;
import fr.insa.projetbanque.services.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comptes")
public class CompteRessource {
    @Autowired
    CompteService compteService;


    @PostMapping
    public CompteDTO createCompte(@RequestBody CompteDTO compteDTO) throws ProcessExeption {
        compteService.validateCompteModel(compteDTO);
        return compteService.saveCompte(compteDTO);

    }

}
