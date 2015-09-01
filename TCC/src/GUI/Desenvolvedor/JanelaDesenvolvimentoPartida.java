/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Desenvolvedor;

import Controle.ControladoraIdioma;
import GUI.Jogador.JanelaExecucaoPartida;
import GUI.Suporte.SituacoesTbModel;
import GUI.Suporte.VariaveisTbModel;
import Modelo.Partida;
import Modelo.Situacao;
import Modelo.Variavel;
import Persistencia.IOPartida;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Rafael
 */
public class JanelaDesenvolvimentoPartida extends javax.swing.JFrame {

    private final Partida partidaDesenvolvimento;

    private static JanelaDesenvolvimentoPartida instancia;

    private final ControladoraIdioma idioma;

    /**
     * Creates new form JanelaAdministrarJogo
     */
    public JanelaDesenvolvimentoPartida() {
        initComponents();
        setLocationRelativeTo(null);

        idioma = ControladoraIdioma.getInstancia();

        PreencheComponentes();

        partidaDesenvolvimento = Partida.getInstancia();
        if (partidaDesenvolvimento.getNomeArquivo()!= null) {
            setTitle(partidaDesenvolvimento.getNomeArquivo());
        }
        AtualizaSituacoes();
        AtualizaVariaveis();

        if (partidaDesenvolvimento.getAssistente() != null) {
            AtualizaAssistente();
        }

        partidaDesenvolvimento.setIdioma(idioma.getIdiomaAtual());

        //AtualizaSaidas();
    }

    /**
     * Preenche os componentes da tela de acordo com o idioma selecionado
     */
    public final void PreencheComponentes() {
        //Labels

        //Botões        
        btnNovaSituacao.setText(idioma.Valor("desPartidaBtnNovaSituacao"));
        btnEditarAssistente.setText(idioma.Valor("desPartidaBtnEditarAssistente"));
    }

    /**
     * Preenche a lista de situaçãoes com a situação da partida
     */
    public final void AtualizaSituacoes() {

        SituacoesTbModel model = new SituacoesTbModel(partidaDesenvolvimento.getSituacoes());
        
        tblSituacoes.setModel(model);
        
        //Esconder a coluna contendo o objeto da situação
        tblSituacoes.getColumnModel().getColumn(0).setMinWidth(0);
        tblSituacoes.getColumnModel().getColumn(0).setMaxWidth(0);
        tblSituacoes.getColumnModel().getColumn(0).setPreferredWidth(0);

    }
    
