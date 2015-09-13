/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Desenvolvedor;

import Controle.ControladoraIdioma;
import GUI.Jogador.JanelaExecucaoPartida;
import GUI.Suporte.AvaliacoesTbModel;
import GUI.Suporte.SituacoesTbModel;
import GUI.Suporte.VariaveisTbModel;
import Modelo.Acao;
import Modelo.Avaliacao;
import Modelo.Partida;
import Modelo.Saida;
import Modelo.SaidaNumerica;
import Modelo.SaidaOpcional;
import Modelo.Situacao;
import Modelo.Variavel;
import Persistencia.IOPartida;
import java.io.File;
import java.util.ArrayList;
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

    private boolean partidaSalva;

    /**
     * Creates new form JanelaAdministrarJogo
     */
    public JanelaDesenvolvimentoPartida() {
        initComponents();
        setLocationRelativeTo(null);

        idioma = ControladoraIdioma.getInstancia();

        PreencheComponentes();

        partidaDesenvolvimento = Partida.getInstancia();
        if (partidaDesenvolvimento.getNomeArquivo() != null) {
            setTitle(partidaDesenvolvimento.getNomeArquivo());
        }
        AtualizarDados();

        if (partidaDesenvolvimento.getAssistente() != null) {
            AtualizaAssistente();
        }

        partidaDesenvolvimento.setIdioma(idioma.getIdiomaAtual());

    }

    /**
     * Preenche os componentes da tela de acordo com o idioma selecionado
     */
    public final void PreencheComponentes() {

        //Nomeia os componentes conforme o idioma selecionado
        //Botões        
        btnNovaSituacao.setText(idioma.Valor("btnNovaSituacao"));
        btnEditarAssistente.setText(idioma.Valor("btnEditarAssistente"));
    }

    public final void AtualizarDados() {
        AtualizaSituacoes();
        AtualizaVariaveis();
        AtualizaAvaliacoes();
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

        //Habilitar o botão de editar e excluir apenas quando houverem registros na lista
        btnEditarSituacao.setEnabled(!partidaDesenvolvimento.getSituacoes().isEmpty());
        btnExcluirSituacao.setEnabled(!partidaDesenvolvimento.getSituacoes().isEmpty());

    }

    /**
     * Atualiza a tabela de variáveis da partida
     */
    public final void AtualizaVariaveis() {
        VariaveisTbModel model = new VariaveisTbModel(partidaDesenvolvimento.getVariaveis());

        tblVariaveis.setModel(model);

        //Esconder a coluna contendo o objeto da variável
        tblVariaveis.getColumnModel().getColumn(0).setMinWidth(0);
        tblVariaveis.getColumnModel().getColumn(0).setMaxWidth(0);
        tblVariaveis.getColumnModel().getColumn(0).setPreferredWidth(0);

        //Habilitar o botão de editar e excluir apenas quando houverem registros na lista
        btnEditarVariavel.setEnabled(!partidaDesenvolvimento.getVariaveis().isEmpty());
        btnExcluirVariavel.setEnabled(!partidaDesenvolvimento.getVariaveis().isEmpty());

    }

    /**
     * Atualiza a tabela de avaliações
     */
    public final void AtualizaAvaliacoes() {
        AvaliacoesTbModel model = new AvaliacoesTbModel(partidaDesenvolvimento.getAvaliacoes());

        tblAvaliacoes.setModel(model);

        //Esconder a coluna contendo o objeto da avaliação
        tblAvaliacoes.getColumnModel().getColumn(0).setMinWidth(0);
        tblAvaliacoes.getColumnModel().getColumn(0).setMaxWidth(0);
        tblAvaliacoes.getColumnModel().getColumn(0).setPreferredWidth(0);

        //Habilitar o botão de editar e excluir apenas quando houverem registros na lista
        btnEditarAvaliacao.setEnabled(!partidaDesenvolvimento.getAvaliacoes().isEmpty());
        btnExcluirAvaliacao.setEnabled(!partidaDesenvolvimento.getAvaliacoes().isEmpty());

        //Habilitar o botão de nova avaliação apenas se houverem variáveis criadas
        btnNovaAvaliacao.setEnabled(!partidaDesenvolvimento.getVariaveis().isEmpty());
        btnNovaAvaliacao.setToolTipText(idioma.Valor("msgNaoHaVariaveis"));
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
        Situacao situacao = (Situacao) tblSituacoes.getValueAt(index, 0);

        JanelaDesenvolvimentoSituacao jds = new JanelaDesenvolvimentoSituacao(2, situacao);
        jds.setVisible(true);

    }

    public void ExcluirSituacao() {

        boolean continuar = false;

        String mensagem = idioma.Valor("msgExclusaoSituacao");
        int opcao = JOptionPane.showConfirmDialog(null, mensagem, "Aviso", JOptionPane.YES_NO_OPTION);

        if (opcao == 0) {

            continuar = true;

        }

        if (continuar) {

            //Recuperar o item selecionado
            int index = tblSituacoes.getSelectedRow();
            ArrayList<Integer> itensRemover = new ArrayList<>();

            int cont;

            //Recupera o objeto na tabela
            Situacao situacao = (Situacao) tblSituacoes.getValueAt(index, 0);

            //Excluir todas as saídas que tem a situação como destino
            for (Situacao s : partidaDesenvolvimento.getSituacoes()) {
                Saida saida = s.getSaida();

                switch (saida.getTipoSaida()) {

                    case 0:
                        //Não há saídas, não faz nada
                        break;

                    case 1:
                        ArrayList<SaidaOpcional> saidasO = saida.getSaidasOpcao();

                        cont = 0;
                        for (SaidaOpcional so : saidasO) {
                            if (so.getSituacaoDestino() == situacao) {
                                itensRemover.add(cont);
                            }
                            cont++;
                        }

                        for (int i : itensRemover) {
                            saidasO.remove(i);
                        }
                        itensRemover.clear();
                        break;

                    case 2:

                        ArrayList<SaidaNumerica> saidasN = saida.getSaidasNumerica();

                        cont = 0;
                        for (SaidaNumerica sn : saidasN) {
                            if (sn.getSituacaoDestino() == situacao) {
                                itensRemover.add(cont);
                            }
                            cont++;
                        }

                        for (int i : itensRemover) {
                            saidasN.remove(i);
                        }
                        itensRemover.clear();

                        break;
                }
            }

            partidaDesenvolvimento.getSituacoes().remove(situacao);
            AtualizarDados();
        }

    }

    public void PreviaSituacao() {
        //Recuperar o item selecionado
        int index = tblSituacoes.getSelectedRow();

        //Recupera o objeto na tabela
        Situacao situacao = (Situacao) tblSituacoes.getValueAt(index, 0);

        JanelaExecucaoPartida jsj = new JanelaExecucaoPartida(2);
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

        if (!partidaDesenvolvimento.getAssistente().isCriado()) {
            mensagem = idioma.Valor("mensagemSemAssistente");

            int opcao = JOptionPane.showConfirmDialog(null, mensagem, "Aviso", JOptionPane.YES_NO_OPTION);

            if (opcao == 1) {

                continuar = false;

            }

        }
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

    /**
     * Criar uma nova variável
     */
    public void NovaVariavel() {
        Variavel variavel = new Variavel();
        JanelaDesenvolvimentoVariavel jdv = new JanelaDesenvolvimentoVariavel(1, variavel);
        jdv.setVisible(true);
    }

    /**
     * Editar variável
     */
    public void EditarVariavel() {
        int index = tblVariaveis.getSelectedRow();

        Variavel variavel = (Variavel) tblVariaveis.getValueAt(index, 0);
        System.out.println("recuperou " + variavel.getNome());

        JanelaDesenvolvimentoVariavel jdv = new JanelaDesenvolvimentoVariavel(2, variavel);
        jdv.setVisible(true);
    }

    /**
     * Excluir variável
     */
    public void ExcluirVariavel() {

        boolean continuar = false;
        ArrayList<Integer> itensRemover = new ArrayList<>();
        int cont;

        String mensagem = idioma.Valor("msgExclusaoVariavel");
        int opcao = JOptionPane.showConfirmDialog(null, mensagem, "Aviso", JOptionPane.YES_NO_OPTION);

        if (opcao == 0) {

            continuar = true;

        }

        if (continuar) {

            //Recuperar o item selecionado
            int index = tblVariaveis.getSelectedRow();

            //Recupera o objeto na tabela
            Variavel variavel = (Variavel) tblVariaveis.getValueAt(index, 0);

            //Excluir todas as ações relacionadas à variável
            for (Situacao s : partidaDesenvolvimento.getSituacoes()) {
                Saida saida = s.getSaida();

                switch (saida.getTipoSaida()) {

                    case 0:
                        //Não há saídas, não faz nada
                        break;

                    case 1:

                        ArrayList<SaidaOpcional> saidasO = saida.getSaidasOpcao();

                        for (SaidaOpcional so : saidasO) {

                            cont = 0;
                            for (Acao a : so.getAcoes()) {

                                if (a.getVariavel() == variavel) {
                                    itensRemover.add(cont);

                                }
                                cont++;
                            }
                            for (int i : itensRemover) {
                                so.getAcoes().remove(i);
                            }
                            itensRemover.clear();

                        }

                        break;

                    case 2:

                        ArrayList<SaidaNumerica> saidasN = saida.getSaidasNumerica();

                        for (SaidaNumerica sn : saidasN) {

                            cont = 0;
                            for (Acao a : sn.getAcoes()) {

                                if (a.getVariavel() == variavel) {
                                    itensRemover.add(cont);
                                }
                                cont++;
                            }

                            for (int i : itensRemover) {
                                sn.getAcoes().remove(i);
                            }
                            itensRemover.clear();

                        }
                        break;
                }
            }

            //Excluir todas as avaliações envolvendo a variável
            cont = 0;
            for (Avaliacao a : partidaDesenvolvimento.getAvaliacoes()) {
                if (a.getVariavel() == variavel) {
                    itensRemover.add(cont);
                    
                }
                cont++;
            }
            
            for(int i : itensRemover)
            {
                partidaDesenvolvimento.getAvaliacoes().remove(i);
            }

            partidaDesenvolvimento.getVariaveis().remove(variavel);
            AtualizarDados();
        }
    }

    /**
     * Editar/Criar Avaliação
     *
     * @param modo
     */
    public void EditarAvaliacao(int modo) {
        Avaliacao avaliacao;

        if (modo == 1) {
            avaliacao = new Avaliacao();

        } else {
            int index = tblAvaliacoes.getSelectedRow();

            avaliacao = (Avaliacao) tblAvaliacoes.getValueAt(index, 0);
        }

        JanelaDesenvolvimentoAvaliacao jda = new JanelaDesenvolvimentoAvaliacao(modo, avaliacao);
        jda.setVisible(true);
    }

    /**
     * Excluir avaliação
     */
    public void ExcluirAvaliacao() {
        boolean continuar = false;

        String mensagem = idioma.Valor("msgExclusaoAvaliacao");
        int opcao = JOptionPane.showConfirmDialog(null, mensagem, idioma.Valor("lblAviso"), JOptionPane.YES_NO_OPTION);

        if (opcao == 0) {

            continuar = true;

        }

        if (continuar) {

            //Recuperar o item selecionado
            int index = tblAvaliacoes.getSelectedRow();

            //Recupera o objeto na tabela
            Avaliacao avaliacao = (Avaliacao) tblAvaliacoes.getValueAt(index, 0);

            partidaDesenvolvimento.getAvaliacoes().remove(avaliacao);
            AtualizarDados();
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

        jFrame1 = new javax.swing.JFrame();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jPopupMenu3 = new javax.swing.JPopupMenu();
        jPopupMenu4 = new javax.swing.JPopupMenu();
        jPopupMenu5 = new javax.swing.JPopupMenu();
        jFrame2 = new javax.swing.JFrame();
        jFrame3 = new javax.swing.JFrame();
        jDialog1 = new javax.swing.JDialog();
        abaAvaliacoes = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        btnNovaSituacao = new javax.swing.JButton();
        btnEditarSituacao = new javax.swing.JButton();
        btnExcluirSituacao = new javax.swing.JButton();
        btnPreviaSituacao = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSituacoes = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblVariaveis = new javax.swing.JTable();
        btnNovaVariavel = new javax.swing.JButton();
        btnEditarVariavel = new javax.swing.JButton();
        btnExcluirVariavel = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        pnlParametrizador = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblAvaliacoes = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        btnNovaAvaliacao = new javax.swing.JButton();
        btnEditarAvaliacao = new javax.swing.JButton();
        btnExcluirAvaliacao = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        lblImgAssistente = new javax.swing.JLabel();
        lblAssistente = new javax.swing.JLabel();
        lblNomeAssistente = new javax.swing.JLabel();
        btnEditarAssistente = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        mniSalvar = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();

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

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame3Layout = new javax.swing.GroupLayout(jFrame3.getContentPane());
        jFrame3.getContentPane().setLayout(jFrame3Layout);
        jFrame3Layout.setHorizontalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame3Layout.setVerticalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

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

        jButton1.setText("btnAjuda");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton1)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnNovaSituacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                .addComponent(jButton1)
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovaSituacao)
                    .addComponent(btnEditarSituacao)
                    .addComponent(btnExcluirSituacao)
                    .addComponent(btnPreviaSituacao))
                .addContainerGap())
        );

        abaAvaliacoes.addTab("Situações", jPanel1);

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
        btnExcluirVariavel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirVariavelActionPerformed(evt);
            }
        });

        jButton2.setText("btnAjuda");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(btnNovaVariavel, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnEditarVariavel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnExcluirVariavel))))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovaVariavel)
                    .addComponent(btnEditarVariavel)
                    .addComponent(btnExcluirVariavel))
                .addContainerGap())
        );

        abaAvaliacoes.addTab("abaVariaveis", jPanel2);

        tblAvaliacoes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblAvaliacoes);

        jButton3.setText("btnAjuda");

        btnNovaAvaliacao.setText("btnNovaAvaliacao");
        btnNovaAvaliacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovaAvaliacaoActionPerformed(evt);
            }
        });

        btnEditarAvaliacao.setText("btnEditarAvaliacao");
        btnEditarAvaliacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarAvaliacaoActionPerformed(evt);
            }
        });

        btnExcluirAvaliacao.setText("btnExcluirAvaliacao");
        btnExcluirAvaliacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirAvaliacaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlParametrizadorLayout = new javax.swing.GroupLayout(pnlParametrizador);
        pnlParametrizador.setLayout(pnlParametrizadorLayout);
        pnlParametrizadorLayout.setHorizontalGroup(
            pnlParametrizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlParametrizadorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlParametrizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlParametrizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(pnlParametrizadorLayout.createSequentialGroup()
                            .addGap(375, 375, 375)
                            .addComponent(jButton3))
                        .addGroup(pnlParametrizadorLayout.createSequentialGroup()
                            .addComponent(btnNovaAvaliacao, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnEditarAvaliacao)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnExcluirAvaliacao)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        pnlParametrizadorLayout.setVerticalGroup(
            pnlParametrizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlParametrizadorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlParametrizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExcluirAvaliacao)
                    .addComponent(btnEditarAvaliacao)
                    .addComponent(btnNovaAvaliacao))
                .addContainerGap())
        );

        abaAvaliacoes.addTab("abaAvaliacoes", pnlParametrizador);

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

        jMenu1.setText("mniArquivo");

        jMenuItem2.setText("mniAbrirJogar");
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("mniAbrirEditar");
        jMenu1.add(jMenuItem3);

        mniSalvar.setText("mniSalvar");
        mniSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSalvarActionPerformed(evt);
            }
        });
        jMenu1.add(mniSalvar);

        jMenuItem1.setText("mniSalvarComo");
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("mniConfigurar");

        jMenuItem6.setText("mniIdioma");
        jMenu2.add(jMenuItem6);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("mniAjuda");

        jMenuItem4.setText("mniManualUtilizacao");
        jMenu3.add(jMenuItem4);

        jMenuItem5.setText("mniSobre");
        jMenu3.add(jMenuItem5);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo)
                    .addComponent(abaAvaliacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEditarAssistente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 47, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNomeAssistente, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblImgAssistente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblAssistente)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(abaAvaliacoes))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(lblImgAssistente, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblAssistente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNomeAssistente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditarAssistente)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
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
        ExcluirSituacao();
    }//GEN-LAST:event_btnExcluirSituacaoActionPerformed

    private void btnPreviaSituacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviaSituacaoActionPerformed
        PreviaSituacao();
    }//GEN-LAST:event_btnPreviaSituacaoActionPerformed

    private void btnNovaVariavelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovaVariavelActionPerformed

        NovaVariavel();

    }//GEN-LAST:event_btnNovaVariavelActionPerformed

    private void btnEditarVariavelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarVariavelActionPerformed

        EditarVariavel();

    }//GEN-LAST:event_btnEditarVariavelActionPerformed

    private void mniSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSalvarActionPerformed

        SalvarPartida();

    }//GEN-LAST:event_mniSalvarActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed


    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

        if (!partidaSalva) {

            int opcao = JOptionPane.showConfirmDialog(null, idioma.Valor("msgDesejaSalvar"), "Aviso", JOptionPane.YES_NO_CANCEL_OPTION);

            switch (opcao) {
                case 0: //Salvar
                    SalvarPartida();
                    break;
                case 1: //Não salvar
                    dispose();
                    System.exit(0);
                    break;
            }

        }
    }//GEN-LAST:event_formWindowClosing

    private void btnEditarAvaliacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarAvaliacaoActionPerformed

        EditarAvaliacao(2);

    }//GEN-LAST:event_btnEditarAvaliacaoActionPerformed

    private void btnNovaAvaliacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovaAvaliacaoActionPerformed

        EditarAvaliacao(1);

    }//GEN-LAST:event_btnNovaAvaliacaoActionPerformed

    private void btnExcluirVariavelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirVariavelActionPerformed

        ExcluirVariavel();

    }//GEN-LAST:event_btnExcluirVariavelActionPerformed

    private void btnExcluirAvaliacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirAvaliacaoActionPerformed

        ExcluirAvaliacao();

    }//GEN-LAST:event_btnExcluirAvaliacaoActionPerformed

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
    private javax.swing.JTabbedPane abaAvaliacoes;
    private javax.swing.JButton btnEditarAssistente;
    private javax.swing.JButton btnEditarAvaliacao;
    private javax.swing.JButton btnEditarSituacao;
    private javax.swing.JButton btnEditarVariavel;
    private javax.swing.JButton btnExcluirAvaliacao;
    private javax.swing.JButton btnExcluirSituacao;
    private javax.swing.JButton btnExcluirVariavel;
    private javax.swing.JButton btnNovaAvaliacao;
    private javax.swing.JButton btnNovaSituacao;
    private javax.swing.JButton btnNovaVariavel;
    private javax.swing.JButton btnPreviaSituacao;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JFrame jFrame3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JPopupMenu jPopupMenu3;
    private javax.swing.JPopupMenu jPopupMenu4;
    private javax.swing.JPopupMenu jPopupMenu5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblAssistente;
    private javax.swing.JLabel lblImgAssistente;
    private javax.swing.JLabel lblNomeAssistente;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JMenuItem mniSalvar;
    private javax.swing.JPanel pnlParametrizador;
    private javax.swing.JTable tblAvaliacoes;
    private javax.swing.JTable tblSituacoes;
    private javax.swing.JTable tblVariaveis;
    // End of variables declaration//GEN-END:variables
}
