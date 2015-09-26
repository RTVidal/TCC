/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Controle.ControladoraIdioma;
import Modelo.Partida;
import Modelo.ParametrosArquivo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Rafael
 */
public class IOPartida {

    private final ControladoraIdioma idioma;

    public IOPartida() {
        idioma = ControladoraIdioma.getInstancia();
    }

    public ParametrosArquivo selecionadorDeArquivos() {
        ParametrosArquivo sda = new ParametrosArquivo();
        JFileChooser jFileChooser = new JFileChooser();
        //Selecionar apenas arquivos
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //desabilita todos os tipos de arquivos
        jFileChooser.setAcceptAllFileFilterUsed(false);
        //filtra por extensao
        jFileChooser.setFileFilter(new FileFilter() {
            @Override
            public String getDescription() {
                return "tcc";
            }

            @Override
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith("tcc");
            }
        });
        //mostra janela para salvar
        int acao = jFileChooser.showSaveDialog(null);
        //executa acao conforme opcao selecionada
        if (acao == JFileChooser.APPROVE_OPTION) {
            sda.setNomeDoArquivo(jFileChooser.getSelectedFile().getName());
            sda.setPatchDoArquivo(jFileChooser.getSelectedFile().getAbsolutePath());
            sda.setArquivoSelecionado(true);
        } else if (acao == JFileChooser.CANCEL_OPTION) {
            sda.setArquivoSelecionado(false);
        } else if (acao == JFileChooser.ERROR_OPTION) {
            sda.setArquivoSelecionado(false);
        }
        return sda;
    }

    public boolean SalvaPartida(Partida partidaSalvar) {
        try {
            ParametrosArquivo pa = selecionadorDeArquivos();
            if (pa.isArquivoSelecionado()) {
                partidaSalvar.setParametrosArquivo(pa);
                String diretorio = pa.getPatchDoArquivo();
                String finalNome = diretorio.substring(diretorio.length() - 4, diretorio.length());
                FileOutputStream arquivoGrav;
                if (finalNome.equalsIgnoreCase(".tcc")) {
                    arquivoGrav = new FileOutputStream(diretorio, true);
                } else {
                    arquivoGrav = new FileOutputStream(diretorio + ".tcc", true);
                }
                ObjectOutputStream objGravar = new ObjectOutputStream(arquivoGrav);
                objGravar.writeObject(partidaSalvar);
                objGravar.flush();
                objGravar.close();
                arquivoGrav.flush();
                arquivoGrav.close();
                Partida.setInstancia(partidaSalvar);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Partida LePartida() {
        ParametrosArquivo pa = selecionadorDeArquivos();
        Partida partida;
        try {
            if (pa.isArquivoSelecionado()) {
                FileInputStream arquivoLeitura = new FileInputStream(pa.getPatchDoArquivo());
                //Classe responsavel por recuperar os objetos do arquivo
                ObjectInputStream objLeitura = new ObjectInputStream(arquivoLeitura);
                partida = (Partida) (objLeitura.readObject());
                objLeitura.close();
                arquivoLeitura.close();
                return partida;
            } else {
                return null;
            }
        } catch (Exception e) {
            String mensagem = idioma.Valor("msgErroAoAbrirArquivo") + ": " + e.getMessage();
            JOptionPane.showMessageDialog(null, mensagem, idioma.Valor("aviso"), JOptionPane.OK_OPTION);
            return null;
        }
    }

}
