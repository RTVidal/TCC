/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Suporte;

import Controle.ControladoraIdioma;
import Modelo.SaidaOpcional;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Rafael
 */
public class SaidasOpcionaisTbModel extends AbstractTableModel {

    private final ControladoraIdioma idioma;

    private static final int COL_SAIDA = 0;
    private static final int COL_NOME = 1;
    private static final int COL_DESTINO = 2;

    private ArrayList<SaidaOpcional> saidas;

    public SaidasOpcionaisTbModel(ArrayList<SaidaOpcional> saidas) {
        this.saidas = new ArrayList<SaidaOpcional>(saidas);

        idioma = ControladoraIdioma.getInstancia();

    }

    @Override
    public int getRowCount() {
        return saidas.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    public String getColumnName(int column) {
        //Qual é o nome das nossas colunas?  
        if (column == COL_NOME) {
            return "Nome";
        }
        return ""; //Nunca deve ocorrer
    }

    @Override
    public Object getValueAt(int rowIndex, int column) {

        if (column == COL_SAIDA) {
            return saidas.get(rowIndex);
        }
        if (column == COL_NOME) {
            return saidas.get(rowIndex).getNome();
        }
        if (column == COL_DESTINO) {
            return saidas.get(rowIndex).getSituacaoDestino().getNome();
        }
        return "";

    }

}
