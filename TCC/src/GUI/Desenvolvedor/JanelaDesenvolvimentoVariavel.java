/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Desenvolvedor;

import Modelo.Acao;
import Modelo.Partida;
import Modelo.SaidaOpcional;
import Modelo.Situacao;
import Modelo.Variavel;
import java.util.ArrayList;

/**
 *
 * @author Rafael
 */
public class JanelaDesenvolvimentoVariavel extends javax.swing.JFrame {

    private final Partida partidaDesenvolvimento;

    private final JanelaDesenvolvimentoPartida janelaDesenvolvimento;
    private final int modo;
    private final Variavel variavel;

    /**
     * Creates new form JanelaDesenvolvimentoVariavel
     *
     * @param modo
     * @param variavel
     */
    public JanelaDesenvolvimentoVariavel(int modo, Variavel variavel) {
        initComponents();

        partidaDesenvolvimento = Partida.getInstancia();

        janelaDesenvolvimento = JanelaDesenvolvimentoPartida.getInstancia();

        setLocationRelativeTo(janelaDesenvolvimento);

        this.variavel = variavel;
        this.modo = modo;

        //Caso o modo seja edição, preenche os campos
        if (modo == 2) {
            CarregarVariavel();
        }
    }

    public final void CarregarVariavel() {
        txtNome.setText(variavel.getNome());
        jspValorInicial.setValue(variavel.getValorInicial());
        chbOculta.setSelected(variavel.isOculta());
    }

    public void SalvarVariavel() {
        variavel.setNome(txtNome.getText());
        variavel.setValorInicial((double) jspValorInicial.getValue());
        variavel.setOculta(chbOculta.isSelected());

        //Caso o modo seja inserir, adiciona a variável à partida
        if (modo == 1) {
            partidaDesenvolvimento.getVariaveis().add(variavel);
        }
        
        CriarAcoesVariavel();

        janelaDesenvolvimento.AtualizaVariaveis();

        dispose();
    }

    /**
     * Pré-criar as ações para a variável em cada saída
     */
    public void CriarAcoesVariavel() {
        for (Situacao situacao : partidaDesenvolvimento.getSituacoes()) {
            if (situacao.getSaida().getTipoSaida() == 1) {
                
                ArrayList<SaidaOpcional> saidas = situacao.getSaida().getSaidasOpcao();
                
                for(SaidaOpcional saida : saidas)
                {
                    Acao acao = new Acao();
                    acao.setVariavel(variavel);
                    acao.setNumero(0);
                    acao.setAbortarJogoSeNegativo(false);
                    acao.setOperacao(0);
                    
                    saida.getAcoes().add(acao);
                }

            } else {

            }

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        chbOculta = new javax.swing.JCheckBox();
        btnCancelar = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();
        txtNome = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jspValorInicial = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("lblNome");

        jLabel2.setText("lblValorInicial");

        chbOculta.setText("lblOculta");

        btnCancelar.setText("btnCancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnConfirmar.setText("btnConfirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        txtNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeActionPerformed(evt);
            }
        });

        jButton3.setText("btnAjuda");

        jspValorInicial.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), null, null, Double.valueOf(1.0d)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(chbOculta)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jspValorInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jspValorInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chbOculta)
                    .addComponent(jButton3))
                .addGap(66, 66, 66)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirmar)
                    .addComponent(btnCancelar))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed

        SalvarVariavel();

    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void txtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        dispose();

    }//GEN-LAST:event_btnCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JCheckBox chbOculta;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSpinner jspValorInicial;
    private javax.swing.JTextField txtNome;
    // End of variables declaration//GEN-END:variables
}
