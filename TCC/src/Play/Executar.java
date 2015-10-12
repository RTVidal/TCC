/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Play;

import Controle.ControladoraExecucao;
import Controle.ControladoraIdioma;
import Modelo.Partida;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.zip.GZIPInputStream;
import javax.swing.JOptionPane;

/**
 *
 * @author Bruno
 */
public class Executar extends Thread {

    @Override
    public void run() {
        try {
            Partida partida;
            try {
                InputStream arquivoLeitura = getClass().getResourceAsStream("/Arquivos/proj.tcc");
                GZIPInputStream gzi = new GZIPInputStream(arquivoLeitura);
                ObjectInputStream objLeitura = new ObjectInputStream(gzi);
                partida = (Partida) (objLeitura.readObject());
                objLeitura.close();
                arquivoLeitura.close();

                ControladoraIdioma idioma = new ControladoraIdioma();
                idioma.DefineIdioma(partida.getIdioma());

                if (!partida.getSituacoes().isEmpty()) {
                    Partida.setInstancia(partida);
                    ControladoraExecucao ce = new ControladoraExecucao();
                    ce.ExecutaPartida();
                } else {
                    JOptionPane.showMessageDialog(null, idioma.Valor("msgNaoExecutarSemInicial"),
                            idioma.Valor("aviso"), JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) {
                String mensagem = "Erro ao Abrir Arquivo: " + e.getMessage();
                JOptionPane.showMessageDialog(null, mensagem, "erro", JOptionPane.OK_OPTION);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

}
