/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Suporte;

import Modelo.Saida;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Rafael
 */
public class SaidasTableModel extends AbstractTableModel{
    
    private static final int COL_SAIDA = 0;
    private static final int COL_NOME = 1;  
    private static final int COL_ORIGEM = 2;
    private static final int COL_DESTINO = 3;  
    private static final int COL_TIPO = 4;
    
    
    private ArrayList<Saida> saidas;
    
    public SaidasTableModel(ArrayList<Saida> saidas)
    {
        this.saidas = new ArrayList<Saida>(saidas);
    }
    
    @Override
    public int getRowCount() {
        return saidas.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }
    
    public String getColumnName(int column) {  
        //Qual é o nome das nossas colunas?  
        if (column == COL_NOME) return "Nome";
        if (column == COL_ORIGEM) return "Situação Origem";
        if (column == COL_DESTINO) return "Situação Destino";
        if (column == COL_TIPO) return "Tipo";
        return ""; //Nunca deve ocorrer
    }  

    @Override
    public Object getValueAt(int rowIndex, int column) {
        
        if (column == COL_SAIDA) return saidas.get(rowIndex);
        if (column == COL_NOME) return saidas.get(rowIndex).getNome();
        if (column == COL_ORIGEM) return saidas.get(rowIndex).getSituacaoOrigem().getNome();
        if (column == COL_DESTINO) return saidas.get(rowIndex).getSituacaoDestino().getNome();
        if (column == COL_TIPO) return "";
        return "";
        
    }
    
}
