/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Início;

import GUI.Jogador.JanelaInicial;

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
//            PainelTeste pt = new PainelTeste();
//            pt.setVisible(true);
            
            JanelaInicial ji = new JanelaInicial();
            ji.setVisible(true);

//            JanelaDesenvolvimentoAssistente jda = new JanelaDesenvolvimentoAssistente();
//            jda.setVisible(true);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
