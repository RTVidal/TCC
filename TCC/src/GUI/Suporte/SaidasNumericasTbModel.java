/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Suporte;

import Modelo.SaidaNumerica;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Rafael
 */
public class SaidasNumericasTbModel extends AbstractTableModel{
    
    private static final int COL_SAIDANUMERICA = 0;
    private static final int COL_VALORINICIAL = 1;
    private static final int COL_VALORFINAL = 2;
    private static final int COL_DESTINO = 3;
    
    
    private ArrayList<SaidaNumerica> saidasNumericas;
    
    public SaidasNumericasTbModel(ArrayList<SaidaNumerica> saidas)
    {
        this.saidasNumericas = new ArrayList<SaidaNumerica>(saidas);
    }
    
    @Override
    public int getRowCount() {
        return saidasNumericas.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }
    
    public String getColumnName(int column) {  
        //Qual é o nome das nossas colunas?  
        if (column == COL_VALORINICIAL) return "Valor inicial";
        if (column == COL_VALORFINAL) return "Valor final";
        if (column == COL_DESTINO) return "Situação Destino";
        return ""; //Nunca deve ocorrer
    }  

    @Override
    public Object getValueAt(int rowIndex, int column) {
        
        if (column == COL_SAIDANUMERICA) return saidasNumericas.get(rowIndex);
        if (column == COL_VALORINICIAL) return saidasNumericas.get(rowIndex).getFaixa().getLimiteInferior().toString();
        if (column == COL_VALORFINAL) return saidasNumericas.get(rowIndex).getFaixa().getLimiteSuperior().toString();
        if (column == COL_DESTINO) return saidasNumericas.get(rowIndex).getSituacaoDestino().getNome();
        return "";
        
    }
    
}
