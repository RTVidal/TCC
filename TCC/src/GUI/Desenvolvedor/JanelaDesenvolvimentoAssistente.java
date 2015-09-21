/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Desenvolvedor;

import Controle.ControladoraIdioma;
import Modelo.Assistente;
import Modelo.Partida;
import java.io.File;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Rafael
 */
public class JanelaDesenvolvimentoAssistente extends javax.swing.JDialog {

    private ArrayList<ImageIcon> avatares;
    private Assistente assistente;
    private final Partida partida;
    private ImageIcon avatarSelecionado;
    private final JanelaDesenvolvimentoPartida jdp;
    private final ControladoraIdioma idioma;

    /**
     * Creates new form JanelaDesenvolvimentoAssistente
     */
    public JanelaDesenvolvimentoAssistente() {
        initComponents();
        setModal(true);
        idioma = ControladoraIdioma.getInstancia();

        //aplica a internacionalização na janela
        CarregaIdioma();

        partida = Partida.getInstancia();
        assistente = partida.getAssistente();
        if (assistente != null) {
            CarregaAssistente();
        } else {
            assistente = new Assistente();
        }

        CarregaAvatares();
        jdp = JanelaDesenvolvimentoPartida.getInstancia();
        setLocationRelativeTo(jdp);
        
    }

    public final void CarregaIdioma() {
        lblApresentacao.setText(idioma.Valor("lblApresentacao"));
        lblNomeAssistente.setText(idioma.Valor("lblNomeAssistente"));
        lblSelecioneAvatar.setText(idioma.Valor("lblSelecioneAvatar"));
        btnAjuda.setText(idioma.Valor("btnAjuda"));
        btnCancelar.setText(idioma.Valor("btnCancelar"));
        btnConfirmar.setText(idioma.Valor("btnConfirmar"));
        lblTitulo.setText(idioma.Valor("tituloAssistente"));
        this.setTitle(idioma.Valor("tituloAssistente"));
    }

    public final void CarregaAssistente() {
        txtNomeAssistente.setText(assistente.getNome());
        txaApresentacao.setText(assistente.getApresentacao());
    }

    /**
     * Carrega os avatares disponíveis para o assistente
     */
    public final void CarregaAvatares() {
        
        int itemSelecionado = 0;
        lblImgAvatar.setText(idioma.Valor("lblSelecioneUmAvatar"));
        DefaultListModel itens = new DefaultListModel<>();
        avatares = new ArrayList<>();
        
        //Recupera a quantidade de avatares disponiveis
        File file = new File("./Recursos/Avatar");
        File arquivos[] = file.listFiles();
        
        
        for (int i = 0; i < arquivos.length; i++) {
            
            ImageIcon avatar = new ImageIcon(arquivos[i].getAbsolutePath());
            avatar.setDescription(arquivos[i].getAbsolutePath());
            avatares.add(avatar);
            if (assistente.getAvatarAssistente().equals(avatar.getDescription())) {
                itemSelecionado = i;
            }
            itens.addElement(arquivos[i].getName());
            
        }
        
        lstAvatares.setModel(itens);
        lstAvatares.setSelectedIndex(itemSelecionado);

    }

    /**
     * Salva o assistente
     */
    public void SalvarAssistente() {

        boolean ok = ValidarDados();

        System.out.println("avatar sel " + avatarSelecionado.getDescription());
        
        if (ok) {
            assistente.setNome(txtNomeAssistente.getText());
            assistente.setAvatarAssistente(avatarSelecionado.getDescription());
            assistente.setApresentacao(txaApresentacao.getText());
            partida.setAssistente(assistente);
            assistente.setCriado(true);
            jdp.AtualizaAssistente();
            dispose();
        }

    }

    /**
     * Valida os dados
     * @return 
     */
    public boolean ValidarDados() {
        
        boolean ok = true;
        String mensagem;
        ArrayList<String> mensagens = new ArrayList<>();

        if (txtNomeAssistente.getText().isEmpty()) {
            
            ok = false;
            mensagem = "msgNomeAssistenteObrigatorio";
            mensagens.add(mensagem);
        }

        if (txaApresentacao.getText().isEmpty()) {
            
            ok = false;
            mensagem = "msgApresentacaoObrigatoria";
            mensagens.add(mensagem);
        }

        if (!ok) {
            
            String mensagemJanela = "";

            for (String s : mensagens) {
                mensagemJanela += s + "\n";
            }

            JOptionPane.showMessageDialog(this, mensagemJanela, idioma.Valor("lblAviso"), JOptionPane.OK_OPTION);
        }

        return ok;
    }
    
    public void SelecionarAvatar()
    {
        int index = lstAvatares.getSelectedIndex();
        if (index > -1) {
            ImageIcon icone = new ImageIcon();
            icone.setImage(avatares.get(index).getImage().getScaledInstance(150, 150, 150));
            lblImgAvatar.setText(null);
            lblImgAvatar.setIcon(icone);
            avatarSelecionado = avatares.get(index);
            
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

        jButton3 = new javax.swing.JButton();
        lblNomeAssistente = new javax.swing.JLabel();
        lblApresentacao = new javax.swing.JLabel();
        lblSelecioneAvatar = new javax.swing.JLabel();
        txtNomeAssistente = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaApresentacao = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstAvatares = new javax.swing.JList();
        btnConfirmar = new javax.swing.JButton();
        lblImgAvatar = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnAjuda = new javax.swing.JButton();

        jButton3.setText("jButton2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Assistente");
        setResizable(false);

        lblNomeAssistente.setText("Nome do Assistente:");

        lblApresentacao.setText("Apresentação:");

        lblSelecioneAvatar.setText("Selecione um Avatar:");

        txtNomeAssistente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeAssistenteActionPerformed(evt);
            }
        });

        txaApresentacao.setColumns(20);
        txaApresentacao.setRows(5);
        jScrollPane1.setViewportView(txaApresentacao);

        lstAvatares.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstAvatares.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstAvataresValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(lstAvatares);

        btnConfirmar.setText("btnConfirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        lblImgAvatar.setText("imgAvatar");

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitulo.setText("lblTitulo");

        btnCancelar.setText("btnCancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnAjuda.setText("btnAjuda");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblApresentacao)
                            .addComponent(lblNomeAssistente))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                            .addComponent(txtNomeAssistente)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnConfirmar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblSelecioneAvatar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblImgAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAjuda)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitulo)
                    .addComponent(btnAjuda))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNomeAssistente)
                    .addComponent(txtNomeAssistente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblApresentacao)
                        .addGap(142, 142, 142))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSelecioneAvatar)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblImgAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnConfirmar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lstAvataresValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstAvataresValueChanged

        SelecionarAvatar();
        
    }//GEN-LAST:event_lstAvataresValueChanged

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed

        SalvarAssistente();

    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void txtNomeAssistenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeAssistenteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeAssistenteActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
       
        dispose();
        
    }//GEN-LAST:event_btnCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAjuda;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblApresentacao;
    private javax.swing.JLabel lblImgAvatar;
    private javax.swing.JLabel lblNomeAssistente;
    private javax.swing.JLabel lblSelecioneAvatar;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JList lstAvatares;
    private javax.swing.JTextArea txaApresentacao;
    private javax.swing.JTextField txtNomeAssistente;
    // End of variables declaration//GEN-END:variables
}
