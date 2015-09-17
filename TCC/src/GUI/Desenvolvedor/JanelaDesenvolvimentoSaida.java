/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Desenvolvedor;

import Controle.ControladoraIdioma;
import GUI.Suporte.AcoesTbModel;
import Modelo.Acao;
import Modelo.Faixa;
import Modelo.Partida;
import Modelo.Saida;
import Modelo.SaidaNumerica;
import Modelo.SaidaOpcional;
import Modelo.Situacao;
import Modelo.Variavel;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Rafael
 */
public class JanelaDesenvolvimentoSaida extends javax.swing.JFrame {

    /**
     * Creates new form JanelaDesenvolvimentoSaida
     */
    private int modo;

    private final Partida partidaDesenvolvimento;

    private final Saida saida;

    private SaidaOpcional saidaOpcao;

    private SaidaNumerica saidaNumerica;

    private final Situacao situacaoOrigem;
    private Situacao situacaoDestino;

    private final ControladoraIdioma idioma;

    private final JanelaDesenvolvimentoSituacao janelaDevSituacao;

    public JanelaDesenvolvimentoSaida(JanelaDesenvolvimentoSituacao jds, int modo, Situacao situacao, Object saidaSelecionada) {
        initComponents();
        //setModal(true);
        setLocationRelativeTo(jds);

        janelaDevSituacao = jds;

        partidaDesenvolvimento = Partida.getInstancia();
        idioma = ControladoraIdioma.getInstancia();

        saida = situacao.getSaida();
        this.modo = modo;

        CarregarRecursos();
        
        txaFalaAssistente.setWrapStyleWord(true);
        
        situacaoOrigem = situacao;

        txtSituacaoOrigem.setText(situacaoOrigem.getNome());

        if (modo == 1) {
            
            switch (saida.getTipoSaida()) {
                case 1:

                    saidaOpcao = (SaidaOpcional) saidaSelecionada;
                    opcaoSaida.setSelectedComponent(pnlSaidaOpcao);

                    break;
                case 2:

                    saidaNumerica = (SaidaNumerica) saidaSelecionada;
                    opcaoSaida.setSelectedComponent(pnlSaidaNumerica);

                    break;
            }
        } else {

            CarregarSaida(saidaSelecionada);
        }

        opcaoSaida.setEnabled(false);

        PreencheListaSituacoes();
        AtualizarAcoes();
    }
    
    /**
     * Carrega os recursos da janela de acordo com o idioma selecionado
     */
    public final void CarregarRecursos()
    {
        msgSalvarSaidaHabAcoes.setText(idioma.Valor("msgSalvarSaidaHabAcoes"));
    }

    /**
     * Carregar a saída, caso o modo seja edição
     *
     * @param saidaSelecionada
     */
    public final void CarregarSaida(Object saidaSelecionada) {

        switch (saida.getTipoSaida()) {
            case 1:

                saidaOpcao = (SaidaOpcional) saidaSelecionada;

                txaFalaAssistente.setText(saidaOpcao.getFalaAssistente());
                txtDescricaoSO.setText(saidaOpcao.getNome());

                opcaoSaida.setSelectedComponent(pnlSaidaOpcao);

                break;
            case 2:

                saidaNumerica = (SaidaNumerica) saidaSelecionada;

                jspValorMaximo.setValue(saidaNumerica.getFaixa().getLimiteSuperior());
                jspValorMinimo.setValue(saidaNumerica.getFaixa().getLimiteInferior());

                txaFalaAssistente.setText(saidaNumerica.getFalaAssistente());

                opcaoSaida.setSelectedComponent(pnlSaidaNumerica);

                break;
        }

    }

