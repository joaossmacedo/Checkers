/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.poli.damas;

import br.com.poli.enums.CoresPeca;

/**
 *
 * @author joaomacedo
 */
public class Peca {

    private Jogador jogador;
    private CoresPeca corPeca;

    public Peca(CoresPeca corPeca, Jogador jogador) {
        this.corPeca = corPeca;
        this.jogador = jogador;
    }

    public Jogador getJogador() {
        return this.jogador;
    }

    public CoresPeca getCorPeca() {
        return this.corPeca;
    }

}
