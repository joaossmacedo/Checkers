/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.poli.damas;

import br.com.poli.enums.CoresPeca;
import br.com.poli.enums.Resultado;
import br.com.poli.exceptions.FimJogoException;
import br.com.poli.exceptions.MovimentoInvalidoException;
import java.util.Date;

/**
 *
 * @author joaomacedo
 */
public class Jogo {

    private Jogador jogador1, jogador2, jogadorAtual, vencedor;
    private Tabuleiro tabuleiro;
    private Resultado resultado;
    private int contadorJogadas, jogadasOcorridas;
    private int linhaCapturaMultipla, colunaCapturaMultipla;
    private int qtdPecasClaras, qtdDamasClaras, qtdPecasEscuras, qtdDamasEscuras;
    private Date tempoInicial, tempo;

    public Jogo(Jogador jogador1, Jogador jogador2) {
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
    }
    
    //inicializa uma nova partida
    public void iniciarPartida() {
        this.tempoInicial = new Date();
        this.tempo = new Date();
        this.jogadasOcorridas = 0;
        this.tabuleiro = new Tabuleiro();
        this.tabuleiro.gerarTabuleiro(this.jogador1, this.jogador2);

        jogadorAtual = jogador1;
        contadorPecas();

        this.linhaCapturaMultipla = 8;
        this.colunaCapturaMultipla = 8;
    }

    public Jogador getVencedor() {
        return vencedor;
    }
    
