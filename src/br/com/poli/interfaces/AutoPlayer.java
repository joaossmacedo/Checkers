/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.poli.interfaces;

import br.com.poli.damas.Jogador;
import br.com.poli.damas.Jogo;

/**
 *
 * @author joaomacedo
 */
public interface AutoPlayer {
    public boolean jogarAuto(Jogo jogo);
    public Jogador vencedor();
}