    public final void PreencheListaSituacoes() {

        final DefaultComboBoxModel modelDestino = new DefaultComboBoxModel();

        int itemSelecionadoDestino = 0;
        int cont = 0;

        for (Situacao s : partidaDesenvolvimento.getSituacoes()) {

            if (saida.getTipoSaida() == 1) {

                if (s == saidaOpcao.getSituacaoDestino()) {
                    itemSelecionadoDestino = cont;
                }

            } else {

                if (s == saidaNumerica.getSituacaoDestino()) {
                    itemSelecionadoDestino = cont;
                }

            }

            modelDestino.addElement(s.getNome());

            cont++;
        }

        cbxSituacaoDestino.setModel(modelDestino);

        cbxSituacaoDestino.setSelectedIndex(itemSelecionadoDestino);

    }

    public final void AtualizarAcoes() {
        
        msgSalvarSaidaHabAcoes.setVisible(modo == 1);
        btnSalvarSaidaAcoes.setVisible(modo == 1);

        
        AcoesTbModel model;

        if (saida.getTipoSaida() == 1) {
            model = new AcoesTbModel(saidaOpcao.getAcoes());
            btnEditarAcao.setEnabled(!saidaOpcao.getAcoes().isEmpty());
            
            if(!saidaOpcao.getAcoes().isEmpty())
            {
                tblAcoes.setRowSelectionInterval(0, 0);
            }
            
        } else {
            model = new AcoesTbModel(saidaNumerica.getAcoes());
            btnEditarAcao.setEnabled(!saidaOpcao.getAcoes().isEmpty());
            
            if(!saidaOpcao.getAcoes().isEmpty())
            {
                tblAcoes.setRowSelectionInterval(0, 0);
            }
        }

        tblAcoes.setModel(model);

        //Esconder a coluna contendo o objeto da situação
        tblAcoes.getColumnModel().getColumn(0).setMinWidth(0);
        tblAcoes.getColumnModel().getColumn(0).setMaxWidth(0);
        tblAcoes.getColumnModel().getColumn(0).setPreferredWidth(0);
        
        
        
        

    }

    public void EditarAcao() {
        int index = tblAcoes.getSelectedRow();

        Acao acao = (Acao) tblAcoes.getValueAt(index, 0);

        JanelaDesenvolvimentoAcao jda = new JanelaDesenvolvimentoAcao(this, acao);
        jda.setVisible(true);
    }

    public void SalvarSaida(boolean fecharJanela) {
        saida.setSituacaoOrigem(situacaoOrigem);

        boolean ok = ValidarDados();

        if (ok) {
            switch (saida.getTipoSaida()) {
                case 1:

                    SalvarSaidaOpcional();
                    break;

                case 2:

                    SalvarSaidaNumerica();
                    break;
            }

            janelaDevSituacao.AtualizaTabelaSaidas();

            if(fecharJanela)
            {
                dispose();
            } else 
            {
                modo = 2;
                AtualizarAcoes();
            }
            
        }

    }

    /**
     * Validação dos dados
     *
     * @return
     */
    public boolean ValidarDados() {

        boolean ok = true;
        ArrayList<String> mensagens = new ArrayList<>();
        String mensagem;

        switch (saida.getTipoSaida()) {
            case 1:

                if (txtDescricaoSO.getText().isEmpty()) {
                    ok = false;
                    mensagem = idioma.Valor("msgDescricaoSOObrigatoria");
                    mensagens.add(mensagem);
                }
                break;

            case 2:

                int valorMinimo = (int) jspValorMinimo.getValue();
                int valorMaximo = (int) jspValorMaximo.getValue();

                if (valorMinimo >= valorMaximo) {
                    ok = false;
                    mensagem = idioma.Valor("msgValorMinimoMenor");
                    mensagens.add(mensagem);
                }
                break;
        }

        //Exibe avisos
        if (ok) {
            //Sem fala de assistente
            if (txaFalaAssistente.getText().isEmpty()) {
                int opcao = JOptionPane.showConfirmDialog(this, "msgSemFalaAssistente", idioma.Valor("lblAviso"),
                        JOptionPane.YES_NO_OPTION);

                if (opcao == 1) {

                    ok = false;

                }
            }

            //Situação destino = situação origem
            if (txtSituacaoOrigem.getText() == cbxSituacaoDestino.getSelectedItem()) {
                int opcao = JOptionPane.showConfirmDialog(this, "msgMesmaSituacao", idioma.Valor("lblAviso"),
                        JOptionPane.YES_NO_OPTION);

                if (opcao == 1) {

                    ok = false;

                }
            }

        } else {
            String mensagemJanela = "";

            for (String s : mensagens) {
                mensagemJanela += s + "\n";
            }

            JOptionPane.showMessageDialog(this, mensagemJanela, idioma.Valor("lblAviso"), JOptionPane.OK_OPTION);
        }

        return ok;
    }

