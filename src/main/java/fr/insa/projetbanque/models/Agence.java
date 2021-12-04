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

public class Agence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAgence;
    private String adresse;
    @ManyToMany
    private List<Client> client;
    private String code;
}
