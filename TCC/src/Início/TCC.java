/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Início;

import GUI.Jogador.JanelaInicial;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Rafael
 */
public class TCC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JanelaInicial ji = new JanelaInicial();
            ji.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
