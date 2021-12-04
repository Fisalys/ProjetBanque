package fr.insa.projetbanque.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToMany
    private List<Client> client;
    @OneToMany
    private List<Carte> carte;
    private String statut; // externe / interne
    private float solde;
    private boolean decouvert;
    private String IBAN;
    @OneToMany
    private List<Transaction> transactions;
}
