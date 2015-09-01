/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Rafael
 */
public class Acao {
    
    private Variavel variavel;
    
    private boolean abortarJogoSeNegativo;
    
    private boolean valida;
    
    //1. adição, 2. subtração, 3. multiplicação, 4. divisão
    private int operacao;
    
    private double numero;

    public Variavel getVariavel() {
        return variavel;
    }

    public void setVariavel(Variavel variavel) {
        this.variavel = variavel;
    }

    public int getOperacao() {
        return operacao;
    }

    public void setOperacao(int operacao) {
        this.operacao = operacao;
    }

    public double getNumero() {
        return numero;
    }

    public void setNumero(double numero) {
        this.numero = numero;
    }

    public boolean isAbortarJogoSeNegativo() {
        return abortarJogoSeNegativo;
    }

    public void setAbortarJogoSeNegativo(boolean abortarJogoSeNegativo) {
        this.abortarJogoSeNegativo = abortarJogoSeNegativo;
    }

    public boolean isValida() {
        return valida;
    }

    public void setValida(boolean valida) {
        this.valida = valida;
    }
}
