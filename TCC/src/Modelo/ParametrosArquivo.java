/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;

/**
 *
 * @author Bruno
 */
public class ParametrosArquivo implements Serializable {

    private boolean arquivoSelecionado;
    private String nomeDoArquivo;
    private String patchDoArquivo;

    public boolean isArquivoSelecionado() {
        return arquivoSelecionado;
    }

    public void setArquivoSelecionado(boolean arquivoSelecionado) {
        this.arquivoSelecionado = arquivoSelecionado;
    }

    public String getNomeDoArquivo() {
        return nomeDoArquivo;
    }

    public void setNomeDoArquivo(String nomeDoArquivo) {
        this.nomeDoArquivo = nomeDoArquivo;
    }

    public String getPatchDoArquivo() {
        return patchDoArquivo;
    }

    public void setPatchDoArquivo(String patchDoArquivo) {
        this.patchDoArquivo = patchDoArquivo;
    }

}
