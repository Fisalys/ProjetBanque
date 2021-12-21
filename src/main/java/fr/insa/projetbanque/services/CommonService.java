package fr.insa.projetbanque.services;

import fr.insa.projetbanque.exeptions.NotValidExeption;
import fr.insa.projetbanque.exeptions.ProcessExeption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class CommonService {

    @ExceptionHandler(NotValidExeption.class)
    public ResponseEntity<String> handleModelNotValidException(
            NotValidExeption ex
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessages().toString());
    }

    @ExceptionHandler(ProcessExeption.class)
    public ResponseEntity<String> handleFonctionnalProcessException(
            ProcessExeption ex
    ) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }
}
