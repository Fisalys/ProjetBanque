package fr.insa.projetbanque.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
@Getter
@Setter
@Builder
public class AgenceDTO implements Serializable {
    private Integer id;
    private String adresse;
    private String code;
    private List<Integer> listIdClient;
}
