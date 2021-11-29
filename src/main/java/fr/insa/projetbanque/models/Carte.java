package fr.insa.projetbanque.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Carte {
    @Id
    private int id;
    private Compte compte;
    private String plafond;
    private String numero;
    private String mdp;
}
