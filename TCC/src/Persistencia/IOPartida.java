/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Modelo.Situacao;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Rafael
 */
public class IOPartida {

    public void SalvaSituacao(Situacao situacao) {
        try {
            FileOutputStream arquivoGrav = new FileOutputStream("./Arquivos/situacao.fje", true);
            
            ObjectOutputStream objGravar = new ObjectOutputStream(arquivoGrav);

            objGravar.writeObject(situacao);

            objGravar.flush();

            objGravar.close();

            arquivoGrav.flush();

            arquivoGrav.close();


            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public Situacao LeSituacao()
    {
        Situacao situacao = new Situacao();
        try
        {
            FileInputStream arquivoLeitura = new FileInputStream("./Arquivos/situacao.fje");

            //Classe responsavel por recuperar os objetos do arquivo
            ObjectInputStream objLeitura = new ObjectInputStream(arquivoLeitura);
            
            situacao = (Situacao)(objLeitura.readObject());

            System.out.println("Recuperou " + situacao.getFalaAssistente());
            objLeitura.close();

            arquivoLeitura.close();
            
            

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return situacao;
    }
}
