/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Desenvolvedor;

import Controle.ControladoraIdioma;
import GUI.Jogador.JanelaSituacaoJogo;
import Modelo.Partida;
import Modelo.Situacao;
import Persistencia.IOPartida;
import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Rafael
 */
public class JanelaDesenvolvimentoPartida extends javax.swing.JFrame {

    private Partida partidaDesenvolvimento;

    private static JanelaDesenvolvimentoPartida instancia;
    
    private ControladoraIdioma idioma;

    /**
     * Creates new form JanelaAdministrarJogo
     */
    public JanelaDesenvolvimentoPartida() {
        initComponents();
        setLocationRelativeTo(null);

        idioma = ControladoraIdioma.getInstancia();
        
        PreencheComponentes();
        
        partidaDesenvolvimento = Partida.getInstancia();

        AtualizaSituacoes();

        if (partidaDesenvolvimento.getAssistente() != null) {
            AtualizaAssistente();
        }

        //AtualizaSaidas();

    }
    
    /**
     * Preenche os componentes da tela de acordo com o idioma selecionado
     */
    public final void PreencheComponentes()
    {
        //Labels
        
        
        //Botões        
        btnNovaSituacao.setText(idioma.Valor("desPartidaBtnNovaSituacao"));
        btnEditarAssistente.setText(idioma.Valor("desPartidaBtnEditarAssistente"));
    }

    /**
     * Preenche a lista de situaçãoes com a situação da partida
     */
    public final void AtualizaSituacoes() {

        DefaultListModel itens = new DefaultListModel();

        if (!(partidaDesenvolvimento.getSituacoes() == null)) {
            for (Situacao s : partidaDesenvolvimento.getSituacoes()) {

                if (s.isSituacaoInicial()) {

                    itens.addElement(s.getNome() + " - " + idioma.Valor("desPartidaBtnInicial"));

                } else {

                    itens.addElement(s.getNome());

                }

            }

            lstSituacoes.setModel(itens);
        }

    }

    /**
     * Atualiza as informações do assiste
     */
    public final void AtualizaAssistente() {
        lblNomeAssistente.setText(partidaDesenvolvimento.getAssistente().getNome());

        lblImgAssistente.setSize(100, 100);
        lblImgAssistente.setText(null);

        ImageIcon imgAssistente = new ImageIcon(partidaDesenvolvimento.getAssistente().getAvatarAssistente());

        ImageIcon icone = new ImageIcon();
        icone.setImage(imgAssistente.getImage().getScaledInstance(75, 75, 75));

        lblImgAssistente.setIcon(icone);
    }

    /**
     * Atualiza a tabela de saídas
     */
//    public final void AtualizaSaidas() {
//
//        ArrayList<Situacao> situacoes = partidaDesenvolvimento.getSituacoes();
//        ArrayList<SaidaOpcao> saidas = new ArrayList<>();
//
//        //Obtem as saídas de cada situacao
//        for (Situacao st : situacoes) {
//            if ((st.getSaida().getsaidasOpcao() != null)) {
//                for (SaidaOpcional sd : st.getSaida().getsaidasOpcao()) {
//                    saidas.add(sd);
//                }
//            }
//        }
//
//        tblSaidas.setModel(new SaidasTbModel(saidas));
//
//        //Esconder a coluna contendo o objeto da saída
//        tblSaidas.getColumnModel().getColumn(0).setMinWidth(0);
//        tblSaidas.getColumnModel().getColumn(0).setMaxWidth(0);
//        tblSaidas.getColumnModel().getColumn(0).setPreferredWidth(0);
//
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jLabel1 = new javax.swing.JLabel();
        lblNomeAssistente = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstSituacoes = new javax.swing.JList();
        btnNovaSituacao = new javax.swing.JButton();
        btnEditarSituacao = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btnPrevia = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnEditarAssistente = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        lblImgAssistente = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("GUI/Desenvolvedor/Bundle"); // NOI18N
        jLabel1.setText(bundle.getString("ASSISTENTE:")); // NOI18N

        lblNomeAssistente.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNomeAssistente.setText(bundle.getString("NOMEASSISTENTE")); // NOI18N

        lstSituacoes.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(lstSituacoes);

