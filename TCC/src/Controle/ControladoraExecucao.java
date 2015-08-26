/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import GUI.Jogador.JanelaSituacaoJogo;
import Modelo.Partida;

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
        
        System.out.println("Iniciando execução");
                
        //Abre a janela inicial para a apresentação do assistente
        jsj = JanelaSituacaoJogo.getInstancia();
        
        System.out.println("Instanciou a janela");
        
        jsj.setVisible(true);
        jsj.setVisible(true);
        
        System.out.println("Abriu a janela");
        
        //Apresenta-se para a janela do jogo
        jsj.setControladora(this);
        jsj.setAssistente(partida.getAssistente());
        
        //Carrega a situação inicial
        jsj.CarregaSituacao(partida.getSituacaoInicial(), true);
        
    }
    
    /**
     * 
     */
    public void IniciarJogo()
    {
        jsj.CarregaSituacao(partida.getSituacaoInicial(), false);
    }
 
}
