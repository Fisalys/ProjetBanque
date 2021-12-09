package fr.insa.projetbanque.services;

import fr.insa.projetbanque.exeption.ProcessExeption;
import fr.insa.projetbanque.models.Agence;
import fr.insa.projetbanque.models.Compte;
import fr.insa.projetbanque.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompteService {

    private static final String COMPTE_NOT_FOUND = "Compte non trouvée avec l'id : %s";

    @Autowired
    CompteRepository compteRepository;

    public Compte getCompteById(Integer id) throws ProcessExeption {
        Compte compte = compteRepository.findById(id).orElseThrow(()-> new ProcessExeption(String.format(COMPTE_NOT_FOUND, id)));
        return compte;
    }

}
