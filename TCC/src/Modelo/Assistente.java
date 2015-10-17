/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 *
 * @author Rafael
 */
public class Assistente implements Serializable {

    private ImageIcon avatarAssistente;

    public ImageIcon getAvatarAssistente() {
        return avatarAssistente;
    }

    public void setAvatarAssistente(ImageIcon avatarAssistente) {
        this.avatarAssistente = avatarAssistente;
    }

}
