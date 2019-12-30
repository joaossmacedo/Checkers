/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.poli.damas;

import br.com.poli.interfaces.AutoPlayer;

//TODO: JOGADOR_ATUAL
//
/**
 *
 * @author joaomacedo
 */
public class JogadorAutonomo extends Jogador implements AutoPlayer {

    private Jogador vencedor;
    private int qtdPecasJogador1, qtdPecasJogador2;

    public JogadorAutonomo(String nome) {
        super(nome);
    }

    @Override
    public boolean jogarAuto(Jogo jogo) {
        int i, j, k;

        boolean capturaPossivelExistente = false;

        //verifica se ha alguma captura possivel
        if (existeCapturaPossivelCampoDama(jogo) || existeCapturaPossivelCampo(jogo)) {
            capturaPossivelExistente = true;
        }

        for (i = 0; i < jogo.getTabuleiro().getGrid().length; i++) {
            for (j = 0; j < jogo.getTabuleiro().getGrid()[0].length; j++) {

                if (jogo.getTabuleiro().getGrid()[i][j].getOcupada()) {
                    if (jogo.getTabuleiro().getGrid()[i][j].getPeca().getJogador().getNome().equals(jogo.getNomeJogadorAtual())) {
                        if (capturaPossivelExistente) { //captura se possivel
                            if (!(jogo.getTabuleiro().getGrid()[i][j].getPeca() instanceof Dama)) { //trata da peca
                                if (existeCapturaPossivel(jogo, i, j)) { //verifica se existe alguma possivel captura para a peca atual
                                    efetuarCaptura(jogo, i, j);
                                    for (k = 0; k < jogo.getTabuleiro().getGrid()[0].length; k++) {
                                        if (jogo.getTabuleiro().getGrid()[0][k].getOcupada()) {
                                            if (jogo.getTabuleiro().getGrid()[0][k].getPeca().getJogador().equals(jogo.getJogador1())) {
                                                jogo.getTabuleiro().getGrid()[0][k].setPeca(new Dama(jogo.getTabuleiro().getGrid()[0][k].getPeca().getCorPeca(), jogo.getTabuleiro().getGrid()[0][k].getPeca().getJogador()));
                                            }
                                        } else if (jogo.getTabuleiro().getGrid()[7][k].getOcupada()) {
                                            if (jogo.getTabuleiro().getGrid()[7][k].getPeca().getJogador().equals(jogo.getJogador2())) {
                                                jogo.getTabuleiro().getGrid()[7][k].setPeca(new Dama(jogo.getTabuleiro().getGrid()[7][k].getPeca().getCorPeca(), jogo.getTabuleiro().getGrid()[7][k].getPeca().getJogador()));
                                            }
                                        }
                                    }
                                    jogo.setContadorJogadasSemCaptura(0);
                                    jogo.setJogadasOcorridas(jogo.getJogadasOcorridas() + 1);
                                    if (jogo.getJogadasOcorridas() % 2 == 0) {
                                        jogo.setJogadorAtual(jogo.getJogador1());
                                    } else {
                                        jogo.setJogadorAtual(jogo.getJogador2());
                                    }
                                    return true;
                                }
                            } else { //trata da dama
                                if (existeCapturaPossivelDama(jogo, i, j)) { //verifica se existe alguma possivel captura para a dama atual
                                    efetuarCapturaDama(jogo, i, j);
                                    jogo.setContadorJogadasSemCaptura(0);
                                    jogo.setJogadasOcorridas(jogo.getJogadasOcorridas() + 1);
                                    if (jogo.getJogadasOcorridas() % 2 == 0) {
                                        jogo.setJogadorAtual(jogo.getJogador1());
                                    } else {
                                        jogo.setJogadorAtual(jogo.getJogador2());
                                    }
                                    return true;
                                }
                            }
                        } else { //move se nao houver captura possivel
                            if (!(jogo.getTabuleiro().getGrid()[i][j].getPeca() instanceof Dama)) { //trata da peca
                                if (existeMovimentoPossivel(jogo, i, j)) { //verifica se existe algum movimento possivel para a peca atual
                                    efetuarMovimento(jogo, i, j);
                                    jogo.setContadorJogadasSemCaptura(jogo.getContadorJogadasSemCaptura() + 1);
                                    jogo.setJogadasOcorridas(jogo.getJogadasOcorridas() + 1);
                                    if (jogo.getJogadasOcorridas() % 2 == 0) {
                                        jogo.setJogadorAtual(jogo.getJogador1());
                                    } else {
                                        jogo.setJogadorAtual(jogo.getJogador2());
                                    }
                                    return true;
                                }
                            } else { //trata da dama
                                if (existeMovimentoPossivelDama(jogo, i, j)) { //verifica se existe algum movimento possivel para a dama atual
                                    efetuarMovimentoDama(jogo, i, j);
                                    jogo.setContadorJogadasSemCaptura(jogo.getContadorJogadasSemCaptura() + 1);
                                    jogo.setJogadasOcorridas(jogo.getJogadasOcorridas() + 1);
                                    if (jogo.getJogadasOcorridas() % 2 == 0) {
                                        jogo.setJogadorAtual(jogo.getJogador1());
                                    } else {
                                        jogo.setJogadorAtual(jogo.getJogador2());
                                    }
                                    return true;
                                }
                            }
                        }

                    }
                }

            }
        }

        this.contadorPecas(jogo);

        //acaba o jogo se acabar as pecas claras
        if (qtdPecasJogador1 == 0) {
            this.vencedor = jogo.getJogador1();
        }if (qtdPecasJogador2 == 0) {
            this.vencedor = this;
        }

        return false;
    }

