package fr.insa.projetbanque.services;

import fr.insa.projetbanque.DTO.TransactionDTO;
import fr.insa.projetbanque.exeption.ProcessExeption;
import fr.insa.projetbanque.models.Agence;
import fr.insa.projetbanque.models.Transaction;
import fr.insa.projetbanque.repositories.AgenceRepository;
import fr.insa.projetbanque.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private static final String TRANSACTION_NOT_FOUND = "Transaction non trouvée avec l'id : %s";

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CompteService compteService;

    public Transaction getTransactionById(Integer id) throws ProcessExeption
    {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(()-> new ProcessExeption(String.format(TRANSACTION_NOT_FOUND, id)));
        return transaction;
    }


}
