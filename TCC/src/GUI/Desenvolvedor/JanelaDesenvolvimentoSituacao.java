/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Desenvolvedor;

import Controle.ControladoraAjuda;
import Controle.ControladoraIdioma;
import GUI.Suporte.LimiteCaracteres;
import GUI.Suporte.SaidasNumericasTbModel;
import GUI.Suporte.SaidasOpcionaisTbModel;
import Modelo.Avaliacao;
import Modelo.Faixa;
import Modelo.Partida;
import Modelo.Saida;
import Modelo.SaidaNumerica;
import Modelo.SaidaOpcional;
import Modelo.Situacao;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Rafael
 */
public class JanelaDesenvolvimentoSituacao extends javax.swing.JDialog {

    private Object[] opcaoSimNao;
    private JFileChooser fileChooser;
    private final Situacao situacao;
    private final Partida partidaDesenvolvimento;

    private ArrayList<ImageIcon> avatares;

    private final Saida saida;

    private final ControladoraIdioma idioma;

    //1. Inserir, 2. Editar
    private int acao;

    private final int ordem;

    //private static JanelaDesenvolvimentoSituacao instancia;
    JanelaDesenvolvimentoPartida jdp;

    private boolean carregando;

    /**
     * Creates new form JanelaNovaSituacao
     *
     * @param acao
     * @param situacao
     * @param ordem
     */
    public JanelaDesenvolvimentoSituacao(int acao, Situacao situacao, int ordem) {
        carregando = true;
        initComponents();
        setModal(true);
        this.acao = acao;
        this.ordem = ordem;

        idioma = ControladoraIdioma.getInstancia();
        CarregaIdioma();

        jdp = JanelaDesenvolvimentoPartida.getInstancia();

        setLocationRelativeTo(jdp);

        partidaDesenvolvimento = Partida.getInstancia();

        txaFalaAssistente.setDocument(new LimiteCaracteres(750));
        txaFalaAssistente.setWrapStyleWord(true);

        if (acao == 2) {
            this.situacao = situacao;
            saida = situacao.getSaida();

            setTitle(situacao.getNome());

            CarregarSituacao();
            CarregaAvatares();
            carregando = false;
        } else {
            //Por default seleciona o lado do assistente como direito
            rbtDireito.setSelected(true);

            setTitle(idioma.Valor("tituloNovaSituacao"));

            this.situacao = new Situacao();
            saida = new Saida();
            saida.setTipoSaida(1);
            this.situacao.setSaida(saida);
            carregando = false;
            CarregaAvatares();
        }

        CarregarComboTipoSaida();
        AtualizaTabelaSaidas();

    }

    public final void CarregaIdioma() {
        opcaoSimNao = new Object[]{idioma.Valor("sim"), idioma.Valor("nao")};
        
        btnCancelar.setText(idioma.Valor("btnCancelar"));
        btnConfirmar.setText(idioma.Valor("btnConfirmar"));
        btnAjuda.setText(idioma.Valor("btnAjuda"));
        btnSelecionarImagem.setText(idioma.Valor("btnSelecionarImagem"));
        btnNovaSaida.setText(idioma.Valor("btnNovaSaida"));
        btnEditarSaida.setText(idioma.Valor("btnEditarSaida"));
        btnExcluirSaida.setText(idioma.Valor("btnExcluirSaida"));
        chbSituacaoFinal.setText(idioma.Valor("lblSituacaoFinal"));
        lblNome.setText(idioma.Valor("lblNome"));
        lblDescricaoDaSituacao.setText(idioma.Valor("lblDescricaoDaSituacao"));
        lblSaidasDessaSituacao.setText(idioma.Valor("lblSaidasDessaSituacao"));
        lblFalaAssistente.setText(idioma.Valor("lblFalaAssistente"));
        lblImgFundo.setText(idioma.Valor("lblImgFundo"));
        lblTipoSaida.setText(idioma.Valor("lblTipoSaida"));
        lblResolucaoIdeal.setText(idioma.Valor("lblResolucaoIdeal"));
        lblTitulo.setText(idioma.Valor("tituloDesenvolvimentoSituacao"));
        rbtDireito.setText(idioma.Valor("lblDireito"));
        rbtEsquerdo.setText(idioma.Valor("lblEsquerdo"));
        lblGerarNoLado.setText(idioma.Valor("lblGerarNoLado"));
        lblPersonagemDaSituacao.setText(idioma.Valor("lblPersonagemDaSituacao"));

        setTitle(idioma.Valor("tituloDesenvolvimentoSituacao"));
    }

