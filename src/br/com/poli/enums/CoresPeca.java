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
public enum CoresPeca {
    CLARA(0), ESCURA(1);

    private final int cor;

    private CoresPeca(int cor) {
        this.cor = cor;
    }
}
