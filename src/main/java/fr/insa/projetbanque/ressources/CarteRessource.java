package fr.insa.projetbanque.ressources;

import fr.insa.projetbanque.DTO.CarteDTO;
import fr.insa.projetbanque.exeption.ProcessExeption;
import fr.insa.projetbanque.services.CarteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cartes")
public class CarteRessource {
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