    public Tabuleiro getTabuleiro() {
        return this.tabuleiro;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public Date getTempoInicial() {
        return this.tempoInicial;
    }

    public Date getTempo() {
        return this.tempo;
    }

    public String getNomeJogadorAtual() {
        return this.jogadorAtual.getNome();
    }

    public void setJogadorAtual(Jogador jogadorAtual) {
        this.jogadorAtual = jogadorAtual;
    }

    public Jogador getJogador1() {
        return jogador1;
    }

    public Jogador getJogador2() {
        return jogador2;
    }

    public int getJogadasOcorridas() {
        return this.jogadasOcorridas;
    }

    public void setJogadasOcorridas(int jogadasOcorridas) {
        this.jogadasOcorridas = jogadasOcorridas;
    }

    public int getContadorJogadasSemCaptura() {
        return contadorJogadas;
    }

    public void setContadorJogadasSemCaptura(int contadorJogadas) {
        this.contadorJogadas = contadorJogadas;
    }

    public int getQtdClaras() {
        return qtdPecasClaras + qtdDamasClaras;
    }

    public int getQtdEscuras() {
        return qtdPecasEscuras + qtdDamasEscuras;
    }

    //muda o jogadorAtual
    public void mudarVez() {
        if (jogador1.equals(jogadorAtual)) {
            jogadorAtual = jogador2;
        } else {
            jogadorAtual = jogador1;
        }
    }

    //executa movimentos e capturas
    public void jogar(int linhaInicial, int colunaInicial, int linhaFinal, int colunaFinal) throws MovimentoInvalidoException, FimJogoException {
        this.tempo = new Date();

        if (!this.getTabuleiro().getGrid()[linhaInicial][colunaInicial].getOcupada()) {
            throw new MovimentoInvalidoException();
        }
        if (this.getTabuleiro().getGrid()[linhaFinal][colunaFinal].getOcupada()) {
            throw new MovimentoInvalidoException();
        }

        if (this.tabuleiro.getGrid()[linhaInicial][colunaInicial].getPeca() instanceof Dama) {
            if (this.existeCapturaPossivelCampoDama()) {
                if (this.isCapturaValidaDama(linhaInicial, colunaInicial, linhaFinal, colunaFinal)) {
                    this.contadorJogadas = 0;
                    this.capturar(linhaInicial, colunaInicial, linhaFinal, colunaFinal);
                    if (this.existeCapturaPossivelDama(linhaInicial, colunaInicial)) {
                        this.linhaCapturaMultipla = linhaFinal;
                        this.colunaCapturaMultipla = colunaFinal;
                    } else {
                        this.jogadasOcorridas++;
                        this.linhaCapturaMultipla = 8;
                        this.colunaCapturaMultipla = 8;
                    }
                } else {
                    throw new MovimentoInvalidoException();
                }
            } else {
                if (this.isMovimentoValidoDama(linhaInicial, colunaInicial, linhaFinal, colunaFinal)) {
                    this.contadorJogadas++;
                    this.jogadasOcorridas++;
                    this.tabuleiro.executarMovimento(linhaInicial, colunaInicial, linhaFinal, colunaFinal);
                } else {
                    throw new MovimentoInvalidoException();
                }
            }
        } else {
            if ((this.linhaCapturaMultipla == 8 && this.colunaCapturaMultipla == 8)
                    || (this.linhaCapturaMultipla == linhaInicial && this.colunaCapturaMultipla == colunaInicial)) {
                if (this.existeCapturaPossivelCampo()) {
                    if (isCapturaValida(linhaInicial, colunaInicial, linhaFinal, colunaFinal)) {
                        this.contadorJogadas = 0;
                        this.capturar(linhaInicial, colunaInicial, linhaFinal, colunaFinal);
                        if (this.existeCapturaPossivel(linhaFinal, colunaFinal)) {
                            this.linhaCapturaMultipla = linhaFinal;
                            this.colunaCapturaMultipla = colunaFinal;
                        } else {
                            this.jogadasOcorridas++;
                            this.linhaCapturaMultipla = 8;
                            this.colunaCapturaMultipla = 8;
                            this.virarDama(linhaFinal, colunaFinal);
                        }
                    } else {
                        throw new MovimentoInvalidoException();
                    }
                } else {
                    if (this.isMovimentoValido(linhaInicial, colunaInicial, linhaFinal, colunaFinal)) {
                        contadorJogadas++;
                        this.jogadasOcorridas++;
                        this.tabuleiro.executarMovimento(linhaInicial, colunaInicial, linhaFinal, colunaFinal);
                        this.virarDama(linhaFinal, colunaFinal);
                    } else {
                        throw new MovimentoInvalidoException();
                    }
                }
            }
        }

        if (this.isFimDeJogo()) {
            throw new FimJogoException();
        }

        if (this.jogadasOcorridas % 2 == 0) {
            this.jogadorAtual = this.jogador1;
        } else {
            this.jogadorAtual = this.jogador2;
        }
    }

    //captura a peca contraria
    public void capturar(int linhaInicial, int colunaInicial, int linhaFinal, int colunaFinal) {
        if (!(this.tabuleiro.getGrid()[linhaInicial][colunaInicial].getPeca() instanceof Dama)) { //caso seja peca
            this.tabuleiro.getGrid()[linhaFinal][colunaFinal].setPeca(this.tabuleiro.getGrid()[linhaInicial][colunaInicial].getPeca());
            this.tabuleiro.getGrid()[(linhaInicial + linhaFinal) / 2][(colunaInicial + colunaFinal) / 2].setPeca(null);
            this.tabuleiro.getGrid()[linhaInicial][colunaInicial].setPeca(null);

            this.tabuleiro.getGrid()[linhaFinal][colunaFinal].setOcupada(true);
            this.tabuleiro.getGrid()[linhaInicial][colunaInicial].setOcupada(false);
            this.tabuleiro.getGrid()[(linhaInicial + linhaFinal) / 2][(colunaInicial + colunaFinal) / 2].setOcupada(false);
        } else { //caso seja dama
            int i, j;

            this.tabuleiro.getGrid()[linhaFinal][colunaFinal].setPeca(this.tabuleiro.getGrid()[linhaInicial][colunaInicial].getPeca());
            this.tabuleiro.getGrid()[linhaInicial][colunaInicial].setPeca(null);

            this.tabuleiro.getGrid()[linhaInicial][colunaInicial].setOcupada(false);
            this.tabuleiro.getGrid()[linhaFinal][colunaFinal].setOcupada(true);

            if (linhaInicial < linhaFinal && colunaInicial < colunaFinal) {
                for (i = 0; linhaInicial + i != linhaFinal; i++) {
                    if (this.tabuleiro.getGrid()[linhaInicial + i][colunaInicial + i].getOcupada()) {
                        this.tabuleiro.getGrid()[linhaInicial + i][colunaInicial + i].setPeca(null);
                        this.tabuleiro.getGrid()[linhaInicial + i][colunaInicial + i].setOcupada(false);
                    }
                }
            } else if (linhaInicial < linhaFinal && colunaInicial > colunaFinal) {
                for (i = 0; linhaInicial + i != linhaFinal; i++) {
                    if (this.tabuleiro.getGrid()[linhaInicial + i][colunaInicial - i].getOcupada()) {
                        this.tabuleiro.getGrid()[linhaInicial + i][colunaInicial - i].setPeca(null);
                        this.tabuleiro.getGrid()[linhaInicial + i][colunaInicial - i].setOcupada(false);
                    }
                }
            } else if (linhaInicial > linhaFinal && colunaInicial < colunaFinal) {
                for (i = 0; linhaInicial - i != linhaFinal; i++) {
                    if (this.tabuleiro.getGrid()[linhaInicial - i][colunaInicial + i].getOcupada()) {
                        this.tabuleiro.getGrid()[linhaInicial - i][colunaInicial + i].setPeca(null);
                        this.tabuleiro.getGrid()[linhaInicial - i][colunaInicial + i].setOcupada(false);
                    }
                }
            } else if (linhaInicial > linhaFinal && colunaInicial > colunaFinal) {
                for (i = 0; linhaInicial - i != linhaFinal; i++) {
                    if (this.tabuleiro.getGrid()[linhaInicial - i][colunaInicial - i].getOcupada()) {
                        this.tabuleiro.getGrid()[linhaInicial - i][colunaInicial - i].setPeca(null);
                        this.tabuleiro.getGrid()[linhaInicial - i][colunaInicial - i].setOcupada(false);
                    }
                }
            }

        }
    }

    //////////////////
    //metodos de verificacao de movimento
    //////////////////
    //verifica se o movimento especifico eh possivel. Eh passado a casa inicial e a final da peca como parametro
    private boolean isMovimentoValido(int linhaInicial, int colunaInicial, int linhaFinal, int colunaFinal) {
        if (this.tabuleiro.getGrid()[linhaFinal][colunaFinal].getOcupada()) {
            return false;
        }

        if (jogadorAtual.equals(jogador1)) {
            if (!this.tabuleiro.getGrid()[linhaInicial][colunaInicial].getPeca().getCorPeca().equals(CoresPeca.CLARA)) {
                return false;
            }
        } else {
            if (!this.tabuleiro.getGrid()[linhaInicial][colunaInicial].getPeca().getCorPeca().equals(CoresPeca.ESCURA)) {
                return false;
            }
        }

        if (this.tabuleiro.getGrid()[linhaInicial][colunaInicial].getPeca().getCorPeca().equals(CoresPeca.CLARA)) {
            if (linhaInicial - 1 != linhaFinal) {
                return false;
            }
        } else {
            if (linhaInicial + 1 != linhaFinal) {
                return false;
            }
        }
        if (colunaInicial + 1 != colunaFinal && colunaInicial - 1 != colunaFinal) {
            return false;
        }
        return true;
    }

    //verifica se o movimento da dama é valido
    private boolean isMovimentoValidoDama(int linhaInicial, int colunaInicial, int linhaFinal, int colunaFinal) {
        int i;

        if (linhaInicial == linhaFinal || colunaInicial == colunaFinal) {
            return false;
        }

        if (this.tabuleiro.getGrid()[linhaInicial][colunaInicial].getPeca().getJogador() != this.jogadorAtual) {
            return false;
        }

        if (linhaInicial < linhaFinal && colunaInicial < colunaFinal) {
            if ((linhaFinal - linhaInicial) != (colunaFinal - colunaInicial)) {
                return false;
            }
            for (; linhaInicial < linhaFinal; linhaInicial++, colunaInicial++) {
                if (this.tabuleiro.getGrid()[linhaInicial + 1][colunaInicial + 1].getOcupada()) {
                    return false;
                }
            }
        }

        if (linhaInicial < linhaFinal && colunaInicial > colunaFinal) {
            if ((linhaFinal - linhaInicial) != (colunaInicial - colunaFinal)) {
                return false;
            }
            for (; linhaInicial < linhaFinal; linhaInicial++, colunaInicial--) {
                if (this.tabuleiro.getGrid()[linhaInicial + 1][colunaInicial - 1].getOcupada()) {
                    return false;
                }
            }
        }
        if (linhaInicial > linhaFinal && colunaInicial < colunaFinal) {
            if ((linhaInicial - linhaFinal) != (colunaFinal - colunaInicial)) {
                return false;
            }
            for (; linhaInicial > linhaFinal; linhaInicial--, colunaInicial++) {
                if (this.tabuleiro.getGrid()[linhaInicial - 1][colunaInicial + 1].getOcupada()) {
                    return false;
                }
            }

        }
        if (linhaInicial > linhaFinal && colunaInicial > colunaFinal) {
            if ((linhaInicial - linhaFinal) != (colunaInicial - colunaFinal)) {
                return false;
            }
            for (; linhaInicial > linhaFinal; linhaInicial--, colunaInicial--) {
                if (this.tabuleiro.getGrid()[linhaInicial - 1][colunaInicial - 1].getOcupada()) {
                    return false;
                }
            }

        }
        return true;
    }
    //////////////////
    //////////////////
    //////////////////

    //////////////////
    //metodos de captura da peca
    //////////////////
    //verifica se existe alguma capatura possivel para o jogador atual
    private boolean existeCapturaPossivelCampo() {
        int i, j;

        for (i = 0; i < this.getTabuleiro().getGrid().length; i++) {
            for (j = 0; j < this.getTabuleiro().getGrid()[0].length; j++) {
                if (this.tabuleiro.getGrid()[i][j].getOcupada()) {

                    if (this.tabuleiro.getGrid()[i][j].getPeca().getJogador().equals(this.jogadorAtual) && this.existeCapturaPossivel(i, j)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    //verifica se existe alguma captura possivel para a peca escolhida
    private boolean existeCapturaPossivel(int linhaInicial, int colunaInicial) {
        if (!(linhaInicial < 2 || colunaInicial > 5)) {
            if (this.isCapturaValidaCimaDireita(linhaInicial, colunaInicial)) {
                return true;
            }
        }
        if (!(linhaInicial < 2 || colunaInicial < 2)) {
            if (this.isCapturaValidaCimaEsquerda(linhaInicial, colunaInicial)) {
                return true;
            }
        }
        if (!(linhaInicial > 5 || colunaInicial > 5)) {
            if (this.isCapturaValidaBaixoDireita(linhaInicial, colunaInicial)) {
                return true;
            }
        }
        if (!(linhaInicial > 5 || colunaInicial < 2)) {
            if (this.isCapturaValidaBaixoEsquerda(linhaInicial, colunaInicial)) {
                return true;
            }
        }
        return false;
    }

    //verifica se a captura especifica e possivel. Eh passado a casa inicial e a final da peca como parametro
    private boolean isCapturaValida(int linhaInicial, int colunaInicial, int linhaFinal, int colunaFinal) {
        if (linhaInicial + 2 != linhaFinal && linhaInicial - 2 != linhaFinal) {
            return false;
        }
        if (colunaInicial + 2 != colunaFinal && colunaInicial - 2 != colunaFinal) {
            return false;
        }

        if (linhaInicial - 2 == linhaFinal && colunaInicial + 2 == colunaFinal) {
            if (this.isCapturaValidaCimaDireita(linhaInicial, colunaInicial)) {
                return true;
            }
        }

        if (linhaInicial - 2 == linhaFinal && colunaInicial - 2 == colunaFinal) {
            if (this.isCapturaValidaCimaEsquerda(linhaInicial, colunaInicial)) {
                return true;
            }
        }

        if (linhaInicial + 2 == linhaFinal && colunaInicial + 2 == colunaFinal) {
            if (this.isCapturaValidaBaixoDireita(linhaInicial, colunaInicial)) {
                return true;
            }
        }

        if (linhaInicial + 2 == linhaFinal && colunaInicial - 2 == colunaFinal) {
            if (this.isCapturaValidaBaixoEsquerda(linhaInicial, colunaInicial)) {
                return true;
            }
        }

        return false;
    }

    //verifica se a captura pra cima e pra direita eh possivel
    private boolean isCapturaValidaCimaDireita(int linhaInicial, int colunaInicial) {
        if (this.tabuleiro.getGrid()[linhaInicial - 1][colunaInicial + 1].getOcupada().equals(true)) {
            if (this.tabuleiro.getGrid()[linhaInicial - 2][colunaInicial + 2].getOcupada().equals(false)) {
                if (this.tabuleiro.getGrid()[linhaInicial][colunaInicial].getPeca().getCorPeca().equals(CoresPeca.CLARA)) {
                    if (this.tabuleiro.getGrid()[linhaInicial - 1][colunaInicial + 1].getPeca().getCorPeca().equals(CoresPeca.ESCURA)) {
                        return true;

                    }
                } else if (this.tabuleiro.getGrid()[linhaInicial][colunaInicial].getPeca().getCorPeca().equals(CoresPeca.ESCURA)) {
                    if (this.tabuleiro.getGrid()[linhaInicial - 1][colunaInicial + 1].getPeca().getCorPeca().equals(CoresPeca.CLARA)) {
                        return true;

                    }
                }
            }
        }
        return false;
    }

    //verifica se a captura pra cima e pra esquerda eh possivel
    private boolean isCapturaValidaCimaEsquerda(int linhaInicial, int colunaInicial) {
        if (this.tabuleiro.getGrid()[linhaInicial - 1][colunaInicial - 1].getOcupada().equals(true)) {
            if (this.tabuleiro.getGrid()[linhaInicial - 2][colunaInicial - 2].getOcupada().equals(false)) {
                if (this.tabuleiro.getGrid()[linhaInicial][colunaInicial].getPeca().getCorPeca().equals(CoresPeca.CLARA)) {
                    if (this.tabuleiro.getGrid()[linhaInicial - 1][colunaInicial - 1].getPeca().getCorPeca().equals(CoresPeca.ESCURA)) {
                        return true;
                    }
                } else if (this.tabuleiro.getGrid()[linhaInicial][colunaInicial].getPeca().getCorPeca().equals(CoresPeca.ESCURA)) {
                    if (this.tabuleiro.getGrid()[linhaInicial - 1][colunaInicial - 1].getPeca().getCorPeca().equals(CoresPeca.CLARA)) {
                        return true;

                    }
                }
            }
        }
        return false;
    }

    //verifica se a captura pra baixo e pra direita eh possivel
    private boolean isCapturaValidaBaixoDireita(int linhaInicial, int colunaInicial) {
        if (this.tabuleiro.getGrid()[linhaInicial + 1][colunaInicial + 1].getOcupada().equals(true)) {
            if (this.tabuleiro.getGrid()[linhaInicial + 2][colunaInicial + 2].getOcupada().equals(false)) {
                if (this.tabuleiro.getGrid()[linhaInicial][colunaInicial].getPeca().getCorPeca().equals(CoresPeca.CLARA)) {
                    if (this.tabuleiro.getGrid()[linhaInicial + 1][colunaInicial + 1].getPeca().getCorPeca().equals(CoresPeca.ESCURA)) {
                        return true;
                    }
                } else {
                    if (this.tabuleiro.getGrid()[linhaInicial + 1][colunaInicial + 1].getPeca().getCorPeca().equals(CoresPeca.CLARA)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //verifica se a captura pra baixo e pra esquerda eh possivel
    private boolean isCapturaValidaBaixoEsquerda(int linhaInicial, int colunaInicial) {
        if (this.tabuleiro.getGrid()[linhaInicial + 1][colunaInicial - 1].getOcupada().equals(true)) {
            if (this.tabuleiro.getGrid()[linhaInicial + 2][colunaInicial - 2].getOcupada().equals(false)) {
                if (this.tabuleiro.getGrid()[linhaInicial][colunaInicial].getPeca().getCorPeca().equals(CoresPeca.CLARA)) {
                    if (this.tabuleiro.getGrid()[linhaInicial + 1][colunaInicial - 1].getPeca().getCorPeca().equals(CoresPeca.ESCURA)) {
                        return true;
                    }
                } else if (this.tabuleiro.getGrid()[linhaInicial][colunaInicial].getPeca().getCorPeca().equals(CoresPeca.ESCURA)) {
                    if (this.tabuleiro.getGrid()[linhaInicial + 1][colunaInicial - 1].getPeca().getCorPeca().equals(CoresPeca.CLARA)) {
                        return true;
                    }
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
    //verifica se alguma dama no campo pode fazer alguma captura
    private boolean existeCapturaPossivelCampoDama() {
        int i, j;

        for (i = 0; i < this.getTabuleiro().getGrid().length; i++) {
            for (j = 0; j < this.getTabuleiro().getGrid()[0].length; j++) {
                if (this.tabuleiro.getGrid()[i][j].getOcupada()) {
                    if (this.tabuleiro.getGrid()[i][j].getPeca() instanceof Dama) {
                        if (this.tabuleiro.getGrid()[i][j].getPeca().getJogador().equals(this.jogadorAtual)) {
                            if (this.existeCapturaPossivelDama(i, j)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    //verifica se uma dama especifica pode fazer uma captura
    private boolean existeCapturaPossivelDama(int linhaInicial, int colunaInicial) {
        int distanciaCima, distanciaBaixo, distanciaEsq, distanciaDir;

        distanciaCima = linhaInicial;
        distanciaBaixo = 7 - linhaInicial;
        distanciaEsq = colunaInicial;
        distanciaDir = 7 - colunaInicial;

        if (distanciaCima < distanciaDir) {
            if (this.existeCapturaValidaCimaDireitaDama(linhaInicial, colunaInicial, linhaInicial - distanciaCima, colunaInicial + distanciaCima)) {
                return true;
            }
        } else {
            if (this.existeCapturaValidaCimaDireitaDama(linhaInicial, colunaInicial, linhaInicial - distanciaDir, colunaInicial + distanciaDir)) {
                return true;
            }
        }

        if (distanciaCima < distanciaEsq) {
            if (this.existeCapturaValidaCimaEsquerdaDama(linhaInicial, colunaInicial, linhaInicial - distanciaCima, colunaInicial - distanciaCima)) {
                return true;
            }
        } else {
            if (this.existeCapturaValidaCimaEsquerdaDama(linhaInicial, colunaInicial, linhaInicial - distanciaEsq, colunaInicial - distanciaEsq)) {
                return true;
            }
        }

        if (distanciaBaixo < distanciaDir) {
            if (this.existeCapturaValidaBaixoDireitaDama(linhaInicial, colunaInicial, linhaInicial + distanciaBaixo, colunaInicial + distanciaBaixo)) {
                return true;
            }
        } else {
            if (this.existeCapturaValidaBaixoDireitaDama(linhaInicial, colunaInicial, linhaInicial + distanciaDir, colunaInicial + distanciaDir)) {
                return true;
            }
        }

        if (distanciaBaixo < distanciaEsq) {
            if (this.existeCapturaValidaBaixoEsquerdaDama(linhaInicial, colunaInicial, linhaInicial + distanciaBaixo, colunaInicial - distanciaBaixo)) {
                return true;
            }
        } else {
            if (this.existeCapturaValidaBaixoEsquerdaDama(linhaInicial, colunaInicial, linhaInicial + distanciaEsq, colunaInicial - distanciaEsq)) {
                return true;
            }
        }

        return false;
    }

    //verifica se um certa captura de certa dama pode ser executada
    private boolean isCapturaValidaDama(int linhaInicial, int colunaInicial, int linhaFinal, int colunaFinal) {
        if (linhaInicial == linhaFinal || colunaInicial == colunaFinal) {
            return false;
        }

        if (!this.tabuleiro.getGrid()[linhaFinal][colunaFinal].getOcupada()) {
            if (linhaInicial < linhaFinal && colunaInicial < colunaFinal) {
                if (!this.isCapturaValidaBaixoDireitaDama(linhaInicial, colunaInicial, linhaFinal, colunaFinal)) {
                    return false;
                }
            }
            if (linhaInicial < linhaFinal && colunaInicial > colunaFinal) {
                if (!this.isCapturaValidaBaixoEsquerdaDama(linhaInicial, colunaInicial, linhaFinal, colunaFinal)) {
                    return false;
                }
            }
            if (linhaInicial > linhaFinal && colunaInicial < colunaFinal) {
                if (!this.isCapturaValidaCimaDireitaDama(linhaInicial, colunaInicial, linhaFinal, colunaFinal)) {
                    return false;
                }
            }
            if (linhaInicial > linhaFinal && colunaInicial > colunaFinal) {
                if (!this.isCapturaValidaCimaEsquerdaDama(linhaInicial, colunaInicial, linhaFinal, colunaFinal)) {
                    return false;
                }
            }
        }

        return true;
    }

    //verifica se existe alguma captura pra baixo e pra direita de uma dama pode ser executada
    private boolean existeCapturaValidaBaixoDireitaDama(int linhaInicial, int colunaInicial, int linhaFinal, int colunaFinal) {
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
            if (this.tabuleiro.getGrid()[linha][coluna].getOcupada()) {
                if (!this.tabuleiro.getGrid()[linha][coluna].getPeca().getJogador().equals(this.jogadorAtual)) {
                    if (this.tabuleiro.getGrid()[linha + 1][coluna + 1].getOcupada()) {
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
    private boolean existeCapturaValidaBaixoEsquerdaDama(int linhaInicial, int colunaInicial, int linhaFinal, int colunaFinal) {
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
            if (this.tabuleiro.getGrid()[linha][coluna].getOcupada()) {
                if (!this.tabuleiro.getGrid()[linha][coluna].getPeca().getJogador().equals(this.jogadorAtual)) {
                    if (this.tabuleiro.getGrid()[linha + 1][coluna - 1].getOcupada()) {
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
    private boolean existeCapturaValidaCimaDireitaDama(int linhaInicial, int colunaInicial, int linhaFinal, int colunaFinal) {
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
            if (this.tabuleiro.getGrid()[linha][coluna].getOcupada()) {
                if (!this.tabuleiro.getGrid()[linha][coluna].getPeca().getJogador().equals(this.jogadorAtual)) {
                    if (this.tabuleiro.getGrid()[linha - 1][coluna + 1].getOcupada()) {
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
    private boolean existeCapturaValidaCimaEsquerdaDama(int linhaInicial, int colunaInicial, int linhaFinal, int colunaFinal) {
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
            if (this.tabuleiro.getGrid()[linha][coluna].getOcupada()) {
                if (!this.tabuleiro.getGrid()[linha][coluna].getPeca().getJogador().equals(this.jogadorAtual)) {
                    if (this.tabuleiro.getGrid()[linha - 1][coluna - 1].getOcupada()) {
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

    //verifica se a captura pra baixo e pra direita de uma dama pode ser executada
    private boolean isCapturaValidaBaixoDireitaDama(int linhaInicial, int colunaInicial, int linhaFinal, int colunaFinal) {
        boolean valido = false;
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
            if (this.tabuleiro.getGrid()[linha][coluna].getOcupada()) {
                if (!this.tabuleiro.getGrid()[linha][coluna].getPeca().getJogador().equals(this.jogadorAtual)) {
                    if (this.tabuleiro.getGrid()[linha + 1][coluna + 1].getOcupada()) {
                        return false;
                    }
                    valido = true;
                } else {
                    return false;
                }
            }

        }

        return valido;
    }

    //verifica se a captura pra baixo e pra esquerda de uma dama pode ser executada
    private boolean isCapturaValidaBaixoEsquerdaDama(int linhaInicial, int colunaInicial, int linhaFinal, int colunaFinal) {
        boolean valido = false;
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
            if (this.tabuleiro.getGrid()[linha][coluna].getOcupada()) {
                if (!this.tabuleiro.getGrid()[linha][coluna].getPeca().getJogador().equals(this.jogadorAtual)) {
                    if (this.tabuleiro.getGrid()[linha + 1][coluna - 1].getOcupada()) {
                        return false;
                    }
                    valido = true;
                } else {
                    return false;
                }
            }

        }

        return valido;
    }

    //verifica se a captura pra cima e pra direita de uma dama pode ser executada
    private boolean isCapturaValidaCimaDireitaDama(int linhaInicial, int colunaInicial, int linhaFinal, int colunaFinal) {
        boolean valido = false;
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
            if (this.tabuleiro.getGrid()[linha][coluna].getOcupada()) {
                if (!this.tabuleiro.getGrid()[linha][coluna].getPeca().getJogador().equals(this.jogadorAtual)) {
                    if (this.tabuleiro.getGrid()[linha - 1][coluna + 1].getOcupada()) {
                        return false;
                    }
                    valido = true;
                } else {
                    return false;
                }
            }

        }

        return valido;
    }

    //verifica se a captura pra cima e pra esquerda de uma dama pode ser executada
    private boolean isCapturaValidaCimaEsquerdaDama(int linhaInicial, int colunaInicial, int linhaFinal, int colunaFinal) {
        boolean valido = false;
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
            if (this.tabuleiro.getGrid()[linha][coluna].getOcupada()) {
                if (!this.tabuleiro.getGrid()[linha][coluna].getPeca().getJogador().equals(this.jogadorAtual)) {
                    if (this.tabuleiro.getGrid()[linha - 1][coluna - 1].getOcupada()) {
                        return false;
                    }
                    valido = true;
                } else {
                    return false;
                }
            }

        }

        return valido;
    }

    //////////////////
    //////////////////
    //////////////////
    //faz a peca virar dama quando chegar no final do tabuleiro
    private void virarDama(int linha, int coluna) {
        Jogador jogador = this.tabuleiro.getGrid()[linha][coluna].getPeca().getJogador();
        CoresPeca cor = this.tabuleiro.getGrid()[linha][coluna].getPeca().getCorPeca();

        if (this.tabuleiro.getGrid()[linha][coluna].getPeca().getCorPeca().equals(CoresPeca.CLARA) && linha == 0) {
            this.tabuleiro.getGrid()[linha][coluna].setPeca(new Dama(cor, jogador));
        } else if (this.tabuleiro.getGrid()[linha][coluna].getPeca().getCorPeca().equals(CoresPeca.ESCURA) && linha == 7) {
            this.tabuleiro.getGrid()[linha][coluna].setPeca(new Dama(cor, jogador));
        }

    }

    //verifica se eh pra acabar o jogo
    public boolean isFimDeJogo() {
        this.contadorPecas();

        //acaba o jogo se acabar as pecas claras
        if (qtdPecasClaras == 0 && qtdDamasClaras == 0) {
            this.resultado = Resultado.COMVENCEDOR;
            this.vencedor = jogador2;
            return true;
        }
        if (qtdPecasEscuras == 0 && qtdDamasEscuras == 0) {
            this.resultado = Resultado.COMVENCEDOR;
            this.vencedor = jogador1;
            return true;
        }

        if (this.contadorJogadas == 5) {
            if (qtdPecasClaras == 0 && qtdDamasClaras == 2 && qtdPecasEscuras == 0 && qtdDamasEscuras == 2) {
                this.resultado = Resultado.EMPATE;
                return true;
            }//2 damas contra 2 damas
            if (qtdPecasClaras == 0 && qtdDamasClaras == 2 && qtdPecasEscuras == 0 && qtdDamasEscuras == 1) {
                this.resultado = Resultado.EMPATE;
                return true;
            }//2 damas claras contra 1 dama escura
            if (qtdPecasClaras == 0 && qtdDamasClaras == 1 && qtdPecasEscuras == 0 && qtdDamasEscuras == 2) {
                this.resultado = Resultado.EMPATE;
                return true;
            }// 2 damas escuras contra 1 damas clara
            if (qtdPecasClaras == 0 && qtdDamasClaras == 2 && qtdPecasEscuras == 1 && qtdDamasEscuras == 1) {
                this.resultado = Resultado.EMPATE;
                return true;
            }//2 damas claras contra 1 dama escura e 1 peca escura
            if (qtdPecasClaras == 1 && qtdDamasClaras == 1 && qtdPecasEscuras == 0 && qtdDamasEscuras == 2) {
                this.resultado = Resultado.EMPATE;
                return true;
            }//2 damas escura contra 1 dama clara e 1 peca clara
            if (qtdPecasClaras == 0 && qtdDamasClaras == 1 && qtdPecasEscuras == 0 && qtdDamasEscuras == 1) {
                this.resultado = Resultado.EMPATE;
                return true;
            }//1 dama contra 1 dama
            if (qtdPecasClaras == 0 && qtdDamasClaras == 1 && qtdPecasEscuras == 1 && qtdDamasEscuras == 1) {
                this.resultado = Resultado.EMPATE;
                return true;
            }//1 dama clara contra 1 dama escura e 1 peca escura
            if (qtdPecasClaras == 1 && qtdDamasClaras == 1 && qtdPecasEscuras == 0 && qtdDamasEscuras == 1) {
                this.resultado = Resultado.EMPATE;
                return true;
            }//1 dama escura contra 1 dama clara e 1 peca clara
        }

        if (this.contadorJogadas >= 20) {
            this.resultado = Resultado.EMPATE;
            return true;
        }

        return false;
    }

    //conta a quantidade de pecas em campo
    private void contadorPecas() {
        qtdPecasClaras = 0;
        qtdPecasEscuras = 0;
        qtdDamasClaras = 0;
        qtdDamasEscuras = 0;

        int i, j;
        for (i = 0; i < this.getTabuleiro().getGrid().length; i++) {
            for (j = 0; j < this.getTabuleiro().getGrid()[0].length; j++) {
                if (this.tabuleiro.getGrid()[i][j].getOcupada()) {
                    if (this.tabuleiro.getGrid()[i][j].getPeca() instanceof Dama) {
                        if (this.tabuleiro.getGrid()[i][j].getPeca().getCorPeca().equals(CoresPeca.CLARA)) {
                            qtdDamasClaras++;
                        } else {
                            qtdDamasEscuras++;
                        }
                    } else {
                        if (this.tabuleiro.getGrid()[i][j].getPeca().getCorPeca().equals(CoresPeca.CLARA)) {
                            qtdPecasClaras++;
                        } else {
                            qtdPecasEscuras++;
                        }
                    }
                }
            }
        }

    }

}
