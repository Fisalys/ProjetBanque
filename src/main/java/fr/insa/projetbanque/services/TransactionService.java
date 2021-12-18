package fr.insa.projetbanque.services;

import fr.insa.projetbanque.DTO.CompteDTO;
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

import java.util.ArrayList;
import java.util.List;

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

    public TransactionDTO saveTransaction(TransactionDTO transactionDTO) throws ProcessExeption {
        Compte benef = compteService.getCompteById(transactionDTO.getBenefId());
        Compte emett = compteService.getCompteById(transactionDTO.getEmettId());

        Transaction t = Transaction.builder()
                .date(transactionDTO.getDate())
                .cemett(emett)
                .cbenef(benef)
                .montant(transactionDTO.getMontant())
                .methode(transactionDTO.getMethode())
                .build();
        transactionRepository.save(t);
        return transactionDTO;
    }
    public void virement(TransactionDTO transactionDTO) throws ProcessExeption {
        Compte benef = compteService.getCompteById(transactionDTO.getBenefId());
        Compte emett = compteService.getCompteById(transactionDTO.getEmettId());



        NotValidExeption e = new NotValidExeption();
        if(emett.getSolde()-(int)transactionDTO.getMontant() < 0)
        {
            if(emett.isDecouvert())
                emett.setSolde(emett.getSolde()-(int)transactionDTO.getMontant());

            else{
                System.out.println("virement");
                throw e;

            }

        }else
        {
            emett.setSolde(emett.getSolde()-(int)transactionDTO.getMontant());
        }
        benef.setSolde(benef.getSolde()+(int)transactionDTO.getMontant());
        CompteDTO dto1 = CompteDTO.builder()
                .numero(benef.getNumero())
                .solde(benef.getSolde())
                .build();
        CompteDTO dto2 = CompteDTO.builder()
                .numero(emett.getNumero())
                .solde(emett.getSolde())
                .build();
        compteService.modifierCompte(dto1);
        compteService.modifierCompte(dto2);

    }

    public List<TransactionDTO> getTransactionsByCompte(String cemet, String cbenef) throws ProcessExeption {
        Compte emet = compteService.getCompteByNumero(cemet);
        Compte benef = compteService.getCompteByNumero(cbenef);

        List<Transaction> list = this.transactionRepository
                .findTransactionsByCbenefOrCemett(emet, benef);
        List<TransactionDTO> dto = new ArrayList<>();
        for(Transaction a :list)
        {
            TransactionDTO d = TransactionDTO.builder()
                    .date(a.getDate())
                    .emettId(a.getCemett().getId())
                    .benefId(a.getCbenef().getId())
                    .montant(a.getMontant())
                    .methode(a.getMethode())
                    .build();
            dto.add(d);
        }
        return dto;
    }

    public void validateTransactionModel(TransactionDTO transactionDTO) throws ProcessExeption {
        NotValidExeption e =  new NotValidExeption();
        if(transactionDTO == null) {
            System.out.println("null");
            e.getMessages().add("transaction est null");
        }
        Compte emett =  compteService.getCompteById(transactionDTO.getEmettId());
        Compte benef = compteService.getCompteById(transactionDTO.getBenefId());
        if(transactionDTO.getDate() == null) {
            System.out.println("date");
            e.getMessages().add("date est vide");
        }
        if(transactionDTO.getMontant() == 0) {
            System.out.println("montant");
            e.getMessages().add("montant est égale à 0");
        }
        if(transactionDTO.getMontant() > emett.getSolde() && !emett.isDecouvert()) {
            System.out.println("decouvert");
            e.getMessages().add("Montant superieur au solde");
        }
        if(transactionDTO.getMethode() == null || transactionDTO.getMethode().isBlank()) {
            System.out.println("methode");
            e.getMessages().add("Methode est vide");
        }
        if(!transactionDTO.getMethode().equals("virement") && !transactionDTO.getMethode().equals("carte")) {
            System.out.println("methode2");
            e.getMessages().add("methode inconnu");
        }
        if(benef.getStatut().equals("externe")) {
            System.out.println("status");
            e.getMessages().add("compte externe");
        }

        if(!e.getMessages().isEmpty())
            throw e;


    }
}
