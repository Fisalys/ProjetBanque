package fr.insa.projetbanque.services;

import fr.insa.projetbanque.dto.CompteDTO;
import fr.insa.projetbanque.exeptions.NotValidExeption;
import fr.insa.projetbanque.exeptions.ProcessExeption;
import fr.insa.projetbanque.models.*;
import fr.insa.projetbanque.repositories.AgenceRepository;
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

    public CompteDTO getCompteByIban(String Iban) throws ProcessExeption {
        Compte compte = compteRepository.findCompteByIban(Iban);
        if(compte == null)
            throw new ProcessExeption(String.format(COMPTE_NOT_FOUND, Iban));
        CompteDTO dto = CompteDTO.builder()
                .iban(compte.getIban())
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
                .statut(CompteToCreate.getStatut())
                .solde(CompteToCreate.getSolde())
                .decouvert(false)
                .build();

        CompteToCreate.setNumero(creerNumero(c));
        c.setNumero(CompteToCreate.getNumero());
        c.setIban(creerIban(CompteToCreate));
        this.compteRepository.save(c);
        CompteToCreate.setId(c.getId());
        CompteToCreate.setIban(c.getIban());
        return CompteToCreate;
    }

    public String creerNumero(Compte c)
    {
        Long leftLimit = 10000000000L;
        Long rightLimit = 99999999999L;
        long generatedLong = (long) (Math.random() * (rightLimit - leftLimit));
        return Long.toString(generatedLong);
    }

    public String creerIban(CompteDTO compteDTO) throws ProcessExeption {

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
        if(temp.length() == 1) temp = "0" + temp;
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
        }else{

            if(compteToCreate.getStatut() == null || compteToCreate.getStatut().isBlank()) {
                e.getMessages().add("statut est vide");
                System.out.println("testStatus");

            }else if(!compteToCreate.getStatut().equals("externe") && !compteToCreate.getStatut().equals("interne")) {
                e.getMessages().add("statut incorrect");
                System.out.println("testSpagethi");
            }

            if(compteToCreate.getSolde() < 0) {
                e.getMessages().add("solde incorrect");
                System.out.println("testFourchette");
            }
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

    public CompteDTO modifierCompte(String numero,String statut)
    {
        Compte compte = compteRepository.findCompteByNumero(numero);
        if(statut != null)
            compte.setStatut(statut);
        compteRepository.save(compte);
        return CompteDTO.builder().id(compte.getId())
                .decouvert(compte.isDecouvert())
                .numero(compte.getNumero())
                .iban(compte.getIban())
                .solde(compte.getSolde())
                .statut(compte.getStatut())
                .build();
    }

    public void deleteCompte(String numero) throws ProcessExeption {
        Compte compte = compteRepository.findCompteByNumero(numero);
        if(compte == null)
            throw new ProcessExeption(String.format(COMPTE_NOT_FOUND, numero));
        compteRepository.delete(compte);
    }

}
