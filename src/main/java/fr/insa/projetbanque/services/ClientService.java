package fr.insa.projetbanque.services;

import fr.insa.projetbanque.dto.ClientDTO;
import fr.insa.projetbanque.exeptions.NotValidExeption;
import fr.insa.projetbanque.exeptions.ProcessExeption;
import fr.insa.projetbanque.models.Client;
import fr.insa.projetbanque.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ClientDTO saveClient(ClientDTO ClientToCreate) throws ProcessExeption
    {
        Client c = Client.builder()
                .nom(ClientToCreate.getNom())
                .prenom(ClientToCreate.getPrenom())
                .age(ClientToCreate.getAge())
                .tel(ClientToCreate.getTel())
                .mail(ClientToCreate.getMail())
                .adresse(ClientToCreate.getAdresse())
                .build();
        this.clientRepository.save(c);
        ClientToCreate.setId(c.getId() );
        return ClientToCreate;
    }

    public void validateClientModel(ClientDTO clientToCreate) throws NotValidExeption
    {
        NotValidExeption e = new NotValidExeption();

        if(clientToCreate == null)
            e.getMessages().add("ClientModel : Null");
        else{
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
        }

        if(!e.getMessages().isEmpty())
            throw e;
    }

    public ClientDTO modifierCLient(ClientDTO clientDTO)
    {
        Client client = clientRepository.getById(clientDTO.getId());
        if(clientDTO.getNom() != null)
            client.setNom(clientDTO.getNom());
        if(clientDTO.getPrenom() != null)
            client.setPrenom(clientDTO.getPrenom());
        if(clientDTO.getAge() != 0)
            client.setAge(clientDTO.getAge());
        if(clientDTO.getTel() != null)
            client.setTel(clientDTO.getTel());
        if(clientDTO.getMail() != null)
            client.setMail(clientDTO.getMail());
        if(clientDTO.getAdresse() != null)
            client.setAdresse(clientDTO.getAdresse());

        clientRepository.save(client);
        ClientDTO dto = ClientDTO.builder()
                .nom(client.getNom())
                .prenom(client.getPrenom())
                .adresse(client.getAdresse())
                .mail(client.getMail())
                .id(client.getId())
                .age(client.getAge())
                .tel(client.getTel())
                .build();
        return dto;
    }

    public ClientDTO getClientByNomAndPrenom(String nom, String prenom) throws ProcessExeption {
        Client c = clientRepository.findClientByNomAndPrenom(nom, prenom);
        if(c == null)
            throw new ProcessExeption(String.format(CLIENT_NOT_FOUND, nom));
        ClientDTO clientDTO = ClientDTO.builder()
                .nom(c.getNom())
                .prenom(c.getPrenom())
                .adresse(c.getAdresse())
                .mail(c.getMail())
                .id(c.getId())
                .age(c.getAge())
                .tel(c.getTel())
                .build();
        return clientDTO;
    }

    public ClientDTO getClientByMail(String mail) throws ProcessExeption {
        Client c = clientRepository.findClientByMail(mail);
        if(c == null)
            throw new ProcessExeption(String.format(CLIENT_NOT_FOUND, mail));
        ClientDTO clientDTO = ClientDTO.builder()
                .nom(c.getNom())
                .prenom(c.getPrenom())
                .adresse(c.getAdresse())
                .mail(c.getMail())
                .id(c.getId())
                .age(c.getAge())
                .tel(c.getTel())
                .build();
        return clientDTO;
    }

    public void deleteClient(ClientDTO clientDTO) throws ProcessExeption {
        Client c = clientRepository.getById(clientDTO.getId());
        if(c==null)
            throw new ProcessExeption(String.format(CLIENT_NOT_FOUND, c.getId()));

        clientRepository.delete(c);
    }

}
