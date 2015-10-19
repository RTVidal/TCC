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
public class Partida implements Serializable {

    private ParametrosArquivo parametrosArquivo;
    private ArrayList<Situacao> situacoes;
    private ArrayList<Variavel> variaveis;
    private ArrayList<Avaliacao> avaliacoes;
    private ArrayList<Caminho> caminhos;
    private String idioma;
    private static Partida instancia;

    private ImageIcon imagemDasAvaliacoes;
    private ImageIcon avatarDasAvaliacoes;

    public Partida() {
        situacoes = new ArrayList<>();
        variaveis = new ArrayList<>();
        avaliacoes = new ArrayList<>();
        caminhos = new ArrayList<>();
        imagemDasAvaliacoes = null;
        avatarDasAvaliacoes = null;
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
        if (instancia == null) {
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

    public ParametrosArquivo getParametrosArquivo() {
        return parametrosArquivo;
    }

    public void setParametrosArquivo(ParametrosArquivo parametrosArquivo) {
        this.parametrosArquivo = parametrosArquivo;
    }

    public ArrayList<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(ArrayList<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public ArrayList<Caminho> getCaminhos() {
        return caminhos;
    }

    public void setCaminhos(ArrayList<Caminho> caminhos) {
        this.caminhos = caminhos;
    }

    public ImageIcon getAvatarDasAvaliacoes() {
        return avatarDasAvaliacoes;
    }

    public void setAvatarDasAvaliacoes(ImageIcon avatarDasAvaliacoes) {
        this.avatarDasAvaliacoes = avatarDasAvaliacoes;
    }

    public ImageIcon getImagemDasAvaliacoes() {
        return imagemDasAvaliacoes;
    }

    public void setImagemDasAvaliacoes(ImageIcon imagemDasAvaliacoes) {
        this.imagemDasAvaliacoes = imagemDasAvaliacoes;
    }

}
