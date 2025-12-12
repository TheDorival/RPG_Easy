package br.com.tormenta20.exception;

public class PersonagemInvalidoException extends RuntimeException {
    
    public PersonagemInvalidoException(String mensagem) {
        super(mensagem);
    }
    
    public PersonagemInvalidoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