    /**
     * Salva a saída do tipo opcional
     */
    public void SalvarSaidaOpcional() {

        saidaOpcao.setFalaAssistente(txaFalaAssistente.getText());
        saidaOpcao.setNome(txtDescricaoSO.getText());
        saidaOpcao.setSituacaoDestino(situacaoDestino);

        if (modo == 1) {

            CriarAcoesSaida(1);
            saida.getSaidasOpcao().add(saidaOpcao);

        }
    }

    public void SalvarSaidaNumerica() {

        saidaNumerica.setFalaAssistente(txaFalaAssistente.getText());
        saidaNumerica.setSituacaoDestino(situacaoDestino);

        Faixa faixa = new Faixa();
        faixa.setLimiteInferior((Integer) jspValorMinimo.getValue());
        faixa.setLimiteSuperior((Integer) jspValorMaximo.getValue());

        saidaNumerica.setFaixa(faixa);

        if (modo == 1) {

            CriarAcoesSaida(2);
            saida.getSaidasNumerica().add(saidaNumerica);

        }
    }

    public void CriarAcoesSaida(int tipoSaida) {

        if (tipoSaida == 1) {
            for (Variavel variavel : partidaDesenvolvimento.getVariaveis()) {

                Acao acao = new Acao();
                acao.setNumero(0);
                acao.setOperacao(0);
                acao.setVariavel(variavel);

                saidaOpcao.getAcoes().add(acao);

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

        opcaoSaida = new javax.swing.JTabbedPane();
        pnlSaidaOpcao = new javax.swing.JPanel();
        lblDescricao = new javax.swing.JLabel();
        txtDescricaoSO = new javax.swing.JTextField();
        pnlSaidaNumerica = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jspValorMinimo = new javax.swing.JSpinner();
        jspValorMaximo = new javax.swing.JSpinner();
        jLabel11 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();
        cbxSituacaoDestino = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        txtSituacaoOrigem = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaFalaAssistente = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAcoes = new javax.swing.JTable();
        btnEditarAcao = new javax.swing.JButton();
        btnAjuda = new javax.swing.JButton();
        msgSalvarSaidaHabAcoes = new javax.swing.JLabel();
        btnSalvarSaidaAcoes = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        opcaoSaida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                opcaoSaidaMouseClicked(evt);
            }
        });

        lblDescricao.setText("lblDescricao");

