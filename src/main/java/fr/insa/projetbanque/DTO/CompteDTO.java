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
    private String numero;
    private List<Integer> listIdClient;
    private List<Integer> listIdCarte;
    private String statut; // externe / interne
    private float solde;
    private String IBAN;
    private List<Integer> listIdTransaction;
}
