package br.com.tormenta20.exception;

public class DadosInvalidosException extends RuntimeException {
    
    public DadosInvalidosException(String mensagem) {
        super(mensagem);
    }
    
    public DadosInvalidosException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}