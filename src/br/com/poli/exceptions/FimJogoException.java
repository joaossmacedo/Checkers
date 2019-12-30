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
public class FimJogoException extends Exception {

    /**
     * Creates a new instance of <code>FimJogoException</code> without detail
     * message.
     */
    public FimJogoException() {
    }

    /**
     * Constructs an instance of <code>FimJogoException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public FimJogoException(String msg) {
        super(msg);
    }
}
