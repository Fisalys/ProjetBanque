package fr.insa.projetbanque.ressources;

import fr.insa.projetbanque.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("clients")
public class ClientRessource {
    @Autowired
    ClientService clientService;


}
