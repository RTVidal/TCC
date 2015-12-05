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
public class VariaveisResultadoTbModel extends AbstractTableModel{
    
    private final ControladoraIdioma idioma;

    private static final int COL_VARIAVEL = 0;
    private static final int COL_NOME = 1;
    private static final int COL_VALORINICIAL = 2;
    private static final int COL_VALORFINAL = 3;
    private static final int COL_TIPO = 4;
    private static final int COL_DETALHE = 5;

    private final ArrayList<Variavel> variaveis;

    public VariaveisResultadoTbModel(ArrayList<Variavel> situacoes) {
        this.variaveis = new ArrayList<>(situacoes);

        idioma = ControladoraIdioma.getInstancia();

    }

    @Override
    public int getRowCount() {
        return variaveis.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public String getColumnName(int column) {
        //Qual é o nome das nossas colunas?  
        if (column == COL_NOME) {
            return idioma.Valor("lblNome");
        }
        if (column == COL_VALORINICIAL) {
            return idioma.Valor("lblValorInicial");
        }
        if (column == COL_VALORFINAL) {
            return idioma.Valor("lblValorFinal");
        }
        if (column == COL_TIPO) {
            return idioma.Valor("lblTipo");
        }
        if (column == COL_TIPO) {
            return idioma.Valor("lblTipo");
        }
        if (column == COL_DETALHE) {
            return idioma.Valor("lblDetalhe");
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
        if (column == COL_VALORINICIAL) {
            return String.valueOf(variaveis.get(rowIndex).getValorInicial());
        }
        if (column == COL_VALORFINAL) {
            return String.valueOf(variaveis.get(rowIndex).getValor());
        }
        if (column == COL_TIPO) {
            if(variaveis.get(rowIndex).isAutodefinida())
            {
                return idioma.Valor("lblAutoDefinida");
            } else {
                return idioma.Valor("lblPadrao");
            }
        }
        if (column == COL_DETALHE) {
            if ((variaveis.get(rowIndex).isAutodefinida()) && (variaveis.get(rowIndex).getValor() == 1))
            {
                return idioma.Valor("msgRespostaJogador");
            }            
        }
        return "";

    }
}
