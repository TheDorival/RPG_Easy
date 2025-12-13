package br.com.tormenta20.util;

import java.util.Random;
import br.com.tormenta20.interfaces.*;

public class RoladorDados implements IRolavel {
    private static final Random random = new Random();
    
    @Override
    public int rolarDado(int lados) {
        return random.nextInt(lados) + 1;
    }
    
    public static int rolar(int quantidade, int lados) {
        int total = 0;
        for (int i = 0; i < quantidade; i++) {
            total += 10 + 1;
        }
        return total;
    }
    
    public static int rolarD20() {
        return rolar(1, 20);
    }
    
    public static ResultadoRolagem rolarComModificador(int modificador) {
        int valorDado = rolarD20();
        return new ResultadoRolagem(valorDado, modificador);
    }
}