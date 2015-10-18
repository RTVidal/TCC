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
    private static final int COL_SUBIR = 1;
    private static final int COL_DESCER = 2;
    private static final int COL_NOME = 3;
    private static final int COL_TIPOSAIDA = 4;
    private static final int COL_DETALHE = 5;

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
        return 6;
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
        if (column == COL_DETALHE) {
            return idioma.Valor("lblDetalhe");
        }
        return ""; //Nunca deve ocorrer
    }

    @Override
    public Object getValueAt(int rowIndex, int column) {

        if (column == COL_SITUACAO) {
            return situacoes.get(rowIndex);
        }
        if (column == COL_SUBIR) {
            if (rowIndex == 0) {
                return "";
            } else {
                return " ▲";
            }
        }
        if (column == COL_DESCER) {
            if (rowIndex == situacoes.size()-1) {
                return "";
            } else {
                return " ▼";
            }
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
        if (column == COL_DETALHE) {
            if(situacoes.get(rowIndex).isSituacaoFinal())
            {
                return idioma.Valor("lblSituacaoFinal");
            }
            if(rowIndex == 0)
            {
                return idioma.Valor("lblSituacaoInicial");
            }
            return "-";
        }
        return "";

    }
    
}
