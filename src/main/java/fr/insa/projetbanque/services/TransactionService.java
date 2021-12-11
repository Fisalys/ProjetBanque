package fr.insa.projetbanque.services;

import fr.insa.projetbanque.DTO.TransactionDTO;
import fr.insa.projetbanque.exeption.NotValidExeption;
import fr.insa.projetbanque.exeption.ProcessExeption;
import fr.insa.projetbanque.models.Agence;
import fr.insa.projetbanque.models.Compte;
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

    public Transaction saveTransaction(TransactionDTO transactionDTO) throws ProcessExeption {
        Compte benef = compteService.getCompteById(transactionDTO.getBenefId());
        Compte emett = compteService.getCompteById(transactionDTO.getEmettId());

        Transaction t = Transaction.builder()
                .date(transactionDTO.getDate())
                .cemett(emett)
                .cbenef(benef)
                .montant(transactionDTO.getMontant())
                .methode(transactionDTO.getMethode())
                .build();

        return transactionRepository.save(t);
    }

    public void validateTransactionModel(TransactionDTO transactionDTO) throws ProcessExeption {
        NotValidExeption e =  new NotValidExeption();
        if(transactionDTO == null)
            e.getMessages().add("transaction est null");
        Compte emett =  compteService.getCompteById(transactionDTO.getEmettId());

        if(transactionDTO.getDate() == null)
            e.getMessages().add("date est vide");
        if(transactionDTO.getMontant() == 0)
            e.getMessages().add("montant est égale à 0");
        if(transactionDTO.getMontant() > emett.getSolde() && !emett.isDecouvert())
            e.getMessages().add("Montant superieur au solde");
        if(transactionDTO.getMethode() == null || transactionDTO.getMethode().isBlank())
            e.getMessages().add("Methdoe est vide");
        if(!transactionDTO.getMethode().equals("virement") || !transactionDTO.getMethode().equals("carte"))
            e.getMessages().add("methode inconnu");

        if(!e.getMessages().isEmpty())
            throw e;


    }
}