        javax.swing.GroupLayout pnlSaidaOpcaoLayout = new javax.swing.GroupLayout(pnlSaidaOpcao);
        pnlSaidaOpcao.setLayout(pnlSaidaOpcaoLayout);
        pnlSaidaOpcaoLayout.setHorizontalGroup(
            pnlSaidaOpcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSaidaOpcaoLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblDescricao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDescricaoSO, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE))
        );
        pnlSaidaOpcaoLayout.setVerticalGroup(
            pnlSaidaOpcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSaidaOpcaoLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(pnlSaidaOpcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDescricaoSO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDescricao))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        opcaoSaida.addTab("Opção", pnlSaidaOpcao);

        jLabel10.setText("lblValorMinimo");

        jspValorMinimo.setModel(new javax.swing.SpinnerNumberModel());

        jspValorMaximo.setModel(new javax.swing.SpinnerNumberModel());

        jLabel11.setText("lblValorMaximo");

        javax.swing.GroupLayout pnlSaidaNumericaLayout = new javax.swing.GroupLayout(pnlSaidaNumerica);
        pnlSaidaNumerica.setLayout(pnlSaidaNumericaLayout);
        pnlSaidaNumericaLayout.setHorizontalGroup(
            pnlSaidaNumericaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSaidaNumericaLayout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(pnlSaidaNumericaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSaidaNumericaLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jspValorMaximo, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlSaidaNumericaLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jspValorMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(332, Short.MAX_VALUE))))
        );
        pnlSaidaNumericaLayout.setVerticalGroup(
            pnlSaidaNumericaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSaidaNumericaLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlSaidaNumericaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jspValorMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlSaidaNumericaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jspValorMaximo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        opcaoSaida.addTab("Numérica", pnlSaidaNumerica);

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

        cbxSituacaoDestino.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxSituacaoDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxSituacaoDestinoActionPerformed(evt);
            }
        });

        jLabel4.setText("Situação Destino:");

        txtSituacaoOrigem.setEditable(false);
        txtSituacaoOrigem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSituacaoOrigemActionPerformed(evt);
            }
        });

        jLabel3.setText("Situação Origem:");

        txaFalaAssistente.setColumns(20);
        txaFalaAssistente.setLineWrap(true);
        txaFalaAssistente.setRows(5);
        jScrollPane1.setViewportView(txaFalaAssistente);

        jLabel2.setText("Fala assistente:");

        tblAcoes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblAcoes);

        btnEditarAcao.setText("btnEditarAcao");
        btnEditarAcao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarAcaoActionPerformed(evt);
            }
        });

        btnAjuda.setText("btnAjdua");

        msgSalvarSaidaHabAcoes.setText("msgSalvarSaidaHabAcoes");

        btnSalvarSaidaAcoes.setText("btnSalvarSaida");
        btnSalvarSaidaAcoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarSaidaAcoesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(opcaoSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtSituacaoOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(cbxSituacaoDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarAcao, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(msgSalvarSaidaHabAcoes)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSalvarSaidaAcoes))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAjuda))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(opcaoSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSituacaoOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnAjuda)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(msgSalvarSaidaHabAcoes, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnSalvarSaidaAcoes, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxSituacaoDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(btnEditarAcao))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirmar)
                    .addComponent(btnCancelar))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void cbxSituacaoDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxSituacaoDestinoActionPerformed

        //Recupera o index do item
        int index = cbxSituacaoDestino.getSelectedIndex();

        if (index >= 0) {
            //Recupera o item na lista e associa a saída
            situacaoDestino = partidaDesenvolvimento.getSituacoes().get(index);
        }

    }//GEN-LAST:event_cbxSituacaoDestinoActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed

        SalvarSaida(true);

    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void opcaoSaidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_opcaoSaidaMouseClicked

    }//GEN-LAST:event_opcaoSaidaMouseClicked

    private void txtSituacaoOrigemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSituacaoOrigemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSituacaoOrigemActionPerformed

    private void btnEditarAcaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarAcaoActionPerformed

        EditarAcao();

    }//GEN-LAST:event_btnEditarAcaoActionPerformed

    private void btnSalvarSaidaAcoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarSaidaAcoesActionPerformed
        
        SalvarSaida(false);
        
    }//GEN-LAST:event_btnSalvarSaidaAcoesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAjuda;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnEditarAcao;
    private javax.swing.JButton btnSalvarSaidaAcoes;
    private javax.swing.JComboBox cbxSituacaoDestino;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jspValorMaximo;
    private javax.swing.JSpinner jspValorMinimo;
    private javax.swing.JLabel lblDescricao;
    private javax.swing.JLabel msgSalvarSaidaHabAcoes;
    private javax.swing.JTabbedPane opcaoSaida;
    private javax.swing.JPanel pnlSaidaNumerica;
    private javax.swing.JPanel pnlSaidaOpcao;
    private javax.swing.JTable tblAcoes;
    private javax.swing.JTextArea txaFalaAssistente;
    private javax.swing.JTextField txtDescricaoSO;
    private javax.swing.JTextField txtSituacaoOrigem;
    // End of variables declaration//GEN-END:variables
}
