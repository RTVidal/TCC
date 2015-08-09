/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Início;

import GUI.Jogador.JanelaSituacaoJogo;
import Modelo.Situacao;
import Persistencia.IOPartida;
import javax.swing.ImageIcon;

/**
 *
 * @author Rafael
 */
public class TCC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Situacao situacao = new Situacao();
        
        ImageIcon img = new ImageIcon("./recursos/teste.jpg");
        situacao.setFundoSituacao(img);
        situacao.setFalaAssistente("teste");
        
        //Grava a situação
        IOPartida iop = new IOPartida();
        iop.SalvaSituacao(situacao);
        
        //Recupera a situação
        //IOPartida iop = new IOPartida();
        Situacao situacao2 = iop.LeSituacao();
        
        JanelaSituacaoJogo jsj = new JanelaSituacaoJogo(situacao2);
        
        jsj.setVisible(true);
        jsj.setSize(1024, 768);
    }
    
}
