package fr.insa.projetbanque.ressources;

import fr.insa.projetbanque.dto.CompteDTO;
import fr.insa.projetbanque.exeptions.ProcessExeption;
import fr.insa.projetbanque.models.Compte;
import fr.insa.projetbanque.services.CommonService;
import fr.insa.projetbanque.services.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comptes")
public class CompteRessource extends CommonService {
    @Autowired
    CompteService compteService;

    @PostMapping
    public CompteDTO createCompte(@RequestBody CompteDTO compteDTO) throws ProcessExeption {
        compteService.validateCompteModel(compteDTO);
        return compteService.saveCompte(compteDTO);
    }

    @GetMapping
    public CompteDTO getCompteByIban(@RequestParam String iban) throws Exception {
        return compteService.getCompteByIban(iban);
    }

    @GetMapping("{numero}")
    public CompteDTO getCompteByNumero(@PathVariable("numero") String numero) throws ProcessExeption {
        Compte compte = compteService.getCompteByNumero(numero);
        CompteDTO dto = CompteDTO.builder()
                .numero(compte.getNumero())
                .solde(compte.getSolde())
                .iban(compte.getIban())
                .id(compte.getId())
                .build();
        return dto;
    }

    @PutMapping("{numero}")
    public CompteDTO modifierCompte(@PathVariable String numero, @RequestParam String statut)
    {
        return compteService.modifierCompte(numero, statut);
    }

    @DeleteMapping("{numero}")
    public void deleteCompte(@PathVariable String numero) throws ProcessExeption {
        compteService.deleteCompte(numero);
    }
}
