/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.poli.exceptions;

/**
 *
 * @author joaomacedo
 */
public class MovimentoInvalidoException extends Exception {

    /**
     * Creates a new instance of <code>MovimentoInvalidoException</code> without
     * detail message.
     */
    public MovimentoInvalidoException() {
    }

    /**
     * Constructs an instance of <code>MovimentoInvalidoException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public MovimentoInvalidoException(String msg) {
        super(msg);
    }
}
