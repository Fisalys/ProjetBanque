package fr.insa.projetbanque.repositories;

import fr.insa.projetbanque.exeption.ProcessExeption;
import fr.insa.projetbanque.models.Client;
import fr.insa.projetbanque.models.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Integer> {

    List<Compte> findComptesByClient(Client c);
    Compte findCompteByNumero(String numero);
    Compte findCompteByIBAN(String iban);
}
