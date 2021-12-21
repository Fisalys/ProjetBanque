package fr.insa.projetbanque.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CarteDTO {
    private String numero;
    private int plafond;
    private String mdp;
    private Integer idCompte;
}
