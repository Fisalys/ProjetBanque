package fr.insa.projetbanque.services;

import fr.insa.projetbanque.dto.AgenceDTO;
import fr.insa.projetbanque.exeptions.NotValidExeption;
import fr.insa.projetbanque.exeptions.ProcessExeption;
import fr.insa.projetbanque.models.Agence;
import fr.insa.projetbanque.models.Client;
import fr.insa.projetbanque.repositories.AgenceRepository;
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

    public AgenceDTO saveAgence(AgenceDTO agenceToCreate) throws ProcessExeption
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
        agenceRepository.save(a);
        agenceToCreate.setId(a.getIdAgence());
        return agenceToCreate;
    }

    public void validateAgenceModel(AgenceDTO agenceToCreate) throws NotValidExeption
    {
        NotValidExeption e = new NotValidExeption();

        if(agenceToCreate ==  null)
            e.getMessages().add("AgenceModel : null");
        else {
            if(agenceToCreate.getAdresse() == null || agenceToCreate.getAdresse().isBlank())
                e.getMessages().add("Adresse est vide");
            if(agenceToCreate.getCode() == null || agenceToCreate.getAdresse().isBlank())
            {
                e.getMessages().add("Code est null");
            }else if(agenceToCreate.getCode().length() != 5)
                e.getMessages().add("Code n'est pas sur 5 caractère");
        }

        if(!e.getMessages().isEmpty())
            throw e;
    }

    public List<AgenceDTO> getAllAgence(){
        List<Agence> list = this.agenceRepository
                .findAll();
        List<AgenceDTO> dto = new ArrayList<>();
        for(Agence a :list)
        {
            AgenceDTO d = AgenceDTO.builder()
                    .adresse(a.getAdresse())
                    .code(a.getCode())
                    .build();
            dto.add(d);
        }
        return dto;
    }


}
