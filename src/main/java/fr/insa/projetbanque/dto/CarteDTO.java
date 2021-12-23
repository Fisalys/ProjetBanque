package fr.insa.projetbanque.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class CarteDTO implements Serializable {
    private String numero;
    private int plafond;
    private String mdp;
    private Integer idCompte;
}
