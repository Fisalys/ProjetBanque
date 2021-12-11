package fr.insa.projetbanque.DTO;

import lombok.*;
import java.io.Serializable;
@Getter
@Setter
public class ClientDTO implements Serializable {
    private Integer id;
    private String nom;
    private String prenom;
    private int age;
    private String tel;
    private String mail;
    private String adresse;
    private Integer idAgence;
}
