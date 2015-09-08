/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Suporte;

import Controle.ControladoraIdioma;
import Modelo.Avaliacao;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Rafael
 */
public class AvaliacoesTbModel extends AbstractTableModel {
    
    private final ControladoraIdioma idioma;

    private static final int COL_AVALIACAO = 0;
    private static final int COL_DESCRICAO = 1;
    private static final int COL_VARIAVEL = 2;
    private static final int COL_VALORINICIAL = 3;
    private static final int COL_VALORFINAL = 4;

    private final ArrayList<Avaliacao> avaliacoes;

    public AvaliacoesTbModel(ArrayList<Avaliacao> situacoes) {
        this.avaliacoes = new ArrayList<>(situacoes);

        idioma = ControladoraIdioma.getInstancia();

    }

    @Override
    public int getRowCount() {
        return avaliacoes.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int column) {
        //Qual é o nome das nossas colunas?
        if (column == COL_DESCRICAO) {
            return idioma.Valor("lblDescricao");
        }        
        if (column == COL_VARIAVEL) {
            return idioma.Valor("lblVariavel");
        }
        if (column == COL_VALORINICIAL) {
            return idioma.Valor("lblValorInicial");
        }
        if (column == COL_VALORFINAL) {
            return idioma.Valor("lblValorFinal");
        }
        return ""; //Nunca deve ocorrer
    }

    @Override
    public Object getValueAt(int rowIndex, int column) {

        if (column == COL_AVALIACAO) {
            return avaliacoes.get(rowIndex);
        }
        if (column == COL_DESCRICAO) {
            return avaliacoes.get(rowIndex).getDescricao();
        }
        if (column == COL_VARIAVEL) {
            return avaliacoes.get(rowIndex).getVariavel().getNome();
        }
        
        if (column == COL_VALORINICIAL) {
            return String.valueOf(avaliacoes.get(rowIndex).getValorInicial());
        }
        
        if (column == COL_VALORFINAL) {
            return String.valueOf(avaliacoes.get(rowIndex).getValorFinal());
        }
        return "";

    }
    
}
