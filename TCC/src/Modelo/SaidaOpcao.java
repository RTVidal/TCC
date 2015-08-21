/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Rafael
 */
public class SaidaOpcao implements Serializable{
    
    private Situacao situacaoOrigem;
    private Situacao situacaoDestino;
    private String falaAssistente;
    private String nome;
        
    private ArrayList<Acao> acoes;

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

    public String getFalaAssistente() {
        return falaAssistente;
    }

    public void setFalaAssistente(String falaAssistente) {
        this.falaAssistente = falaAssistente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Acao> getAcoes() {
        return acoes;
    }

    public void setAcoes(ArrayList<Acao> acoes) {
        this.acoes = acoes;
    }    
}
