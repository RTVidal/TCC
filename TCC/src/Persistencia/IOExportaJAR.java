/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Controle.ControladoraIdioma;
import Modelo.ParametrosArquivo;
import Modelo.Partida;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import javax.swing.JOptionPane;

/**
 *
 * @author Bruno
 */
public class IOExportaJAR {

    ControladoraIdioma idioma;
    
    public IOExportaJAR() {
        idioma = ControladoraIdioma.getInstancia();
    }
    
    public void criarJAR(Partida partida) throws IOException {
        if (new File("geraJar").exists()) { // Verifica se o diretório existe.
            IOProjetoPartida iop = new IOProjetoPartida();
            boolean salvou = iop.SalvarJarParaExportacao(partida);
            if (salvou) {
                ParametrosArquivo pa = iop.selecionadorDeArquivos(3);
                if (pa.isArquivoSelecionado()) {
                    try {
                        String diretorio = pa.getPatchDoArquivo();
                        String finalNome = diretorio.substring(diretorio.length() - 4, diretorio.length());
                        if (!(finalNome.equalsIgnoreCase(".jar"))) {
                            diretorio = diretorio.concat(".jar");
                        }
                        JarOutputStream target = new JarOutputStream(new FileOutputStream(diretorio));
                        add(new File("geraJar"), target);
                        target.close();
                        JOptionPane.showMessageDialog(null, idioma.Valor("msgExportacaoSucesso"), idioma.Valor("aviso"), JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception e) {
                        String mensagem = "<html><center>" + idioma.Valor("msgExportacaoFalha") + "<br>Erro:" + e.getMessage();
                        JOptionPane.showMessageDialog(null, mensagem, idioma.Valor("aviso"), JOptionPane.OK_OPTION);
                    }
                }
            }
        }
    }

    private void add(File source, JarOutputStream target) throws IOException {
        BufferedInputStream in = null;
        try {
            if (source.isDirectory()) {
                for (File nestedFile : source.listFiles()) {
                    add(nestedFile, target);
                }
                return;
            }
            JarEntry entry = new JarEntry(source.getPath().replace("\\", "/").substring("geraJar".length() + 1));
            entry.setTime(source.lastModified());
            target.putNextEntry(entry);
            in = new BufferedInputStream(new FileInputStream(source));
            byte[] buffer = new byte[1024];
            while (true) {
                int count = in.read(buffer);
                if (count == -1) {
                    break;
                }
                target.write(buffer, 0, count);
            }
            target.closeEntry();
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

}
