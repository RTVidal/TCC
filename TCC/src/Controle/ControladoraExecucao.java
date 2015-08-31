/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import GUI.Jogador.JanelaExecucaoPartida;
import Modelo.Partida;

/**
 *
 * @author Rafael
 */
public class ControladoraExecucao {

    private Partida partida;

    private JanelaExecucaoPartida jsj;

    private static ControladoraExecucao instancia;

    public ControladoraExecucao() {
        partida = Partida.getInstancia();
    }

    /**
     * Método principal da execução da partida
     */
    public final void ExecutaPartida() {

        //Abre a janela inicial para a apresentação do assistente
        jsj = JanelaExecucaoPartida.getInstancia();

        jsj.setVisible(true);

        //Apresenta-se para a janela do jogo
        jsj.setControladora(this);
        //jsj.setAssistente(partida.getAssistente());

        //Carrega a situação inicial
        jsj.CarregaSituacao(partida.getSituacaoInicial(), true);

    }

    /**
     *
     */
    public void IniciarJogo() {

        //jsj = new JanelaExecucaoPartida();
        
        jsj.CarregaSituacao(partida.getSituacaoInicial(), false);
        //jsj.RecarregarComponentes();
//        jsj.invalidate();
//        jsj.validate();
//        jsj.repaint();

    }

    public static ControladoraExecucao getInstancia() {
        return instancia;
    }

    public static void setInstancia(ControladoraExecucao instancia) {
        ControladoraExecucao.instancia = instancia;
    }
}
