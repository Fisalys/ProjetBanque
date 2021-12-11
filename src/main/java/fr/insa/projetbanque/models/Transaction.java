package fr.insa.projetbanque.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String date;
    @ManyToOne
    private Compte cbenef;
    @ManyToOne
    private Compte cemett;
    private float montant;
    private String methode; // virement ou carte
}
