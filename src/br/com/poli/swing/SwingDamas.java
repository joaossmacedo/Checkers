/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.poli.swing;

/**
 *
 * @author joaomacedo
 */
import br.com.poli.damas.*;
import br.com.poli.enums.*;
import br.com.poli.exceptions.*;
import br.com.poli.interfaces.*;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

/**
 *
 * @author joaomacedo
 */
public class SwingDamas implements Interface {

    private static Jogo jogo1;
    private static int linha, coluna;
    private static final ImageIcon DAMA_CLARA = new ImageIcon("resources/Imagens/DamaClara.png");
    private static final ImageIcon DAMA_ESCURA = new ImageIcon("resources/Imagens/DamaEscura.png");
    private static final ImageIcon PECA_CLARA = new ImageIcon("resources/Imagens/PecaClara.png");
    private static final ImageIcon PECA_ESCURA = new ImageIcon("resources/Imagens/PecaEscura.png");

    private final JFrame tela;
    //1a tela
    private final JLabel titulo;
    private final JButton doisJogadores;
    private final JButton umJogador;
    //2a tela
    private final JLabel txtAddJogador1;
    private final JTextField addJogador1;
    private final JLabel txtAddJogador2;
    private final JTextField addJogador2;
    private final JButton tela2Avancar;
    //3a tela
    private final JButton casas[][];
    private final JPanel displayJogadores, displayConfig, displayAvisos;
    private final JLabel txtNomeJogador1, nomeJogador1, txtNomeJogador2, nomeJogador2,
            tempo, jogadorAtual, jogadasSemCaptura, aviso, txtGanhador, ganhador;
    private final JLabel txtTempo, txtJogadorAtual, txtJogadasSemCaptura;
    private final JButton desistir;

    //define os atributos iniciais dos componentes
    public SwingDamas() {
        tela = new JFrame("DAMAS");
        //1a tela
        titulo = new JLabel("DAMAS", SwingConstants.CENTER);
        doisJogadores = new JButton("DOIS JOGADORES");
        umJogador = new JButton("UM JOGADOR");
        //2a tela
        txtAddJogador1 = new JLabel("Nome do jogador 1:");
        addJogador1 = new JTextField();
        txtAddJogador2 = new JLabel("Nome do jogador 2:");
        addJogador2 = new JTextField();
        tela2Avancar = new JButton("Jogar");
        //3a tela
        casas = new JButton[8][8];
        displayJogadores = new JPanel();
        txtNomeJogador1 = new JLabel("Jogador 1: 12 pecas");
        nomeJogador1 = new JLabel();
        txtNomeJogador2 = new JLabel("Jogador 2: 12 pecas");
        nomeJogador2 = new JLabel();
        displayConfig = new JPanel();
        txtTempo = new JLabel("Tempo de jogo:");
        tempo = new JLabel("0 segundos");
        txtJogadorAtual = new JLabel("Jogador Atual:");
        jogadorAtual = new JLabel();
        txtJogadasSemCaptura = new JLabel("Jogadas sem captura:");
        jogadasSemCaptura = new JLabel("0");
        displayAvisos = new JPanel();
        aviso = new JLabel();
        txtGanhador = new JLabel();
        ganhador = new JLabel();
        desistir = new JButton("DESISTIR");

        //essa eh area onde sao definidos os tamanhos dos componentes
        tela.setSize(980, 820);
        //1a tela
        titulo.setBounds(0, 50, 980, 250);
        doisJogadores.setBounds(430, 340, 150, 100);
        umJogador.setBounds(430, 460, 150, 100);
        //2a tela
        txtAddJogador1.setBounds(435, 300, 150, 30);
        addJogador1.setBounds(430, 340, 150, 30);
        txtAddJogador2.setBounds(435, 380, 150, 30);
        addJogador2.setBounds(430, 420, 150, 30);
        tela2Avancar.setBounds(430, 460, 150, 100);
        //3a tela
        displayJogadores.setBounds(815, 20, 150, 65);
        displayConfig.setBounds(815, 115, 150, 100);
        displayAvisos.setBounds(815, 245, 150, 50);
        desistir.setBounds(815, 700, 150, 50);

        txtNomeJogador1.setSize(150, 30);
        nomeJogador1.setSize(150, 30);
        txtNomeJogador2.setSize(150, 30);
        nomeJogador2.setSize(150, 30);
        txtTempo.setSize(150, 30);
        tempo.setSize(150, 30);
        txtJogadorAtual.setSize(150, 30);
        jogadorAtual.setSize(150, 30);
        txtJogadasSemCaptura.setSize(150, 30);
        jogadasSemCaptura.setSize(150, 30);
        aviso.setSize(150, 30);
        txtGanhador.setSize(150, 30);
        ganhador.setSize(150, 30);

        //define a fonte do titulo
        titulo.setFont(new Font("Serif", Font.BOLD, 200));

        //esse eh area onde sao modificadas as cores de certos componentes
        displayJogadores.setBackground(Color.CYAN);
        displayConfig.setBackground(Color.GREEN);
        displayAvisos.setBackground(Color.BLACK);
        aviso.setForeground(new Color(236, 93, 0));
        txtGanhador.setForeground(Color.YELLOW);
        ganhador.setForeground(Color.YELLOW);

        //configura o layout dos JPanels
        displayJogadores.setLayout(new BoxLayout(displayJogadores, BoxLayout.Y_AXIS));
        displayConfig.setLayout(new BoxLayout(displayConfig, BoxLayout.Y_AXIS));

        //adiciona cada componente ao seu devido JPanel
        displayJogadores.add(txtNomeJogador1);
        displayJogadores.add(nomeJogador1);
        displayJogadores.add(txtNomeJogador2);
        displayJogadores.add(nomeJogador2);
        displayConfig.add(txtTempo);
        displayConfig.add(tempo);
        displayConfig.add(txtJogadorAtual);
        displayConfig.add(jogadorAtual);
        displayConfig.add(txtJogadasSemCaptura);
        displayConfig.add(jogadasSemCaptura);
        displayAvisos.add(aviso);
        displayAvisos.add(txtGanhador);
        displayAvisos.add(ganhador);
    }

