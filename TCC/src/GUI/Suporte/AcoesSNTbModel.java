/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Suporte;

import Modelo.AcaoSaidaNumerica;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Rafael
 */
public class AcoesSNTbModel extends AbstractTableModel{
    
    private static final int COL_SAIDANUMERICA = 0;
    private static final int COL_VALORINICIAL = 1;
    private static final int COL_VALORFINAL = 2;
    private static final int COL_DESTINO = 3;
    
    
    private ArrayList<AcaoSaidaNumerica> acoesSaidaNumerica;
    
    public AcoesSNTbModel(ArrayList<AcaoSaidaNumerica> saidas)
    {
        this.acoesSaidaNumerica = new ArrayList<AcaoSaidaNumerica>(saidas);
    }
    
    @Override
    public int getRowCount() {
        return acoesSaidaNumerica.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
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
        
        if (column == COL_SAIDANUMERICA) return acoesSaidaNumerica.get(rowIndex);
        if (column == COL_VALORINICIAL) return acoesSaidaNumerica.get(rowIndex).getFaixa().getLimiteInferior();
        if (column == COL_VALORFINAL) return acoesSaidaNumerica.get(rowIndex).getFaixa().getLimiteSuperior();
        if (column == COL_DESTINO) return acoesSaidaNumerica.get(rowIndex).getSituacaoDestino().getNome();
        return "";
        
    }
    
}
