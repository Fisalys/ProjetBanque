package fr.insa.projetbanque.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    private int id;
    private List<Agence> agence;
    private List<Compte> compte;
    private String nom;
    private String prenom;
    private int age;
    private String tel;
    private String mail;
    private String adresse;
}
