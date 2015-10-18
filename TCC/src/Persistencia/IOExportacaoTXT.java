/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Controle.ControladoraIdioma;
import Modelo.Caminho;
import Modelo.ParametrosArquivo;
import Modelo.Partida;
import Modelo.Variavel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Rafael
 */
public class IOExportacaoTXT {

    ControladoraIdioma idioma;

    public IOExportacaoTXT() {
        idioma = ControladoraIdioma.getInstancia();
    }

    public void CriarTXT() {

        Partida partida = Partida.getInstancia();

    }

    public void SalvarTXT() {
        try {

            Partida partida = Partida.getInstancia();

            IOPartida iop = new IOPartida();
            ParametrosArquivo pa = iop.selecionadorDeArquivos(4);

            if (pa.isArquivoSelecionado()) {

                String diretorio = pa.getPatchDoArquivo();
                String finalNome = diretorio.substring(diretorio.length() - 4, diretorio.length());
                FileOutputStream arquivoGrav;

                if (finalNome.equalsIgnoreCase(".txt")) {
                    File arquivo = new File(diretorio);
                    arquivo.delete();
                    arquivoGrav = new FileOutputStream(diretorio, true);
                } else {
                    File arquivo = new File(diretorio + ".txt");
                    arquivo.delete();
                    arquivoGrav = new FileOutputStream(diretorio + ".txt", true);
                }

                FileWriter arq = new FileWriter(diretorio + ".txt");
                PrintWriter gravarArq = new PrintWriter(arq);

                gravarArq.printf("---- " + idioma.Valor("lblVariaveis") + " ----%n");

                //Separa as variáveis auto-definidas das padrões para que as auto definidas sejam exibidas ao fim da tabela
                ArrayList<Variavel> padroes = new ArrayList<>();
                ArrayList<Variavel> autodefinidas = new ArrayList<>();

                for (Variavel v : partida.getVariaveis()) {
                    if (v.isAutodefinida()) {
                        autodefinidas.add(v);
                    } else {
                        padroes.add(v);
                    }
                }

                if (!padroes.isEmpty()) {
                    
                    gravarArq.printf("-- " + idioma.Valor("lblVariaveisPadroes") + " --%n");
                    
                    for (Variavel v : padroes) {
                        gravarArq.printf(v.getNome());
                        gravarArq.printf(idioma.Valor("lblValorInicial") + ": " + v.getValorInicial() + "%n");
                        gravarArq.printf(idioma.Valor("lblValorFinal") + ": " + v.getValor() + "%n");
                    }
                }
                
                if (!autodefinidas.isEmpty()) {
                    
                    gravarArq.printf("-- " + idioma.Valor("lblVariaveisAutoDefinidas") + " --%n");
                    
                    for (Variavel v : autodefinidas) {
                        gravarArq.printf("%n");
                        gravarArq.printf(v.getNome());
                        gravarArq.printf(idioma.Valor("lblValorInicial") + ": " + v.getValorInicial() + "%n");
                        gravarArq.printf(idioma.Valor("lblValorFinal") + ": " + v.getValor() + "%n");
                    }
                }
                
                gravarArq.printf("%n%n");
                
                //Salva o caminho
                if(!partida.getCaminhos().isEmpty())
                {
                    gravarArq.printf("-- " + idioma.Valor("lblCaminho") + " --%n");
                    for(Caminho c : partida.getCaminhos())
                    {
                        gravarArq.printf("%n");
                        gravarArq.printf(idioma.Valor("lblSituacao") + ": " + c.getSituacao() + "%n");
                        gravarArq.printf(idioma.Valor("lblEscolha") + ": " + c.getEscolha() + "%n");
                    }
                }


                arq.close();
            }

        } catch (Exception e) {

        }
    }

}
