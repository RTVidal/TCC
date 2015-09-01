/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Suporte;

import Controle.ControladoraIdioma;
import Modelo.Acao;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Rafael
 */
public class AcoesTbModel extends AbstractTableModel {
    
    private final ControladoraIdioma idioma;

    private static final int COL_ACAO = 0;
    private static final int COL_VALIDA = 1;
    private static final int COL_OPERACAO = 2;
    private static final int COL_VALOR = 3;
    private static final int COL_ABORTARNEGATIVO = 4;

    private final ArrayList<Acao> acoes;

    public AcoesTbModel(ArrayList<Acao> situacoes) {
        this.acoes = new ArrayList<>(situacoes);

        idioma = ControladoraIdioma.getInstancia();

    }

    @Override
    public int getRowCount() {
        return acoes.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int column) {
        //Qual é o nome das nossas colunas?  
        if (column == COL_VALIDA) {
            return idioma.Valor("lblValida");
        }
        if (column == COL_OPERACAO) {
            return idioma.Valor("lblOperacao");
        }
        if (column == COL_VALOR) {
            return idioma.Valor("lblValor");
        }
        if (column == COL_ABORTARNEGATIVO) {
            return idioma.Valor("lblAbortarNegativo");
        }
        return ""; //Nunca deve ocorrer
    }

    @Override
    public Object getValueAt(int rowIndex, int column) {

        if (column == COL_ACAO) {
            return acoes.get(rowIndex);
        }
        if (column == COL_VALIDA) {
            if (acoes.get(rowIndex).isValida())
            {
                return idioma.Valor("lblSim");
            } else {
                return idioma.Valor("lblNao");
            }
        }
        if (column == COL_OPERACAO) {
            switch(acoes.get(rowIndex).getOperacao())
            {
                case 1:
                    return idioma.Valor("lblAdicao");
                case 2:
                    return idioma.Valor("lblSubtracao");
                case 3:
                    return idioma.Valor("lblMultiplicacao");
                case 4:
                    return idioma.Valor("lblDivisao");
            }
        }
        if (column == COL_ABORTARNEGATIVO) {
            if(acoes.get(rowIndex).isAbortarJogoSeNegativo())
            {
                return idioma.Valor("lblSim");
            } else {
                return idioma.Valor("lblNao");
            }
        }
        return "";

    }
    
}