    //define a tela inicial
    @Override
    public void inicializar() {
        tela.add(titulo);
        tela.add(doisJogadores);
        tela.add(umJogador);

        SwingUtilities.updateComponentTreeUI(tela);

        //define a acao do botao doisJogadores
        doisJogadores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tela.remove(doisJogadores);
                tela.remove(umJogador);

                tela.add(txtAddJogador1);
                tela.add(addJogador1);
                tela.add(txtAddJogador2);
                tela.add(addJogador2);
                tela.add(tela2Avancar);

                SwingUtilities.updateComponentTreeUI(tela);

                abrirJogo(false);
            }
        });

        //define a acao do botao umJogadores
        umJogador.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tela.remove(doisJogadores);
                tela.remove(umJogador);

                tela.add(txtAddJogador1);
                tela.add(addJogador1);
                tela.add(tela2Avancar);
                addJogador2.setText("T-1000");

                SwingUtilities.updateComponentTreeUI(tela);

                abrirJogo(true);
            }
        });

        tela.setResizable(false);
        tela.setLayout(null);
        tela.setVisible(true);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //faz a transicao da 2a para a 3a tela e define as funcoes de todos os botoes dessa tela
    @Override
    public void abrirJogo(boolean criarJogadorAutonomo) {

        //define a acao do botao tela2Avancar que passa da tela de nome dos jogadores para o jogo
        tela2Avancar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tela.remove(titulo);
                tela.remove(tela2Avancar);
                tela.remove(addJogador1);
                tela.remove(addJogador2);
                tela.remove(txtAddJogador1);
                tela.remove(txtAddJogador2);
                if (!criarJogadorAutonomo) {
                    jogo1 = new Jogo(new Jogador(addJogador1.getText()), new Jogador(addJogador2.getText()));
                } else {
                    jogo1 = new Jogo(new Jogador(addJogador1.getText()), new JogadorAutonomo(addJogador2.getText()));
                }
                jogo1.iniciarPartida();

                nomeJogador1.setText(addJogador1.getText());
                nomeJogador2.setText(addJogador2.getText());
                jogadorAtual.setText(jogo1.getNomeJogadorAtual());

                createTabuleiro();
                definirAcoesCasas();

                tela.add(displayJogadores);
                tela.add(displayConfig);
                tela.add(displayAvisos);
                tela.add(desistir);

                SwingUtilities.updateComponentTreeUI(tela);
            }
        });

        //define a acao do botao desistir
        desistir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jogo1.mudarVez();
                acabarJogo(jogo1.getNomeJogadorAtual());
            }
        });
    }

    //cria as casas do tabuleiro
    @Override
    public void createTabuleiro() {
        int i, j;

        for (i = 0; i < casas.length; i++) {
            for (j = 0; j < casas[0].length; j++) {
                casas[i][j] = new JButton();
                if (jogo1.getTabuleiro().getGrid()[i][j].getOcupada()) {
                    if (jogo1.getTabuleiro().getGrid()[i][j].getPeca().getCorPeca().equals(CoresPeca.ESCURA)) {
                        casas[i][j].setIcon(PECA_ESCURA);
                    } else if (jogo1.getTabuleiro().getGrid()[i][j].getPeca().getCorPeca().equals(CoresPeca.CLARA)) {
                        casas[i][j].setIcon(PECA_CLARA);
                    }
                    casas[i][j].setBackground(new Color(75, 75, 75));
                    casas[i][j].setOpaque(true);
                    casas[i][j].setBorderPainted(false);
                } else {
                    if (jogo1.getTabuleiro().getGrid()[i][j].getCorCasa().equals(CoresCasa.PRETA)) {
                        casas[i][j].setBackground(new Color(75, 75, 75));
                    } else {
                        casas[i][j].setEnabled(false);
                        casas[i][j].setBackground(Color.WHITE);
                    }
                    casas[i][j].setOpaque(true);
                    casas[i][j].setBorderPainted(false);
                }
                casas[i][j].setBounds(j * 100, i * 100, 100, 100);
                tela.add(casas[i][j]);

            }
        }
    }

    //define a acao das casas quando apertadas
    public void definirAcoesCasas() {
        int i, j;
        for (i = 0; i < casas.length; i++) {
            for (j = 0; j < casas[0].length; j++) {
                int i0, j0;
                SwingDamas a = new SwingDamas();
                i0 = i;
                j0 = j;
                linha = 8;
                coluna = 8;
                casas[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (linha == 8) {
                            linha = i0;
                            coluna = j0;
                        } else {
                            aviso.setText(null);
                            try {
                                jogo1.jogar(linha, coluna, i0, j0);
                            } catch (MovimentoInvalidoException ex) {
                                aviso.setText("MOVIMENTO INVALIDO");
                            } catch (FimJogoException ex) {
                                acabarJogo(jogo1.getNomeJogadorAtual());
                            }
                            linha = 8;
                            coluna = 8;
                        }
                        drawTabuleiro();

                        if (jogo1.getJogador2().getNome().equals(jogo1.getNomeJogadorAtual()) && jogo1.getJogador2() instanceof JogadorAutonomo) {                            
                            ((JogadorAutonomo) jogo1.getJogador2()).jogarAuto(jogo1);

                            drawTabuleiro();
                            if (jogo1.isFimDeJogo()) {
                                if (jogo1.getResultado().equals(Resultado.COMVENCEDOR)) {
                                    acabarJogo(jogo1.getVencedor().getNome());
                                } else {
                                    acabarJogo("EMPATE");
                                }
                            }
                        }
                        jogadorAtual.setText(jogo1.getNomeJogadorAtual());
                        jogadasSemCaptura.setText("" + jogo1.getContadorJogadasSemCaptura());
                        txtNomeJogador1.setText("Jogador 1: " + jogo1.getQtdClaras() + " pecas");
                        txtNomeJogador2.setText("Jogador 2: " + jogo1.getQtdEscuras() + " pecas");
                        tempo.setText(((jogo1.getTempo().getTime() - jogo1.getTempoInicial().getTime()) / 1000) + " segundos");
                    }
                });

            }
        }

    }

    //desenha o tabuleiro
    @Override
    public void drawTabuleiro() {
        int i, j;
        for (i = 0; i < casas.length; i++) {
            for (j = 0; j < casas[0].length; j++) {
                if (jogo1.getTabuleiro().getGrid()[i][j].getOcupada()) {
                    if (jogo1.getTabuleiro().getGrid()[i][j].getPeca() instanceof Dama) {
                        if (jogo1.getTabuleiro().getGrid()[i][j].getPeca().getCorPeca().equals(CoresPeca.ESCURA)) {
                            casas[i][j].setIcon(DAMA_ESCURA);
                        } else if (jogo1.getTabuleiro().getGrid()[i][j].getPeca().getCorPeca().equals(CoresPeca.CLARA)) {
                            casas[i][j].setIcon(DAMA_CLARA);
                        }
                    } else {
                        try {
                            if (jogo1.getTabuleiro().getGrid()[i][j].getPeca().getCorPeca().equals(CoresPeca.ESCURA)) {
                                casas[i][j].setIcon(PECA_ESCURA);
                            } else if (jogo1.getTabuleiro().getGrid()[i][j].getPeca().getCorPeca().equals(CoresPeca.CLARA)) {
                                casas[i][j].setIcon(PECA_CLARA);
                            }
                        } catch (Exception ex) {
                            jogo1.getTabuleiro().getGrid()[i][j].setOcupada(false);
                        }
                    }
                } else {
                    if (jogo1.getTabuleiro().getGrid()[i][j].getCorCasa().equals(CoresCasa.PRETA)) {
                        try {
                            casas[i][j].setIcon(null);
                        } catch (Exception ex) {
                            casas[i][j].setBackground(new Color(75, 75, 75));
                        }
                    }
                }
                if ((i + j) % 2 != 0) {
                    if (i == linha && j == coluna) {
                        casas[i][j].setBackground(Color.green);
                    } else {
                        casas[i][j].setBackground(new Color(75, 75, 75));
                    }
                }
            }
        }

    }

    //acaba o jogo
    @Override
    public void acabarJogo(String nomeGanhador) {
        txtGanhador.setText("GANHADOR: ");
        ganhador.setText(nomeGanhador);
        desistir.setEnabled(false);
        int i, j;
        for (i = 0; i < casas.length; i++) {
            for (j = 0; j < casas[0].length; j++) {
                casas[i][j].setEnabled(false);
            }
        }
    }

    public static void main(String[] args) {
        SwingDamas a = new SwingDamas();
        a.inicializar();
    }

}
