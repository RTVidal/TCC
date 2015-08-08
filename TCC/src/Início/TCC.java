/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Início;

import GUI.Jogador.JanelaSituacaoJogo;
import Modelo.Situacao;
import java.net.URL;
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
        
        JanelaSituacaoJogo jsj = new JanelaSituacaoJogo(situacao);
        
        jsj.setVisible(true);
        jsj.setSize(1024, 768);
    }
    
        
//        //janela do programa      
//        JFrame frame = new JFrame("Carregar Imagem");  
//        //container onde serão adicionados todos componentes  
//        Container container = frame.getContentPane();  
//  
//        //carrega a imagem passando o nome da mesma  
//        ImageIcon img = new ImageIcon("C:/teste.jpg");  
//          
//        //pega a altura e largura  
//        int altura = img.getIconHeight();  
//        int largura = img.getIconWidth();  
//          
//        //adiciona a imagem em um label  
//        JLabel label = new JLabel(img);  
//        //adiciona a altura e largura em outro label  
//        JLabel label2 = new JLabel("Altura: "+altura+"      Largura: "+largura);  
//  
//        //cria o JPanel para adicionar os labels  
//        JPanel panel = new JPanel();  
//        panel.add(label, BorderLayout.NORTH);  
//        panel.add(label2, BorderLayout.SOUTH);  
//  
//        //adiciona o panel no container  
//        container.add(panel, BorderLayout.CENTER);  
//          
//        frame.pack();  
//        frame.setVisible(true); 
    
    
}
