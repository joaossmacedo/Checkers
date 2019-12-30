/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.poli.teste;

import br.com.poli.damas.Jogador;
import br.com.poli.damas.Jogo;
import br.com.poli.exceptions.FimJogoException;
import br.com.poli.exceptions.MovimentoInvalidoException;

/*
import br.com.poli.damas.Jogo;
import br.com.poli.exceptions.FimJogoException;
import br.com.poli.exceptions.MovimentoInvalidoException;

/**
 *
 * @author joaomacedo
 */
public class Teste {

    /**
     * @param args the command line arguments
     * @throws br.com.poli.exceptions.MovimentoInvalidoException
     */
    public static void main(String[] args) {
        //essa classe eh usada para simular jogadas e verificar se elas se compartam corretamente        
        try {
            //aqui o jogo eh criado
            Jogo jogo1 = new Jogo(new Jogador("joao"), new Jogador("rafael"));

            
            //aqui a partida se inicia
            jogo1.iniciarPartida();
            //aqui se imprime o tabuleiro
            jogo1.getTabuleiro().imprimirTabuleiro();

            //aqui se executa uma jogada
            //os 2 primeiros parametros do codigo sao as coordenadas inicias e os 2 ultimos as coordenadas finais
            jogo1.jogar(5, 6, 4, 5);
            //aqui se imprime o tabuleiro mais uma vez
            jogo1.getTabuleiro().imprimirTabuleiro();

            //a partir desse ponto ate o primeiro catch todo o codigo consiste de uma sequencia de jogadas e impressoes do tabuleiro  
            jogo1.jogar(2, 5, 3, 4);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(6, 5, 5, 6);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(2, 3, 3, 2);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(4, 5, 2, 3);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(5, 0, 4, 1);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(2, 3, 4, 1);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(2, 1, 3, 2);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(4, 1, 2, 3);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(1, 4, 3, 2);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(5, 2, 4, 3);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(0, 3, 1, 4);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(4, 3, 2, 1);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(2, 1, 0, 3);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(0, 3, 2, 5);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(1, 6, 3, 4);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(5, 4, 4, 5);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(0, 5, 1, 4);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(4, 5, 2, 3);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(2, 3, 0, 5);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(0, 7, 1, 6);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(5, 6, 4, 7);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(2, 7, 3, 6);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(4, 7, 2, 5);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(2, 5, 0, 7);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(1, 0, 2, 1);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(7, 6, 6, 5);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(2, 1, 3, 2);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(0, 5, 4, 1);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(0, 1, 1, 2);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(4, 1, 3, 2);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(1, 2, 2, 3);
            jogo1.getTabuleiro().imprimirTabuleiro();

            jogo1.jogar(3, 2, 0, 5);
            jogo1.getTabuleiro().imprimirTabuleiro();

        } catch (FimJogoException ex) {
            //aqui se trata o final do jogo
            System.out.println("\nfim de jogo");
        } catch (MovimentoInvalidoException ex) {
            //aqui se trata movimentos invalidos
            System.out.println("\nmovimento errado");
        }

    }
}
