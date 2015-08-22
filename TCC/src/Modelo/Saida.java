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
public class Saida {
    
    //1. Multipla escolha, //2. Dissertativa
    private int tipoSaida;
    
    private Situacao situacaoOrigem;
    
    private ArrayList<SaidaOpcao> saidasOpcao;
    
    private SaidaNumerica saidaNumerica;

    public Saida()
    {
        saidasOpcao = new ArrayList<>();
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

    public ArrayList<SaidaOpcao> getsaidasOpcao() {
        return saidasOpcao;
    }

    public void setsaidasOpcao(ArrayList<SaidaOpcao> saidasOpcao) {
        this.saidasOpcao = saidasOpcao;
    }

    public SaidaNumerica getSaidaNumerica() {
        return saidaNumerica;
    }

    public void setSaidaNumerica(SaidaNumerica saidaNumerica) {
        this.saidaNumerica = saidaNumerica;
    }
    
}
