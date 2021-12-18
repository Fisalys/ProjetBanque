package fr.insa.projetbanque.ressources;

import fr.insa.projetbanque.DTO.CompteDTO;
import fr.insa.projetbanque.exeption.ProcessExeption;
import fr.insa.projetbanque.models.Compte;
import fr.insa.projetbanque.services.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

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

    @GetMapping
    public CompteDTO getCompteByIBAN(@RequestParam String IBAN) throws Exception {
        return compteService.getCompteByIBAN(IBAN);
    }

    @GetMapping("{Numero}")
    public CompteDTO getCompteByNumero(@PathVariable("Numero") String numero) throws ProcessExeption {
        Compte compte = compteService.getCompteByNumero(numero);
        CompteDTO dto = CompteDTO.builder()
                .numero(compte.getNumero())
                .solde(compte.getSolde())
                .IBAN(compte.getIBAN())
                .id(compte.getId())
                .build();
        return dto;
    }


    @DeleteMapping
    public void deleteCompte(@RequestParam String numero) throws ProcessExeption {
        compteService.deleteCompte(numero);
    }
}