    /**
     * Carrega os avatares do assistente
     */
    public final void CarregaAvatares() {

        int itemSelecionado = 0;
        DefaultListModel itens = new DefaultListModel<>();
        avatares = new ArrayList<>();

        //Recupera a quantidade de avatares disponiveis
        File file = new File("Arquivos/Avatares");
        File arquivos[] = file.listFiles();

        String avatarSituacaoAnterior = "";

        for (int i = 0; i < arquivos.length; i++) {

            if (ordem > 1) {
                Situacao situacaoAnterior = partidaDesenvolvimento.getSituacoes().get(ordem - 2);
                avatarSituacaoAnterior = situacaoAnterior.getImagemPersonagem().getDescription();
            }

            ImageIcon avatar = new ImageIcon(arquivos[i].getAbsolutePath());
            avatar.setDescription(arquivos[i].getName());
            avatares.add(avatar);
            if (situacao.getImagemPersonagem() != null) {
                if (situacao.getImagemPersonagem().getDescription().equals(avatar.getDescription())) {
                    itemSelecionado = i;

                }
            } else {
                if (avatarSituacaoAnterior.equals(avatar.getDescription())) {
                    itemSelecionado = i;
                }
            }
            itens.addElement(arquivos[i].getName());

        }

        lstAvatares.setModel(itens);
        lstAvatares.setSelectedIndex(itemSelecionado);

    }

    public final void CarregarComboTipoSaida() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addElement(idioma.Valor("lblOpcional"));
        model.addElement(idioma.Valor("lblNumerica"));

        cbxTipoSaida.setModel(model);

