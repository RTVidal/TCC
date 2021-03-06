/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Suporte;

import Controle.ControladoraIdioma;
import Modelo.Variavel;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Rafael
 */
public class VariaveisTbModel extends AbstractTableModel{
    
    private final ControladoraIdioma idioma;

    private static final int COL_VARIAVEL = 0;
    private static final int COL_NOME = 1;
    private static final int COL_VALOR = 2;
    private static final int COL_OCULTA = 3;
    private static final int COL_TIPO = 4;

    private final ArrayList<Variavel> variaveis;

    public VariaveisTbModel(ArrayList<Variavel> situacoes) {
        this.variaveis = new ArrayList<>(situacoes);

        idioma = ControladoraIdioma.getInstancia();

    }

    @Override
    public int getRowCount() {
        return variaveis.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int column) {
        //Qual é o nome das nossas colunas?  
        if (column == COL_NOME) {
            return idioma.Valor("lblNome");
        }
        if (column == COL_VALOR) {
            return idioma.Valor("lblValorInicial");
        }
        if (column == COL_OCULTA) {
            return idioma.Valor("lblOculta");
        }
        if (column == COL_TIPO) {
            return idioma.Valor("lblTipo");
        }
        return ""; //Nunca deve ocorrer
    }

    @Override
    public Object getValueAt(int rowIndex, int column) {

        if (column == COL_VARIAVEL) {
            return variaveis.get(rowIndex);
        }
        if (column == COL_NOME) {
            return variaveis.get(rowIndex).getNome();
        }
        if (column == COL_VALOR) {
            return String.valueOf(variaveis.get(rowIndex).getValorInicial());
        }
        if (column == COL_OCULTA) {
            if(variaveis.get(rowIndex).isOculta())
            {
                return "✓";
            } else {
                return "";
            }
        }
        if (column == COL_TIPO) {
            if(variaveis.get(rowIndex).isAutodefinida())
            {
                return idioma.Valor("lblAutoDefinida");
            } else {
                return idioma.Valor("lblPadrao");
            }
        }
        return "";

    }
}
