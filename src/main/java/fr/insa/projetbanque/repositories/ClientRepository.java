package fr.insa.projetbanque.repositories;

import fr.insa.projetbanque.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    Client findClientByNomAndPrenom(String nom, String prenom);
    Client findClientByMail(String mail);
}
