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
    @OneToOne
    private Compte cbenef;
    @OneToOne
    private Compte cemett;
    private String methode; // virement ou carte
}