        if (saida.getTipoSaida() > 0) {
            cbxTipoSaida.setSelectedIndex(saida.getTipoSaida() - 1);
        }
    }

    public final void CarregarSituacao() {
        txtNomeSituacao.setText(situacao.getNome());
        txaFalaAssistente.setText(situacao.getFalaAssistente());
        if (situacao.getFundoSituacao() != null) {
            txtArquivo.setText(situacao.getFundoSituacao().getDescription());
        }
        chbSituacaoFinal.setSelected(situacao.isSituacaoFinal());

        rbtEsquerdo.setSelected(situacao.getLadoGeracao() == 1);
        rbtDireito.setSelected(situacao.getLadoGeracao() == 2);

        botaoRotacionar.setSelected(situacao.isRotacionarPersonagem());
        imgAvatar.setText(null);
        ImageIcon icone = new ImageIcon();
        icone.setImage(situacao.getImagemPersonagem().getImage().getScaledInstance(100, 100, 100));
        imgAvatar.setIcon(icone);

        AtualizaTabelaSaidas();

    }

    public final void AtualizaTabelaSaidas() {

        //Atualiza a tabela conforme o tipo de saída
        boolean naoHaSaidas = saida.getSaidasNumerica().isEmpty() && saida.getSaidasOpcao().isEmpty();

        //Habilitar o combobox apenas quando não houverem saídas cadastradas
        cbxTipoSaida.setEnabled(naoHaSaidas);

        //Habilitar para marcar como situação final apenas quando não hoverem saídas
        chbSituacaoFinal.setEnabled(naoHaSaidas);

        btnEditarSaida.setEnabled(!(saida.getSaidasOpcao().isEmpty() && saida.getSaidasNumerica().isEmpty()));
        btnExcluirSaida.setEnabled(!(saida.getSaidasOpcao().isEmpty() && saida.getSaidasNumerica().isEmpty()));

        switch (saida.getTipoSaida()) {
            case 1:

                SaidasOpcionaisTbModel modelSO = new SaidasOpcionaisTbModel(saida.getSaidasOpcao());
                tblSaidas.setModel(modelSO);
                cbxTipoSaida.setSelectedIndex(0);

                break;
            case 2:

                SaidasNumericasTbModel modelSN = new SaidasNumericasTbModel(saida.getSaidasNumerica());
                tblSaidas.setModel(modelSN);
                cbxTipoSaida.setSelectedIndex(1);

                break;
        }

        //Esconder a coluna contendo o objeto da saída
        tblSaidas.getColumnModel().getColumn(0).setMinWidth(0);
        tblSaidas.getColumnModel().getColumn(0).setMaxWidth(0);
        tblSaidas.getColumnModel().getColumn(0).setPreferredWidth(0);

        if (!naoHaSaidas) {
            tblSaidas.setRowSelectionInterval(0, 0);
            chbSituacaoFinal.setSelected(false);
        }
    }

    /**
     * Excluir a saída selecionada
     */
    public void ExcluirSaida() {
        boolean continuar = false;
        int opcao = JOptionPane.showOptionDialog(null, idioma.Valor("msgExclusaoSaida"), idioma.Valor("aviso"),
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcaoSimNao, opcaoSimNao[0]);

        if (opcao == 0) {

            continuar = true;

        }

        if (continuar) {

            int index = tblSaidas.getSelectedRow();
            ArrayList<Integer> itensRemover = new ArrayList<>();
            int cont;

            switch (saida.getTipoSaida()) {
                case 0: //Não há tipo de saída definido, não faz nada

                    break;

                case 1:

                    SaidaOpcional saidaO = (SaidaOpcional) tblSaidas.getValueAt(index, 0);

                    //Excluir todas as avaliações envolvendo a variável
                    cont = 0;
                    for (Avaliacao a : partidaDesenvolvimento.getAvaliacoes()) {
                        if (a.getVariavel() == saidaO.getVariavelSaida()) {
                            itensRemover.add(cont);

                        }
                        cont++;
                    }
                    for (int i : itensRemover) {
                        partidaDesenvolvimento.getAvaliacoes().remove(i);
                    }

                    partidaDesenvolvimento.getVariaveis().remove(saidaO.getVariavelSaida());

                    saida.getsaidasOpcao().remove(saidaO);
                    break;

                case 2:

                    SaidaNumerica saidaN = (SaidaNumerica) tblSaidas.getValueAt(index, 0);
                    saida.getSaidasNumerica().remove(saidaN);
                    break;
            }
        }
        AtualizaTabelaSaidas();

    }

    /**
     * Salva a situação inserida/editada
     *
     * @param fecharJanela
     * @return
     */
    public boolean SalvarSituacao(boolean fecharJanela) {

        boolean ok = ValidarDados();

        if (ok) {

            situacao.setFalaAssistente(txaFalaAssistente.getText());
            situacao.setNome(txtNomeSituacao.getText());
            situacao.setSituacaoFinal(chbSituacaoFinal.isSelected());

            if (rbtEsquerdo.isSelected()) {
                situacao.setLadoGeracao(1);
            } else {
                situacao.setLadoGeracao(2);
            }

//            novaOrdem = (int) jspOrdem.getValue();
            //Caso não tenha fundo, torna o fundo vazio
            if (txtArquivo.getText().isEmpty()) {
                situacao.setFundoSituacao(null);
            }

            //Caso a ação seja iserir, adiciona a situação à lista de situações da partida
            if (acao == 1) {
                partidaDesenvolvimento.getSituacoes().add(situacao);
            }

            jdp.AtualizarDados();

            if (fecharJanela) {
                dispose();
            }

        }

        return ok;

    }

    /**
     * Validação dos dados
     *
     * @return
     */
    public boolean ValidarDados() {

        boolean ok = true;
        String mensagem;
        ArrayList<String> mensagens = new ArrayList<>();

        //Caso a saída seja numérica, valida os valores
        if (situacao.getSaida().getTipoSaida() == 2) {
            ArrayList<String> mensagensErro = ValidarValoresSN();

            if (!mensagensErro.isEmpty()) {
                ok = false;
                mensagens.addAll(mensagensErro);
            }
        }

        if (txtNomeSituacao.getText().isEmpty()) {
            ok = false;
            mensagem = idioma.Valor("msgNomeSituacaoObrigatorio");
            mensagens.add(mensagem);
        }
        if (txaFalaAssistente.getText().isEmpty()) {
            ok = false;
            mensagem = idioma.Valor("msgFalaAssistenteObrigatoria");
            mensagens.add(mensagem);
        }

        //Imprime as mensgens
        if (!ok) {
            String mensagemJanela = "<html><center>";

            for (String s : mensagens) {
                mensagemJanela += s + "<br>";
            }

            JOptionPane.showMessageDialog(this, mensagemJanela, idioma.Valor("aviso"), JOptionPane.OK_OPTION);

        } else {

            //Exibe avisos
            if (txtArquivo.getText().isEmpty() && acao == 1) {
                int opcao = JOptionPane.showConfirmDialog(null, idioma.Valor("msgSemImagemFundo"),
                        idioma.Valor("aviso"), JOptionPane.YES_NO_OPTION);

                if (opcao == 1) {

                    ok = false;

                }
            }
        }
        return ok;
    }

    /**
     * Seleciona o lado do assistente personalizado conforme o que está marcado
     * nos radios
     *
     * @param lado
     */
    public void SelecionarLadoAssistenteP(int lado) {

        rbtEsquerdo.setSelected(lado == 1);
        rbtDireito.setSelected(lado == 2);

    }

    /**
     * Valida os valores da saída numérica
     *
     * @return
     */
    public ArrayList<String> ValidarValoresSN() {

        ArrayList<String> mensagens = new ArrayList<>();
        String mensagem;
        boolean emSequencia = false;
        ArrayList<Faixa> sequencias = new ArrayList<>();
        Faixa sequenciaAtual = new Faixa();

        //Recupera o menor e o maior valor
        Integer valorMinimo = 0;
        Integer valorMaximo = 0;
        int cont = 0;

        for (SaidaNumerica s : saida.getSaidasNumerica()) {
            if (cont == 0) {
                valorMinimo = s.getFaixa().getLimiteInferior();
                valorMaximo = s.getFaixa().getLimiteSuperior();
            } else {
                if (s.getFaixa().getLimiteInferior() < valorMinimo) {
                    valorMinimo = s.getFaixa().getLimiteInferior();
                }
                if (s.getFaixa().getLimiteSuperior() > valorMaximo) {
                    valorMaximo = s.getFaixa().getLimiteSuperior();
                }
            }
            cont++;
        }

        //Verifica se o valor pertence a alguma faixa
        for (int valor = valorMinimo; valor < valorMaximo; valor++) {

            boolean temSaida = false;
            for (SaidaNumerica s : saida.getSaidasNumerica()) {

                if (valor >= s.getFaixa().getLimiteInferior() && valor <= s.getFaixa().getLimiteSuperior()) {
                    temSaida = true;

                    //Caso tenha sequencia em aberto, fecha
                    if (emSequencia) {
                        sequencias.add(sequenciaAtual);
                    }

                    emSequencia = false;

                    //Assume o valor como o ultimo da faixa, uma vez que não há necessidade de testar os de dentro da faixa
                    valor = s.getFaixa().getLimiteSuperior();
                    break;
                }

            }

            if (!temSaida) {

                if (emSequencia) {

                    //Cresce a sequência
                    sequenciaAtual.setLimiteSuperior(valor);

                } else {

                    //Cria uma nova sequência
                    sequenciaAtual = new Faixa();
                    sequenciaAtual.setLimiteInferior(valor);
                    sequenciaAtual.setLimiteSuperior(valor);
                    emSequencia = true;

                }
            }
        }

        for (Faixa f : sequencias) {

            int diferenca = f.getLimiteSuperior() - f.getLimiteInferior();

            switch (diferenca) {
                case 0: //Apenas um valor fora da Sequência
                    mensagem = idioma.Valor("msgValUnicoForaSeq1") + " "
                            + f.getLimiteSuperior() + " "
                            + idioma.Valor("msgValUnicoForaSeq2");
                    break;

                case 1: //Sequência com dois valores
                    mensagem = idioma.Valor("msgValForaSeq1") + " "
                            + f.getLimiteInferior() + " "
                            + idioma.Valor("msgValForaSeqE") + " "
                            + f.getLimiteSuperior() + " "
                            + idioma.Valor("msgValForaSeq2");
                    break;

                default: //Sequência com mais de dois valores
                    mensagem = idioma.Valor("msgValForaSeq1") + " "
                            + f.getLimiteInferior() + " "
                            + idioma.Valor("msgValForaSeqA") + " "
                            + f.getLimiteSuperior() + " "
                            + idioma.Valor("msgValForaSeq2");
                    break;
            }
            mensagens.add(mensagem);

        }

        return mensagens;
    }

    public void SelecionarAvatar() {
        if (!carregando) {
            botaoRotacionar.setSelected(false);
            situacao.setRotacionarPersonagem(false);
            int index = lstAvatares.getSelectedIndex();
            if (index > -1) {
                imgAvatar.setText(null);
                situacao.setImagemPersonagem(avatares.get(index));
                ImageIcon icone = new ImageIcon();
                icone.setImage(situacao.getImagemPersonagem().getImage().getScaledInstance(100, 100, 100));
                imgAvatar.setIcon(icone);
            }
        }
    }

    public void EditarSaida() {
        JanelaDesenvolvimentoSaida jds;

        switch (saida.getTipoSaida()) {
            case 1:
                SaidaOpcional saidaOpcional = (SaidaOpcional) tblSaidas.getValueAt(tblSaidas.getSelectedRow(), 0);
                jds = new JanelaDesenvolvimentoSaida(this, 2, situacao, saidaOpcional);
                jds.setVisible(true);
                break;
            case 2:
                SaidaNumerica saidaNumerica = (SaidaNumerica) tblSaidas.getValueAt(tblSaidas.getSelectedRow(), 0);
                jds = new JanelaDesenvolvimentoSaida(this, 2, situacao, saidaNumerica);
                jds.setVisible(true);
                break;
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        buttonGroup1 = new javax.swing.ButtonGroup();
        btnConfirmar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        btnAjuda = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        lblDescricaoDaSituacao = new javax.swing.JLabel();
        lblNome = new javax.swing.JLabel();
        lblFalaAssistente = new javax.swing.JLabel();
        lblImgFundo = new javax.swing.JLabel();
        txtNomeSituacao = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaFalaAssistente = new javax.swing.JTextArea();
        txtArquivo = new javax.swing.JTextField();
        btnSelecionarImagem = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSaidas = new javax.swing.JTable();
        lblTipoSaida = new javax.swing.JLabel();
        cbxTipoSaida = new javax.swing.JComboBox();
        btnNovaSaida = new javax.swing.JButton();
        btnEditarSaida = new javax.swing.JButton();
        btnExcluirSaida = new javax.swing.JButton();
        lblResolucaoIdeal = new javax.swing.JLabel();
        chbSituacaoFinal = new javax.swing.JCheckBox();
        pnlAssistenteP = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstAvatares = new javax.swing.JList();
        imgAvatar = new javax.swing.JLabel();
        lblPersonagemDaSituacao = new javax.swing.JLabel();
        botaoRotacionar = new javax.swing.JToggleButton();
        lblSaidasDessaSituacao = new javax.swing.JLabel();
        lblGerarNoLado = new javax.swing.JLabel();
        rbtDireito = new javax.swing.JRadioButton();
        rbtEsquerdo = new javax.swing.JRadioButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

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

        btnAjuda.setText("btnAjuda");
        btnAjuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAjudaActionPerformed(evt);
            }
        });

        jLabel6.setText("<html>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|<br>|");

        lblDescricaoDaSituacao.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblDescricaoDaSituacao.setText("lblDescricaoDaSituacao");

        lblNome.setText("lblNome");

        lblFalaAssistente.setText("lblFalaAssistente");

        lblImgFundo.setText("lblImgFundo");

        txaFalaAssistente.setColumns(20);
        txaFalaAssistente.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        txaFalaAssistente.setLineWrap(true);
        txaFalaAssistente.setRows(5);
        txaFalaAssistente.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txaFalaAssistente);

        txtArquivo.setEnabled(false);

        btnSelecionarImagem.setText("btnSelecionarImagem");
        btnSelecionarImagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionarImagemActionPerformed(evt);
            }
        });

        tblSaidas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblSaidas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSaidasMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblSaidas);

        lblTipoSaida.setText("lblTipoSaida");

        cbxTipoSaida.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxTipoSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTipoSaidaActionPerformed(evt);
            }
        });

        btnNovaSaida.setText("btnNovaSaida");
        btnNovaSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovaSaidaActionPerformed(evt);
            }
        });

        btnEditarSaida.setText("btnEditarSaida");
        btnEditarSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarSaidaActionPerformed(evt);
            }
        });

        btnExcluirSaida.setText("btnExcluirSaida");
        btnExcluirSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirSaidaActionPerformed(evt);
            }
        });

        lblResolucaoIdeal.setText("lblResolucaoIdeal");

        chbSituacaoFinal.setText("lblSituacaoFinal");

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
        jScrollPane3.setViewportView(lstAvatares);

        imgAvatar.setText("imgAvatar");

        lblPersonagemDaSituacao.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblPersonagemDaSituacao.setText("lblPersonagemDaSituacao");

        botaoRotacionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/iconeRotacionar.png"))); // NOI18N
        botaoRotacionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoRotacionarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAssistentePLayout = new javax.swing.GroupLayout(pnlAssistenteP);
        pnlAssistenteP.setLayout(pnlAssistentePLayout);
        pnlAssistentePLayout.setHorizontalGroup(
            pnlAssistentePLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAssistentePLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAssistentePLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAssistentePLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(pnlAssistentePLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(imgAvatar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(botaoRotacionar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(lblPersonagemDaSituacao))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlAssistentePLayout.setVerticalGroup(
            pnlAssistentePLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAssistentePLayout.createSequentialGroup()
                .addComponent(lblPersonagemDaSituacao)
                .addGroup(pnlAssistentePLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAssistentePLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(imgAvatar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botaoRotacionar))
                    .addGroup(pnlAssistentePLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        lblSaidasDessaSituacao.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSaidasDessaSituacao.setText("lblSaidasDessaSituacao");

        lblGerarNoLado.setText("<html><p align='center'>lblGerarNoLado");

        rbtDireito.setText("lblDireito");
        rbtDireito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtDireitoActionPerformed(evt);
            }
        });

        rbtEsquerdo.setText("lblEsquerdo");
        rbtEsquerdo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtEsquerdoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAjuda))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblDescricaoDaSituacao)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lblNome)
                                            .addComponent(lblFalaAssistente)
                                            .addComponent(lblImgFundo))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnSelecionarImagem)
                                                .addGap(18, 18, 18)
                                                .addComponent(lblResolucaoIdeal, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE))
                                            .addComponent(txtNomeSituacao)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
                                            .addComponent(txtArquivo)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(chbSituacaoFinal)
                                                .addGap(0, 0, Short.MAX_VALUE)))))
                                .addGap(10, 10, 10))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblGerarNoLado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(169, 169, 169)
                                        .addComponent(rbtEsquerdo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rbtDireito))
                                    .addComponent(btnCancelar))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblSaidasDessaSituacao)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(pnlAssistenteP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(lblTipoSaida)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cbxTipoSaida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnNovaSaida))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(btnEditarSaida)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnExcluirSaida)))
                                        .addGap(15, 15, 15))
                                    .addComponent(btnConfirmar, javax.swing.GroupLayout.Alignment.TRAILING))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnAjuda)
                            .addComponent(lblTitulo))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblDescricaoDaSituacao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblSaidasDessaSituacao)
                                .addGap(19, 19, 19)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblNome)
                                    .addComponent(txtNomeSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFalaAssistente)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblImgFundo)
                                    .addComponent(txtArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnSelecionarImagem)
                                    .addComponent(lblResolucaoIdeal))
                                .addGap(29, 29, 29)
                                .addComponent(chbSituacaoFinal)
                                .addGap(19, 19, 19)
                                .addComponent(lblGerarNoLado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rbtEsquerdo)
                                    .addComponent(rbtDireito)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnNovaSaida)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cbxTipoSaida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblTipoSaida)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnExcluirSaida)
                                    .addComponent(btnEditarSaida))
                                .addGap(32, 32, 32)
                                .addComponent(pnlAssistenteP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addComponent(btnConfirmar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelar)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSelecionarImagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionarImagemActionPerformed

        fileChooser = new javax.swing.JFileChooser();

        fileChooser.setDialogTitle(idioma.Valor("lblSelImagem"));
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory() || file.getAbsolutePath().endsWith(".jpg") || file.getAbsolutePath().endsWith(".png") || file.getAbsolutePath().endsWith(".bmp") || file.getAbsolutePath().endsWith(".gif");
            }

            @Override
            public String getDescription() {
                return idioma.Valor("descricaoArquivosImagem") + " (*.bmp, *.gif, *.jpg, *.png)";
            }
        });

        int returnVal = fileChooser.showDialog(this, idioma.Valor("btnSelecionar"));
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                txtArquivo.setText(file.getName());

                Image image = ImageIO.read(file);
                ImageIcon imagec = new ImageIcon();
                imagec.setImage(image);
                imagec.setDescription(file.getName());

                situacao.setFundoSituacao(imagec);

            } catch (Exception ex) {
                System.out.println("problem accessing file" + file.getAbsolutePath());
            }
        }

    }//GEN-LAST:event_btnSelecionarImagemActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        SalvarSituacao(true);
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnEditarSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarSaidaActionPerformed

        EditarSaida();

    }//GEN-LAST:event_btnEditarSaidaActionPerformed

    private void btnNovaSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovaSaidaActionPerformed
        boolean ok = true;

        //Caso esteja no modo insert, salva a situação e altera o modo para edição
        if (acao == 1) {
            ok = SalvarSituacao(false);
        }

        if (ok) {
            acao = 2;
            JanelaDesenvolvimentoSaida jds;
            switch (saida.getTipoSaida()) {
                case 1:
                    SaidaOpcional saidaOpcional = new SaidaOpcional();
                    jds = new JanelaDesenvolvimentoSaida(this, 1, situacao, saidaOpcional);
                    jds.setVisible(true);
                    break;
                case 2:
                    SaidaNumerica saidaNumerica = new SaidaNumerica();
                    jds = new JanelaDesenvolvimentoSaida(this, 1, situacao, saidaNumerica);
                    jds.setVisible(true);
                    break;
            }
        }
    }//GEN-LAST:event_btnNovaSaidaActionPerformed

    private void cbxTipoSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTipoSaidaActionPerformed
        switch (cbxTipoSaida.getSelectedIndex()) {
            case 0:
                saida.setTipoSaida(1);
                break;
            case 1:
                saida.setTipoSaida(2);
                break;
        }
    }//GEN-LAST:event_cbxTipoSaidaActionPerformed

    private void btnExcluirSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirSaidaActionPerformed
        ExcluirSaida();
    }//GEN-LAST:event_btnExcluirSaidaActionPerformed

    private void rbtDireitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtDireitoActionPerformed

        SelecionarLadoAssistenteP(2);

    }//GEN-LAST:event_rbtDireitoActionPerformed

    private void rbtEsquerdoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtEsquerdoActionPerformed

        SelecionarLadoAssistenteP(1);

    }//GEN-LAST:event_rbtEsquerdoActionPerformed

    private void lstAvataresValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstAvataresValueChanged
        SelecionarAvatar();
    }//GEN-LAST:event_lstAvataresValueChanged

    private void tblSaidasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSaidasMouseClicked
        if (evt.getClickCount() == 2) {
            EditarSaida();
        }
    }//GEN-LAST:event_tblSaidasMouseClicked

    private void botaoRotacionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoRotacionarActionPerformed
        situacao.setRotacionarPersonagem(botaoRotacionar.isSelected());
        ImageIcon icone = new ImageIcon();
        icone.setImage(situacao.getImagemPersonagem().getImage().getScaledInstance(100, 100, 100));
        imgAvatar.setIcon(icone);
    }//GEN-LAST:event_botaoRotacionarActionPerformed

    private void btnAjudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjudaActionPerformed
        ControladoraAjuda caj = new ControladoraAjuda();
        caj.ExibirAjuda(this, "DesenvolvimentoSituacao");
    }//GEN-LAST:event_btnAjudaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton botaoRotacionar;
    private javax.swing.JButton btnAjuda;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnEditarSaida;
    private javax.swing.JButton btnExcluirSaida;
    private javax.swing.JButton btnNovaSaida;
    private javax.swing.JButton btnSelecionarImagem;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbxTipoSaida;
    private javax.swing.JCheckBox chbSituacaoFinal;
    private javax.swing.JLabel imgAvatar;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblDescricaoDaSituacao;
    private javax.swing.JLabel lblFalaAssistente;
    private javax.swing.JLabel lblGerarNoLado;
    private javax.swing.JLabel lblImgFundo;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblPersonagemDaSituacao;
    private javax.swing.JLabel lblResolucaoIdeal;
    private javax.swing.JLabel lblSaidasDessaSituacao;
    private javax.swing.JLabel lblTipoSaida;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JList lstAvatares;
    private javax.swing.JPanel pnlAssistenteP;
    private javax.swing.JRadioButton rbtDireito;
    private javax.swing.JRadioButton rbtEsquerdo;
    private javax.swing.JTable tblSaidas;
    private javax.swing.JTextArea txaFalaAssistente;
    private javax.swing.JTextField txtArquivo;
    private javax.swing.JTextField txtNomeSituacao;
    // End of variables declaration//GEN-END:variables
}
