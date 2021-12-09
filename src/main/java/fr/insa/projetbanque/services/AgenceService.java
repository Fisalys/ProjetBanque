package fr.insa.projetbanque.services;

import fr.insa.projetbanque.DTO.AgenceDTO;
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
public class AgenceService extends CommonService{

    private static final String AGENCE_NOT_FOUND = "Agence non trouvée avec l'id : %s";

    @Autowired
    private AgenceRepository agenceRepository;
    @Autowired
    private ClientService clientService;

    public Agence getAgenceById(Integer id) throws ProcessExeption
    {
        Agence agence = agenceRepository.findById(id).orElseThrow(()-> new ProcessExeption(String.format(AGENCE_NOT_FOUND, id)));
        return agence;
    }

    public Agence saveAgence(AgenceDTO agenceToCreate) throws ProcessExeption
    {
        List<Client> list = new ArrayList<>();
        for(Integer i:agenceToCreate.getListIdClient())
        {
            list.add(clientService.getClientById(i));
        }

        Agence a = Agence.builder()
                .adresse(agenceToCreate.getAdresse())
                .code(agenceToCreate.getCode())
                .client(list)
                .build();
        return agenceRepository.save(a);
    }

    public void validateAgenceModel(AgenceDTO agenceToCreate) throws NotValidExeption
    {
        NotValidExeption e = new NotValidExeption();

        if(agenceToCreate ==  null)
            e.getMessages().add("AgenceModel : null");
        if(agenceToCreate.getAdresse() == null || agenceToCreate.getAdresse().isBlank())
            e.getMessages().add("Adresse est vide");
        if(agenceToCreate.getCode() == null || agenceToCreate.getAdresse().isBlank())
        {
            e.getMessages().add("Code est null");
            if(agenceToCreate.getCode().length() != 5)
                e.getMessages().add("Code n'est pas sur 5 caractère");
        }

        if(!e.getMessages().isEmpty())
            throw e;
    }



}