    @Override
    public Jogador vencedor() {
        return vencedor;
    }

    //////////////////
    //metodos de captura da dama
    //////////////////
    private boolean existeCapturaPossivelCampoDama(Jogo jogo) {
        int i, j;

        for (i = 0; i < jogo.getTabuleiro().getGrid().length; i++) {
            for (j = 0; j < jogo.getTabuleiro().getGrid()[0].length; j++) {
                if (jogo.getTabuleiro().getGrid()[i][j].getOcupada()) {
                    if (jogo.getTabuleiro().getGrid()[i][j].getPeca() instanceof Dama) {
                        if (jogo.getTabuleiro().getGrid()[i][j].getPeca().getJogador().getNome().equals(jogo.getNomeJogadorAtual())) {
                            if (existeCapturaPossivelDama(jogo, i, j)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean existeCapturaPossivelDama(Jogo jogo, int linhaInicial, int colunaInicial) {
        int distanciaCima, distanciaBaixo, distanciaEsq, distanciaDir;

        distanciaCima = linhaInicial;
        distanciaBaixo = 7 - linhaInicial;
        distanciaEsq = colunaInicial;
        distanciaDir = 7 - colunaInicial;

        if (distanciaBaixo < distanciaDir) {
            if (existeCapturaValidaBaixoDireitaDama(jogo, linhaInicial, colunaInicial, linhaInicial + distanciaBaixo, colunaInicial + distanciaBaixo)) {
                return true;
            }
        } else {
            if (existeCapturaValidaBaixoDireitaDama(jogo, linhaInicial, colunaInicial, linhaInicial + distanciaDir, colunaInicial + distanciaDir)) {
                return true;
            }
        }

        if (distanciaBaixo < distanciaEsq) {
            if (existeCapturaValidaBaixoEsquerdaDama(jogo, linhaInicial, colunaInicial, linhaInicial + distanciaBaixo, colunaInicial - distanciaBaixo)) {
                return true;
            }
        } else {
            if (existeCapturaValidaBaixoEsquerdaDama(jogo, linhaInicial, colunaInicial, linhaInicial + distanciaEsq, colunaInicial - distanciaEsq)) {
                return true;
            }
        }

        if (distanciaCima < distanciaDir) {
            if (existeCapturaValidaCimaDireitaDama(jogo, linhaInicial, colunaInicial, linhaInicial - distanciaCima, colunaInicial + distanciaCima)) {
                return true;
            }
        } else {
            if (existeCapturaValidaCimaDireitaDama(jogo, linhaInicial, colunaInicial, linhaInicial - distanciaDir, colunaInicial + distanciaDir)) {
                return true;
            }
        }

        if (distanciaCima < distanciaEsq) {
            if (existeCapturaValidaCimaEsquerdaDama(jogo, linhaInicial, colunaInicial, linhaInicial - distanciaCima, colunaInicial - distanciaCima)) {
                return true;
            }
        } else {
            if (existeCapturaValidaCimaEsquerdaDama(jogo, linhaInicial, colunaInicial, linhaInicial - distanciaEsq, colunaInicial - distanciaEsq)) {
                return true;
            }
        }

        return false;
    }

    private void efetuarCapturaDama(Jogo jogo, int linhaInicial, int colunaInicial) {
        int k, distanciaCima, distanciaBaixo, distanciaEsq, distanciaDir;

        distanciaCima = linhaInicial;
        distanciaBaixo = 7 - linhaInicial;
        distanciaEsq = colunaInicial;
        distanciaDir = 7 - colunaInicial;

        if (distanciaBaixo < distanciaDir) {
            if (existeCapturaValidaBaixoDireitaDama(jogo, linhaInicial, colunaInicial, linhaInicial + distanciaBaixo, colunaInicial + distanciaBaixo)) {
                for (k = 1; linhaInicial + k != linhaInicial + distanciaBaixo; k++) {
                    if (jogo.getTabuleiro().getGrid()[linhaInicial + k][colunaInicial + k].getOcupada()) {
                        jogo.capturar(linhaInicial, colunaInicial, linhaInicial + k + 1, colunaInicial + k + 1);
                        if (existeCapturaPossivelDama(jogo, linhaInicial + k + 1, colunaInicial + k + 1)) {
                            efetuarCapturaDama(jogo, linhaInicial + k + 1, colunaInicial + k + 1);
                        }
                    }
                }
                return;
            }
        } else {
            if (existeCapturaValidaBaixoDireitaDama(jogo, linhaInicial, colunaInicial, linhaInicial + distanciaDir, colunaInicial + distanciaDir)) {
                for (k = 1; linhaInicial + k != linhaInicial + distanciaDir; k++) {
                    if (jogo.getTabuleiro().getGrid()[linhaInicial + k][colunaInicial + k].getOcupada()) {
                        jogo.capturar(linhaInicial, colunaInicial, linhaInicial + k + 1, colunaInicial + k + 1);
                        if (existeCapturaPossivelDama(jogo, linhaInicial + k + 1, colunaInicial + k + 1)) {
                            efetuarCapturaDama(jogo, linhaInicial + k + 1, colunaInicial + k + 1);
                        }
                    }
                }
                return;
            }
        }

        if (distanciaBaixo < distanciaEsq) {
            if (existeCapturaValidaBaixoEsquerdaDama(jogo, linhaInicial, colunaInicial, linhaInicial + distanciaBaixo, colunaInicial - distanciaBaixo)) {
                for (k = 1; linhaInicial + k != linhaInicial + distanciaBaixo; k++) {
                    if (jogo.getTabuleiro().getGrid()[linhaInicial + k][colunaInicial - k].getOcupada()) {
                        jogo.capturar(linhaInicial, colunaInicial, linhaInicial + k + 1, colunaInicial - k - 1);
                        if (existeCapturaPossivelDama(jogo, linhaInicial + k + 1, colunaInicial - k - 1)) {
                            efetuarCapturaDama(jogo, linhaInicial + k + 1, colunaInicial - k - 1);
                        }
                    }
                }
                return;
            }
        } else {
            if (existeCapturaValidaBaixoEsquerdaDama(jogo, linhaInicial, colunaInicial, linhaInicial + distanciaEsq, colunaInicial - distanciaEsq)) {
                for (k = 1; linhaInicial + k != linhaInicial + distanciaEsq; k++) {
                    if (jogo.getTabuleiro().getGrid()[linhaInicial + k][colunaInicial - k].getOcupada()) {
                        jogo.capturar(linhaInicial, colunaInicial, linhaInicial + k + 1, colunaInicial - k - 1);
                        if (existeCapturaPossivelDama(jogo, linhaInicial + k + 1, colunaInicial - k - 1)) {
                            efetuarCapturaDama(jogo, linhaInicial + k + 1, colunaInicial - k - 1);
                        }
                    }
                }
                return;
            }
        }

        if (distanciaCima < distanciaDir) {
            if (existeCapturaValidaCimaDireitaDama(jogo, linhaInicial, colunaInicial, linhaInicial - distanciaCima, colunaInicial + distanciaCima)) {
                for (k = 1; linhaInicial - k != linhaInicial - distanciaCima; k++) {
                    if (jogo.getTabuleiro().getGrid()[linhaInicial - k][colunaInicial + k].getOcupada()) {
                        jogo.capturar(linhaInicial, colunaInicial, linhaInicial - k - 1, colunaInicial + k + 1);
                        if (existeCapturaPossivelDama(jogo, linhaInicial - k - 1, colunaInicial + k + 1)) {
                            efetuarCapturaDama(jogo, linhaInicial - k - 1, colunaInicial + k + 1);
                        }
                    }
                }
                return;
            }
        } else {
            if (existeCapturaValidaCimaDireitaDama(jogo, linhaInicial, colunaInicial, linhaInicial - distanciaDir, colunaInicial + distanciaDir)) {
                for (k = 1; linhaInicial - k != linhaInicial - distanciaDir; k++) {
                    if (jogo.getTabuleiro().getGrid()[linhaInicial - k][colunaInicial + k].getOcupada()) {
                        jogo.capturar(linhaInicial, colunaInicial, linhaInicial - k - 1, colunaInicial + k + 1);
                        if (existeCapturaPossivelDama(jogo, linhaInicial - k - 1, colunaInicial + k + 1)) {
                            efetuarCapturaDama(jogo, linhaInicial - k - 1, colunaInicial + k + 1);
                        }
                    }
                }
                return;
            }
        }

        if (distanciaCima < distanciaEsq) {
            if (existeCapturaValidaCimaEsquerdaDama(jogo, linhaInicial, colunaInicial, linhaInicial - distanciaCima, colunaInicial - distanciaCima)) {
                for (k = 1; linhaInicial - k != linhaInicial - distanciaCima; k++) {
                    if (jogo.getTabuleiro().getGrid()[linhaInicial - k][colunaInicial - k].getOcupada()) {
                        jogo.capturar(linhaInicial, colunaInicial, linhaInicial - k - 1, colunaInicial - k - 1);
                        if (existeCapturaPossivelDama(jogo, linhaInicial - k - 1, colunaInicial - k - 1)) {
                            efetuarCapturaDama(jogo, linhaInicial - k - 1, colunaInicial - k - 1);
                        }
                    }
                }
                return;
            }
        } else {
            if (existeCapturaValidaCimaEsquerdaDama(jogo, linhaInicial, colunaInicial, linhaInicial - distanciaEsq, colunaInicial - distanciaEsq)) {
                for (k = 1; linhaInicial - k != linhaInicial - distanciaEsq; k++) {
                    if (jogo.getTabuleiro().getGrid()[linhaInicial - k][colunaInicial - k].getOcupada()) {
                        jogo.capturar(linhaInicial, colunaInicial, linhaInicial - k - 1, colunaInicial - k - 1);
                        if (existeCapturaPossivelDama(jogo, linhaInicial - k - 1, colunaInicial - k - 1)) {
                            efetuarCapturaDama(jogo, linhaInicial - k - 1, colunaInicial - k - 1);
                        }
                    }
                }
                return;
            }
        }

    }

    //verifica se existe alguma captura pra baixo e pra direita de uma dama pode ser executada
    private boolean existeCapturaValidaBaixoDireitaDama(Jogo jogo, int linhaInicial, int colunaInicial, int linhaFinal, int colunaFinal) {
        int linha, coluna;

        if (linhaInicial == 7) {
            return false;
        } else {
            linha = linhaInicial + 1;
        }

        if (colunaInicial == 7) {
            return false;
        } else {
            coluna = colunaInicial + 1;
        }

        if ((linhaInicial - linhaFinal) != (colunaInicial - colunaFinal)) {
            return false;
        }

        for (; linha < linhaFinal; linha++, coluna++) {
            if (jogo.getTabuleiro().getGrid()[linha][coluna].getOcupada()) {
                if (!jogo.getTabuleiro().getGrid()[linha][coluna].getPeca().getJogador().getNome().equals(jogo.getNomeJogadorAtual())) {
                    if (jogo.getTabuleiro().getGrid()[linha + 1][coluna + 1].getOcupada()) {
                        return false;
                    }
                    return true;
                } else {
                    return false;
                }
            }

        }

        return false;
    }

    //verifica se existe alguma captura pra baixo e pra esquerda de uma dama pode ser executada
    private boolean existeCapturaValidaBaixoEsquerdaDama(Jogo jogo, int linhaInicial, int colunaInicial, int linhaFinal, int colunaFinal) {
        int linha, coluna;

        if (linhaInicial == 7) {
            return false;
        } else {
            linha = linhaInicial + 1;
        }

        if (colunaInicial == 0) {
            return false;
        } else {
            coluna = colunaInicial - 1;
        }

        if ((linhaInicial - linhaFinal) != ((colunaInicial - colunaFinal) * -1)) {
            return false;
        }

        for (; linha < linhaFinal; linha++, coluna--) {
            if (jogo.getTabuleiro().getGrid()[linha][coluna].getOcupada()) {
                if (!jogo.getTabuleiro().getGrid()[linha][coluna].getPeca().getJogador().getNome().equals(jogo.getNomeJogadorAtual())) {
                    if (jogo.getTabuleiro().getGrid()[linha + 1][coluna - 1].getOcupada()) {
                        return false;
                    }
                    return true;
                } else {
                    return false;
                }
            }

        }

        return false;
    }

    //verifica se existe alguma captura pra cima e pra direita de uma dama pode ser executada
    private boolean existeCapturaValidaCimaDireitaDama(Jogo jogo, int linhaInicial, int colunaInicial, int linhaFinal, int colunaFinal) {
        int linha, coluna;

        if (linhaInicial == 0) {
            return false;
        } else {
            linha = linhaInicial - 1;
        }

        if (colunaInicial == 7) {
            return false;
        } else {
            coluna = colunaInicial + 1;
        }

        if ((linhaInicial - linhaFinal) != ((colunaInicial - colunaFinal) * -1)) {
            return false;
        }

        for (; linha > linhaFinal; linha--, coluna++) {
            if (jogo.getTabuleiro().getGrid()[linha][coluna].getOcupada()) {
                if (!jogo.getTabuleiro().getGrid()[linha][coluna].getPeca().getJogador().getNome().equals(jogo.getNomeJogadorAtual())) {
                    if (jogo.getTabuleiro().getGrid()[linha - 1][coluna + 1].getOcupada()) {
                        return false;
                    }
                    return true;
                } else {
                    return false;
                }
            }

        }

        return false;
    }

    //verifica se existe alguma captura pra cima e pra esquerda de uma dama pode ser executada
    private boolean existeCapturaValidaCimaEsquerdaDama(Jogo jogo, int linhaInicial, int colunaInicial, int linhaFinal, int colunaFinal) {
        int linha, coluna;

        if (linhaInicial == 0) {
            return false;
        } else {
            linha = linhaInicial - 1;
        }

        if (colunaInicial == 0) {
            return false;
        } else {
            coluna = colunaInicial - 1;
        }

        if ((linhaInicial - linhaFinal) != (colunaInicial - colunaFinal)) {
            return false;
        }

        for (; linha > linhaFinal; linha--, coluna--) {
            if (jogo.getTabuleiro().getGrid()[linha][coluna].getOcupada()) {
                if (!jogo.getTabuleiro().getGrid()[linha][coluna].getPeca().getJogador().getNome().equals(jogo.getNomeJogadorAtual())) {
                    if (jogo.getTabuleiro().getGrid()[linha - 1][coluna - 1].getOcupada()) {
                        return false;
                    }
                    return true;
                } else {
                    return false;
                }
            }

        }

        return false;
    }
    //////////////////
    //////////////////
    //////////////////

    //////////////////
    //metodos de captura da peca
    //////////////////
    private boolean existeCapturaPossivelCampo(Jogo jogo) {
        int i, j;

        for (i = 0; i < jogo.getTabuleiro().getGrid().length; i++) {
            for (j = 0; j < jogo.getTabuleiro().getGrid()[0].length; j++) {
                if (jogo.getTabuleiro().getGrid()[i][j].getOcupada()) {
                    if (jogo.getTabuleiro().getGrid()[i][j].getPeca().getJogador().getNome().equals(jogo.getNomeJogadorAtual())) {
                        if (existeCapturaPossivel(jogo, i, j)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean existeCapturaPossivel(Jogo jogo, int linhaInicial, int colunaInicial) {
        if (!(linhaInicial < 2 || colunaInicial > 5)) {
            if (this.isCapturaValidaCimaDireita(jogo, linhaInicial, colunaInicial)) {
                return true;
            }
        }
        if (!(linhaInicial < 2 || colunaInicial < 2)) {
            if (this.isCapturaValidaCimaEsquerda(jogo, linhaInicial, colunaInicial)) {
                return true;
            }
        }
        if (!(linhaInicial > 5 || colunaInicial > 5)) {
            if (this.isCapturaValidaBaixoDireita(jogo, linhaInicial, colunaInicial)) {
                return true;
            }
        }
        if (!(linhaInicial > 5 || colunaInicial < 2)) {
            if (this.isCapturaValidaBaixoEsquerda(jogo, linhaInicial, colunaInicial)) {
                return true;
            }
        }
        return false;
    }

    private void efetuarCaptura(Jogo jogo, int linhaInicial, int colunaInicial) {
        int j;
        if (jogo.getTabuleiro().getGrid()[linhaInicial][colunaInicial].getPeca().getJogador().equals(jogo.getJogador2())) {//trata se a peca for escura
            if (isCapturaValidaBaixoDireita(jogo, linhaInicial, colunaInicial)) {//captura pra direita e pra baixo
                jogo.capturar(linhaInicial, colunaInicial, linhaInicial + 2, colunaInicial + 2);
                if (existeCapturaPossivel(jogo, linhaInicial + 2, colunaInicial + 2)) {
                    efetuarCaptura(jogo, linhaInicial + 2, colunaInicial + 2);
                }
            } else if (isCapturaValidaBaixoEsquerda(jogo, linhaInicial, colunaInicial)) {//captura pra esquerda e pra baixo
                jogo.capturar(linhaInicial, colunaInicial, linhaInicial + 2, colunaInicial - 2);
                if (existeCapturaPossivel(jogo, linhaInicial + 2, colunaInicial - 2)) {
                    efetuarCaptura(jogo, linhaInicial + 2, colunaInicial - 2);
                }
            } else if (isCapturaValidaCimaDireita(jogo, linhaInicial, colunaInicial)) {//captura pra direita e pra cima
                jogo.capturar(linhaInicial, colunaInicial, linhaInicial - 2, colunaInicial + 2);
                if (existeCapturaPossivel(jogo, linhaInicial - 2, colunaInicial + 2)) {
                    efetuarCaptura(jogo, linhaInicial - 2, colunaInicial + 2);
                }
            } else if (isCapturaValidaCimaEsquerda(jogo, linhaInicial, colunaInicial)) {//captura pra esquerda e pra cima
                jogo.capturar(linhaInicial, colunaInicial, linhaInicial - 2, colunaInicial - 2);
                if (existeCapturaPossivel(jogo, linhaInicial - 2, colunaInicial - 2)) {
                    efetuarCaptura(jogo, linhaInicial - 2, colunaInicial - 2);
                }
            }
        }

    }

    //verifica se a captura pra baixo e pra direita eh possivel
    private boolean isCapturaValidaBaixoDireita(Jogo jogo, int linhaInicial, int colunaInicial) {
        try {
            if (jogo.getTabuleiro().getGrid()[linhaInicial + 1][colunaInicial + 1].getOcupada().equals(true)) {
                if (jogo.getTabuleiro().getGrid()[linhaInicial + 2][colunaInicial + 2].getOcupada().equals(false)) {
                    if (jogo.getTabuleiro().getGrid()[linhaInicial][colunaInicial].getPeca().getJogador().equals(jogo.getJogador1())) {
                        if (jogo.getTabuleiro().getGrid()[linhaInicial + 1][colunaInicial + 1].getPeca().getJogador().equals(jogo.getJogador2())) {
                            return true;
                        }
                    } else {
                        if (jogo.getTabuleiro().getGrid()[linhaInicial + 1][colunaInicial + 1].getPeca().getJogador().equals(jogo.getJogador1())) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception ex) {
        }

        return false;
    }

    //verifica se a captura pra baixo e pra esquerda eh possivel
    private boolean isCapturaValidaBaixoEsquerda(Jogo jogo, int linhaInicial, int colunaInicial) {
        try {
            if (jogo.getTabuleiro().getGrid()[linhaInicial + 1][colunaInicial - 1].getOcupada().equals(true)) {
                if (jogo.getTabuleiro().getGrid()[linhaInicial + 2][colunaInicial - 2].getOcupada().equals(false)) {
                    if (jogo.getTabuleiro().getGrid()[linhaInicial][colunaInicial].getPeca().getJogador().equals(jogo.getJogador1())) {
                        if (jogo.getTabuleiro().getGrid()[linhaInicial + 1][colunaInicial - 1].getPeca().getJogador().equals(jogo.getJogador2())) {
                            return true;
                        }
                    } else {
                        if (jogo.getTabuleiro().getGrid()[linhaInicial + 1][colunaInicial - 1].getPeca().getJogador().equals(jogo.getJogador1())) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception ex) {
        }

        return false;
    }

    //verifica se a captura pra cima e pra direita eh possivel
    private boolean isCapturaValidaCimaDireita(Jogo jogo, int linhaInicial, int colunaInicial) {
        try {
            if (jogo.getTabuleiro().getGrid()[linhaInicial - 1][colunaInicial + 1].getOcupada().equals(true)) {
                if (jogo.getTabuleiro().getGrid()[linhaInicial - 2][colunaInicial + 2].getOcupada().equals(false)) {
                    if (jogo.getTabuleiro().getGrid()[linhaInicial][colunaInicial].getPeca().getJogador().equals(jogo.getJogador1())) {
                        if (jogo.getTabuleiro().getGrid()[linhaInicial - 1][colunaInicial + 1].getPeca().getJogador().equals(jogo.getJogador2())) {
                            return true;
                        }
                    } else {
                        if (jogo.getTabuleiro().getGrid()[linhaInicial - 1][colunaInicial + 1].getPeca().getJogador().equals(jogo.getJogador1())) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception ex) {
        }

        return false;
    }

    //verifica se a captura pra cima e pra esquerda eh possivel
    private boolean isCapturaValidaCimaEsquerda(Jogo jogo, int linhaInicial, int colunaInicial) {
        try {
            if (jogo.getTabuleiro().getGrid()[linhaInicial - 1][colunaInicial - 1].getOcupada().equals(true)) {
                if (jogo.getTabuleiro().getGrid()[linhaInicial - 2][colunaInicial - 2].getOcupada().equals(false)) {
                    if (jogo.getTabuleiro().getGrid()[linhaInicial][colunaInicial].getPeca().getJogador().equals(jogo.getJogador1())) {
                        if (jogo.getTabuleiro().getGrid()[linhaInicial - 1][colunaInicial - 1].getPeca().getJogador().equals(jogo.getJogador2())) {
                            return true;
                        }
                    } else {
                        if (jogo.getTabuleiro().getGrid()[linhaInicial - 1][colunaInicial - 1].getPeca().getJogador().equals(jogo.getJogador1())) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception ex) {
        }

        return false;
    }
    //////////////////
    //////////////////
    //////////////////

    //////////////////
    //meotodos de movimentacao da peca
    //////////////////
    private void efetuarMovimento(Jogo jogo, int linhaInicial, int colunaInicial) {
        if (jogo.getTabuleiro().getGrid()[linhaInicial][colunaInicial].getPeca().getJogador().equals(jogo.getJogador2())) {//trata se a peca for escura
            if (colunaInicial != 7) {
                if (!jogo.getTabuleiro().getGrid()[linhaInicial + 1][colunaInicial + 1].getOcupada()) {//move pra direita
                    jogo.getTabuleiro().executarMovimento(linhaInicial, colunaInicial, linhaInicial + 1, colunaInicial + 1);
                    if (linhaInicial == 6) {
                        jogo.getTabuleiro().getGrid()[linhaInicial + 1][colunaInicial + 1].setPeca(new Dama(jogo.getTabuleiro().getGrid()[linhaInicial + 1][colunaInicial + 1].getPeca().getCorPeca(), jogo.getTabuleiro().getGrid()[linhaInicial + 1][colunaInicial + 1].getPeca().getJogador()));
                    }
                    return;
                }
            }
            if (colunaInicial != 0) {
                if (!jogo.getTabuleiro().getGrid()[linhaInicial + 1][colunaInicial - 1].getOcupada()) {//move para a esquerda
                    jogo.getTabuleiro().executarMovimento(linhaInicial, colunaInicial, linhaInicial + 1, colunaInicial - 1);
                    if (linhaInicial == 6) {
                        jogo.getTabuleiro().getGrid()[linhaInicial + 1][colunaInicial - 1].setPeca(new Dama(jogo.getTabuleiro().getGrid()[linhaInicial + 1][colunaInicial - 1].getPeca().getCorPeca(), jogo.getTabuleiro().getGrid()[linhaInicial + 1][colunaInicial - 1].getPeca().getJogador()));
                    }
                    return;
                }
            }
        } else {//trata se a peca for clara
            if (colunaInicial != 7) {
                if (!jogo.getTabuleiro().getGrid()[linhaInicial - 1][colunaInicial + 1].getOcupada()) {//move para a direita
                    jogo.getTabuleiro().executarMovimento(linhaInicial, colunaInicial, linhaInicial - 1, colunaInicial + 1);
                    if (linhaInicial == 1) {
                        jogo.getTabuleiro().getGrid()[linhaInicial - 1][colunaInicial + 1].setPeca(new Dama(jogo.getTabuleiro().getGrid()[linhaInicial - 1][colunaInicial + 1].getPeca().getCorPeca(), jogo.getTabuleiro().getGrid()[linhaInicial - 1][colunaInicial + 1].getPeca().getJogador()));
                    }
                    return;
                }
            }
            if (colunaInicial != 0) {
                if (!jogo.getTabuleiro().getGrid()[linhaInicial - 1][colunaInicial - 1].getOcupada()) {//move para a esquerda
                    jogo.getTabuleiro().executarMovimento(linhaInicial, colunaInicial, linhaInicial - 1, colunaInicial - 1);
                    if (linhaInicial == 1) {
                        jogo.getTabuleiro().getGrid()[linhaInicial - 1][colunaInicial - 1].setPeca(new Dama(jogo.getTabuleiro().getGrid()[linhaInicial - 1][colunaInicial - 1].getPeca().getCorPeca(), jogo.getTabuleiro().getGrid()[linhaInicial - 1][colunaInicial - 1].getPeca().getJogador()));
                    }
                    return;
                }
            }
        }
    }

    //verifica se o movimento especifico eh possivel. Eh passado a casa inicial e a final da peca como parametro
    private boolean existeMovimentoPossivel(Jogo jogo, int linhaInicial, int colunaInicial) {
        try {
            if (jogo.getTabuleiro().getGrid()[linhaInicial][colunaInicial].getPeca().getJogador().equals(jogo.getJogador2())) {//trata se a peca for escura
                if (!jogo.getTabuleiro().getGrid()[linhaInicial + 1][colunaInicial + 1].getOcupada()) {
                    return true;
                }
            }
        } catch (Exception ex) {
        }

        try {
            if (jogo.getTabuleiro().getGrid()[linhaInicial][colunaInicial].getPeca().getJogador().equals(jogo.getJogador2())) {//trata se a peca for escura
                if (!jogo.getTabuleiro().getGrid()[linhaInicial + 1][colunaInicial - 1].getOcupada()) {
                    return true;
                }
            }
        } catch (Exception ex) {
        }

        try {
            if (jogo.getTabuleiro().getGrid()[linhaInicial][colunaInicial].getPeca().getJogador().equals(jogo.getJogador1())) {//trata se a peca for escura
                if (!jogo.getTabuleiro().getGrid()[linhaInicial - 1][colunaInicial + 1].getOcupada()) {
                    return true;
                }
            }
        } catch (Exception ex) {
        }

        try {
            if (jogo.getTabuleiro().getGrid()[linhaInicial][colunaInicial].getPeca().getJogador().equals(jogo.getJogador1())) {//trata se a peca for escura
                if (!jogo.getTabuleiro().getGrid()[linhaInicial - 1][colunaInicial - 1].getOcupada()) {
                    return true;
                }
            }
        } catch (Exception ex) {
        }

        return false;
    }
    //////////////////
    //////////////////
    //////////////////

    //////////////////
    //meotodos de movimentacao da dama
    //////////////////
    private void efetuarMovimentoDama(Jogo jogo, int linhaInicial, int colunaInicial) {
        if (linhaInicial != 7) {
            if (colunaInicial != 7) {
                if (!jogo.getTabuleiro().getGrid()[linhaInicial + 1][colunaInicial + 1].getOcupada()) {//move pra direita
                    jogo.getTabuleiro().executarMovimento(linhaInicial, colunaInicial, linhaInicial + 1, colunaInicial + 1);
                    return;
                }
            }
            if (colunaInicial != 0) {
                if (!jogo.getTabuleiro().getGrid()[linhaInicial + 1][colunaInicial - 1].getOcupada()) {//move para a esquerda
                    jogo.getTabuleiro().executarMovimento(linhaInicial, colunaInicial, linhaInicial + 1, colunaInicial - 1);
                    return;
                }
            }
        }
        if (linhaInicial != 0) {
            if (colunaInicial != 7) {
                if (!jogo.getTabuleiro().getGrid()[linhaInicial - 1][colunaInicial + 1].getOcupada()) {//move para a direita
                    jogo.getTabuleiro().executarMovimento(linhaInicial, colunaInicial, linhaInicial - 1, colunaInicial + 1);
                    return;
                }
            }
            if (colunaInicial != 0) {
                if (!jogo.getTabuleiro().getGrid()[linhaInicial - 1][colunaInicial - 1].getOcupada()) {//move para a esquerda
                    jogo.getTabuleiro().executarMovimento(linhaInicial, colunaInicial, linhaInicial - 1, colunaInicial - 1);
                    return;
                }
            }
        }

    }

    private boolean existeMovimentoPossivelDama(Jogo jogo, int linhaInicial, int colunaInicial) {
        try {
            if (!jogo.getTabuleiro().getGrid()[linhaInicial + 1][colunaInicial + 1].getOcupada()) {
                return true;
            }
        } catch (Exception ex) {
        }

        try {
            if (!jogo.getTabuleiro().getGrid()[linhaInicial + 1][colunaInicial - 1].getOcupada()) {
                return true;
            }
        } catch (Exception ex) {
        }

        try {
            if (!jogo.getTabuleiro().getGrid()[linhaInicial - 1][colunaInicial + 1].getOcupada()) {
                return true;
            }
        } catch (Exception ex) {
        }

        try {
            if (!jogo.getTabuleiro().getGrid()[linhaInicial - 1][colunaInicial - 1].getOcupada()) {
                return true;
            }
        } catch (Exception ex) {
        }

        return false;
    }
    //////////////////
    //////////////////
    //////////////////

    //conta o numero de pecas de cada jogador
    private void contadorPecas(Jogo jogo) {
        qtdPecasJogador1 = 0;
        qtdPecasJogador2 = 0;

        int i, j;
        for (i = 0; i < jogo.getTabuleiro().getGrid().length; i++) {
            for (j = 0; j < jogo.getTabuleiro().getGrid()[0].length; j++) {
                if (jogo.getTabuleiro().getGrid()[i][j].getOcupada()) {
                    if (jogo.getTabuleiro().getGrid()[i][j].getPeca().getJogador().equals(jogo.getJogador1())) {
                        qtdPecasJogador1++;
                    }
                } else {
                    if (jogo.getTabuleiro().getGrid()[i][j].getPeca().getJogador().equals(jogo.getJogador2())) {
                        qtdPecasJogador2++;
                    }
                }
            }
        }
    }

}
