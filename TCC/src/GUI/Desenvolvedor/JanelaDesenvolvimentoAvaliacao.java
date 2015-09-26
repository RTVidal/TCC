/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Desenvolvedor;

import Controle.ControladoraIdioma;
import Modelo.Avaliacao;
import Modelo.Partida;
import Modelo.Variavel;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Rafael
 */
public class JanelaDesenvolvimentoAvaliacao extends javax.swing.JFrame {

    private final JanelaDesenvolvimentoPartida janelaDevPartida;
    private final Avaliacao avaliacao;
    private final Partida partidaDesenvolvimento;
    private final int modo;
    private Variavel variavel;
    private final ControladoraIdioma idioma;

    /**
     * Creates new form JanelaDesenvolvimentoAvaliacao
     *
     * @param modo
     * @param avaliacao
     */
    public JanelaDesenvolvimentoAvaliacao(int modo, Avaliacao avaliacao) {
        initComponents();
        
        janelaDevPartida = JanelaDesenvolvimentoPartida.getInstancia();
        partidaDesenvolvimento = Partida.getInstancia();
        idioma = ControladoraIdioma.getInstancia();
        CarregaIdioma();

        setLocationRelativeTo(janelaDevPartida);

        this.avaliacao = avaliacao;
        this.modo = modo;

        if (modo == 2) {
            CarregarAvaliacao();
        }
        CarregarVariaveis();

    }

    /**
     * Preenche os componentes da tela de acordo com o idioma selecionado
     */
    public final void CarregaIdioma() {
        lblDescricao.setText(idioma.Valor("lblDescricao"));
        lblTxtAvaliacao.setText(idioma.Valor("lblTxtAvaliacao"));
        lblValorFinal.setText(idioma.Valor("lblValorFinal"));
        lblValorInicial.setText(idioma.Valor("lblValorInicial"));
        lblVariavel.setText(idioma.Valor("lblVariavel"));
        btnCancelar.setText(idioma.Valor("btnCancelar"));
        btnConfirmar.setText(idioma.Valor("btnConfirmar"));
        lblTitulo.setText(idioma.Valor("tituloDesenvAvaliacao"));
        this.setTitle(idioma.Valor("tituloDesenvAvaliacao"));
    }

    /**
     * Carrega os dados da avaliação
     */
    public final void CarregarAvaliacao() {
        txtDescricao.setText(avaliacao.getDescricao());
        jspValorInicial.setValue(avaliacao.getValorInicial());
        jspValorFinal.setValue(avaliacao.getValorFinal());
        txaTextoAvaliacao.setText(avaliacao.getTexto());
    }

    /**
     * Carrega o combo de variáveis
     */
    public final void CarregarVariaveis() {
        int index = 0;
        int itemSelecionado = 0;

        DefaultComboBoxModel model = new DefaultComboBoxModel();

        for (Variavel v : partidaDesenvolvimento.getVariaveis()) {
            model.addElement(v.getNome());

            if (v == avaliacao.getVariavel()) {
                itemSelecionado = index;
            }
            index++;
        }

        cbxVariavel.setModel(model);
        cbxVariavel.setSelectedIndex(itemSelecionado);
        variavel = partidaDesenvolvimento.getVariaveis().get(itemSelecionado);
    }

    public void SalvarAvaliacao() {

        boolean ok = ValidarDados();

        if (ok) {
            avaliacao.setDescricao(txtDescricao.getText());
            avaliacao.setValorInicial((double) jspValorInicial.getValue());
            avaliacao.setValorFinal((double) jspValorFinal.getValue());
            avaliacao.setVariavel(variavel);
            avaliacao.setTexto(txaTextoAvaliacao.getText());

            if (modo == 1) {
                partidaDesenvolvimento.getAvaliacoes().add(avaliacao);
            }

            janelaDevPartida.AtualizarDados();
            dispose();
        }

    }

    /**
     * Validação dos dados
     */
    public boolean ValidarDados() {
        boolean ok = true;
        ArrayList<String> mensagens = new ArrayList<>();
        String mensagem;

        if (txtDescricao.getText().isEmpty()) {
            ok = false;
            mensagem = idioma.Valor("msgDescricaoObrigatoria");
            mensagens.add(mensagem);

        }
        if (txaTextoAvaliacao.getText().isEmpty()) {
            ok = false;
            mensagem = idioma.Valor("msgTextoAvaliacaoObrigatorio");
            mensagens.add(mensagem);

        }

        double valorInicial = (double) jspValorInicial.getValue();
        double valorFinal = (double) jspValorFinal.getValue();

        if (valorInicial >= valorFinal) {
            ok = false;
            mensagem = idioma.Valor("msgValorInicialMenor");
            mensagens.add(mensagem);
        }

        //Exibe as mensagens
        if (!ok) {
            String mensagemJanela = "<html><center>";

            for (String s : mensagens) {
                mensagemJanela += s + "<br>";
            }

            JOptionPane.showMessageDialog(this, mensagemJanela, idioma.Valor("aviso"), JOptionPane.OK_OPTION);
        }

        return ok;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnConfirmar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        lblValorInicial = new javax.swing.JLabel();
        lblValorFinal = new javax.swing.JLabel();
        lblTxtAvaliacao = new javax.swing.JLabel();
        lblDescricao = new javax.swing.JLabel();
        lblVariavel = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JTextField();
        cbxVariavel = new javax.swing.JComboBox();
        jspValorInicial = new javax.swing.JSpinner();
        jspValorFinal = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaTextoAvaliacao = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btnConfirmar.setText("btnConfirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        btnCancelar.setText("btnCancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitulo.setText("lblTitulo");

        lblValorInicial.setText("lblValorInicial");

        lblValorFinal.setText("lvlValorFinal");

        lblTxtAvaliacao.setText("lblTxtAvaliacao");

        lblDescricao.setText("lblDescricao");

        lblVariavel.setText("lblVariavel");

        cbxVariavel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxVariavel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxVariavelActionPerformed(evt);
            }
        });

        jspValorInicial.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(1.0d)));

        jspValorFinal.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(1.0d)));

        txaTextoAvaliacao.setColumns(20);
        txaTextoAvaliacao.setRows(5);
        jScrollPane1.setViewportView(txaTextoAvaliacao);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addContainerGap(388, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnCancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnConfirmar))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblValorInicial)
                                    .addComponent(lblTxtAvaliacao)
                                    .addComponent(lblVariavel)
                                    .addComponent(lblDescricao))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jspValorInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblValorFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jspValorFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtDescricao)
                                    .addComponent(cbxVariavel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE))))
                        .addGap(0, 40, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDescricao)
                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVariavel)
                    .addComponent(cbxVariavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblValorInicial)
                    .addComponent(lblValorFinal)
                    .addComponent(jspValorInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jspValorFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTxtAvaliacao)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnConfirmar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed

        SalvarAvaliacao();

    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        dispose();

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void cbxVariavelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxVariavelActionPerformed

        int index = cbxVariavel.getSelectedIndex();

        variavel = partidaDesenvolvimento.getVariaveis().get(index);

    }//GEN-LAST:event_cbxVariavelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JComboBox cbxVariavel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jspValorFinal;
    private javax.swing.JSpinner jspValorInicial;
    private javax.swing.JLabel lblDescricao;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTxtAvaliacao;
    private javax.swing.JLabel lblValorFinal;
    private javax.swing.JLabel lblValorInicial;
    private javax.swing.JLabel lblVariavel;
    private javax.swing.JTextArea txaTextoAvaliacao;
    private javax.swing.JTextField txtDescricao;
    // End of variables declaration//GEN-END:variables
}
