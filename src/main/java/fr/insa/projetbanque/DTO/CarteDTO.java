package fr.insa.projetbanque.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarteDTO {
    private String numero;
    private int plafond;
    private String mdp;
    private Integer idCompte;
}
