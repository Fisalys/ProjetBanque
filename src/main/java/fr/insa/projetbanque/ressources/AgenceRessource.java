package fr.insa.projetbanque.ressources;

import fr.insa.projetbanque.dto.AgenceDTO;
import fr.insa.projetbanque.exeptions.ProcessExeption;
import fr.insa.projetbanque.services.AgenceService;
import fr.insa.projetbanque.services.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("agences")
public class AgenceRessource extends CommonService {
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
