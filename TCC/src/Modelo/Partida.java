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
public class Partida implements Serializable{
    
    private Situacao situacaoInicial;
    private Assistente assistente;
    private ArrayList<Situacao> situacoes;
    private ArrayList<Variavel> variaveis;
    private String idioma;
    private static Partida instancia;

    public Partida() {
        
        situacoes = new ArrayList<>();
        variaveis = new ArrayList<>();
        
        assistente = new Assistente();
        
    }
    
    public Situacao getSituacaoInicial() {
        return situacaoInicial;
    }

    public void setSituacaoInicial(Situacao situacaoInicial) {
        this.situacaoInicial = situacaoInicial;
    }

    public Assistente getAssistente() {
        return assistente;
    }

    public void setAssistente(Assistente assistente) {
        this.assistente = assistente;
    }

    public ArrayList<Situacao> getSituacoes() {
        return situacoes;
    }

    public void setSituacoes(ArrayList<Situacao> situacoes) {
        this.situacoes = situacoes;
    }

    public ArrayList<Variavel> getVariaveis() {
        return variaveis;
    }

    public void setVariaveis(ArrayList<Variavel> variaveis) {
        this.variaveis = variaveis;
    }

    public static Partida getInstancia() {
        if(instancia == null) {
            instancia = new Partida();
        }
        return instancia;
    }

    public static void setInstancia(Partida instancia) {
        Partida.instancia = instancia;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    
}
