package fr.insa.projetbanque.services;

import fr.insa.projetbanque.DTO.CarteDTO;
import fr.insa.projetbanque.exeption.NotValidExeption;
import fr.insa.projetbanque.exeption.ProcessExeption;
import fr.insa.projetbanque.models.Carte;
import fr.insa.projetbanque.models.Compte;
import fr.insa.projetbanque.repositories.CarteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarteService extends CommonService{

    @Autowired
    CarteRepository carteRepository;

    @Autowired
    CompteService compteService;

    private static final String CARTE_NOT_FOUND = "Carte non trouvée avec l'id : %s";

    public Carte getCarteById(Integer id) throws ProcessExeption
    {
        Carte carte = carteRepository.findById(id).orElseThrow(()-> new ProcessExeption(String.format(CARTE_NOT_FOUND, id)));
        return carte;
    }

    public Carte saveCarte(CarteDTO carteToCreate) throws ProcessExeption {
        Compte compte = compteService.getCompteById(carteToCreate.getIdCompte());

        Carte c = Carte.builder()
                .compte(compte)
                .numero(carteToCreate.getNumero())
                .plafond(0)
                .mdp(carteToCreate.getMdp())
                .build();


        return carteRepository.save(c);

    }

    public void validateCarteModel(CarteDTO carteToCreate) throws NotValidExeption
    {
        NotValidExeption e = new NotValidExeption();

        if(carteToCreate.getNumero() == null || carteToCreate.getNumero().isBlank())
            e.getMessages().add("Numero de carte est vide");
        if(carteToCreate.getMdp() == null || carteToCreate.getMdp().isBlank())
            e.getMessages().add("Mot de passe est vide");

        if(!e.getMessages().isEmpty())
            throw e;
    }
}
