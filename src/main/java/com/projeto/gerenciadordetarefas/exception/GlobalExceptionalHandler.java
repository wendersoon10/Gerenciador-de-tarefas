package com.projeto.gerenciadordetarefas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionalHandler {


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> tratarCredenciaisInvalidas(BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Email ou senha incorretos");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> tratarErro(RuntimeException ex){

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarErroValidacao(
            MethodArgumentNotValidException ex){

        Map<String, String> erros = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(erro -> {
                            erros.put(
                                    erro.getField(),
                                    erro.getDefaultMessage()
                            );
                });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erros);
    }

    @ExceptionHandler(AcessoNegadoException.class)
    public ResponseEntity<String> tratarAcessoNegado (AcessoNegadoException ex){

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ex.getMessage());
    }

    @ExceptionHandler(EmailExistenteException.class)
    public ResponseEntity<String> tratarEmailExistente (EmailExistenteException ex){

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}
