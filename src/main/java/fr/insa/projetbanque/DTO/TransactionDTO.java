package fr.insa.projetbanque.DTO;

import fr.insa.projetbanque.models.Compte;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Setter
@Getter
@Builder
public class TransactionDTO {
    private Date date;
    private Integer benefId;
    private Integer emettId;
    private float montant;
    private String methode;


}
