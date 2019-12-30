/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.poli.interfaces;


/**
 *
 * @author joaomacedo
 */
public interface Interface {
    
    void inicializar();
    void abrirJogo(boolean criarJogadorAutonomo);    
    void createTabuleiro();
    void drawTabuleiro();
    void acabarJogo(String nomeGanhador);

    
}
