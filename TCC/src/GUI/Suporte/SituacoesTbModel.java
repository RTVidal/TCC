/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Suporte;

import Controle.ControladoraIdioma;
import Modelo.Situacao;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Rafael
 */
public class SituacoesTbModel extends AbstractTableModel {
    
    private final ControladoraIdioma idioma;

    private static final int COL_SITUACAO = 0;
    private static final int COL_ORDEM = 1;
    private static final int COL_NOME = 2;
    private static final int COL_TIPOSAIDA = 3;
    private static final int COL_SITUACAOFINAL = 4;

    private final ArrayList<Situacao> situacoes;

    public SituacoesTbModel(ArrayList<Situacao> situacoes) {
        this.situacoes = new ArrayList<>(situacoes);

        idioma = ControladoraIdioma.getInstancia();

    }

    @Override
    public int getRowCount() {
        return situacoes.size();
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
        if (column == COL_TIPOSAIDA) {
            return idioma.Valor("lblTipoSaida");
        }
        if (column == COL_SITUACAOFINAL) {
            return idioma.Valor("lblDetalhe");
        }
        return ""; //Nunca deve ocorrer
    }

    @Override
    public Object getValueAt(int rowIndex, int column) {

        if (column == COL_SITUACAO) {
            return situacoes.get(rowIndex);
        }
        if (column == COL_ORDEM)
        {
            return rowIndex + 1;
        }
        if (column == COL_NOME) {
            return situacoes.get(rowIndex).getNome();
        }
        if (column == COL_TIPOSAIDA) {
            switch(situacoes.get(rowIndex).getSaida().getTipoSaida())
            {
                case 1:
                    return idioma.Valor("lblOpcional");
                case 2:
                    return idioma.Valor("lblNumerica");
            }
        }
        if (column == COL_SITUACAOFINAL) {
            if(situacoes.get(rowIndex).isSituacaoFinal())
            {
                return "✓";
            }
            return "-";
        }
        return "";

    }
    
}
