/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Desenvolvedor;

import Modelo.Partida;
import Modelo.SaidaOpcao;
import Modelo.Situacao;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Rafael
 */
public class JanelaDesenvolvimentoSaida extends javax.swing.JFrame {

    /**
     * Creates new form JanelaDesenvolvimentoSaida
     */
    private int acao;
    private final JanelaDesenvolvimentoPartida jdp;
    private final Partida partidaDesenvolvimento;
    private SaidaOpcao saida;
    private Situacao situacaoOrigem;
    private Situacao situacaoDestino;
    
    public JanelaDesenvolvimentoSaida(int acao, SaidaOpcao saida) {
        initComponents();
        
        jdp = JanelaDesenvolvimentoPartida.getInstancia();        
        
        setLocationRelativeTo(jdp);
        
        partidaDesenvolvimento = Partida.getInstancia();
        
        this.acao = acao;
        
        if(acao == 1)
        {
            this.saida = new SaidaOpcao();
        } else
        {
            this.saida = saida;
            CarregaSaida();
        }
        
        situacaoOrigem = new Situacao();
        situacaoDestino = new Situacao();
        
        PreencheListaSituacoes();
    }
    
    public final void CarregaSaida()
    {
        txtNome.setText(saida.getNome());
        txaFalaAssistente.setText(saida.getFalaAssistente());
    }
    
    public final void PreencheListaSituacoes() {

        Vector comboBoxItems = new Vector();
        
        final DefaultComboBoxModel modelDestino = new DefaultComboBoxModel(comboBoxItems);
        final DefaultComboBoxModel modelOrigem = new DefaultComboBoxModel(comboBoxItems);

        int itemSelecionadoOrigem = 0;
        int itemSelecionadoDestino = 0;
        int cont = 0;
        
        if (!(partidaDesenvolvimento.getSituacoes() == null)) {
            for (Situacao s : partidaDesenvolvimento.getSituacoes()) {
                
                if(s == saida.getSituacaoOrigem())
                {
                    itemSelecionadoOrigem = cont;
                }
                
                if(s == saida.getSituacaoDestino())
                {
                    itemSelecionadoDestino = cont;
                }
                
                comboBoxItems.add(s.getNome());
                
                cont++;
            }

            cbxSituacaoDestino.setModel(modelDestino);
            cbxSituacaoOrigem.setModel(modelOrigem);
            
            cbxSituacaoDestino.setSelectedIndex(itemSelecionadoDestino);
            cbxSituacaoOrigem.setSelectedIndex(itemSelecionadoOrigem);
            
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnlSaidaOpcao = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaFalaAssistente = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        cbxSituacaoDestino = new javax.swing.JComboBox();
        cbxSituacaoOrigem = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pnlSaidaNumerica = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtNome = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Nome:");

        txaFalaAssistente.setColumns(20);
        txaFalaAssistente.setRows(5);
        jScrollPane1.setViewportView(txaFalaAssistente);

        jLabel2.setText("Fala assistente:");

        cbxSituacaoDestino.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxSituacaoDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxSituacaoDestinoActionPerformed(evt);
            }
        });

        cbxSituacaoOrigem.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxSituacaoOrigem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxSituacaoOrigemActionPerformed(evt);
            }
        });

        jLabel4.setText("Situação Destino:");

        jLabel3.setText("Situação Origem:");

        javax.swing.GroupLayout pnlSaidaOpcaoLayout = new javax.swing.GroupLayout(pnlSaidaOpcao);
        pnlSaidaOpcao.setLayout(pnlSaidaOpcaoLayout);
        pnlSaidaOpcaoLayout.setHorizontalGroup(
            pnlSaidaOpcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSaidaOpcaoLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(pnlSaidaOpcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlSaidaOpcaoLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlSaidaOpcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlSaidaOpcaoLayout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cbxSituacaoOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnlSaidaOpcaoLayout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cbxSituacaoDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        pnlSaidaOpcaoLayout.setVerticalGroup(
            pnlSaidaOpcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSaidaOpcaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSaidaOpcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(pnlSaidaOpcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbxSituacaoOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(pnlSaidaOpcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxSituacaoDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(94, 94, 94))
        );

        jTabbedPane1.addTab("Opção", pnlSaidaOpcao);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout pnlSaidaNumericaLayout = new javax.swing.GroupLayout(pnlSaidaNumerica);
        pnlSaidaNumerica.setLayout(pnlSaidaNumericaLayout);
        pnlSaidaNumericaLayout.setHorizontalGroup(
            pnlSaidaNumericaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSaidaNumericaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(132, Short.MAX_VALUE))
        );
        pnlSaidaNumericaLayout.setVerticalGroup(
            pnlSaidaNumericaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSaidaNumericaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(282, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Numérica", pnlSaidaNumerica);

        txtNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeActionPerformed(evt);
            }
        });

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(btnConfirmar))
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeActionPerformed

    private void cbxSituacaoOrigemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxSituacaoOrigemActionPerformed
        
        //Recupera o index do item
        int index = cbxSituacaoOrigem.getSelectedIndex();
                
        //Recupera o item na lista e associa a saída
        situacaoOrigem = partidaDesenvolvimento.getSituacoes().get(index);
        System.out.println(situacaoOrigem.getNome());
        
    }//GEN-LAST:event_cbxSituacaoOrigemActionPerformed

    private void cbxSituacaoDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxSituacaoDestinoActionPerformed
        
        //Recupera o index do item
        int index = cbxSituacaoDestino.getSelectedIndex();
                
        //Recupera o item na lista e associa a saída
        situacaoDestino = partidaDesenvolvimento.getSituacoes().get(index);
        System.out.println(situacaoDestino.getNome());
        
    }//GEN-LAST:event_cbxSituacaoDestinoActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        
        saida.setFalaAssistente(txaFalaAssistente.getText());
        saida.setNome(txtNome.getText());
        saida.setSituacaoDestino(situacaoDestino);
        saida.setSituacaoOrigem(situacaoOrigem);
        
        //Adiciona a saída na situação origem caso a ação seja inserir
        if(acao == 1)
        {
            situacaoOrigem.getSaidas().add(saida);
        }
        
        jdp.AtualizaSaidas();
        
        dispose();
        
    }//GEN-LAST:event_btnConfirmarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JComboBox cbxSituacaoDestino;
    private javax.swing.JComboBox cbxSituacaoOrigem;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel pnlSaidaNumerica;
    private javax.swing.JPanel pnlSaidaOpcao;
    private javax.swing.JTextArea txaFalaAssistente;
    private javax.swing.JTextField txtNome;
    // End of variables declaration//GEN-END:variables
}
