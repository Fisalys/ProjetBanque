package fr.insa.projetbanque.repositories;

import fr.insa.projetbanque.models.Agence;
import fr.insa.projetbanque.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgenceRepository extends JpaRepository<Agence, Integer> {

    List<Agence> findAgenceByClient(Client c);

}
