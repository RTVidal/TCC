/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Suporte;

import Controle.ControladoraIdioma;
import Modelo.Caminho;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Rafael
 */
public class CaminhosTbModel extends AbstractTableModel {
    
    private final ControladoraIdioma idioma;

    private static final int COL_MOMENTO = 0;
    private static final int COL_SITUACAO = 1;
    private static final int COL_ESCOLHA = 2;

    private final ArrayList<Caminho> caminhos;

    public CaminhosTbModel(ArrayList<Caminho> caminhos) {
        this.caminhos = new ArrayList<>(caminhos);

        idioma = ControladoraIdioma.getInstancia();

    }

    @Override
    public int getRowCount() {
        return caminhos.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int column) {
        //Qual é o nome das nossas colunas?
        if (column == COL_MOMENTO) {
            return idioma.Valor("lblDataHora");
        }
        if (column == COL_SITUACAO) {
            return idioma.Valor("lblSituacao");
        }        
        if (column == COL_ESCOLHA) {
            return idioma.Valor("lblEscolha");
        }
        return ""; //Nunca deve ocorrer
    }

    @Override
    public Object getValueAt(int rowIndex, int column) {

        if (column == COL_MOMENTO) {            
        
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            
        return fmt.format(caminhos.get(rowIndex).getHora());
        
        }
        if (column == COL_SITUACAO) {
            return caminhos.get(rowIndex).getSituacao();
        }
        
        if (column == COL_ESCOLHA) {
            return caminhos.get(rowIndex).getEscolha();
        }
        
        return "";

    }
    
}