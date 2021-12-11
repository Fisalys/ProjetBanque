package fr.insa.projetbanque.ressources;

import fr.insa.projetbanque.DTO.AgenceDTO;
import fr.insa.projetbanque.models.Agence;
import fr.insa.projetbanque.services.AgenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("agences")
public class AgenceRessource {
    @Autowired
    AgenceService agenceService;

    @GetMapping()
    public List<AgenceDTO> getAllAgence(){
        return  this.agenceService.getAllAgence();
    }
}
