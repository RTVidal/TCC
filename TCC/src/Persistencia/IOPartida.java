/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Controle.ControladoraIdioma;
import GUI.Desenvolvedor.JanelaDesenvolvimentoPartida;
import Modelo.Partida;
import Modelo.ParametrosArquivo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Rafael
 */
public class IOPartida {

    private final ControladoraIdioma idioma;
    private Object[] opcaoSimNaoCancelar;

    public IOPartida() {
        idioma = ControladoraIdioma.getInstancia();
        opcaoSimNaoCancelar = new Object[]{idioma.Valor("sim"), idioma.Valor("nao"), idioma.Valor("btnCancelar")};
    }

    public ParametrosArquivo selecionadorDeArquivos(boolean paraSalvar) {
        ParametrosArquivo pa = new ParametrosArquivo();
        JFileChooser jFileChooser = new JFileChooser();
        //Selecionar apenas arquivos
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //desabilita todos os tipos de arquivos
        jFileChooser.setAcceptAllFileFilterUsed(false);
        //filtra por extensao
        jFileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory() || file.getAbsolutePath().endsWith(".tcc");
            }

            @Override
            public String getDescription() {
                return idioma.Valor("descricaoArquivo") + " (*.tcc)";
            }
        });
        //mostra janela para de acordo com o parametro
        int acao;
        if (paraSalvar) {
            acao = jFileChooser.showSaveDialog(null);
        } else {
            acao = jFileChooser.showOpenDialog(null);
        }
        //executa acao conforme opcao selecionada
        if (acao == JFileChooser.APPROVE_OPTION) {
            String nome = jFileChooser.getSelectedFile().getName();
            pa.setPatchDoArquivo(jFileChooser.getSelectedFile().getAbsolutePath());
            pa.setArquivoSelecionado(true);
            String finalNome = nome.substring(nome.length() - 4, nome.length());
            if (finalNome.equalsIgnoreCase(".tcc")) {
                pa.setNomeDoArquivo(nome.substring(0, nome.length() - 4));
            } else {
                pa.setNomeDoArquivo(nome);
            }
        } else if (acao == JFileChooser.CANCEL_OPTION) {
            pa.setArquivoSelecionado(false);
        } else if (acao == JFileChooser.ERROR_OPTION) {
            pa.setArquivoSelecionado(false);
        }
        return pa;
    }

    public boolean SalvarDireto(Partida partidaSalvar) throws FileNotFoundException, IOException {
        if (partidaSalvar.getParametrosArquivo() == null) {
            return SalvarComo(partidaSalvar);
        } else {
            String diretorio = partidaSalvar.getParametrosArquivo().getPatchDoArquivo();
            String finalNome = diretorio.substring(diretorio.length() - 4, diretorio.length());
            FileOutputStream arquivoGrav;
            if (finalNome.equalsIgnoreCase(".tcc")) {
                File arquivo = new File(diretorio);
                arquivo.delete();
                arquivoGrav = new FileOutputStream(diretorio, true);
            } else {
                File arquivo = new File(diretorio + ".tcc");
                arquivo.delete();
                arquivoGrav = new FileOutputStream(diretorio + ".tcc", true);
            }

            GZIPOutputStream gz = new GZIPOutputStream(arquivoGrav);

            ObjectOutputStream objGravar = new ObjectOutputStream(gz);
            objGravar.writeObject(partidaSalvar);
            objGravar.flush();
            objGravar.close();
            arquivoGrav.flush();
            arquivoGrav.close();
            return true;
        }
    }

    public boolean SalvarComo(Partida partidaSalvar) throws FileNotFoundException, IOException {
        int selecao = 3;
        ParametrosArquivo pa = new ParametrosArquivo();
        for (;;) {
            pa = selecionadorDeArquivos(true);
            if (pa.isArquivoSelecionado()) {
                if (partidaSalvar.getParametrosArquivo() == null) {
                    selecao = 0;
                } else {
                    if (partidaSalvar.getParametrosArquivo().getPatchDoArquivo().equalsIgnoreCase(pa.getPatchDoArquivo())) {
                        selecao = JOptionPane.showOptionDialog(null, idioma.Valor("msgSubstituirArquivo"),
                                idioma.Valor("aviso"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                                null, opcaoSimNaoCancelar, opcaoSimNaoCancelar[0]);
                    } else {
                        selecao = 0;
                    }
                }
            } else {
                selecao = 2;
            }
            if (selecao == 0 || selecao == 2) {
                break;
            }
        }

        if (selecao == 0) {
            partidaSalvar.setParametrosArquivo(pa);
            String diretorio = partidaSalvar.getParametrosArquivo().getPatchDoArquivo();
            String finalNome = diretorio.substring(diretorio.length() - 4, diretorio.length());
            FileOutputStream arquivoGrav;
            if (finalNome.equalsIgnoreCase(".tcc")) {
                File arquivo = new File(diretorio);
                arquivo.delete();
                arquivoGrav = new FileOutputStream(diretorio, true);
            } else {
                File arquivo = new File(diretorio + ".tcc");
                arquivo.delete();
                arquivoGrav = new FileOutputStream(diretorio + ".tcc", true);
            }

            GZIPOutputStream gz = new GZIPOutputStream(arquivoGrav);

            ObjectOutputStream objGravar = new ObjectOutputStream(gz);
            objGravar.writeObject(partidaSalvar);
            objGravar.flush();
            objGravar.close();
            arquivoGrav.flush();
            arquivoGrav.close();
            Partida.setInstancia(partidaSalvar);
            JanelaDesenvolvimentoPartida jdp = JanelaDesenvolvimentoPartida.getInstancia();
            jdp.CarregaIdioma();
            jdp.AtualizarDados();
            return true;
        } else {
            return false;
        }
    }

    public Partida LePartida() {
        ParametrosArquivo pa = selecionadorDeArquivos(false);
        Partida partida;
        try {
            if (pa.isArquivoSelecionado()) {
                FileInputStream arquivoLeitura = new FileInputStream(pa.getPatchDoArquivo());

                GZIPInputStream gzi = new GZIPInputStream(arquivoLeitura);

                //Classe responsavel por recuperar os objetos do arquivo
                ObjectInputStream objLeitura = new ObjectInputStream(gzi);

                partida = (Partida) (objLeitura.readObject());
                objLeitura.close();
                arquivoLeitura.close();
                partida.setParametrosArquivo(pa);
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

    public boolean SalvarParaExportacao(Partida partidaSalvar) throws FileNotFoundException, IOException {
        File pasta = new File("geraJar/Arquivos");
        if (!pasta.exists()) {
            pasta.mkdir();
        }
        String diretorio = "geraJar/Arquivos/proj.tcc";
        File arquivo = new File(diretorio);
        if (arquivo.exists()) {
            arquivo.delete();
        }
        FileOutputStream arquivoGrav = new FileOutputStream(diretorio, true);
        GZIPOutputStream gz = new GZIPOutputStream(arquivoGrav);
        ObjectOutputStream objGravar = new ObjectOutputStream(gz);
        objGravar.writeObject(partidaSalvar);
        objGravar.flush();
        objGravar.close();
        arquivoGrav.flush();
        arquivoGrav.close();
        return true;
    }

}
