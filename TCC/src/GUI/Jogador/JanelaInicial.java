/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Jogador;

import Controle.ControladoraExecucao;
import Controle.ControladoraIdioma;
import GUI.Desenvolvedor.JanelaDesenvolvimentoPartida;
import Modelo.Partida;
import Persistencia.IOPartida;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Rafael
 */
public class JanelaInicial extends javax.swing.JFrame {

    private JanelaDesenvolvimentoPartida jdp;
    private ControladoraIdioma ci;
    private Object[] opcaoSimNao;
    private Object[] opcaoSimNaoCancelar;
    private final ControladoraIdioma idioma;

    /**
     * Creates new form JanelaInicial
     */
    public JanelaInicial() {
        initComponents();

        setLocationRelativeTo(jdp);
        idioma = ControladoraIdioma.getInstancia();
        PreencheComboIdiomas();
        idioma.DefineIdioma("Português");
        CarregaIdioma();
    }

    public final void CarregaIdioma() {
        opcaoSimNao = new Object[]{idioma.Valor("sim"), idioma.Valor("nao")};
        opcaoSimNaoCancelar = new Object[]{idioma.Valor("sim"), idioma.Valor("nao"), idioma.Valor("btnCancelar")};
        
        btnAbrirJogo.setText(idioma.Valor("principalBtnAbrirJogo"));
        btnEditarJogo.setText(idioma.Valor("principalBtnEditarJogo"));
        btnNovoJogo.setText(idioma.Valor("principalBtnNovoJogo"));
        lblTituloPrincipal.setText(idioma.Valor("tituloTelaPrincipal"));
        lblIdioma.setText(idioma.Valor("principalLblIdioma"));
        cbxIdiomas.setSelectedItem(idioma.getIdiomaAtual());
    }

    public final void PreencheComboIdiomas() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addElement("English");
        model.addElement("Español");
        model.addElement("Português");
        cbxIdiomas.setModel(model);
    }

    public void ExecutarJogo() {
        IOPartida iop = new IOPartida();
        Partida partidaExecutar = iop.LePartida();
        if (partidaExecutar != null) {
            if (!(((String) cbxIdiomas.getSelectedItem()).equalsIgnoreCase(partidaExecutar.getIdioma()))) {
                int i = JOptionPane.showOptionDialog(null, idioma.Valor("mensagemTrocaIdioma"), idioma.Valor("aviso"),
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcaoSimNao, opcaoSimNao[0]);
                if (i == 0) {
                    idioma.DefineIdioma(partidaExecutar.getIdioma());
                    CarregaIdioma();
                }
                if (partidaExecutar.getSituacaoInicial() != null) {
                    Partida.setInstancia(partidaExecutar);
                    ControladoraExecucao ce = new ControladoraExecucao();
                    ce.ExecutaPartida();
                } else {
                    int selecionada = JOptionPane.showOptionDialog(null, idioma.Valor("msgNaoHaSituacaoInicial"),
                            idioma.Valor("aviso"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                            null, opcaoSimNao, opcaoSimNao[0]);
                    if (selecionada == 0) {
                        EditarJogo(partidaExecutar);
                    }
                }
            }
        }
    }

    public void EditarJogo(Partida partidaDesenvolvimento) {
        if (partidaDesenvolvimento == null) {
            IOPartida iop = new IOPartida();
            partidaDesenvolvimento = iop.LePartida();
        }

        if (partidaDesenvolvimento != null) {
            if (!(((String) cbxIdiomas.getSelectedItem()).equalsIgnoreCase(partidaDesenvolvimento.getIdioma()))) {
                int i = JOptionPane.showOptionDialog(null, idioma.Valor("mensagemTrocaIdioma"), idioma.Valor("aviso"),
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcaoSimNao, opcaoSimNao[0]);
                if (i == 0) {
                    idioma.DefineIdioma(partidaDesenvolvimento.getIdioma());
                    CarregaIdioma();
                }
            }
            Partida.setInstancia(partidaDesenvolvimento);
            jdp = JanelaDesenvolvimentoPartida.getInstancia();
            jdp.setVisible(true);
            dispose();
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        btnAbrirJogo = new javax.swing.JButton();
        btnNovoJogo = new javax.swing.JButton();
        btnEditarJogo = new javax.swing.JButton();
        cbxIdiomas = new javax.swing.JComboBox();
        lblIdioma = new javax.swing.JLabel();
        lblTituloPrincipal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        btnAbrirJogo.setText("Abrir Jogo");
        btnAbrirJogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirJogoActionPerformed(evt);
            }
        });

        btnNovoJogo.setText("Novo Jogo");
        btnNovoJogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoJogoActionPerformed(evt);
            }
        });

        btnEditarJogo.setText("Editar Jogo");
        btnEditarJogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarJogoActionPerformed(evt);
            }
        });

        cbxIdiomas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxIdiomas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxIdiomasActionPerformed(evt);
            }
        });

        lblIdioma.setText("Idioma");

        lblTituloPrincipal.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTituloPrincipal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloPrincipal.setText("Faça seu Jogo!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblIdioma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAbrirJogo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTituloPrincipal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                    .addComponent(btnEditarJogo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNovoJogo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxIdiomas, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloPrincipal)
                .addGap(18, 18, 18)
                .addComponent(btnAbrirJogo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditarJogo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNovoJogo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(lblIdioma)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxIdiomas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoJogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoJogoActionPerformed

        jdp = JanelaDesenvolvimentoPartida.getInstancia();

        jdp.setVisible(true);

        dispose();
    }//GEN-LAST:event_btnNovoJogoActionPerformed

    private void btnEditarJogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarJogoActionPerformed

        EditarJogo(null);

    }//GEN-LAST:event_btnEditarJogoActionPerformed

    private void cbxIdiomasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxIdiomasActionPerformed
        idioma.DefineIdioma((String) cbxIdiomas.getSelectedItem());
        CarregaIdioma();
    }//GEN-LAST:event_cbxIdiomasActionPerformed

    private void btnAbrirJogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirJogoActionPerformed

        ExecutarJogo();

    }//GEN-LAST:event_btnAbrirJogoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbrirJogo;
    private javax.swing.JButton btnEditarJogo;
    private javax.swing.JButton btnNovoJogo;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbxIdiomas;
    private javax.swing.JLabel lblIdioma;
    private javax.swing.JLabel lblTituloPrincipal;
    // End of variables declaration//GEN-END:variables
}