    /**
     * Atualiza a tabela de variáveis da partida
     */
    public final void AtualizaVariaveis()
    {
        VariaveisTbModel model = new VariaveisTbModel(partidaDesenvolvimento.getVariaveis());
        
        tblVariaveis.setModel(model);
        
        //Esconder a coluna contendo o objeto da variável
        tblVariaveis.getColumnModel().getColumn(0).setMinWidth(0);
        tblVariaveis.getColumnModel().getColumn(0).setMaxWidth(0);
        tblVariaveis.getColumnModel().getColumn(0).setPreferredWidth(0);
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

    public void NovaSituacao() {
        JanelaDesenvolvimentoSituacao jds = new JanelaDesenvolvimentoSituacao(1, null);
        jds.setVisible(true);
    }

    public void EditarSituacao() {
        //Recuperar o item selecionado
        int index = tblSituacoes.getSelectedRow();
        
        //Recupera o objeto na tabela
        Situacao situacao = (Situacao)tblSituacoes.getValueAt(index, 0);

        JanelaDesenvolvimentoSituacao jds = new JanelaDesenvolvimentoSituacao(2, situacao);
        jds.setVisible(true);

    }

    public void PreviaSituacao() {
        //Recuperar o item selecionado
        int index = tblSituacoes.getSelectedRow();
        
        //Recupera o objeto na tabela
        Situacao situacao = (Situacao)tblSituacoes.getValueAt(index, 0);

        JanelaExecucaoPartida jsj = new JanelaExecucaoPartida();
        jsj.CarregarPreviaSituacao(situacao, partidaDesenvolvimento.getAssistente());
        jsj.setVisible(true);
    }

    public void EditarAssistente() {
        JanelaDesenvolvimentoAssistente jda = new JanelaDesenvolvimentoAssistente();
        jda.setVisible(true);
    }

    public void SalvarPartida() {
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
                partidaDesenvolvimento.setNomeArquivo(jFileChooser.getSelectedFile().getName());
                
                String diretorio = jFileChooser.getSelectedFile().getAbsolutePath();
                IOPartida iop = new IOPartida();
                iop.SalvaPartida(diretorio);
                dispose();
                Partida.setInstancia(null);
                JanelaDesenvolvimentoPartida.setInstancia(null);

            } else if (acao == JFileChooser.CANCEL_OPTION) {
                //apertou botao cancelar
            } else if (acao == JFileChooser.ERROR_OPTION) {
                //outra opcao
            }
        } else {
            JOptionPane.showMessageDialog(this, mensagem);
        }
    }
    
    public void AtualizarAcoes()
    {
        
    }
    
    /**
     * Criar uma nova variável
     */
    public void NovaVariavel()
    {
        Variavel variavel = new Variavel();
        JanelaDesenvolvimentoVariavel jdv = new JanelaDesenvolvimentoVariavel(1, variavel);
        jdv.setVisible(true);
    }
    
    /**
     * Editar variável
     */
    public void EditarVariavel()
    {
        int index = tblVariaveis.getSelectedRow();
        
        Variavel variavel = (Variavel)tblVariaveis.getValueAt(index, 0);
        System.out.println("recuperou " + variavel.getNome());
        
        JanelaDesenvolvimentoVariavel jdv = new JanelaDesenvolvimentoVariavel(2, variavel);
        jdv.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        btnNovaSituacao = new javax.swing.JButton();
        btnEditarSituacao = new javax.swing.JButton();
        btnExcluirSituacao = new javax.swing.JButton();
        btnPreviaSituacao = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSituacoes = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblVariaveis = new javax.swing.JTable();
        btnNovaVariavel = new javax.swing.JButton();
        btnEditarVariavel = new javax.swing.JButton();
        btnExcluirVariavel = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        lblImgAssistente = new javax.swing.JLabel();
        lblAssistente = new javax.swing.JLabel();
        lblNomeAssistente = new javax.swing.JLabel();
        btnEditarAssistente = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();

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

        btnNovaSituacao.setText("btnNovaSituacao");
        btnNovaSituacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovaSituacaoActionPerformed(evt);
            }
        });

        btnEditarSituacao.setText("btnEditarSituacao");
        btnEditarSituacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarSituacaoActionPerformed(evt);
            }
        });

        btnExcluirSituacao.setText("btnExcluir");
        btnExcluirSituacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirSituacaoActionPerformed(evt);
            }
        });

        btnPreviaSituacao.setText("btnPrevia");
        btnPreviaSituacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviaSituacaoActionPerformed(evt);
            }
        });

        tblSituacoes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblSituacoes);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnNovaSituacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditarSituacao)
                        .addGap(18, 18, 18)
                        .addComponent(btnExcluirSituacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPreviaSituacao)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovaSituacao)
                    .addComponent(btnEditarSituacao)
                    .addComponent(btnExcluirSituacao)
                    .addComponent(btnPreviaSituacao))
                .addGap(22, 22, 22))
        );

        jTabbedPane1.addTab("Situações", jPanel1);

        tblVariaveis.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblVariaveis);

        btnNovaVariavel.setText("btnNovaVariavel");
        btnNovaVariavel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovaVariavelActionPerformed(evt);
            }
        });

        btnEditarVariavel.setText("btnEditarVariavel");
        btnEditarVariavel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarVariavelActionPerformed(evt);
            }
        });

        btnExcluirVariavel.setText("btnExcluirVariavel");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnNovaVariavel)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditarVariavel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExcluirVariavel)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovaVariavel)
                    .addComponent(btnEditarVariavel)
                    .addComponent(btnExcluirVariavel))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("abaVariaveis", jPanel2);

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitulo.setText("lblTitulo");

        lblImgAssistente.setText("imgAssistente");

        lblAssistente.setText("lblAssistente");

        lblNomeAssistente.setText("lblNomeAssistente");

        btnEditarAssistente.setText("btnEditarAssistente");
        btnEditarAssistente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarAssistenteActionPerformed(evt);
            }
        });

        btnSalvar.setText("btnSalvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEditarAssistente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 5, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNomeAssistente, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblImgAssistente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblAssistente)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addGap(18, 18, 18)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblImgAssistente, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblAssistente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNomeAssistente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditarAssistente)
                        .addGap(174, 174, 174)
                        .addComponent(btnSalvar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditarAssistenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarAssistenteActionPerformed
        EditarAssistente();
    }//GEN-LAST:event_btnEditarAssistenteActionPerformed

    private void btnNovaSituacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovaSituacaoActionPerformed
        NovaSituacao();
    }//GEN-LAST:event_btnNovaSituacaoActionPerformed

    private void btnEditarSituacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarSituacaoActionPerformed
        EditarSituacao();
    }//GEN-LAST:event_btnEditarSituacaoActionPerformed

    private void btnExcluirSituacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirSituacaoActionPerformed

    }//GEN-LAST:event_btnExcluirSituacaoActionPerformed

    private void btnPreviaSituacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviaSituacaoActionPerformed
        PreviaSituacao();
    }//GEN-LAST:event_btnPreviaSituacaoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        SalvarPartida();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnNovaVariavelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovaVariavelActionPerformed
        
        NovaVariavel();
        
    }//GEN-LAST:event_btnNovaVariavelActionPerformed

    private void btnEditarVariavelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarVariavelActionPerformed
        
        EditarVariavel();
        
    }//GEN-LAST:event_btnEditarVariavelActionPerformed

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
    private javax.swing.JButton btnEditarVariavel;
    private javax.swing.JButton btnExcluirSituacao;
    private javax.swing.JButton btnExcluirVariavel;
    private javax.swing.JButton btnNovaSituacao;
    private javax.swing.JButton btnNovaVariavel;
    private javax.swing.JButton btnPreviaSituacao;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblAssistente;
    private javax.swing.JLabel lblImgAssistente;
    private javax.swing.JLabel lblNomeAssistente;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tblSituacoes;
    private javax.swing.JTable tblVariaveis;
    // End of variables declaration//GEN-END:variables
}
