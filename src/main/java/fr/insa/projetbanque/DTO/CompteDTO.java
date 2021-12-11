package fr.insa.projetbanque.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class CompteDTO implements Serializable {
    private Integer id;
    private String numero;
    private List<Integer> listIdClient;
    private String statut; // externe / interne
    private int solde;
    private String IBAN;
}
