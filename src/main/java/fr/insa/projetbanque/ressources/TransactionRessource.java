package fr.insa.projetbanque.ressources;

import fr.insa.projetbanque.DTO.CompteDTO;
import fr.insa.projetbanque.DTO.TransactionDTO;
import fr.insa.projetbanque.exeption.ProcessExeption;
import fr.insa.projetbanque.models.Transaction;
import fr.insa.projetbanque.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transactions")
public class TransactionRessource {
    @Autowired
    TransactionService transactionService;
    @GetMapping
    List<TransactionDTO> getTransactionsByCompte(@RequestParam String cemett, @RequestParam String cbenef) throws ProcessExeption
    {
        return this.transactionService.getTransactionsByCompte(cemett,cbenef);
    }

    @PostMapping
    public TransactionDTO createTransaction(@RequestBody TransactionDTO transactionDTO) throws ProcessExeption {
        transactionService.validateTransactionModel(transactionDTO);
        transactionService.virement(transactionDTO);
        return transactionService.saveTransaction(transactionDTO);
    }
}
