/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;

/**
 *
 * @author Rafael
 */
public class AcaoSaidaNumerica {
    
    private String Descricao;
    private String falaAssistente;
    
    private Faixa faixa;
        
    private Situacao situacaoDestino;
    
    private boolean acaoSNDefault;
    
    private ArrayList<Acao> acoes;

    public AcaoSaidaNumerica()
    {
        acaoSNDefault = false;
        
        situacaoDestino = new Situacao();
    }
    
    public String getFalaAssistente() {
        return falaAssistente;
    }

    public void setFalaAssistente(String falaAssistente) {
        this.falaAssistente = falaAssistente;
    }
    
    public Faixa getFaixa() {
        return faixa;
    }

    public void setFaixa(Faixa faixa) {
        this.faixa = faixa;
    }

    public Situacao getSituacaoDestino() {
        return situacaoDestino;
    }

    public void setSituacaoDestino(Situacao situacaoDestino) {
        this.situacaoDestino = situacaoDestino;
    }

    public ArrayList<Acao> getAcoes() {
        return acoes;
    }

    public void setAcoes(ArrayList<Acao> acoes) {
        this.acoes = acoes;
    }

    public boolean isAcaoSNDefault() {
        return acaoSNDefault;
    }

    public void setAcaoSNDefault(boolean acaoSNDefault) {
        this.acaoSNDefault = acaoSNDefault;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }
   
}
