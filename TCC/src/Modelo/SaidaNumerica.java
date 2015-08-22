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
public class SaidaNumerica {
            
    private AcaoSaidaNumerica acaoSNDefaultInferior;
    
    private AcaoSaidaNumerica acaoSNDefaultSuperior;
    
    private ArrayList<AcaoSaidaNumerica> acaoSaidaNumerica;
    
    private int valorMinimo;
    
    private int valorMaximo;
    

    public AcaoSaidaNumerica getAcaoSNDefaultInferior() {
        return acaoSNDefaultInferior;
    }

    public void setAcaoSNDefaultInferior(AcaoSaidaNumerica acaoSNDefaultInferior) {
        this.acaoSNDefaultInferior = acaoSNDefaultInferior;
    }

    public AcaoSaidaNumerica getAcaoSNDefaultSuperior() {
        return acaoSNDefaultSuperior;
    }

    public void setAcaoSNDefaultSuperior(AcaoSaidaNumerica acaoSNDefaultSuperior) {
        this.acaoSNDefaultSuperior = acaoSNDefaultSuperior;
    }

    public ArrayList<AcaoSaidaNumerica> getAcaoSaidaNumerica() {
        return acaoSaidaNumerica;
    }

    public void setAcaoSaidaNumerica(ArrayList<AcaoSaidaNumerica> acaoSaidaNumerica) {
        this.acaoSaidaNumerica = acaoSaidaNumerica;
    }

    public int getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(int valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public int getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(int valorMaximo) {
        this.valorMaximo = valorMaximo;
    }    
}
