/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Modelo.Partida;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Rafael
 */
public class IOPartida {

    private Partida partidaDesenvolvimento;

    public IOPartida() {
        partidaDesenvolvimento = Partida.getInstancia();
    }

    public void SalvaPartida(String diretorio) {
        try {
            
//            //Converte a imagem de fundo para array de bytes
//            for(Situacao s : partidaDesenvolvimento.getSituacoes())
//            {
//                BufferedImage buffered = (BufferedImage) s.getFundoSituacao();
//                byte[] imgByte = imageToByteArray(buffered);
//                s.setImgFundoByte(imgByte);
//            }

            FileOutputStream arquivoGrav = new FileOutputStream(diretorio + ".tcc", true);

            ObjectOutputStream objGravar = new ObjectOutputStream(arquivoGrav);

            objGravar.writeObject(partidaDesenvolvimento);

            objGravar.flush();

            objGravar.close();

            arquivoGrav.flush();

            arquivoGrav.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Partida LePartida() {

        Partida partida = new Partida();

        try {

            String diretorio = "";

            JFileChooser jFileChooser = new JFileChooser();

            //Selecionar apenas arquivos
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

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

            //mostra janela para abrir
            int acao = jFileChooser.showOpenDialog(null);

            //executa acao conforme opcao selecionada
            if (acao == JFileChooser.APPROVE_OPTION) {
                //escolheu arquivo

                diretorio = jFileChooser.getSelectedFile().getAbsolutePath();

                System.out.println("abrir " + diretorio);
                FileInputStream arquivoLeitura = new FileInputStream(diretorio);

                //Classe responsavel por recuperar os objetos do arquivo
                ObjectInputStream objLeitura = new ObjectInputStream(arquivoLeitura);

                partida = (Partida) (objLeitura.readObject());

                objLeitura.close();

                arquivoLeitura.close();

            } else if (acao == JFileChooser.CANCEL_OPTION) {
                //apertou botao cancelar
            } else if (acao == JFileChooser.ERROR_OPTION) {
                //outra opcao
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return partida;
    }

    public static byte[] imageToByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}

//public void SalvaSituacao(Situacao situacao) {
//        try {
//            FileOutputStream arquivoGrav = new FileOutputStream("./Arquivos/situacao.fje", true);
//            
//            ObjectOutputStream objGravar = new ObjectOutputStream(arquivoGrav);
//
//            objGravar.writeObject(situacao);
//
//            objGravar.flush();
//
//            objGravar.close();
//
//            arquivoGrav.flush();
//
//            arquivoGrav.close();
//
//
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    public Situacao LeSituacao()
//    {
//        Situacao situacao = new Situacao();
//        try
//        {
//            FileInputStream arquivoLeitura = new FileInputStream("./Arquivos/situacao.fje");
//
//            //Classe responsavel por recuperar os objetos do arquivo
//            ObjectInputStream objLeitura = new ObjectInputStream(arquivoLeitura);
//            
//            situacao = (Situacao)(objLeitura.readObject());
//
//            System.out.println("Recuperou " + situacao.getFalaAssistente());
//            objLeitura.close();
//
//            arquivoLeitura.close();
//            
//            
//
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        
//        return situacao;
//    }
//}