        btnNovaSituacao.setText(bundle.getString("NOVA SITUAÇÃO")); // NOI18N
        btnNovaSituacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovaSituacaoActionPerformed(evt);
            }
        });

        btnEditarSituacao.setText(bundle.getString("EDITAR")); // NOI18N
        btnEditarSituacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarSituacaoActionPerformed(evt);
            }
        });

        jButton4.setText(bundle.getString("EXCLUIR")); // NOI18N

        btnPrevia.setText(bundle.getString("PRÉVIA")); // NOI18N
        btnPrevia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnNovaSituacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarSituacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrevia)
                        .addGap(0, 109, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovaSituacao)
                    .addComponent(btnEditarSituacao)
                    .addComponent(jButton4)
                    .addComponent(btnPrevia))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Situações", jPanel1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab(bundle.getString("PARAMETRIZADOR"), jPanel3); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab(bundle.getString("VARIÁVEIS"), jPanel4); // NOI18N

        btnEditarAssistente.setText(bundle.getString("EDITAR ASSISTENTE")); // NOI18N
        btnEditarAssistente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarAssistenteActionPerformed(evt);
            }
        });

        btnSalvar.setText(bundle.getString("SALVAR")); // NOI18N
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        lblImgAssistente.setText(bundle.getString("IMGASSISTENTE")); // NOI18N

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitulo.setText("lblTitulo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblImgAssistente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNomeAssistente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditarAssistente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addGap(18, 18, 18)
                        .addComponent(jTabbedPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblImgAssistente, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNomeAssistente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarAssistente)
                        .addGap(177, 177, 177)
                        .addComponent(btnSalvar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovaSituacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovaSituacaoActionPerformed

        JanelaDesenvolvimentoSituacao jds = new JanelaDesenvolvimentoSituacao(1, null);
        jds.setVisible(true);

    }//GEN-LAST:event_btnNovaSituacaoActionPerformed

    private void btnEditarSituacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarSituacaoActionPerformed

        //Recuperar o item selecionado
        int index = lstSituacoes.getSelectedIndex();

        JanelaDesenvolvimentoSituacao jds = new JanelaDesenvolvimentoSituacao(2, partidaDesenvolvimento.getSituacoes().get(index));
        jds.setVisible(true);

    }//GEN-LAST:event_btnEditarSituacaoActionPerformed

    private void btnPreviaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviaActionPerformed

        //Recuperar o item selecionado
        int index = lstSituacoes.getSelectedIndex();

        Situacao situacao = partidaDesenvolvimento.getSituacoes().get(index);

        JanelaSituacaoJogo jsj = new JanelaSituacaoJogo();
        jsj.CarregarPreviaSituacao(situacao, partidaDesenvolvimento.getAssistente());
        jsj.setVisible(true);

    }//GEN-LAST:event_btnPreviaActionPerformed

    private void btnEditarAssistenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarAssistenteActionPerformed

        JanelaDesenvolvimentoAssistente jda = new JanelaDesenvolvimentoAssistente();
        jda.setVisible(true);

    }//GEN-LAST:event_btnEditarAssistenteActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        boolean continuar = true;
        String mensagem = "";

//        if(!partidaDesenvolvimento.getAssistente().isCriado())
//        {
//            continuar = false;
//            mensagem = idioma.Valor("mensagemSemAssistente");
//        }
        
        
        if (continuar) {
            JFileChooser jFileChooser = new JFileChooser();

            //Selecionar apenas arquivos
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            //desabilita todos os tipos de arquivos
            jFileChooser.setAcceptAllFileFilterUsed(false);

            //filtra por extensao
            jFileChooser.setFileFilter(new FileFilter() {
                @Override
                public String getDescription() {
                    return "tcc";
                }

                @Override
                public boolean accept(File f) {
                    return f.getName().toLowerCase().endsWith("tcc");
                }
            });

            //mostra janela para salvar
            int acao = jFileChooser.showSaveDialog(null);

            //executa acao conforme opcao selecionada
            if (acao == JFileChooser.APPROVE_OPTION) {
                //escolheu arquivo
                String diretorio = jFileChooser.getSelectedFile().getAbsolutePath();

                IOPartida iop = new IOPartida();
                iop.SalvaPartida(diretorio);

            } else if (acao == JFileChooser.CANCEL_OPTION) {
                //apertou botao cancelar
            } else if (acao == JFileChooser.ERROR_OPTION) {
                //outra opcao
            }
        } else
        {
            JOptionPane.showMessageDialog(this, mensagem);
        }


    }//GEN-LAST:event_btnSalvarActionPerformed

    public static JanelaDesenvolvimentoPartida getInstancia() {

        if (instancia == null) {
            instancia = new JanelaDesenvolvimentoPartida();
        }

        return instancia;
    }

    public static void setInstancia(JanelaDesenvolvimentoPartida instancia) {
        JanelaDesenvolvimentoPartida.instancia = instancia;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditarAssistente;
    private javax.swing.JButton btnEditarSituacao;
    private javax.swing.JButton btnNovaSituacao;
    private javax.swing.JButton btnPrevia;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton jButton4;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblImgAssistente;
    private javax.swing.JLabel lblNomeAssistente;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JList lstSituacoes;
    // End of variables declaration//GEN-END:variables
}
