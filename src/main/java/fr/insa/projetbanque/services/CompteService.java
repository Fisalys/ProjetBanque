package fr.insa.projetbanque.services;

import fr.insa.projetbanque.DTO.ClientDTO;
import fr.insa.projetbanque.DTO.CompteDTO;
import fr.insa.projetbanque.exeption.NotValidExeption;
import fr.insa.projetbanque.exeption.ProcessExeption;
import fr.insa.projetbanque.models.*;
import fr.insa.projetbanque.repositories.ClientRepository;
import fr.insa.projetbanque.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompteService extends CommonService{
    @Autowired
    CompteRepository compteRepository;
    @Autowired
    ClientService clientService;
    @Autowired
    CarteService carteService;
    @Autowired
    TransactionService transactionService;

    private static final String COMPTE_NOT_FOUND = "Compte non trouvée avec l'id : %s";

    public Compte getCompteById(Integer id) throws ProcessExeption {
        Compte compte = compteRepository.findById(id).orElseThrow(()-> new ProcessExeption(String.format(COMPTE_NOT_FOUND, id)));
        return compte;
    }

    public Compte saveCompte(CompteDTO CompteToCreate) throws ProcessExeption
    {
        List<Client> client = new ArrayList<>();
        for(int i : CompteToCreate.getListIdClient())
            client.add(clientService.getClientById(i));

        List<Carte> carte = new ArrayList<>();
        for(int j : CompteToCreate.getListIdCarte())
            carte.add(carteService.getCarteById(j));

        List<Transaction> transaction = new ArrayList<>();
        for(int k : CompteToCreate.getListIdTransaction())
            transaction.add(transactionService.getTransactionById(k));

        Compte c = Compte.builder()
                .client(client)
                .carte(carte)
                .statut(CompteToCreate.getStatut())
                .solde(CompteToCreate.getSolde())
                .decouvert(false)
                .transactions(transaction)
                .build();

        CompteToCreate.setNumero(creerNumero(c));
        c.setNumero(CompteToCreate.getNumero());
        c.setIBAN(creerIBAN(CompteToCreate));

        return this.compteRepository.save(c);
    }

    public String creerNumero(Compte c)
    {
        return null;
    }

    public String creerIBAN(CompteDTO compteDTO) throws ProcessExeption {

        String codeBanque = "30076";
        String IBAN = "FR76";
        IBAN =  IBAN.concat(codeBanque);
        Client c = clientService.getClientById(compteDTO.getListIdClient().get(0));
        IBAN = IBAN.concat(c.getAgence().get(0).getCode());
        IBAN = IBAN.concat(compteDTO.getNumero());

        int rib = 97 - ((
                  89 * Integer.parseInt(codeBanque)
                + 15 * Integer.parseInt(c.getAgence().get(0).getCode())
                + 3  * Integer.parseInt(compteDTO.getNumero())) % 97);
        IBAN = IBAN.concat(String.valueOf(rib));
        return IBAN;
    }

    public void validateClientModel(CompteDTO compteToCreate) throws NotValidExeption
    {
        NotValidExeption e = new NotValidExeption();

        if(compteToCreate == null)
            e.getMessages().add("CompteModel : Null");
        if(compteToCreate.getListIdCarte().size() > 2)
            e.getMessages().add("2 cartes maximum");
        if(compteToCreate.getStatut() == null || compteToCreate.getStatut().isBlank())
            e.getMessages().add("statut est vide");
        if(!compteToCreate.getStatut().equals("externe") || !compteToCreate.getStatut().equals("interne"))
            e.getMessages().add("statut incorrect");
        if(compteToCreate.getSolde() < 0)
            e.getMessages().add("solde incorrect");
        if(compteToCreate.getIBAN() == null || compteToCreate.getIBAN().isBlank())
            e.getMessages().add("IBAN est vide");
        if(!e.getMessages().isEmpty())
            throw e;
    }
    
}
