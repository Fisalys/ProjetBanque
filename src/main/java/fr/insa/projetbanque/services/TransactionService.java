package fr.insa.projetbanque.services;

import fr.insa.projetbanque.dto.CompteDTO;
import fr.insa.projetbanque.dto.TransactionDTO;
import fr.insa.projetbanque.exeptions.NotValidExeption;
import fr.insa.projetbanque.exeptions.ProcessExeption;
import fr.insa.projetbanque.models.Compte;
import fr.insa.projetbanque.models.Transaction;
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
    // permet de creer un virement à partir de 2 comptes
    public void virement(TransactionDTO transactionDTO) throws ProcessExeption {
        Compte benef = compteService.getCompteById(transactionDTO.getBenefId());
        Compte emett = compteService.getCompteById(transactionDTO.getEmettId());

        NotValidExeption e = new NotValidExeption();
        if(emett.getSolde()-(int)transactionDTO.getMontant() < 0)
        {
            if(emett.isDecouvert())
                emett.setSolde(emett.getSolde()-(int)transactionDTO.getMontant());
            else throw e;
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
            e.getMessages().add("transaction est null");
        }else
        {
            Compte emett =  compteService.getCompteById(transactionDTO.getEmettId());
            Compte benef = compteService.getCompteById(transactionDTO.getBenefId());
            if(transactionDTO.getDate() == null) {
                e.getMessages().add("date est vide");
            }
            if(transactionDTO.getMontant() == 0) {
                e.getMessages().add("montant est égale à 0");
            }
            if(transactionDTO.getMontant() > emett.getSolde() && !emett.isDecouvert()) {
                e.getMessages().add("Montant superieur au solde");
            }
            if(transactionDTO.getMethode() == null || transactionDTO.getMethode().isBlank()) {
                e.getMessages().add("Methode est vide");
            }else if(!transactionDTO.getMethode().equals("virement") && !transactionDTO.getMethode().equals("carte")) {
                e.getMessages().add("methode inconnu");
            }
            if(benef.getStatut().equals("externe")) {
                e.getMessages().add("compte externe");
            }
        }

        if(!e.getMessages().isEmpty())
            throw e;
    }
}
