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
public class Saida {
    
    private Situacao situacaoOrigem;
    private Situacao situacaoDestino;
    private String textoSaida;
    private String nome;

    public Situacao getSituacaoOrigem() {
        return situacaoOrigem;
    }

    public void setSituacaoOrigem(Situacao situacaoOrigem) {
        this.situacaoOrigem = situacaoOrigem;
    }

    public Situacao getSituacaoDestino() {
        return situacaoDestino;
    }

    public void setSituacaoDestino(Situacao situacaoDestino) {
        this.situacaoDestino = situacaoDestino;
    }

    public String getTextoSaida() {
        return textoSaida;
    }

    public void setTextoSaida(String textoSaida) {
        this.textoSaida = textoSaida;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}
