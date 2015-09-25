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

    private final Partida partidaDesenvolvimento;

    private static JanelaDesenvolvimentoPartida instancia;

    private final ControladoraIdioma idioma;

    private boolean partidaSalva;

    private ArrayList<ImageIcon> avatares;
    private ImageIcon avatarSelecionado;

    private Object[] opcaoSimNao;
    private Object[] opcaoSimNaoCancelar;

    /**
     * Creates new form JanelaAdministrarJogo
     */
    public JanelaDesenvolvimentoPartida() {
        initComponents();
        setLocationRelativeTo(null);

        idioma = ControladoraIdioma.getInstancia();

        CarregaIdioma();

        partidaDesenvolvimento = Partida.getInstancia();
        if (partidaDesenvolvimento.getNomeArquivo() != null) {
            setTitle(partidaDesenvolvimento.getNomeArquivo());
            lblTitulo.setText(idioma.Valor("tituloDesenvPartidaEdicao")
                    + " " + partidaDesenvolvimento.getNomeArquivo());
        }
        AtualizarDados();

        if (partidaDesenvolvimento.getAssistente() != null) {
            AtualizaAssistente();
        }
        CarregaAvatares();
        partidaDesenvolvimento.setIdioma(idioma.getIdiomaAtual());

    }

    public final void CarregaAvatares() {

        int itemSelecionado = 0;
        lblImgAssistente.setText(idioma.Valor("lblSelecioneUmAvatar"));
        DefaultListModel itens = new DefaultListModel<>();
        avatares = new ArrayList<>();

        //Recupera a quantidade de avatares disponiveis
        File file = new File("./Recursos/Avatar");
        File arquivos[] = file.listFiles();

        for (int i = 0; i < arquivos.length; i++) {

            ImageIcon avatar = new ImageIcon(arquivos[i].getAbsolutePath());
            avatar.setDescription(arquivos[i].getAbsolutePath());
            avatares.add(avatar);
            if (partidaDesenvolvimento.getAssistente().getAvatarAssistente().equals(avatar.getDescription())) {
                itemSelecionado = i;
            }
            itens.addElement(arquivos[i].getName());

        }

        lstAvatares.setModel(itens);
        lstAvatares.setSelectedIndex(itemSelecionado);

    }

    /**
     * Preenche os componentes da tela de acordo com o idioma selecionado
     */
    public final void CarregaIdioma() {
        //Nomeia os componentes conforme o idioma selecionado
        opcaoSimNao = new Object[]{idioma.Valor("sim"), idioma.Valor("nao")};
        opcaoSimNaoCancelar = new Object[]{idioma.Valor("sim"), idioma.Valor("nao"), idioma.Valor("btnCancelar")};

        //Botões
        btnNovaSituacao.setText(idioma.Valor("btnNovaSituacao"));
        btnAjudaAvaliacoes.setText(idioma.Valor("btnAjuda"));
        btnAjudaSituacoes.setText(idioma.Valor("btnAjuda"));
        btnAjudaVariaveis.setText(idioma.Valor("btnAjuda"));
        btnEditarSituacao.setText(idioma.Valor("btnEditarSituacao"));
        btnPreviaSituacao.setText(idioma.Valor("btnPrevia"));
        btnExcluirSituacao.setText(idioma.Valor("btnExcluirSituacao"));
        btnNovaVariavel.setText(idioma.Valor("btnNovaVariavel"));
        btnEditarVariavel.setText(idioma.Valor("btnEditarVariavel"));
        btnExcluirVariavel.setText(idioma.Valor("btnExcluirVariavel"));
        btnNovaAvaliacao.setText(idioma.Valor("btnNovaAvaliacao"));
        btnEditarAvaliacao.setText(idioma.Valor("btnEditarAvaliacao"));
        btnExcluirAvaliacao.setText(idioma.Valor("btnExcluirAvaliacao"));
        //Label
        lblTitulo.setText(idioma.Valor("tituloDesenvPartidaNova"));

        //abas
        painelConfiguracoes.setTitleAt(0, idioma.Valor("abaAssistente"));
        painelConfiguracoes.setTitleAt(1, idioma.Valor("abaSituacoes"));
        painelConfiguracoes.setTitleAt(2, idioma.Valor("abaVariaveis"));
        painelConfiguracoes.setTitleAt(3, idioma.Valor("abaAvaliacoes"));

        //assistente
        lblApresentacao.setText(idioma.Valor("lblApresentacao"));
        lblSelecioneAvatar.setText(idioma.Valor("lblSelecioneAvatar"));
        lblNomeAssistente.setText(idioma.Valor("lblNomeAssistente"));
        btnAjudaAssistente.setText(idioma.Valor("btnAjuda"));
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

        if (!partidaDesenvolvimento.getSituacoes().isEmpty()) {
            tblSituacoes.setRowSelectionInterval(0, 0);
        }

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

        //Selecionar o primeiro item
        if (!partidaDesenvolvimento.getVariaveis().isEmpty()) {
            tblVariaveis.setRowSelectionInterval(0, 0);
        }

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

        //Selecionar o primeiro item
        if (!partidaDesenvolvimento.getAvaliacoes().isEmpty()) {
            tblAvaliacoes.setRowSelectionInterval(0, 0);
        }
    }

    /**
     * Atualiza as informações do assiste
     */
    public final void AtualizaAssistente() {
        txtNomeAssistente.setText(partidaDesenvolvimento.getAssistente().getNome());
        txaApresentacao.setText(partidaDesenvolvimento.getAssistente().getApresentacao());
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
        int opcao = JOptionPane.showConfirmDialog(null, mensagem, idioma.Valor("aviso"), JOptionPane.YES_NO_OPTION);

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

    public void SalvarPartida() {
        boolean continuar = true;
        String mensagem = "";
        ArrayList<String> mensagens = new ArrayList<>();

        if (txtNomeAssistente.getText().isEmpty()) {
            continuar = false;
            mensagem = "msgNomeAssistenteObrigatorio";
            mensagens.add(mensagem);
        }

        if (txaApresentacao.getText().isEmpty()) {
            continuar = false;
            mensagem = "msgApresentacaoObrigatoria";
            mensagens.add(mensagem);
        }

        if (continuar) {
            partidaDesenvolvimento.getAssistente().setNome(txtNomeAssistente.getText());
            partidaDesenvolvimento.getAssistente().setAvatarAssistente(avatarSelecionado.getDescription());
            partidaDesenvolvimento.getAssistente().setApresentacao(txaApresentacao.getText());
            
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
            String mensagemJanela = "";

            for (String s : mensagens) {
                mensagemJanela += s + "\n";
            }

            JOptionPane.showMessageDialog(this, mensagemJanela, idioma.Valor("aviso"), JOptionPane.OK_OPTION);
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
        int opcao = JOptionPane.showConfirmDialog(null, mensagem, idioma.Valor("aviso"), JOptionPane.YES_NO_OPTION);

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

            for (int i : itensRemover) {
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
        int opcao = JOptionPane.showConfirmDialog(null, mensagem, idioma.Valor("aviso"), JOptionPane.YES_NO_OPTION);

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
        painelConfiguracoes = new javax.swing.JTabbedPane();
        abaAssistente = new javax.swing.JPanel();
        lblImgAssistente = new javax.swing.JLabel();
        btnAjudaAssistente = new javax.swing.JButton();
        lblNomeAssistente = new javax.swing.JLabel();
        txtNomeAssistente = new javax.swing.JTextField();
        lblApresentacao = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txaApresentacao = new javax.swing.JTextArea();
        lblSelecioneAvatar = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        lstAvatares = new javax.swing.JList();
        abaSituacoes = new javax.swing.JPanel();
        btnNovaSituacao = new javax.swing.JButton();
        btnEditarSituacao = new javax.swing.JButton();
        btnExcluirSituacao = new javax.swing.JButton();
        btnPreviaSituacao = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSituacoes = new javax.swing.JTable();
        btnAjudaSituacoes = new javax.swing.JButton();
        abaVariaveis = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblVariaveis = new javax.swing.JTable();
        btnNovaVariavel = new javax.swing.JButton();
        btnEditarVariavel = new javax.swing.JButton();
        btnExcluirVariavel = new javax.swing.JButton();
        btnAjudaVariaveis = new javax.swing.JButton();
        abaAvaliacoes = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblAvaliacoes = new javax.swing.JTable();
        btnAjudaAvaliacoes = new javax.swing.JButton();
        btnNovaAvaliacao = new javax.swing.JButton();
        btnEditarAvaliacao = new javax.swing.JButton();
        btnExcluirAvaliacao = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        mniSalvar = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mniIdioma = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
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
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        lblImgAssistente.setText("imgAssistente");

        btnAjudaAssistente.setText("btnAjuda");

        lblNomeAssistente.setText("Nome do Assistente:");

        txtNomeAssistente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeAssistenteActionPerformed(evt);
            }
        });

        lblApresentacao.setText("Apresentação:");

        txaApresentacao.setColumns(20);
        txaApresentacao.setLineWrap(true);
        txaApresentacao.setRows(5);
        jScrollPane4.setViewportView(txaApresentacao);

        lblSelecioneAvatar.setText("Selecione um Avatar:");

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
        jScrollPane5.setViewportView(lstAvatares);

        javax.swing.GroupLayout abaAssistenteLayout = new javax.swing.GroupLayout(abaAssistente);
        abaAssistente.setLayout(abaAssistenteLayout);
        abaAssistenteLayout.setHorizontalGroup(
            abaAssistenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaAssistenteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(abaAssistenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(abaAssistenteLayout.createSequentialGroup()
                        .addComponent(lblImgAssistente, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(abaAssistenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblNomeAssistente)
                            .addComponent(lblApresentacao))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(abaAssistenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNomeAssistente)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(btnAjudaAssistente))
                    .addGroup(abaAssistenteLayout.createSequentialGroup()
                        .addComponent(lblSelecioneAvatar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        abaAssistenteLayout.setVerticalGroup(
            abaAssistenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaAssistenteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(abaAssistenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImgAssistente, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(abaAssistenteLayout.createSequentialGroup()
                        .addGroup(abaAssistenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAjudaAssistente)
                            .addComponent(lblNomeAssistente)
                            .addComponent(txtNomeAssistente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(abaAssistenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblApresentacao)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(abaAssistenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSelecioneAvatar))
                .addGap(39, 39, 39))
        );

        painelConfiguracoes.addTab("abaAssistente", abaAssistente);

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

        btnExcluirSituacao.setText("btnExcluirSituacao");
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

        btnAjudaSituacoes.setText("btnAjuda");

        javax.swing.GroupLayout abaSituacoesLayout = new javax.swing.GroupLayout(abaSituacoes);
        abaSituacoes.setLayout(abaSituacoesLayout);
        abaSituacoesLayout.setHorizontalGroup(
            abaSituacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaSituacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(abaSituacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(abaSituacoesLayout.createSequentialGroup()
                        .addComponent(btnNovaSituacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarSituacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExcluirSituacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                        .addComponent(btnPreviaSituacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAjudaSituacoes)))
                .addContainerGap())
        );
        abaSituacoesLayout.setVerticalGroup(
            abaSituacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaSituacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(abaSituacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovaSituacao)
                    .addComponent(btnEditarSituacao)
                    .addComponent(btnExcluirSituacao)
                    .addComponent(btnPreviaSituacao)
                    .addComponent(btnAjudaSituacoes))
                .addContainerGap())
        );

        painelConfiguracoes.addTab("abaSituacoes", abaSituacoes);

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

        btnAjudaVariaveis.setText("btnAjuda");

        javax.swing.GroupLayout abaVariaveisLayout = new javax.swing.GroupLayout(abaVariaveis);
        abaVariaveis.setLayout(abaVariaveisLayout);
        abaVariaveisLayout.setHorizontalGroup(
            abaVariaveisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaVariaveisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(abaVariaveisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                    .addGroup(abaVariaveisLayout.createSequentialGroup()
                        .addComponent(btnNovaVariavel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarVariavel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExcluirVariavel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAjudaVariaveis)))
                .addContainerGap())
        );
        abaVariaveisLayout.setVerticalGroup(
            abaVariaveisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaVariaveisLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(abaVariaveisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovaVariavel)
                    .addComponent(btnEditarVariavel)
                    .addComponent(btnExcluirVariavel)
                    .addComponent(btnAjudaVariaveis))
                .addContainerGap())
        );

        painelConfiguracoes.addTab("abaVariaveis", abaVariaveis);

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

        btnAjudaAvaliacoes.setText("btnAjuda");

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

        javax.swing.GroupLayout abaAvaliacoesLayout = new javax.swing.GroupLayout(abaAvaliacoes);
        abaAvaliacoes.setLayout(abaAvaliacoesLayout);
        abaAvaliacoesLayout.setHorizontalGroup(
            abaAvaliacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaAvaliacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(abaAvaliacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(abaAvaliacoesLayout.createSequentialGroup()
                        .addComponent(btnNovaAvaliacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarAvaliacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExcluirAvaliacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 161, Short.MAX_VALUE)
                        .addComponent(btnAjudaAvaliacoes)))
                .addContainerGap())
        );
        abaAvaliacoesLayout.setVerticalGroup(
            abaAvaliacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, abaAvaliacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(abaAvaliacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExcluirAvaliacao)
                    .addComponent(btnEditarAvaliacao)
                    .addComponent(btnNovaAvaliacao)
                    .addComponent(btnAjudaAvaliacoes))
                .addContainerGap())
        );

        painelConfiguracoes.addTab("abaAvaliacoes", abaAvaliacoes);

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitulo.setText("lblTitulo");

        jMenu1.setText("mniArquivo");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("mniAbrirJogar");
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("mniAbrirEditar");
        jMenu1.add(jMenuItem3);

        mniSalvar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        mniSalvar.setText("mniSalvar");
        mniSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniSalvarActionPerformed(evt);
            }
        });
        jMenu1.add(mniSalvar);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("mniSalvarComo");
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("mniConfigurar");

        mniIdioma.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        mniIdioma.setText("mniIdioma");
        mniIdioma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniIdiomaActionPerformed(evt);
            }
        });
        jMenu2.add(mniIdioma);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("mniAjuda");

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("mniManualUtilizacao");
        jMenu3.add(jMenuItem4);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem7.setText("mniProjetosExemploEditar");
        jMenu3.add(jMenuItem7);

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem8.setText("mniProjetosExemploJogar");
        jMenu3.add(jMenuItem8);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(painelConfiguracoes))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(painelConfiguracoes)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (!partidaSalva) {
            int selecionado = JOptionPane.showOptionDialog(null, idioma.Valor("msgDesejaSalvar"),
                    idioma.Valor("aviso"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, opcaoSimNaoCancelar, opcaoSimNaoCancelar[0]);
            switch (selecionado) {
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

    private void mniIdiomaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniIdiomaActionPerformed
        JanelaTrocaIdioma jti = new JanelaTrocaIdioma();
        jti.setVisible(true);
    }//GEN-LAST:event_mniIdiomaActionPerformed

    private void txtNomeAssistenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeAssistenteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeAssistenteActionPerformed

    private void lstAvataresValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstAvataresValueChanged
        int index = lstAvatares.getSelectedIndex();
        if (index > -1) {
            ImageIcon icone = new ImageIcon();
            icone.setImage(avatares.get(index).getImage().getScaledInstance(150, 150, 150));
            lblImgAssistente.setText(null);
            lblImgAssistente.setIcon(icone);
            avatarSelecionado = avatares.get(index);

        }
    }//GEN-LAST:event_lstAvataresValueChanged

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
    private javax.swing.JPanel abaAssistente;
    private javax.swing.JPanel abaAvaliacoes;
    private javax.swing.JPanel abaSituacoes;
    private javax.swing.JPanel abaVariaveis;
    private javax.swing.JButton btnAjudaAssistente;
    private javax.swing.JButton btnAjudaAvaliacoes;
    private javax.swing.JButton btnAjudaSituacoes;
    private javax.swing.JButton btnAjudaVariaveis;
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
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JPopupMenu jPopupMenu3;
    private javax.swing.JPopupMenu jPopupMenu4;
    private javax.swing.JPopupMenu jPopupMenu5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblApresentacao;
    private javax.swing.JLabel lblImgAssistente;
    private javax.swing.JLabel lblNomeAssistente;
    private javax.swing.JLabel lblSelecioneAvatar;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JList lstAvatares;
    private javax.swing.JMenuItem mniIdioma;
    private javax.swing.JMenuItem mniSalvar;
    private javax.swing.JTabbedPane painelConfiguracoes;
    private javax.swing.JTable tblAvaliacoes;
    private javax.swing.JTable tblSituacoes;
    private javax.swing.JTable tblVariaveis;
    private javax.swing.JTextArea txaApresentacao;
    private javax.swing.JTextField txtNomeAssistente;
    // End of variables declaration//GEN-END:variables
}
