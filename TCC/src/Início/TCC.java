/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Início;

import GUI.Jogador.JanelaInicial;
import GUI.Jogador.JanelaSituacaoJogo;
import Modelo.Assistente;
import Modelo.Saida;
import Modelo.Situacao;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;

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

            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
