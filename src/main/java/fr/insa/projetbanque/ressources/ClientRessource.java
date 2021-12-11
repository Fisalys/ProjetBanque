package fr.insa.projetbanque.ressources;

import fr.insa.projetbanque.DTO.ClientDTO;
import fr.insa.projetbanque.exeption.ProcessExeption;
import fr.insa.projetbanque.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("clients")
public class ClientRessource {
    @Autowired
    ClientService clientService;

    @PostMapping
    public ClientDTO createClient(@RequestBody ClientDTO clientToCreate) throws ProcessExeption
    {
        clientService.validateClientModel(clientToCreate);
        return clientService.saveClient(clientToCreate);
    }


}
