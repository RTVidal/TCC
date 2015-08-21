/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author Rafael
 */
public class Situacao implements Serializable{

    private ArrayList<SaidaOpcao> saidasOpcao;
    private SaidaNumerica saidaNumerica;
    private boolean situacaoInicial;
    private boolean situacaoFinal;
    private ImageIcon fundoSituacao;
    private String falaAssistente;
    private String nome;

    public Situacao() {
        saidasOpcao = new ArrayList<>();
        fundoSituacao = new ImageIcon();
        fundoSituacao.setDescription("");
    }    

    public ArrayList<SaidaOpcao> getSaidas() {
        return saidasOpcao;
    }

    public void setSaidas(ArrayList<SaidaOpcao> saidas) {
        this.saidasOpcao = saidas;
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

    public ArrayList<SaidaOpcao> getSaidasOpcao() {
        return saidasOpcao;
    }

    public void setSaidasOpcao(ArrayList<SaidaOpcao> saidasOpcao) {
        this.saidasOpcao = saidasOpcao;
    }

    public SaidaNumerica getSaidaNumerica() {
        return saidaNumerica;
    }

    public void setSaidaNumerica(SaidaNumerica saidaNumerica) {
        this.saidaNumerica = saidaNumerica;
    }

}
