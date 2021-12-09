package fr.insa.projetbanque.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
@Getter
@Setter
public class AgenceDTO implements Serializable {
    private String adresse;
    private String code;
    private List<Integer> listIdClient;
}
