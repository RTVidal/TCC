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
public class Saida implements Serializable{
    
    //1. Multipla escolha, //2. Dissertativa
    private int tipoSaida;
    
    private Situacao situacaoOrigem;
    
    private ArrayList<SaidaOpcional> saidasOpcao;
    
    private ArrayList<SaidaNumerica> saidasNumerica;

    public Saida()
    {
        saidasOpcao = new ArrayList<>();
        saidasNumerica = new ArrayList<>();
        tipoSaida = 0;
    }
    
    public int getTipoSaida() {
        return tipoSaida;
    }

    public void setTipoSaida(int tipoSaida) {
        this.tipoSaida = tipoSaida;
    }

    public Situacao getSituacaoOrigem() {
        return situacaoOrigem;
    }

    public void setSituacaoOrigem(Situacao situacaoOrigem) {
        this.situacaoOrigem = situacaoOrigem;
    }

    public ArrayList<SaidaOpcional> getsaidasOpcao() {
        return saidasOpcao;
    }

    public void setsaidasOpcao(ArrayList<SaidaOpcional> saidasOpcao) {
        this.saidasOpcao = saidasOpcao;
    }

    public ArrayList<SaidaNumerica> getSaidasNumerica() {
        return saidasNumerica;
    }

    public void setSaidasNumerica(ArrayList<SaidaNumerica> saidasNumerica) {
        this.saidasNumerica = saidasNumerica;
    }

    public ArrayList<SaidaOpcional> getSaidasOpcao() {
        return saidasOpcao;
    }

    public void setSaidasOpcao(ArrayList<SaidaOpcional> saidasOpcao) {
        this.saidasOpcao = saidasOpcao;
    }
    
}
