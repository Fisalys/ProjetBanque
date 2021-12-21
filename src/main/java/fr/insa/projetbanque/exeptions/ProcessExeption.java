package fr.insa.projetbanque.exeptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ProcessExeption extends Exception{

    public ProcessExeption(String message)
    {super(message);}
}
