package fr.insa.projetbanque.ressources;

import fr.insa.projetbanque.DTO.AgenceDTO;
import fr.insa.projetbanque.exeption.ProcessExeption;
import fr.insa.projetbanque.models.Agence;
import fr.insa.projetbanque.services.AgenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public AgenceDTO createAgence(@RequestBody   AgenceDTO agenceDTO) throws ProcessExeption {
        agenceService.validateAgenceModel(agenceDTO);
        return agenceService.saveAgence(agenceDTO);
    }
}
