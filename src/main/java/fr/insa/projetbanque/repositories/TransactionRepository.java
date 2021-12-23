package fr.insa.projetbanque.repositories;

import fr.insa.projetbanque.models.Compte;
import fr.insa.projetbanque.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findTransactionsByCbenefOrCemett(Compte cbenef, Compte cemett);
}
