package fr.insa.projetbanque.services;

import fr.insa.projetbanque.DTO.ClientDTO;
import fr.insa.projetbanque.DTO.CompteDTO;
import fr.insa.projetbanque.exeption.NotValidExeption;
import fr.insa.projetbanque.exeption.ProcessExeption;
import fr.insa.projetbanque.models.*;
import fr.insa.projetbanque.repositories.AgenceRepository;
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
    AgenceRepository agenceRepository;

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


        Compte c = Compte.builder()
                .client(client)
                .carte(null)
                .statut(CompteToCreate.getStatut())
                .solde(CompteToCreate.getSolde())
                .decouvert(false)
                .transactions(null)
                .build();

        CompteToCreate.setNumero(creerNumero(c));
        c.setNumero(CompteToCreate.getNumero());
        c.setIBAN(creerIBAN(CompteToCreate));

        return this.compteRepository.save(c);
    }

    public String creerNumero(Compte c)
    {
        String num = String.valueOf(c.getId());
        if(num.length()==1) num = "0000000000".concat(num);
        if(num.length()==2) num = "000000000".concat(num);
        if(num.length()==3) num = "00000000".concat(num);
        if(num.length()==4) num = "0000000".concat(num);
        if(num.length()==5) num = "000000".concat(num);
        if(num.length()==6) num = "00000".concat(num);
        if(num.length()==7) num = "0000".concat(num);
        if(num.length()==8) num = "000".concat(num);
        if(num.length()==9) num = "00".concat(num);
        if(num.length()==10)num = "0".concat(num);
        return num;
    }

    public String creerIBAN(CompteDTO compteDTO) throws ProcessExeption {

        String codeBanque = "30076";
        String IBAN = "FR76";
        IBAN =  IBAN.concat(codeBanque);
        Client c = clientService.getClientById(compteDTO.getListIdClient().get(0));
        IBAN = IBAN.concat(agenceRepository.findAgenceByClient(c).get(0).getCode());
        IBAN = IBAN.concat(compteDTO.getNumero());

        int rib = 97 - ((
                  89 * Integer.parseInt(codeBanque)
                + 15 * Integer.parseInt(agenceRepository.findAgenceByClient(c).get(0).getCode())
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
