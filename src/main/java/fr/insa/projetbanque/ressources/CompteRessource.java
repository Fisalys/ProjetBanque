package fr.insa.projetbanque.ressources;

import fr.insa.projetbanque.services.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comptes")
public class CompteRessource {
    @Autowired
    CompteService compteService;


}
