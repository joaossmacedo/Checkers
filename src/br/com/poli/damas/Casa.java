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



public class Casa {
    private Boolean ocupada;
    private Peca peca;
    private final CoresCasa corCasa;
    
    public Casa(Boolean ocupada, CoresCasa corCasa){
        this.ocupada = ocupada;
        
        this.corCasa = corCasa;
    }
        
    public void createPeca(CoresPeca corPeca, Jogador jogador){
        this.peca = new Peca(corPeca, jogador);
    }
    public void setPeca(Peca peca){
        this.peca = peca;
    }
    public Peca getPeca(){
        return this.peca;
    }
    public void setOcupada(Boolean ocupada){
        this.ocupada = ocupada;
    }
    public Boolean getOcupada(){
        return this.ocupada;
    }
    public CoresCasa getCorCasa(){
        return this.corCasa;
    }
    
    
}
