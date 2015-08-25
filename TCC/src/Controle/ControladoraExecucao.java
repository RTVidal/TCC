/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import GUI.Jogador.JanelaSituacaoJogo;
import Modelo.Partida;
import Modelo.Situacao;

/**
 *
 * @author Rafael
 */
public class ControladoraExecucao {
    
    private Partida partida;
    
    private JanelaSituacaoJogo jsj;
    
    public ControladoraExecucao()
    {
        partida = Partida.getInstancia();
    }
    
    /**
     * Método principal da execução da partida
     */
    public final void ExecutaPartida()
    {
        
        //Abre a janela inicial para a apresentação do assistente
        jsj = JanelaSituacaoJogo.getInstancia();
        jsj.setVisible(true);
        
        //Apresenta-se para a janela do jogo
        jsj.setControladora(this);
        
        //Adiciona o assiste à partida
        jsj.CarregaAssistente(partida.getAssistente());        
        
        System.out.println(partida.getSituacaoInicial());
        
        //Carrega a situação inicial
        jsj.CarregaSituacao(partida.getSituacaoInicial());
        
    }
 
}
