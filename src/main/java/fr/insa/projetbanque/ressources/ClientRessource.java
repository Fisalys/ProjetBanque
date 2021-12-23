package fr.insa.projetbanque.ressources;

import fr.insa.projetbanque.dto.ClientDTO;
import fr.insa.projetbanque.exeptions.ProcessExeption;
import fr.insa.projetbanque.services.ClientService;
import fr.insa.projetbanque.services.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
public class ClientRessource extends CommonService {
    @Autowired
    ClientService clientService;

    @PostMapping
    public ClientDTO createClient(@RequestBody ClientDTO clientToCreate) throws ProcessExeption
    {
        clientService.validateClientModel(clientToCreate);
        return clientService.saveClient(clientToCreate);
    }

    @GetMapping
    public ClientDTO getClientByNomAndPrenom(@RequestParam String nom, @RequestParam String prenom) throws ProcessExeption {
        return clientService.getClientByNomAndPrenom(nom, prenom);

    }

    @GetMapping("/mail")
    public ClientDTO getClientByMail(@RequestParam String mail) throws ProcessExeption {
        return clientService.getClientByMail(mail);

    }

    @PutMapping
    public ClientDTO modifierClient(@RequestBody ClientDTO clientDTO)
    {
        return clientService.modifierCLient(clientDTO);
    }

    @DeleteMapping
    public void deleteClient(@RequestBody  ClientDTO clientDTO) throws ProcessExeption {
        clientService.deleteClient(clientDTO);
    }
}
