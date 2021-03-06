/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Desenvolvedor;

import Controle.ControladoraAjuda;
import Controle.ControladoraExecucao;
import Controle.ControladoraIdioma;
import GUI.Jogador.JanelaExecucaoPartida;
import GUI.Jogador.JanelaInicial;
import GUI.Suporte.AvaliacoesTbModel;
import GUI.Suporte.SituacoesTbModel;
import GUI.Suporte.VariaveisTbModel;
import Modelo.Acao;
import Modelo.Avaliacao;
import Modelo.ParametrosArquivo;
import Modelo.Partida;
import Modelo.Saida;
import Modelo.SaidaNumerica;
import Modelo.SaidaOpcional;
import Modelo.Situacao;
import Modelo.Variavel;
import Persistencia.IOExportaJAR;
import Persistencia.IOProjetoPartida;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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

    private Object[] opcaoSimNao;
    private Object[] opcaoSimNaoCancelar;

    /**
     * Creates new form JanelaAdministrarJogo
     */
    public JanelaDesenvolvimentoPartida() {

        initComponents();
        setLocationRelativeTo(null);
        partidaDesenvolvimento = Partida.getInstancia();
        idioma = ControladoraIdioma.getInstancia();
        CarregaIdioma();
        AtualizarDados();

        partidaDesenvolvimento.setIdioma(idioma.getIdiomaAtual());
        partidaSalva = true;
        VerificaExemplo();
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
        btnFundoAvaliacoes.setText(idioma.Valor("btnFundoAvaliacoes"));
        btnAvatarAvaliacoes.setText(idioma.Valor("btnAvatarAvaliacoes"));

        //Label
        if (partidaDesenvolvimento.getParametrosArquivo() != null) {
            String nomeArquivo = partidaDesenvolvimento.getParametrosArquivo().getNomeDoArquivo();
            setTitle(idioma.Valor("tituloDesenvPartidaEdicao")
                    + " " + nomeArquivo);
            lblTitulo.setText(idioma.Valor("tituloDesenvPartidaEdicao")
                    + " " + nomeArquivo);
        } else {
            setTitle(idioma.Valor("tituloDesenvPartidaNova"));
            lblTitulo.setText(idioma.Valor("tituloDesenvPartidaNova"));
        }

        //abas
        painelConfiguracoes.setTitleAt(0, idioma.Valor("abaSituacoes"));
        painelConfiguracoes.setTitleAt(1, idioma.Valor("abaVariaveis"));
        painelConfiguracoes.setTitleAt(2, idioma.Valor("abaAvaliacoes"));

        //menus
        menuArquivo.setText(idioma.Valor("mniArquivo"));
        menuItemSalvar.setText(idioma.Valor("mniSalvar"));
        menuItemSalvarComo.setText(idioma.Valor("mniSalvarComo"));
        menuConfigurar.setText(idioma.Valor("mniConfigurar"));
        menuItemIdioma.setText(idioma.Valor("mniIdioma"));
        menuAjuda.setText(idioma.Valor("mniAjuda"));
        menuItemManualUtilizacao.setText(idioma.Valor("mniManualUtilizacao"));
        menuItemProjetosExemploEditar.setText(idioma.Valor("mniProjetosExemploEditar"));
        menuItemProjetosExemploJogar.setText(idioma.Valor("mniProjetosExemploJogar"));
        menuItemSobre.setText(idioma.Valor("mniSobre"));
        menuItemSalvarJogar.setText(idioma.Valor("mniSalvarJogar"));
        mnItemExportarExecutavel.setText(idioma.Valor("mniExportarExecutavel"));
    }

    public final void AtualizarDados() {
        AtualizaSituacoes();
        AtualizaVariaveis();
        AtualizaAvaliacoes();
    }

    public void VerificaExemplo() {
        File arquivoExemplo = new File("Arquivos/Exemplo/exemplo.tcc");
        if (!arquivoExemplo.exists()) {
            menuItemProjetosExemploEditar.setEnabled(false);
            menuItemProjetosExemploJogar.setEnabled(false);
        }
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

        tblSituacoes.getColumnModel().getColumn(1).setMinWidth(20);
        tblSituacoes.getColumnModel().getColumn(1).setMaxWidth(20);
        tblSituacoes.getColumnModel().getColumn(1).setPreferredWidth(20);

        tblSituacoes.getColumnModel().getColumn(2).setMinWidth(20);
        tblSituacoes.getColumnModel().getColumn(2).setMaxWidth(20);
        tblSituacoes.getColumnModel().getColumn(2).setPreferredWidth(20);

        //Habilitar o botão de editar e excluir apenas quando houverem registros na lista
        btnEditarSituacao.setEnabled(!partidaDesenvolvimento.getSituacoes().isEmpty());
        btnExcluirSituacao.setEnabled(!partidaDesenvolvimento.getSituacoes().isEmpty());
        btnPreviaSituacao.setEnabled(!partidaDesenvolvimento.getSituacoes().isEmpty());

        if (!partidaDesenvolvimento.getSituacoes().isEmpty()) {
            tblSituacoes.setRowSelectionInterval(0, 0);
        }

    }

    /**
     * Atualiza a tabela de variáveis da partida
     */
    public final void AtualizaVariaveis() {

        //Separa as variáveis auto-definidas das padrões para que as auto definidas sejam exibidas ao fim da tabela
        ArrayList<Variavel> padroes = new ArrayList<>();
        ArrayList<Variavel> autodefinidas = new ArrayList<>();

        for (Variavel v : partidaDesenvolvimento.getVariaveis()) {
            if (v.isAutodefinida()) {
                autodefinidas.add(v);
            } else {
                padroes.add(v);
            }
        }

        ArrayList<Variavel> todas = new ArrayList();
        todas.addAll(padroes);
        todas.addAll(autodefinidas);

        VariaveisTbModel model = new VariaveisTbModel(todas);

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

            //Caso o primeiro item seja uma variável auto definida, desabilitar o botão editar
            int index = tblVariaveis.getSelectedRow();

            Variavel variavel = (Variavel) tblVariaveis.getValueAt(index, 0);

            btnEditarVariavel.setEnabled(!variavel.isAutodefinida());
            btnExcluirVariavel.setEnabled(!variavel.isAutodefinida());
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
        btnAvatarAvaliacoes.setEnabled(!partidaDesenvolvimento.getAvaliacoes().isEmpty());
        btnFundoAvaliacoes.setEnabled(!partidaDesenvolvimento.getAvaliacoes().isEmpty());

        //Habilitar o botão de nova avaliação apenas se houverem variáveis criadas
        if (partidaDesenvolvimento.getVariaveis().isEmpty()) {
            btnNovaAvaliacao.setEnabled(false);
            btnNovaAvaliacao.setToolTipText(idioma.Valor("msgNaoHaVariaveis"));
        } else {
            btnNovaAvaliacao.setEnabled(true);
            btnNovaAvaliacao.setToolTipText(null);
        }

        //Selecionar o primeiro item
        if (!partidaDesenvolvimento.getAvaliacoes().isEmpty()) {
            tblAvaliacoes.setRowSelectionInterval(0, 0);
        }

        //Mensagens sobre assistente e fundo personalizado
        if (partidaDesenvolvimento.getAvatarDasAvaliacoes() != null) {
            lblArquivoAvatar.setText(partidaDesenvolvimento.getAvatarDasAvaliacoes().getDescription());
        } else {
            if (btnAvatarAvaliacoes.isEnabled()) {
                lblArquivoAvatar.setText(idioma.Valor("lblUsandoAssistenteDaUltimaSituacao"));
            } else {
                lblArquivoAvatar.setText(idioma.Valor("lblNaoHaAvaliacoes"));
            }
        }

        if (partidaDesenvolvimento.getImagemDasAvaliacoes() != null) {
            lblArquivoFundo.setText(partidaDesenvolvimento.getImagemDasAvaliacoes().getDescription());
        } else {
            if (btnFundoAvaliacoes.isEnabled()) {
                lblArquivoFundo.setText(idioma.Valor("lblUsandoFundoDaUltimaSituacao"));
            } else {
                lblArquivoFundo.setText(idioma.Valor("lblNaoHaAvaliacoes"));
            }
        }
    }

    public void NovaSituacao() {

        int ordem = partidaDesenvolvimento.getSituacoes().size() + 1;

        JanelaDesenvolvimentoSituacao jds = new JanelaDesenvolvimentoSituacao(1, null, ordem);
        jds.setVisible(true);
    }

    public void EditarSituacao() {
        //Recuperar o item selecionado
        int index = tblSituacoes.getSelectedRow();

        //Recupera o objeto na tabela
        Situacao situacao = (Situacao) tblSituacoes.getValueAt(index, 0);

        JanelaDesenvolvimentoSituacao jds = new JanelaDesenvolvimentoSituacao(2, situacao, index + 1);
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

                                ArrayList<Integer> avaliacoesRemover = new ArrayList<>();

                                //Excluir todas as avaliações envolvendo a variável vinculada à saida
                                int aux = 0;
                                for (Avaliacao a : partidaDesenvolvimento.getAvaliacoes()) {
                                    if (a.getVariavel() == so.getVariavelSaida()) {
                                        avaliacoesRemover.add(aux);

                                    }
                                    aux++;
                                }
                                for (int i : avaliacoesRemover) {
                                    partidaDesenvolvimento.getAvaliacoes().remove(i);
                                }

                                //Exclui a variavel vinculada à saída
                                partidaDesenvolvimento.getVariaveis().remove(so.getVariavelSaida());

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

    public void AlterarOrdemSituacao(Situacao s, int posicao) {

        boolean adicionou = false;
        ArrayList<Situacao> situacoes = new ArrayList<>();

        //Remove a situação atual da lista
        partidaDesenvolvimento.getSituacoes().remove(s);
        int qtdSituacoes = partidaDesenvolvimento.getSituacoes().size() - 1;

        //Cria uma nova lista, adicionando a situação na posição desejada
        for (int i = 0; i <= qtdSituacoes; i++) {
            if (i == posicao) {
                situacoes.add(s);
                adicionou = true;
            }
            situacoes.add(partidaDesenvolvimento.getSituacoes().get(i));
        }

        //Caso não tenha adicionado a situação na lista, apenas adiciona na ultima posição
        if (!adicionou) {
            situacoes.add(s);
        }

        //Atualiza a lista
        partidaDesenvolvimento.setSituacoes(situacoes);
    }

    public void PreviaSituacao() {
        //Recuperar o item selecionado
        int index = tblSituacoes.getSelectedRow();

        //Recupera o objeto na tabela
        Situacao situacao = (Situacao) tblSituacoes.getValueAt(index, 0);

        JanelaExecucaoPartida jsj = new JanelaExecucaoPartida(2);
        jsj.CarregarPreviaSituacao(situacao);
        jsj.setVisible(true);
    }

    public void Salvar() {
        try {
            partidaDesenvolvimento.setIdioma(idioma.getIdiomaAtual());
            IOProjetoPartida iop = new IOProjetoPartida();
            boolean salvou = iop.SalvarDireto(partidaDesenvolvimento);
            if (salvou) {
                partidaSalva = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(JanelaDesenvolvimentoPartida.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void SalvarComo() {
        try {

            partidaDesenvolvimento.setIdioma(idioma.getIdiomaAtual());
            IOProjetoPartida iop = new IOProjetoPartida();
            boolean salvou = iop.SalvarComo(partidaDesenvolvimento);
            if (salvou) {
                partidaSalva = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(JanelaDesenvolvimentoPartida.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void SalvarJogar() {
        try {
            partidaDesenvolvimento.setIdioma(idioma.getIdiomaAtual());
            IOProjetoPartida iop = new IOProjetoPartida();
            boolean salvou = iop.SalvarDireto(partidaDesenvolvimento);
            if (salvou) {
                partidaSalva = true;
                dispose();
                Partida.setInstancia(partidaDesenvolvimento);
                ControladoraExecucao ce = new ControladoraExecucao();
                ce.ExecutaPartida();
                JanelaDesenvolvimentoPartida.setInstancia(null);
            }
        } catch (IOException ex) {
            Logger.getLogger(JanelaDesenvolvimentoPartida.class.getName()).log(Level.SEVERE, null, ex);
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
        btnFundoAvaliacoes = new javax.swing.JButton();
        btnAvatarAvaliacoes = new javax.swing.JButton();
        lblArquivoFundo = new javax.swing.JLabel();
        lblArquivoAvatar = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuArquivo = new javax.swing.JMenu();
        menuItemSalvar = new javax.swing.JMenuItem();
        menuItemSalvarComo = new javax.swing.JMenuItem();
        menuItemSalvarJogar = new javax.swing.JMenuItem();
        mnItemExportarExecutavel = new javax.swing.JMenuItem();
        menuConfigurar = new javax.swing.JMenu();
        menuItemIdioma = new javax.swing.JMenuItem();
        menuAjuda = new javax.swing.JMenu();
        menuItemManualUtilizacao = new javax.swing.JMenuItem();
        menuItemProjetosExemploEditar = new javax.swing.JMenuItem();
        menuItemProjetosExemploJogar = new javax.swing.JMenuItem();
        menuItemSobre = new javax.swing.JMenuItem();

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
                {null}
            },
            new String [] {
                "Title 1"
            }
        ));
        tblSituacoes.setColumnSelectionAllowed(true);
        tblSituacoes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSituacoesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSituacoes);
        tblSituacoes.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        btnAjudaSituacoes.setText("btnAjuda");
        btnAjudaSituacoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAjudaSituacoesActionPerformed(evt);
            }
        });

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 248, Short.MAX_VALUE)
                        .addComponent(btnPreviaSituacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAjudaSituacoes)))
                .addContainerGap())
        );
        abaSituacoesLayout.setVerticalGroup(
            abaSituacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaSituacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
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
        tblVariaveis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVariaveisMouseClicked(evt);
            }
        });
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
        btnAjudaVariaveis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAjudaVariaveisActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout abaVariaveisLayout = new javax.swing.GroupLayout(abaVariaveis);
        abaVariaveis.setLayout(abaVariaveisLayout);
        abaVariaveisLayout.setHorizontalGroup(
            abaVariaveisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaVariaveisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(abaVariaveisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE)
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
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
        tblAvaliacoes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAvaliacoesMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblAvaliacoes);

        btnAjudaAvaliacoes.setText("btnAjuda");
        btnAjudaAvaliacoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAjudaAvaliacoesActionPerformed(evt);
            }
        });

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

        btnFundoAvaliacoes.setText("btnFundoAvaliacoes");
        btnFundoAvaliacoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFundoAvaliacoesActionPerformed(evt);
            }
        });

        btnAvatarAvaliacoes.setText("btnAvatarAvaliacoes");
        btnAvatarAvaliacoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvatarAvaliacoesActionPerformed(evt);
            }
        });

        lblArquivoFundo.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        lblArquivoFundo.setText("lblArquivoFundo");

        lblArquivoAvatar.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        lblArquivoAvatar.setText("lblArquivoAvatar");

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 319, Short.MAX_VALUE)
                        .addComponent(btnAjudaAvaliacoes))
                    .addGroup(abaAvaliacoesLayout.createSequentialGroup()
                        .addComponent(btnFundoAvaliacoes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblArquivoFundo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblArquivoAvatar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAvatarAvaliacoes)))
                .addContainerGap())
        );
        abaAvaliacoesLayout.setVerticalGroup(
            abaAvaliacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, abaAvaliacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(abaAvaliacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFundoAvaliacoes)
                    .addComponent(btnAvatarAvaliacoes)
                    .addComponent(lblArquivoFundo)
                    .addComponent(lblArquivoAvatar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
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

        menuArquivo.setText("mniArquivo");

        menuItemSalvar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuItemSalvar.setText("mniSalvar");
        menuItemSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSalvarActionPerformed(evt);
            }
        });
        menuArquivo.add(menuItemSalvar);

        menuItemSalvarComo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        menuItemSalvarComo.setText("mniSalvarComo");
        menuItemSalvarComo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSalvarComoActionPerformed(evt);
            }
        });
        menuArquivo.add(menuItemSalvarComo);

        menuItemSalvarJogar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuItemSalvarJogar.setText("mniSalvarJogar");
        menuItemSalvarJogar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSalvarJogarActionPerformed(evt);
            }
        });
        menuArquivo.add(menuItemSalvarJogar);

        mnItemExportarExecutavel.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        mnItemExportarExecutavel.setText("mniExportarExecutavel");
        mnItemExportarExecutavel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnItemExportarExecutavelActionPerformed(evt);
            }
        });
        menuArquivo.add(mnItemExportarExecutavel);

        jMenuBar1.add(menuArquivo);

        menuConfigurar.setText("mniConfigurar");

        menuItemIdioma.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        menuItemIdioma.setText("mniIdioma");
        menuItemIdioma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemIdiomaActionPerformed(evt);
            }
        });
        menuConfigurar.add(menuItemIdioma);

        jMenuBar1.add(menuConfigurar);

        menuAjuda.setText("mniAjuda");

        menuItemManualUtilizacao.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        menuItemManualUtilizacao.setText("mniManualUtilizacao");
        menuItemManualUtilizacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemManualUtilizacaoActionPerformed(evt);
            }
        });
        menuAjuda.add(menuItemManualUtilizacao);

        menuItemProjetosExemploEditar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuItemProjetosExemploEditar.setText("mniProjetosExemploEditar");
        menuItemProjetosExemploEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemProjetosExemploEditarActionPerformed(evt);
            }
        });
        menuAjuda.add(menuItemProjetosExemploEditar);

        menuItemProjetosExemploJogar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        menuItemProjetosExemploJogar.setText("mniProjetosExemploJogar");
        menuItemProjetosExemploJogar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemProjetosExemploJogarActionPerformed(evt);
            }
        });
        menuAjuda.add(menuItemProjetosExemploJogar);

        menuItemSobre.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuItemSobre.setText("mniSobre");
        menuItemSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSobreActionPerformed(evt);
            }
        });
        menuAjuda.add(menuItemSobre);

        jMenuBar1.add(menuAjuda);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painelConfiguracoes)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(painelConfiguracoes)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuItemSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemSalvarActionPerformed
        Salvar();
    }//GEN-LAST:event_menuItemSalvarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (!partidaSalva) {
            int selecionado = JOptionPane.showOptionDialog(null, idioma.Valor("msgDesejaSalvar"),
                    idioma.Valor("aviso"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, opcaoSimNaoCancelar, opcaoSimNaoCancelar[0]);
            switch (selecionado) {
                case 0: //Salvar
                    Salvar();
                    dispose();
                    Partida.setInstancia(null);
                    JanelaDesenvolvimentoPartida.setInstancia(null);
                    JanelaInicial ji = new JanelaInicial();
                    ji.setVisible(true);
                    break;
                case 1: //Não salvar
                    dispose();
                    Partida.setInstancia(null);
                    JanelaDesenvolvimentoPartida.setInstancia(null);
                    JanelaInicial ji2 = new JanelaInicial();
                    ji2.setVisible(true);
                    break;
            }
        } else {
            dispose();
            Partida.setInstancia(null);
            JanelaDesenvolvimentoPartida.setInstancia(null);
            JanelaInicial ji = new JanelaInicial();
            ji.setVisible(true);
        }
    }//GEN-LAST:event_formWindowClosing

    private void menuItemIdiomaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemIdiomaActionPerformed
        JanelaTrocaIdioma jti = new JanelaTrocaIdioma();
        jti.setVisible(true);
    }//GEN-LAST:event_menuItemIdiomaActionPerformed

    private void menuItemSalvarJogarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemSalvarJogarActionPerformed
        SalvarJogar();
    }//GEN-LAST:event_menuItemSalvarJogarActionPerformed

    private void menuItemSalvarComoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemSalvarComoActionPerformed
        SalvarComo();
    }//GEN-LAST:event_menuItemSalvarComoActionPerformed

    private void mnItemExportarExecutavelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnItemExportarExecutavelActionPerformed
        try {
            if (!partidaSalva) {
                Salvar();
            }
            if (partidaSalva) {
                IOExportaJAR ioJar = new IOExportaJAR();
                ioJar.criarJAR(partidaDesenvolvimento);
            }
        } catch (IOException ex) {
            Logger.getLogger(JanelaDesenvolvimentoPartida.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mnItemExportarExecutavelActionPerformed

    private void btnExcluirAvaliacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirAvaliacaoActionPerformed
        partidaSalva = false;
        ExcluirAvaliacao();
    }//GEN-LAST:event_btnExcluirAvaliacaoActionPerformed

    private void btnEditarAvaliacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarAvaliacaoActionPerformed
        partidaSalva = false;
        EditarAvaliacao(2);
    }//GEN-LAST:event_btnEditarAvaliacaoActionPerformed

    private void btnNovaAvaliacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovaAvaliacaoActionPerformed
        partidaSalva = false;
        EditarAvaliacao(1);
    }//GEN-LAST:event_btnNovaAvaliacaoActionPerformed

    private void tblAvaliacoesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAvaliacoesMouseClicked

        if (evt.getClickCount() == 2) {
            partidaSalva = false;
            EditarAvaliacao(2);
        }
    }//GEN-LAST:event_tblAvaliacoesMouseClicked

    private void btnExcluirVariavelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirVariavelActionPerformed
        partidaSalva = false;
        ExcluirVariavel();
    }//GEN-LAST:event_btnExcluirVariavelActionPerformed

    private void btnEditarVariavelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarVariavelActionPerformed
        partidaSalva = false;
        EditarVariavel();
    }//GEN-LAST:event_btnEditarVariavelActionPerformed

    private void btnNovaVariavelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovaVariavelActionPerformed
        partidaSalva = false;
        NovaVariavel();
    }//GEN-LAST:event_btnNovaVariavelActionPerformed

    private void tblVariaveisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVariaveisMouseClicked

        int index = tblVariaveis.getSelectedRow();

        Variavel variavel = (Variavel) tblVariaveis.getValueAt(index, 0);

        btnEditarVariavel.setEnabled(!variavel.isAutodefinida());
        btnExcluirVariavel.setEnabled(!variavel.isAutodefinida());

        if ((evt.getClickCount() == 2) && (!variavel.isAutodefinida())) {
            partidaSalva = false;
            EditarVariavel();
        }
    }//GEN-LAST:event_tblVariaveisMouseClicked

    private void tblSituacoesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSituacoesMouseClicked
        if (evt.getClickCount() == 2) {
            partidaSalva = false;
            EditarSituacao();
        }
        int coluna = tblSituacoes.getSelectedColumn();
        int index = tblSituacoes.getSelectedRow();
        if (coluna == 1) {
            if (index != 0) {
                Situacao s = (Situacao) tblSituacoes.getValueAt(index, 0);
                AlterarOrdemSituacao(s, index - 1);
                AtualizaSituacoes();
            }
        }
        if (coluna == 2) {
            if (index != (partidaDesenvolvimento.getSituacoes().size() - 1)) {
                Situacao s = (Situacao) tblSituacoes.getValueAt(index, 0);
                AlterarOrdemSituacao(s, index + 1);
                AtualizaSituacoes();
            }
        }

    }//GEN-LAST:event_tblSituacoesMouseClicked

    private void btnPreviaSituacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviaSituacaoActionPerformed
        PreviaSituacao();
    }//GEN-LAST:event_btnPreviaSituacaoActionPerformed

    private void btnExcluirSituacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirSituacaoActionPerformed
        partidaSalva = false;
        ExcluirSituacao();
    }//GEN-LAST:event_btnExcluirSituacaoActionPerformed

    private void btnEditarSituacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarSituacaoActionPerformed
        partidaSalva = false;
        EditarSituacao();
    }//GEN-LAST:event_btnEditarSituacaoActionPerformed

    private void btnNovaSituacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovaSituacaoActionPerformed
        partidaSalva = false;
        NovaSituacao();
    }//GEN-LAST:event_btnNovaSituacaoActionPerformed

    private void btnAvatarAvaliacoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvatarAvaliacoesActionPerformed
        JanelaSelecionaAvatar jsa = new JanelaSelecionaAvatar();
        jsa.setVisible(true);
    }//GEN-LAST:event_btnAvatarAvaliacoesActionPerformed

    private void btnFundoAvaliacoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFundoAvaliacoesActionPerformed
        JFileChooser fileChooser = new javax.swing.JFileChooser();
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
                Image image = ImageIO.read(file);
                ImageIcon imagec = new ImageIcon();
                imagec.setImage(image);
                imagec.setDescription(file.getName());
                partidaDesenvolvimento.setImagemDasAvaliacoes(imagec);
                AtualizaAvaliacoes();
            } catch (Exception ex) {
                System.out.println("problem accessing file" + file.getAbsolutePath());
            }
        }

    }//GEN-LAST:event_btnFundoAvaliacoesActionPerformed

    private void menuItemSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemSobreActionPerformed
        ControladoraAjuda caj = new ControladoraAjuda();
        caj.ExibirAjuda(this, "Sobre");
    }//GEN-LAST:event_menuItemSobreActionPerformed

    private void menuItemManualUtilizacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemManualUtilizacaoActionPerformed
        ControladoraAjuda caj = new ControladoraAjuda();
        caj.ManualUtilizacao(this);
    }//GEN-LAST:event_menuItemManualUtilizacaoActionPerformed

    private void btnAjudaSituacoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjudaSituacoesActionPerformed
        ControladoraAjuda caj = new ControladoraAjuda();
        caj.ExibirAjuda(this, "DesenvolvimentoPartidaAbaSituacoes");
    }//GEN-LAST:event_btnAjudaSituacoesActionPerformed

    private void btnAjudaVariaveisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjudaVariaveisActionPerformed
        ControladoraAjuda caj = new ControladoraAjuda();
        caj.ExibirAjuda(this, "DesenvolvimentoPartidaAbaVariaveis");
    }//GEN-LAST:event_btnAjudaVariaveisActionPerformed

    private void btnAjudaAvaliacoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjudaAvaliacoesActionPerformed
        ControladoraAjuda caj = new ControladoraAjuda();
        caj.ExibirAjuda(this, "DesenvolvimentoPartidaAbaAvaliacoes");
    }//GEN-LAST:event_btnAjudaAvaliacoesActionPerformed

    private void menuItemProjetosExemploEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemProjetosExemploEditarActionPerformed
        if (!partidaSalva) {
            int selecionado = JOptionPane.showOptionDialog(null, idioma.Valor("msgDesejaSalvar"),
                    idioma.Valor("aviso"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, opcaoSimNaoCancelar, opcaoSimNaoCancelar[0]);
            switch (selecionado) {
                case 0: //Salvar
                    Salvar();
                    dispose();
                    JanelaDesenvolvimentoPartida.setInstancia(null);
                    ParametrosArquivo pa = new ParametrosArquivo();
                    pa.setArquivoSelecionado(true);
                    pa.setNomeDoArquivo("Exemplo");
                    pa.setPatchDoArquivo("Arquivos/Exemplo/exemplo.tcc");
                    IOProjetoPartida iop = new IOProjetoPartida();
                    Partida.setInstancia(iop.LePartida(pa));
                    JanelaDesenvolvimentoPartida jdp = JanelaDesenvolvimentoPartida.getInstancia();
                    jdp.setVisible(true);
                    break;
                case 1: //Não salvar
                    dispose();
                    JanelaDesenvolvimentoPartida.setInstancia(null);
                    ParametrosArquivo pa2 = new ParametrosArquivo();
                    pa2.setArquivoSelecionado(true);
                    pa2.setNomeDoArquivo("Exemplo");
                    pa2.setPatchDoArquivo("Arquivos/Exemplo/exemplo.tcc");
                    IOProjetoPartida iop2 = new IOProjetoPartida();
                    Partida.setInstancia(iop2.LePartida(pa2));
                    JanelaDesenvolvimentoPartida jdp2 = JanelaDesenvolvimentoPartida.getInstancia();
                    jdp2.setVisible(true);
                    break;
            }
        } else {
            dispose();
            JanelaDesenvolvimentoPartida.setInstancia(null);
            ParametrosArquivo pa = new ParametrosArquivo();
            pa.setArquivoSelecionado(true);
            pa.setNomeDoArquivo("Exemplo");
            pa.setPatchDoArquivo("Arquivos/Exemplo/exemplo.tcc");
            IOProjetoPartida iop = new IOProjetoPartida();
            Partida.setInstancia(iop.LePartida(pa));
            JanelaDesenvolvimentoPartida jdp = JanelaDesenvolvimentoPartida.getInstancia();
            jdp.setVisible(true);
        }
    }//GEN-LAST:event_menuItemProjetosExemploEditarActionPerformed

    private void menuItemProjetosExemploJogarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemProjetosExemploJogarActionPerformed
        if (!partidaSalva) {
            int selecionado = JOptionPane.showOptionDialog(null, idioma.Valor("msgDesejaSalvar"),
                    idioma.Valor("aviso"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, opcaoSimNaoCancelar, opcaoSimNaoCancelar[0]);
            switch (selecionado) {
                case 0: //Salvar
                    Salvar();
                    dispose();
                    ParametrosArquivo pa = new ParametrosArquivo();
                    pa.setArquivoSelecionado(true);
                    pa.setNomeDoArquivo("Exemplo");
                    pa.setPatchDoArquivo("Arquivos/Exemplo/exemplo.tcc");
                    IOProjetoPartida iop = new IOProjetoPartida();
                    Partida.setInstancia(iop.LePartida(pa));
                    ControladoraExecucao ce = new ControladoraExecucao();
                    ce.ExecutaPartida();
                    JanelaDesenvolvimentoPartida.setInstancia(null);
                    break;
                case 1: //Não salvar
                    dispose();
                    ParametrosArquivo pa2 = new ParametrosArquivo();
                    pa2.setArquivoSelecionado(true);
                    pa2.setNomeDoArquivo("Exemplo");
                    pa2.setPatchDoArquivo("Arquivos/Exemplo/exemplo.tcc");
                    IOProjetoPartida iop2 = new IOProjetoPartida();
                    Partida.setInstancia(iop2.LePartida(pa2));
                    ControladoraExecucao ce2 = new ControladoraExecucao();
                    ce2.ExecutaPartida();
                    JanelaDesenvolvimentoPartida.setInstancia(null);
                    break;
            }
        } else {
            dispose();
            ParametrosArquivo pa = new ParametrosArquivo();
            pa.setArquivoSelecionado(true);
            pa.setNomeDoArquivo("Exemplo");
            pa.setPatchDoArquivo("Arquivos/Exemplo/exemplo.tcc");
            IOProjetoPartida iop = new IOProjetoPartida();
            Partida.setInstancia(iop.LePartida(pa));
            ControladoraExecucao ce = new ControladoraExecucao();
            ce.ExecutaPartida();
            JanelaDesenvolvimentoPartida.setInstancia(null);
        }
    }//GEN-LAST:event_menuItemProjetosExemploJogarActionPerformed

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
    private javax.swing.JPanel abaAvaliacoes;
    private javax.swing.JPanel abaSituacoes;
    private javax.swing.JPanel abaVariaveis;
    private javax.swing.JButton btnAjudaAvaliacoes;
    private javax.swing.JButton btnAjudaSituacoes;
    private javax.swing.JButton btnAjudaVariaveis;
    private javax.swing.JButton btnAvatarAvaliacoes;
    private javax.swing.JButton btnEditarAvaliacao;
    private javax.swing.JButton btnEditarSituacao;
    private javax.swing.JButton btnEditarVariavel;
    private javax.swing.JButton btnExcluirAvaliacao;
    private javax.swing.JButton btnExcluirSituacao;
    private javax.swing.JButton btnExcluirVariavel;
    private javax.swing.JButton btnFundoAvaliacoes;
    private javax.swing.JButton btnNovaAvaliacao;
    private javax.swing.JButton btnNovaSituacao;
    private javax.swing.JButton btnNovaVariavel;
    private javax.swing.JButton btnPreviaSituacao;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JFrame jFrame3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JPopupMenu jPopupMenu3;
    private javax.swing.JPopupMenu jPopupMenu4;
    private javax.swing.JPopupMenu jPopupMenu5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblArquivoAvatar;
    private javax.swing.JLabel lblArquivoFundo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JMenu menuAjuda;
    private javax.swing.JMenu menuArquivo;
    private javax.swing.JMenu menuConfigurar;
    private javax.swing.JMenuItem menuItemIdioma;
    private javax.swing.JMenuItem menuItemManualUtilizacao;
    private javax.swing.JMenuItem menuItemProjetosExemploEditar;
    private javax.swing.JMenuItem menuItemProjetosExemploJogar;
    private javax.swing.JMenuItem menuItemSalvar;
    private javax.swing.JMenuItem menuItemSalvarComo;
    private javax.swing.JMenuItem menuItemSalvarJogar;
    private javax.swing.JMenuItem menuItemSobre;
    private javax.swing.JMenuItem mnItemExportarExecutavel;
    private javax.swing.JTabbedPane painelConfiguracoes;
    private javax.swing.JTable tblAvaliacoes;
    private javax.swing.JTable tblSituacoes;
    private javax.swing.JTable tblVariaveis;
    // End of variables declaration//GEN-END:variables
}
