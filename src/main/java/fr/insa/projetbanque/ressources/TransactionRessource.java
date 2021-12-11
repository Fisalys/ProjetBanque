package fr.insa.projetbanque.ressources;

import fr.insa.projetbanque.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transactions")
public class TransactionRessource {
    @Autowired
    TransactionService transactionService;
}
