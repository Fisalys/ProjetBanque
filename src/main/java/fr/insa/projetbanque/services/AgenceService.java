package fr.insa.projetbanque.services;

import fr.insa.projetbanque.exeption.ProcessExeption;
import fr.insa.projetbanque.models.Agence;
import fr.insa.projetbanque.repositories.AgenceRepository;
import fr.insa.projetbanque.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgenceService extends CommonService{

    private static final String AGENCE_NOT_FOUND = "Agence non trouvée avec l'id : %s";

    @Autowired
    private AgenceRepository agenceRepository;

    public Agence getAgenceById(Integer id) throws ProcessExeption
    {
        Agence agence = agenceRepository.findById(id).orElseThrow(()-> new ProcessExeption(String.format(AGENCE_NOT_FOUND, id)));
        return agence;
    }

    public Agence saveAgence()
    {
        return null;
    }

}
