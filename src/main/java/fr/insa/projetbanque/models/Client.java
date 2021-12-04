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
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String prenom;
    private int age;
    private String tel;
    private String mail;
    private String adresse;
    @ManyToMany
    private List<Agence> agence;
    @ManyToMany
    private List<Compte> compte;
}
