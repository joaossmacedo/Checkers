/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.poli.damas;

import br.com.poli.enums.CoresCasa;
import br.com.poli.enums.CoresPeca;

/**
 *
 * @author joaomacedo
 */
public class Tabuleiro {

    private Casa[][] grid = new Casa[8][8];

    public Tabuleiro() {
        int i, j;
        for (i = 0; i < grid.length; i++) {
            for (j = 0; j < grid[0].length; j++) {
                if ((i + j) % 2 == 0) {
                    grid[i][j] = new Casa(false, CoresCasa.BRANCA);
                } else {
                    grid[i][j] = new Casa(false, CoresCasa.PRETA);
                }
            }
        }

    }

    public void gerarTabuleiro(Jogador jogador1, Jogador jogador2) {
        int i, j;
        for (i = 0; i < grid.length; i++) {
            for (j = 0; j < grid[0].length; j++) {
                if ((i + j) % 2 == 1) {
                    if (i >= 5) {
                        this.grid[i][j].setOcupada(true);
                        this.grid[i][j].createPeca(CoresPeca.CLARA, jogador1);
                    } else if (i <= 2) {
                        this.grid[i][j].setOcupada(true);
                        this.grid[i][j].createPeca(CoresPeca.ESCURA, jogador2);
                    }
                }
            }
        }
    }

    public void executarMovimento(int linhaInicial, int colunaInicial, int linhaFinal, int colunaFinal) {
        grid[linhaFinal][colunaFinal].setOcupada(true);
        grid[linhaInicial][colunaInicial].setOcupada(false);
        grid[linhaFinal][colunaFinal].setPeca(grid[linhaInicial][colunaInicial].getPeca());
        grid[linhaInicial][colunaInicial].setPeca(null);

    }

    public void imprimirTabuleiro() {
        int i, j;

        System.out.print("\n\n");

        for (i = 0; i < grid.length; i++) {
            for (j = 0; j < grid[0].length; j++) {
                if (this.grid[i][j].getOcupada() == true) {
                    if (this.grid[i][j].getPeca() instanceof Dama) {
                        if (this.grid[i][j].getPeca().getCorPeca().equals(CoresPeca.CLARA)) {
                            System.out.print("C");
                        } else {
                            System.out.print("E");
                        }
                    } else {
                        if (this.grid[i][j].getPeca().getCorPeca().equals(CoresPeca.CLARA)) {
                            System.out.print("c");
                        } else {
                            System.out.print("e");
                        }
                    }
                } else {
                    if (this.grid[i][j].getCorCasa().equals(CoresCasa.BRANCA)) {
                        System.out.print(" ");
                    } else {
                        System.out.print("-");
                    }
                }
            }
            System.out.print("\n");
        }
    }

    public Casa[][] getGrid() {
        return grid;
    }
}
