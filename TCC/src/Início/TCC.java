/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Início;

import GUI.Jogador.JanelaInicial;
import GUI.Jogador.JanelaSituacaoJogo;
import GUI.Teste.PainelTeste;
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
            PainelTeste pt = new PainelTeste();
            pt.setVisible(true);
            
            JanelaInicial ji = new JanelaInicial();
            ji.setVisible(true);

            //Preenche a instancia do assistente
            Assistente assistente = new Assistente();

            assistente.setNome("Joselito");
            assistente.setApresentacao("Oi amiguinho");
            assistente.setAvatarAssistente("avatar1");

            Assistente.setInstancia(assistente);

            Situacao situacao = new Situacao();

            File file = new File("./recursos/teste.jpg");
            BufferedImage img = ImageIO.read(file);

            situacao.setFundoSituacao(img);
            situacao.setFalaAssistente("No menu está uma variedade de sanduíches com preparos, "
                    + "apresentações e acompanhamentos criativos. Há hambúrguer de "
                    + "kafta, joelho de porco, falafel, feijoada, barreado, "
                    + "couve-flor e diversos cortes de carnes, inclusive premium. "
                    + "Nos pães também há grande variedade. Tem pão folha, "
                    + "integral, de aveia, batata, milho, brioche e de aipim, "
                    + "apenas para citar alguns. Os acompanhamentos são outras "
                    + "atrações. Além da tradicional batata frita, há guacamole, "
                    + "nuggets de banana, palmito assado, minipastel de geleia de "
                    + "pimenta, babaganoush e chips de banana.");

            Saida saida1 = new Saida();
            saida1.setNome("Saida 1");
            saida1.setTextoSaida("Você escolheu saida 1");

            Saida saida2 = new Saida();
            saida2.setNome("Saida 2");
            saida2.setTextoSaida("É a saída 2 que você escolheu");

            Saida saida3 = new Saida();
            saida3.setNome("Saída 3");
            saida3.setTextoSaida("Você quer a saída 3?");

            Saida saida4 = new Saida();
            saida4.setNome("Saida 4");
            saida4.setTextoSaida("Saída 4 então");

            ArrayList<Saida> saidas = new ArrayList<>();

            saidas.add(saida1);
            saidas.add(saida2);
            saidas.add(saida3);
            saidas.add(saida4);

            situacao.setSaidas(saidas);

        //Grava a situação
//        IOPartida iop = new IOPartida();
//        iop.SalvaSituacao(situacao);
//        
//        //Recupera a situação
//        //IOPartida iop = new IOPartida();
//        Situacao situacao2 = iop.LeSituacao();
            JanelaSituacaoJogo jsj = new JanelaSituacaoJogo(situacao, assistente);

            jsj.setVisible(true);
            jsj.setSize(1024, 768);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
