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
import java.util.Random;

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

    public CompteDTO getCompteByIBAN(String Iban) throws ProcessExeption {
        Compte compte = compteRepository.findCompteByIBAN(Iban);
        if(compte == null)
            throw new ProcessExeption(String.format(COMPTE_NOT_FOUND, Iban));
        CompteDTO dto = CompteDTO.builder()
                .IBAN(compte.getIBAN())
                .id(compte.getId())
                .numero(compte.getNumero())
                .solde(compte.getSolde())
                .build();
        return dto;
    }



    public Compte getCompteByNumero(String numero) throws ProcessExeption
    {
        Compte compte = compteRepository.findCompteByNumero(numero);
        if(compte == null)
            throw new ProcessExeption(String.format(COMPTE_NOT_FOUND, numero));
        return compte;
    }

    public CompteDTO saveCompte(CompteDTO CompteToCreate) throws ProcessExeption
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
                .transactionsBenef(null)
                .build();

        CompteToCreate.setNumero(creerNumero(c));
        c.setNumero(CompteToCreate.getNumero());
        c.setIBAN(creerIBAN(CompteToCreate));
        this.compteRepository.save(c);
        CompteToCreate.setId(c.getId());
        CompteToCreate.setIBAN(c.getIBAN());
        return CompteToCreate;
    }

    public String creerNumero(Compte c)
    {
        Long leftLimit = 10000000000L;
        Long rightLimit = 99999999999L;
        long generatedLong = (long) (Math.random() * (rightLimit - leftLimit));
        return Long.toString(generatedLong);
    }

    public String creerIBAN(CompteDTO compteDTO) throws ProcessExeption {

        String codeBanque = "30076";
        String IBAN = "FR76";
        IBAN =  IBAN.concat(codeBanque);
        Client c = clientService.getClientById(compteDTO.getListIdClient().get(0));
        IBAN = IBAN.concat(agenceRepository.findAgenceByClient(c).get(0).getCode());
        IBAN = IBAN.concat(compteDTO.getNumero());

        long rib = 97 - ((
                  89 * Integer.parseInt(codeBanque)
                + 15 * Integer.parseInt(agenceRepository.findAgenceByClient(c).get(0).getCode())
                + 3  * Long.parseLong(compteDTO.getNumero())) % 97);
        String temp = String.valueOf(rib);
        if(temp.length() == 1)
            temp = "0" + temp;
        IBAN = IBAN.concat(temp);
        System.out.println(rib);
        return IBAN;
    }

    public void validateCompteModel(CompteDTO compteToCreate) throws NotValidExeption
    {
        NotValidExeption e = new NotValidExeption();

        if(compteToCreate == null) {
            System.out.println("testNull");
            e.getMessages().add("CompteModel : Null");
        }
        if(compteToCreate.getStatut() == null || compteToCreate.getStatut().isBlank()) {
            e.getMessages().add("statut est vide");
            System.out.println("testStatus");
        }
        if(!compteToCreate.getStatut().equals("externe") && !compteToCreate.getStatut().equals("interne")) {
            e.getMessages().add("statut incorrect");
            System.out.println("testSpagethi");
        }
        if(compteToCreate.getSolde() < 0) {
            e.getMessages().add("solde incorrect");
            System.out.println("testFourchette");
        }
        if(!e.getMessages().isEmpty())
            throw e;
    }

    public CompteDTO modifierCompte(CompteDTO compteDTO) throws ProcessExeption {
        Compte compte = compteRepository.findCompteByNumero(compteDTO.getNumero());
        compte.setSolde(compteDTO.getSolde());
        compteRepository.save(compte);
        return compteDTO;

    }

    public void deleteCompte(String numero) throws ProcessExeption {
        Compte compte = compteRepository.findCompteByNumero(numero);
        if(compte == null)
            throw new ProcessExeption(String.format(COMPTE_NOT_FOUND, numero));
        compteRepository.delete(compte);
    }

}
