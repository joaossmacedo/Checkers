/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.poli.enums;

/**
 *
 * @author joaomacedo
 */
public enum Resultado {
    EMPATE(0), COMVENCEDOR(1);

    private final int resultado;

    private Resultado(int resultado) {
        this.resultado = resultado;
    }
}
