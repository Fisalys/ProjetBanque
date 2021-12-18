package fr.insa.projetbanque.services;

import fr.insa.projetbanque.DTO.CarteDTO;
import fr.insa.projetbanque.exeption.NotValidExeption;
import fr.insa.projetbanque.exeption.ProcessExeption;
import fr.insa.projetbanque.models.Carte;
import fr.insa.projetbanque.models.Compte;
import fr.insa.projetbanque.repositories.CarteRepository;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    public CarteDTO getCarteByNumero(String numero) throws ProcessExeption {
        Carte carte = carteRepository.findCarteByNumero(numero);
        if(carte == null)
            throw new ProcessExeption(String.format(CARTE_NOT_FOUND, numero));
        CarteDTO dto = CarteDTO.builder()
                .numero(carte.getNumero())
                .mdp(carte.getMdp())
                .plafond(carte.getPlafond())
                .build();
        return dto;
    }

    public CarteDTO saveCarte(CarteDTO carteToCreate) throws ProcessExeption {
        Compte compte = compteService.getCompteById(carteToCreate.getIdCompte());
        carteToCreate.setMdp(DigestUtils.md5Hex(carteToCreate.getMdp()));
        System.out.println(new String(Base64.decodeBase64(carteToCreate.getMdp().getBytes())));
        Carte c = Carte.builder()
                .compte(compte)
                .numero(carteToCreate.getNumero())
                .plafond(0)
                .mdp(carteToCreate.getMdp())
                .build();

        carteRepository.save(c);
        return carteToCreate;

    }

    public void validateCarteModel(CarteDTO carteToCreate) throws NotValidExeption
    {
        NotValidExeption e = new NotValidExeption();

        if(carteToCreate.getNumero() == null || carteToCreate.getNumero().isBlank())
            e.getMessages().add("Numero de carte est vide");
        if(carteToCreate.getNumero().length() != 16)
            e.getMessages().add("Numero de carte invalide");
        if(carteToCreate.getMdp() == null || carteToCreate.getMdp().isBlank())
            e.getMessages().add("Mot de passe est vide");

        if(!e.getMessages().isEmpty())
            throw e;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    public void deleteCarte(Integer id)
    {
        carteRepository.deleteById(id);
    }

    public void deleteCarte(String numero)
    {
       Carte c = carteRepository.findCarteByNumero(numero);
       carteRepository.delete(c);
    }

}
