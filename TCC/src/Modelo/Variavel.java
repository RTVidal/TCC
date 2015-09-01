/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;

/**
 *
 * @author Rafael
 */
public class Variavel implements Serializable{
    
    private String nome;
    private long valorInicial;
    private boolean oculta;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(long valorInicial) {
        this.valorInicial = valorInicial;
    }

    public boolean isOculta() {
        return oculta;
    }

    public void setOculta(boolean oculta) {
        this.oculta = oculta;
    }    
}
