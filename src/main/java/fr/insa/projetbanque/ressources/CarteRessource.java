package fr.insa.projetbanque.ressources;

import fr.insa.projetbanque.dto.CarteDTO;
import fr.insa.projetbanque.exeptions.ProcessExeption;
import fr.insa.projetbanque.services.CarteService;
import fr.insa.projetbanque.services.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cartes")
public class CarteRessource extends CommonService {
    @Autowired
    CarteService carteService;

    @PostMapping
    public CarteDTO createCarte(@RequestBody CarteDTO carteDTO) throws ProcessExeption {
        carteService.validateCarteModel(carteDTO);
        return carteService.saveCarte(carteDTO);
    }

    @GetMapping
    public CarteDTO getCarteByNumero(@RequestParam String numero) throws ProcessExeption {
        return carteService.getCarteByNumero(numero);
    }

    @DeleteMapping
    public void deleteCarte(@RequestParam String numero)
    {
        carteService.deleteCarte(numero);
    }
}
