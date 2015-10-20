/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import GUI.Desenvolvedor.JanelaTexto;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Bruno
 */
public class ControladoraAjuda {

    ControladoraIdioma idioma;

    public ControladoraAjuda() {
        idioma = ControladoraIdioma.getInstancia();
    }

    public void ExibirAjuda(Component source, String arquivoAjuda) {
        try {
            String idiomaAtual = idioma.getIdiomaAtual();
            File arquivo = new File("Ajuda/" + idiomaAtual + "/" + arquivoAjuda + ".txt");
            FileReader fileReader = new FileReader(arquivo);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            ArrayList<String> conteudoArquivo = new ArrayList<>();
            String linha = "";
            while ((linha = bufferedReader.readLine()) != null) {
                conteudoArquivo.add(linha);
            }
            fileReader.close();
            bufferedReader.close();

            JanelaTexto jt = new JanelaTexto(conteudoArquivo);
            jt.setLocationRelativeTo(source);
            jt.setVisible(true);

        } catch (Exception e) {
            String mensagem = idioma.Valor("msgErroAoAbrirArquivo") + ": " + e.getMessage();
            JOptionPane.showMessageDialog(null, mensagem, idioma.Valor("aviso"), JOptionPane.OK_OPTION);
            e.printStackTrace();
        }
    }

    public void ManualUtilizacao(Component source) {
        try {
            String idiomaAtual = idioma.getIdiomaAtual();
            ArrayList<String> conteudoDosArquivos = new ArrayList<>();
            ArrayList<String> arquivosDeAjuda = new ArrayList<>();
            
            for (String arquivoAjuda : arquivosDeAjuda) {
                File arquivo = new File("Ajuda/" + idiomaAtual + "/" + arquivoAjuda + ".txt");
                FileReader fileReader = new FileReader(arquivo);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String linha = "";
                while ((linha = bufferedReader.readLine()) != null) {
                    conteudoDosArquivos.add(linha);
                }
                fileReader.close();
                bufferedReader.close();
                
                conteudoDosArquivos.add("");
                conteudoDosArquivos.add("");
            }
            
            conteudoDosArquivos.add("");
            conteudoDosArquivos.add("");
            conteudoDosArquivos.add(idioma.Valor("mensagemManualParte1"));
            conteudoDosArquivos.add("");
            conteudoDosArquivos.add(idioma.Valor("mensagemManualParte2"));
            conteudoDosArquivos.add("");
            
            JanelaTexto jt = new JanelaTexto(conteudoDosArquivos);
            jt.setLocationRelativeTo(source);
            jt.setVisible(true);

        } catch (Exception e) {
            String mensagem = idioma.Valor("msgErroAoAbrirArquivo") + ": " + e.getMessage();
            JOptionPane.showMessageDialog(null, mensagem, idioma.Valor("aviso"), JOptionPane.OK_OPTION);
            e.printStackTrace();
        }
    }

}
