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
public class AcoesDefaultSNTbModel extends AbstractTableModel {
    
    private static final int COL_ACAOSAIDANUMERICA = 0;
    private static final int COL_TIPO = 1;
    private static final int COL_DESTINO = 2;
    
    
    private ArrayList<AcaoSaidaNumerica> acoesSaidaNumerica;
    
    public AcoesDefaultSNTbModel(ArrayList<AcaoSaidaNumerica> saidas)
    {
        this.acoesSaidaNumerica = new ArrayList<AcaoSaidaNumerica>(saidas);
    }
    
    @Override
    public int getRowCount() {
        return acoesSaidaNumerica.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }
    
    public String getColumnName(int column) {
        //Qual é o nome das nossas colunas?  
        if (column == COL_TIPO) return "Tipo";
        if (column == COL_DESTINO) return "Situação Destino";
        return ""; //Nunca deve ocorrer
    }  

    @Override
    public Object getValueAt(int rowIndex, int column) {
        
        if (column == COL_ACAOSAIDANUMERICA) return acoesSaidaNumerica.get(rowIndex);
        if (column == COL_TIPO) return acoesSaidaNumerica.get(rowIndex).getDescricao();
        if (column == COL_DESTINO) return acoesSaidaNumerica.get(rowIndex).getSituacaoDestino().getNome();
        return "";
        
    }
    
}
