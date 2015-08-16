/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;

/**
 *
 * @author Rafael
 */
public class Assistente implements Serializable {
    
    
    private String nome;
    private String apresentacao;
    private String avatarAssistente;
    
    private static Assistente instancia;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApresentacao() {
        return apresentacao;
    }

    public void setApresentacao(String apresentacao) {
        this.apresentacao = apresentacao;
    }

    public String getAvatarAssistente() {
        return avatarAssistente;
    }

    public void setAvatarAssistente(String avatarAssistente) {
        this.avatarAssistente = avatarAssistente;
    }

    public static Assistente getInstancia() {
        return instancia;
    }

    public static void setInstancia(Assistente instancia) {
        Assistente.instancia = instancia;
    }
    
        
}
