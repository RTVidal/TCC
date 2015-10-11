/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

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

    public void criarJAR(Partida partida) throws IOException {
        if (new File("geraJar").exists()) { // Verifica se o diretório existe.
            IOPartida iop = new IOPartida();
            boolean salvou = iop.SalvarParaExportacao(partida);
            if (salvou) {
                try {
                    String nomeLocalArquivo = partida.getParametrosArquivo().getPatchDoArquivo().replaceAll(".tcc", ".jar");
                    JarOutputStream target = new JarOutputStream(new FileOutputStream(nomeLocalArquivo));
                    add(new File("geraJar"), target);
                    target.close();
                    JOptionPane.showMessageDialog(null, "Sucesso");
                } catch (IOException io) {
                    JOptionPane.showMessageDialog(null, "erro");
                    io.printStackTrace();
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
