package fr.insa.projetbanque.services;

import fr.insa.projetbanque.DTO.ClientDTO;
import fr.insa.projetbanque.exeption.NotValidExeption;
import fr.insa.projetbanque.exeption.ProcessExeption;
import fr.insa.projetbanque.models.Agence;
import fr.insa.projetbanque.models.Client;
import fr.insa.projetbanque.repositories.AgenceRepository;
import fr.insa.projetbanque.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService extends CommonService{

    public static final String CLIENT_NOT_FOUND = "Client non trouvé avec l'id : %s";


    @Autowired
    ClientRepository clientRepository;

    public Client getClientById(Integer id) throws ProcessExeption
    {
        Client client = clientRepository.findById(id).orElseThrow(()-> new ProcessExeption(String.format(CLIENT_NOT_FOUND, id)));
        return client;
    }


    public Client saveClient(ClientDTO ClientToCreate) throws ProcessExeption
    {
        Client c = Client.builder()
                .nom(ClientToCreate.getNom())
                .prenom(ClientToCreate.getPrenom())
                .age(ClientToCreate.getAge())
                .tel(ClientToCreate.getTel())
                .mail(ClientToCreate.getMail())
                .adresse(ClientToCreate.getAdresse())
                .build();
        return this.clientRepository.save(c);
    }

    public void validateClientModel(ClientDTO clientToCreate) throws NotValidExeption
    {
        NotValidExeption e = new NotValidExeption();

        if(clientToCreate == null)
            e.getMessages().add("ClientModel : Null");
        if(clientToCreate.getNom() == null || clientToCreate.getNom().isBlank())
            e.getMessages().add("nom est vide");
        if(clientToCreate.getPrenom() == null || clientToCreate.getPrenom().isBlank())
            e.getMessages().add("prenom est vide");
        if(clientToCreate.getAge() == 0)
            e.getMessages().add("age est vide");
        if(clientToCreate.getTel() == null || clientToCreate.getTel().isBlank())
            e.getMessages().add("tel est vide");
        if(clientToCreate.getMail() == null || clientToCreate.getMail().isBlank())
            e.getMessages().add("mail est vide");
        if(clientToCreate.getAdresse() == null || clientToCreate.getAdresse().isBlank())
            e.getMessages().add("adresse est vide");

        if(!e.getMessages().isEmpty())
            throw e;
    }

}
