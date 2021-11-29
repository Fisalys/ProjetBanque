package fr.insa.projetbanque.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Compte {
    @Id
    private int id;
    private List<Client> client;
    private List<Carte> carte;
    private String statut; // externe / interne
    private float solde;
    private String decouvert;
    private String IBAN;
}
