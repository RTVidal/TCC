/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 *
 * @author Rafael
 */
public class Situacao implements Serializable{

    private boolean situacaoFinal;
    private ImageIcon fundoSituacao;
    
    private Assistente assistenteP;
    
    //1. Esquerdo, 2. Direito
    private int ladoGeracao;
    
    private String falaAssistente;
    private String nome;
    
    private Saida saida;

    public Situacao() {
        saida = new Saida();
    }

    public boolean isSituacaoFinal() {
        return situacaoFinal;
    }

    public void setSituacaoFinal(boolean situacaoFinal) {
        this.situacaoFinal = situacaoFinal;
    }

    public ImageIcon getFundoSituacao() {
        return fundoSituacao;
    }

    public void setFundoSituacao(ImageIcon fundoSituacao) {
        this.fundoSituacao = fundoSituacao;
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

    public Saida getSaida() {
        return saida;
    }

    public void setSaida(Saida saida) {
        this.saida = saida;
    }

    public Assistente getAssistenteP() {
        return assistenteP;
    }

    public void setAssistenteP(Assistente assistenteP) {
        this.assistenteP = assistenteP;
    }

    public int getLadoGeracao() {
        return ladoGeracao;
    }

    public void setLadoGeracao(int ladoGeracao) {
        this.ladoGeracao = ladoGeracao;
    }
}
