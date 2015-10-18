/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 *
 * @author Rafael
 */
public class Situacao implements Serializable {

    private boolean situacaoFinal;
    private ImageIcon fundoSituacao;

    private ImageIcon imagemPersonagem;
    private boolean rotacionarPersonagem;

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

    public ImageIcon getImagemPersonagem() {
        if (rotacionarPersonagem) {
            return rotacaoDeImagem();
        } else {
            return imagemPersonagem;
        }
    }

    public void setImagemPersonagem(ImageIcon imagemPersonagem) {
        this.imagemPersonagem = imagemPersonagem;
    }

    public int getLadoGeracao() {
        return ladoGeracao;
    }

    public void setLadoGeracao(int ladoGeracao) {
        this.ladoGeracao = ladoGeracao;
    }

    public boolean isRotacionarPersonagem() {
        return rotacionarPersonagem;
    }
    
    public void setRotacionarPersonagem(boolean rotacionarPersonagem) {
        this.rotacionarPersonagem = rotacionarPersonagem;
    }

    private ImageIcon rotacaoDeImagem() {
        BufferedImage img = converterParaBufferedImage(imagemPersonagem.getImage());
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(w, h, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
        g.dispose();
        ImageIcon imagemRotacionada = new ImageIcon();
        imagemRotacionada.setDescription(imagemPersonagem.getDescription());
        imagemRotacionada.setImage(dimg);
        return imagemRotacionada;
    }

    private BufferedImage converterParaBufferedImage(Image image) {
        BufferedImage newImage = new BufferedImage(
                image.getWidth(null), image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

}
