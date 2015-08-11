/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.awt.Image;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Rafael
 */
public class Situacao implements Serializable{

    private ArrayList<Saida> saidas;
    private boolean situacaoInicial;
    private boolean situacaoFinal;
    private Image fundoSituacao;
    private String falaAssistente;
    private String nome;

    public ArrayList<Saida> getSaidas() {
        return saidas;
    }

    public void setSaidas(ArrayList<Saida> saidas) {
        this.saidas = saidas;
    }

    public boolean isSituacaoInicial() {
        return situacaoInicial;
    }

    public void setSituacaoInicial(boolean situacaoInicial) {
        this.situacaoInicial = situacaoInicial;
    }

    public boolean isSituacaoFinal() {
        return situacaoFinal;
    }

    public void setSituacaoFinal(boolean situacaoFinal) {
        this.situacaoFinal = situacaoFinal;
    }

    public Image getFundoSituacao() {
        return fundoSituacao;
    }

    public void setFundoSituacao(Image fundoSituacao) {
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

}
