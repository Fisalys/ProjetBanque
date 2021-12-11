package fr.insa.projetbanque.ressources;

import fr.insa.projetbanque.services.CarteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cartes")
public class CarteRessource {
    @Autowired
    CarteService carteService;


}
