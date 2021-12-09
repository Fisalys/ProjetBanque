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

public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToMany
    private List<Client> client;
    @OneToMany
    private List<Carte> carte;
    private String numero;
    private String statut; // externe / interne
    private float solde;
    private boolean decouvert;
    private String IBAN;
    @OneToMany
    private List<Transaction> transactions;
}
