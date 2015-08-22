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
}
