package com.projeto.gerenciadordetarefas.exception;

public class EmailExistenteException extends RuntimeException {

    public EmailExistenteException(String message){
        super(message);
    }

}
