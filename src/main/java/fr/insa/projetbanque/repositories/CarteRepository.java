package fr.insa.projetbanque.repositories;

import fr.insa.projetbanque.models.Carte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteRepository extends JpaRepository<Carte, Integer> {

    Carte findCarteByNumero(String numero);
}
