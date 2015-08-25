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
public class SaidaOpcional implements Serializable{
    
    private String nome;
        
    private ArrayList<Acao> acoes;
    
    private String falaAssistente;
    
    private Situacao situacaoDestino;

    public SaidaOpcional()
    {
        situacaoDestino = new Situacao();
        acoes = new ArrayList<>();
        falaAssistente = "";
        nome = "";
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

    public String getFalaAssistente() {
        return falaAssistente;
    }

    public void setFalaAssistente(String falaAssistente) {
        this.falaAssistente = falaAssistente;
    }

    public Situacao getSituacaoDestino() {
        return situacaoDestino;
    }

    public void setSituacaoDestino(Situacao situacaoDestino) {
        this.situacaoDestino = situacaoDestino;
    }
}
