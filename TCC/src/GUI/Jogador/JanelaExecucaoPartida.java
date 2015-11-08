/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Jogador;

import Controle.ControladoraExecucao;
import Controle.ControladoraIdioma;
import GUI.Suporte.PainelImagem;
import Modelo.Acao;
import Modelo.Avaliacao;
import Modelo.Caminho;
import Modelo.Partida;
import Modelo.SaidaNumerica;
import Modelo.SaidaOpcional;
import Modelo.Situacao;
import Modelo.Variavel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rafael
 */
public final class JanelaExecucaoPartida extends javax.swing.JFrame {

    private final ControladoraIdioma idioma;

    private ImageIcon personagem;

    private Situacao situacao;

    private JPanel imgFundo;
    private JPanel imgAvatar;
    private JPanel imgBalao;
    private JPanel painelBotoes;

    private ImageIcon imagemAvatar;
    private ImageIcon imagemBalao;

    private JTextArea textoBalao;

    private JTable tblVariaveis;

    private JSlider jslSaidaNumerica;

    private JButton btn;

    private JLabel lblValorSelecionado;

    private ControladoraExecucao controladora;

    private static JanelaExecucaoPartida instancia;

    private ArrayList<Variavel> variaveis;

    private ArrayList<Avaliacao> avaliacoesRealizar;

    private Caminho caminho;

    private int tipoSaida;
    private final int modo;
    private final Partida partida;
    //private final Partida partidaOriginal;

    public JanelaExecucaoPartida(int modo) {
        initComponents();
        idioma = ControladoraIdioma.getInstancia();
        this.modo = modo;
        setLayout(new FlowLayout());

        partida = Partida.getInstancia();

        setLocationRelativeTo(null);
        setResizable(false);

        variaveis = partida.getVariaveis();

        if (partida.getParametrosArquivo() != null) {
            String nomeArquivo = partida.getParametrosArquivo().getNomeDoArquivo();
            setTitle(idioma.Valor(nomeArquivo));
        } else {
            setTitle(idioma.Valor("tituloNovoJogo"));
        }

        CarregarTabelaVariaveis();
        CarregaPainelSaida();
        ResetarVariaveis();

        if (modo == 1) {
            dispose();
            setUndecorated(true);
            setVisible(true);
        }

    }

    public void RecarregarComponentes() {

        tblVariaveis.repaint();
        textoBalao.repaint();
        imgBalao.repaint();
        imgFundo.repaint();
        painelPrincipal.repaint();

    }

    public final void ResetarVariaveis() {
        for (Variavel v : partida.getVariaveis()) {
            v.setValor(v.getValorInicial());
        }
    }

    public void CarregaImagemFundoSituacao(Situacao situacao) {

        if (imgFundo != null) {
            painelPrincipal.remove(imgFundo);
        }

        //Ajusta o tamanho da tela
        painelPrincipal.setSize(1024, 700);
        painelPrincipal.setOpaque(false);

        //Caso não haja imagem de fundo, adiciona uma imagem genérica
        if (situacao.getFundoSituacao() == null) {
            ImageIcon fundoGenerico = new ImageIcon(getClass().getResource("/Recursos/fundo.jpg"));
            imgFundo = new PainelImagem(fundoGenerico.getImage().getScaledInstance(1024, 700, 1024));
        } else {
            //Desenha a imagem de fundo
            imgFundo = new PainelImagem(situacao.getFundoSituacao().getImage().getScaledInstance(1024, 700, 1024));
        }

        imgFundo.setOpaque(false);
        //imgFundo.setSize(1024, 768);

        //Adiciona a imagem de fundo à tela
        painelPrincipal.add(imgFundo);
        imgFundo.repaint();
    }

    public void CarregaImagemFundoAvaliacao() {
        if (partida.getImagemDasAvaliacoes() != null) {
            painelPrincipal.remove(imgFundo);

            //Ajusta o tamanho da tela
            painelPrincipal.setSize(1024, 700);
            painelPrincipal.setOpaque(false);

            imgFundo = new PainelImagem(partida.getImagemDasAvaliacoes().getImage().getScaledInstance(1024, 700, 1024));

            imgFundo.setOpaque(false);

            //Adiciona a imagem de fundo à tela
            painelPrincipal.add(imgFundo);
            imgFundo.repaint();
        }
    }

    public void CarregaPainelSaida() {

        painelBotoes = new JPanel();

        painelBotoes.setOpaque(false);
        painelBotoes.setSize(500, 100);
        painelBotoes.setLocation(100, 600);

        painelPrincipal.add(painelBotoes);

    }

    /**
     * Carrega assistente
     *
     * @param ehSituacao
     */
    public void CarregaAssistente(boolean ehSituacao) {
        if (ehSituacao) {
            if (imgAvatar != null) {
                painelPrincipal.remove(imgAvatar);
            }

            personagem = situacao.getImagemPersonagem();

            ImageIcon imagemProvisoria = new ImageIcon();
            imagemProvisoria.setImage(personagem.getImage().getScaledInstance(150, 150, 150));
            imagemAvatar = imagemProvisoria;

            //Exibe o avatar
            imgAvatar = new PainelImagem(imagemAvatar.getImage());

            imgAvatar.setOpaque(false);

            //Exibe o avatar do assistente
            if (situacao.getLadoGeracao() == 1) {
                //Gera o assistente na esquerdsa
                imgAvatar.setLocation(60, 550);

            } else {
                //Gera o assistente na direita
                imgAvatar.setLocation(850, 550);
            }

            painelPrincipal.add(imgAvatar);

            //O avatar precisa ser adicionado antes da imagem de fundo para ser exibido
            if (imgFundo != null) {
                painelPrincipal.remove(imgFundo);
                painelPrincipal.add(imgFundo);
            }
        } else {
            if (partida.getAvatarDasAvaliacoes() != null) {
                painelPrincipal.remove(imgAvatar);

                personagem = partida.getAvatarDasAvaliacoes();

                ImageIcon imagemProvisoria = new ImageIcon();
                imagemProvisoria.setImage(personagem.getImage().getScaledInstance(150, 150, 150));
                imagemAvatar = imagemProvisoria;

                //Exibe o avatar
                imgAvatar = new PainelImagem(imagemAvatar.getImage());

                imgAvatar.setOpaque(false);

                //Gera o assistente na direita
                imgAvatar.setLocation(850, 550);

                painelPrincipal.add(imgAvatar);

                //O avatar precisa ser adicionado antes da imagem de fundo para ser exibido
                if (imgFundo != null) {
                    painelPrincipal.remove(imgFundo);
                    painelPrincipal.add(imgFundo);
                }
            }
        }
    }

    public void CarregarTabelaVariaveis() {
        tblVariaveis = new JTable();

        tblVariaveis.setEnabled(false);
        tblVariaveis.setShowGrid(false);
        tblVariaveis.setFont(new Font("Verdana", Font.BOLD, 14));
        tblVariaveis.setForeground(Color.ORANGE);
        tblVariaveis.setBackground(new Color(0, 0, 255, 150));

    }

    /**
     * Carrega as variáveis
     */
    public void CarregaVariaveis() {

        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("variavel");
        model.addColumn("valor");

        int largura = 0;

        int cont = 0;
        for (Variavel v : variaveis) {
            //Adicionar na lista apenas caso a variável não seja oculta
            if (!v.isOculta()) {
                model.addRow(new Object[]{v.getNome(), v.getValor()});

                if ((v.getNome().length() * 2) > largura) {
                    largura = v.getNome().length() * 10;
                }
                cont++;
            }
        }

        if (cont > 0) {

            //Calcula a altura da tabela de variáveis
            int altura = cont * 16;

            tblVariaveis.setModel(model);
            tblVariaveis.setSize(largura + 50, altura);
            tblVariaveis.setLocation(10, 10);

            tblVariaveis.setModel(model);

            tblVariaveis.getColumnModel().getColumn(0).setPreferredWidth(largura);
            tblVariaveis.getColumnModel().getColumn(1).setPreferredWidth(50);

            tblVariaveis.revalidate();

            painelPrincipal.add(tblVariaveis);
        }

    }

    public void CarregaFalaAssistente(String texto, boolean isSaida) {

        if (imgBalao != null) {
            painelPrincipal.remove(imgBalao);
        }

        if (situacao.getLadoGeracao() == 1) {
            imagemBalao = new ImageIcon(getClass().getResource("/Recursos/balaoEsquerda.png"));
        } else {
            imagemBalao = new ImageIcon(getClass().getResource("/Recursos/balaoDireita.png"));
        }

        imgBalao = new PainelImagem(imagemBalao.getImage());

        //Exibe o texto do balão
        textoBalao = new JTextArea();

        textoBalao.setLocation(40, 40);
        textoBalao.setEditable(false);
        textoBalao.setDragEnabled(false);
        textoBalao.setFont(new Font("Serif", Font.ITALIC, 18));
        textoBalao.setLineWrap(true);
        textoBalao.setWrapStyleWord(true);
        textoBalao.setForeground(Color.black);
        textoBalao.setOpaque(false);

        textoBalao.setText(texto);

        int caracteres = texto.length();

        int altura = (caracteres / 45) * 25;

        int largura = 700; //Largura máxima

        if (altura < 22) {
            altura = 22;
            largura = caracteres * 10;
        }

        textoBalao.setSize(largura, altura);

        ImageIcon imgRedimensionada = imagemBalao;

        int alturaTexto = textoBalao.getHeight();
        int larguraTexto = textoBalao.getWidth();

        int yAvatar = imgAvatar.getY() + 40;
        int xAvatar = imgAvatar.getX() + 300;

        imgRedimensionada.setImage(imgRedimensionada.getImage().getScaledInstance(larguraTexto + 80, alturaTexto + 80, 300));

        if (situacao.getLadoGeracao() == 1) {
            GerarBalaoEsquerda(imgRedimensionada, xAvatar, yAvatar, largura);
        } else {
            GerarBalaoDireita(imgRedimensionada, xAvatar, yAvatar, largura);
        }
        //O balão precisa ser adicionado antes da imagem de fundo para ser exibido
        if (imgFundo != null) {
            painelPrincipal.remove(imgFundo);
            painelPrincipal.add(imgFundo);
        }

    }

    public void GerarBalaoEsquerda(ImageIcon imgRedimensionada, int xAvatar, int yAvatar, int largura) {
        imgBalao = new PainelImagem(imgRedimensionada.getImage());

        //Posicionar o balão sempre acima do avatar e mais a esquerda possível (com 50px de correção)
        int yBalao = yAvatar - imgBalao.getHeight() - 50;

        int xBalao = xAvatar - 300;

        imgBalao.setLocation(xBalao, yBalao);
        imgBalao.setLayout(null);

        imgBalao.add(textoBalao);

        painelPrincipal.add(imgBalao);
    }

    public void GerarBalaoDireita(ImageIcon imgRedimensionada, int xAvatar, int yAvatar, int largura) {
        imgBalao = new PainelImagem(imgRedimensionada.getImage());

        //Posicionar o balão sempre acima do avatar e mais a direita possível
        int yBalao = yAvatar - imgBalao.getHeight() - 50;

        int xBalao;

        //Gerar o final do balão sempre rente a 3/4 do tamanho do avatar
        xBalao = (imgAvatar.getX() + (3 * (imgAvatar.getWidth() / 4))) - imgBalao.getWidth();

        imgBalao.setLocation(xBalao, yBalao);
        imgBalao.setLayout(null);

        imgBalao.add(textoBalao);

        painelPrincipal.add(imgBalao);
    }

    /**
     * Gerar as saídas da situação
     *
     * @param situacao
     */
    public void GerarSaidas(Situacao situacao) {

        boolean naoHaSaidas = false;

        tipoSaida = situacao.getSaida().getTipoSaida();

        switch (tipoSaida) {
            case 0:

                //Não há tipo de saida definido
                naoHaSaidas = true;
                break;

            case 1: //Saida opcional

                if (situacao.getSaida().getsaidasOpcao().isEmpty()) {
                    naoHaSaidas = true;
                } else {
                    GerarSaidaOpcional(situacao.getSaida().getsaidasOpcao());
                }
                break;

            case 2: //Saída numérica

                if (situacao.getSaida().getSaidasNumerica().isEmpty()) {
                    naoHaSaidas = true;
                } else {
                    GerarSaidaNumerica(situacao.getSaida().getSaidasNumerica());
                }

                break;
        }

        //Se não houver saídas então a situação é final                
        if (naoHaSaidas) {

            Situacao ultimaSituacao = partida.getSituacoes().get(partida.getSituacoes().size() - 1);

            //A situação é final se estiver marcada como final ou for a ultima da partida
            boolean situacaoFinal = situacao.isSituacaoFinal() || (situacao == ultimaSituacao);

            if (situacaoFinal) {
                if (partida.getAvaliacoes().isEmpty()) {
                    GerarSaidaFinal();
                } else {
                    VerificarAvaliacoes();
                }
            } else {

                int index = 0;
                for (Situacao s : partida.getSituacoes()) {
                    if (s == situacao) {
                        break;
                    }
                    index++;
                }
                int proxima = index + 1;

                GerarSaidaProximaSituacao(proxima);
            }
        }
    }

    /**
     * Gera a saída para a próxima situação
     *
     * @param proxima
     */
    public void GerarSaidaProximaSituacao(int proxima) {
        painelBotoes.removeAll();
        painelBotoes.revalidate();

        btn = new JButton(idioma.Valor("btnContinuar"));
        btn.setLocation(0, 0);
        btn.setSize(20, 30);
        btn.addActionListener((java.awt.event.ActionEvent e) -> {

            caminho.setEscolha(idioma.Valor("btnContinuar"));
            partida.getCaminhos().add(caminho);

            CarregaSituacao(partida.getSituacoes().get(proxima));

        });
        painelBotoes.add(btn);
    }

    /**
     * Gera a saída para situação final
     */
    public void GerarSaidaFinal() {

        painelBotoes.removeAll();
        painelBotoes.revalidate();

        btn = new JButton(idioma.Valor("btnTerminar"));

        btn.addActionListener((java.awt.event.ActionEvent e) -> {

            partida.getCaminhos().clear();
            dispose();

        });

        btn.setLocation(0, 0);
        btn.setSize(20, 30);

        painelBotoes.add(btn);
        btn.repaint();

        btn = new JButton(idioma.Valor("btnVerResultados"));

        btn.addActionListener((java.awt.event.ActionEvent e) -> {

            JanelaResultados jr = new JanelaResultados(this);
            jr.setVisible(true);

        });

        btn.setLocation(0, 0);
        btn.setSize(20, 30);

        painelBotoes.add(btn);
        btn.repaint();

        //Gerar o "jogar novamente" apenas se estiver no modo execução
        if (modo == 1) {

            btn = new JButton(idioma.Valor("btnJogarNovamente"));

            btn.addActionListener((java.awt.event.ActionEvent e) -> {

                dispose();
                controladora.ExecutaPartida();

            });

            btn.setLocation(0, 0);
            btn.setSize(20, 30);

            painelBotoes.add(btn);
            btn.repaint();

        }

    }

    public void VerificarAvaliacoes() {
        avaliacoesRealizar = new ArrayList<>();

        for (Avaliacao avaliacao : partida.getAvaliacoes()) {

            //Exibe a avaliação apenas caso o valor da variável esteja dentro do range da avaliação
            if (avaliacao.getVariavel().getValor() >= avaliacao.getValorInicial()
                    && avaliacao.getVariavel().getValor() <= avaliacao.getValorFinal()) {

                avaliacoesRealizar.add(avaliacao);

            }
        }

        if (!avaliacoesRealizar.isEmpty()) {
            GerarSaidaAvaliacao(avaliacoesRealizar.get(0), -1);

        } else {

            GerarSaidaFinal();

        }
    }

    public void GerarSaidaAvaliacao(Avaliacao avaliacao, int index) {

        //Caso ainda não tenha carregado avaliação, apenas cria o botão para direcionar para a primeira avaliação
        if (index > -1) {
            CarregaFalaAssistente(avaliacao.getTexto(), false);

            CarregaImagemFundoAvaliacao();
            CarregaAssistente(false);
        }

        //Caso seja a ultima avaliação, gera saída diferenciada
        if (index == (avaliacoesRealizar.size() - 1)) {

            GerarSaidaFinal();

        } else {

            painelBotoes.removeAll();
            painelBotoes.revalidate();

            btn = new JButton(idioma.Valor("btnContemeMais"));

            btn.addActionListener((java.awt.event.ActionEvent e) -> {

                int proximaAvaliacao = index + 1;

                GerarSaidaAvaliacao(avaliacoesRealizar.get(proximaAvaliacao), proximaAvaliacao);

            });

            btn.setLocation(0, 0);
            btn.setSize(20, 30);

            painelBotoes.add(btn);
            btn.repaint();

        }

        RecarregarComponentes();

    }

    /**
     * Gerar dinâmicamente os botões da saída
     *
     * @param saidas
     */
    public void GerarSaidaOpcional(ArrayList<SaidaOpcional> saidas) {

        painelBotoes.removeAll();
        painelBotoes.revalidate();

        for (SaidaOpcional s : saidas) {
            btn = new JButton(s.getNome());
            btn.setLocation(0, 0);
            btn.setSize(20, 30);
            btn.addActionListener((java.awt.event.ActionEvent e) -> {

                caminho.setEscolha(s.getNome());
                partida.getCaminhos().add(caminho);
                CarregarTextoSaida(1, s);
                RecarregarComponentes();

            });
            painelBotoes.add(btn);
            btn.repaint();
        }
    }

    public void GerarSaidaNumerica(ArrayList<SaidaNumerica> saidas) {

        painelBotoes.removeAll();
        painelBotoes.revalidate();

        painelBotoes.setBackground(new Color(223, 114, 32, 150));
        painelBotoes.setOpaque(true);

        jslSaidaNumerica = new JSlider();

        lblValorSelecionado = new JLabel();
        lblValorSelecionado.setFont(new Font("Verdana", Font.BOLD, 16));
        lblValorSelecionado.setForeground(Color.BLUE);

        jslSaidaNumerica.setOpaque(false);
        jslSaidaNumerica.setSize(1000, 200);
        //jslSaidaNumerica.setLocation(100, 600);
        //lblValorSelecionado.setLocation(225, 700);
        lblValorSelecionado.setSize(100, 50);

        painelBotoes.add(jslSaidaNumerica);
        painelBotoes.add(lblValorSelecionado);

        Integer valorMinimo = 0;
        Integer valorMaximo = 0;
        int cont = 0;

        for (SaidaNumerica s : saidas) {
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

        //Define o tamanho do seletor
        jslSaidaNumerica.setMinimum(valorMinimo);
        jslSaidaNumerica.setMaximum(valorMaximo);

        lblValorSelecionado.setText(String.valueOf(jslSaidaNumerica.getValue()));

        jslSaidaNumerica.addChangeListener((javax.swing.event.ChangeEvent evt) -> {

            lblValorSelecionado.setText(String.valueOf(jslSaidaNumerica.getValue()));

            RecarregarPainel();

        });

        btn = new JButton(idioma.Valor("btnConfirmar"));
        btn.setLocation(620, 600);
        btn.setSize(40, 30);
        btn.addActionListener((java.awt.event.ActionEvent e) -> {

            painelBotoes.setOpaque(false);
            TratarSaidaNumerica(saidas);
        });

        painelBotoes.add(btn);

        jslSaidaNumerica.repaint();
        lblValorSelecionado.repaint();

    }

    public void RecarregarPainel() {
        jslSaidaNumerica.repaint();
        painelBotoes.repaint();
        painelPrincipal.repaint();
    }

    public void GerarSaidaSituacao(Object saida, Situacao situacaoDestino, int tipoSaida) {

        boolean temFalaAssistente;
        boolean podeDesistir;

        if (tipoSaida == 1) {

            SaidaOpcional saidaO = (SaidaOpcional) saida;
            temFalaAssistente = !(saidaO.getFalaAssistente().isEmpty());
            podeDesistir = saidaO.isPodeDesistir();

        } else {

            SaidaNumerica saidaN = (SaidaNumerica) saida;
            temFalaAssistente = !(saidaN.getFalaAssistente().isEmpty());
            podeDesistir = saidaN.isPodeDesistir();

        }

        //Caso o assistente não tenha fala, executa as ações da saída e vai direto para a próxima situação
        if (temFalaAssistente) {

            painelBotoes.removeAll();

            painelBotoes.revalidate();

            //Gerar o botão de voltar apenas se for possível desistir
            if (podeDesistir) {

                btn = new JButton(idioma.Valor("btnPrecisoPensarMelhor"));
                btn.setLocation(0, 0);
                btn.setSize(20, 30);
                btn.addActionListener((java.awt.event.ActionEvent e) -> {

                    //Carrega novamente a situação
                    CarregaSituacao(situacao);
                });
                painelBotoes.add(btn);
            }

            btn = new JButton(idioma.Valor("btnContinuar"));
            btn.setLocation(0, 0);
            btn.setSize(20, 30);
            btn.addActionListener((java.awt.event.ActionEvent e) -> {

                ExecutarAcoesSaida(saida);

                //Carrega a situação seguinte
                CarregaSituacao(situacaoDestino);
            });
            painelBotoes.add(btn);

        } else {

            ExecutarAcoesSaida(saida);
            CarregaSituacao(situacaoDestino);

        }
    }

    public void TratarSaidaNumerica(ArrayList<SaidaNumerica> saidas) {
        int valorSelecionado = jslSaidaNumerica.getValue();

        caminho.setEscolha(idioma.Valor("lblValorSelecionado") + " " + valorSelecionado);
        partida.getCaminhos().add(caminho);

        //Verifica em qual saída o valor selecionado se enquadra
        for (SaidaNumerica s : saidas) {

            if (valorSelecionado >= s.getFaixa().getLimiteInferior() && valorSelecionado <= s.getFaixa().getLimiteSuperior()) {

                CarregarTextoSaida(2, s);
                RecarregarComponentes();

                break;
            }
        }
    }

    public void CarregarTextoSaida(int tipoSaida, Object saida) {

        if (tipoSaida == 1) {
            SaidaOpcional saidaOpcional = (SaidaOpcional) saida;
            CarregaFalaAssistente(saidaOpcional.getFalaAssistente(), true);

            GerarSaidaSituacao(saida, saidaOpcional.getSituacaoDestino(), tipoSaida);
        } else {

            SaidaNumerica saidaNumerica = (SaidaNumerica) saida;
            CarregaFalaAssistente(saidaNumerica.getFalaAssistente(), true);

            GerarSaidaSituacao(saida, saidaNumerica.getSituacaoDestino(), tipoSaida);

        }

    }

    public void ExecutarAcoesSaida(Object saida) {

        double novoValor;

        if (tipoSaida == 1) {

            SaidaOpcional saidaOpcional = (SaidaOpcional) saida;

            for (Acao a : saidaOpcional.getAcoes()) {
                switch (a.getOperacao()) {
                    case 0:
                    //Não faz nada
                    case 1: //Soma

                        novoValor = a.getVariavel().getValor() + a.getNumero();
                        a.getVariavel().setValor(novoValor);
                        break;

                    case 2: //Subtração

                        novoValor = a.getVariavel().getValor() - a.getNumero();
                        a.getVariavel().setValor(novoValor);
                        break;

                    case 3: //Multiplicação

                        novoValor = a.getVariavel().getValor() * a.getNumero();
                        a.getVariavel().setValor(novoValor);
                        break;

                    case 4: //Divisão

                        novoValor = a.getVariavel().getValor() / a.getNumero();
                        a.getVariavel().setValor(novoValor);
                        break;

                    case 5: //Igualar

                        novoValor = a.getNumero();
                        a.getVariavel().setValor(novoValor);

                }
            }

        } else {

            SaidaNumerica SaidaNumerica = (SaidaNumerica) saida;

            for (Acao a : SaidaNumerica.getAcoes()) {
                switch (a.getOperacao()) {
                    case 0:
                        //Não faz nada
                        break;

                    case 1: //Soma

                        novoValor = a.getVariavel().getValor() + a.getNumero();
                        a.getVariavel().setValor(novoValor);
                        break;

                    case 2: //Subtração

                        novoValor = a.getVariavel().getValor() - a.getNumero();
                        a.getVariavel().setValor(novoValor);
                        break;

                    case 3: //Multiplicação

                        novoValor = a.getVariavel().getValor() * a.getNumero();
                        a.getVariavel().setValor(novoValor);
                        break;

                    case 4: //Divisão

                        novoValor = a.getVariavel().getValor() / a.getNumero();
                        a.getVariavel().setValor(novoValor);
                        break;

                }
            }
        }

    }

    public void CarregaSituacao(Situacao situacao) {

        this.situacao = situacao;

        //Carrega um novo item de caminho
        caminho = new Caminho();

        caminho.setHora(new Date());
        caminho.setSituacao(situacao.getNome());

        CarregaAssistente(true);

        CarregaFalaAssistente(situacao.getFalaAssistente(), false);
        GerarSaidas(situacao);

        CarregaVariaveis();
        CarregaImagemFundoSituacao(situacao);
        RecarregarComponentes();

    }

    public void CarregarPreviaSituacao(Situacao situacao) {

        CarregaSituacao(situacao);

    }

    public static JanelaExecucaoPartida getInstancia() {

        if (instancia == null) {
            instancia = new JanelaExecucaoPartida(1);
        }

        return instancia;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelPrincipal = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        painelPrincipal.setPreferredSize(new java.awt.Dimension(1024, 700));

        javax.swing.GroupLayout painelPrincipalLayout = new javax.swing.GroupLayout(painelPrincipal);
        painelPrincipal.setLayout(painelPrincipalLayout);
        painelPrincipalLayout.setHorizontalGroup(
            painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1024, Short.MAX_VALUE)
        );
        painelPrincipalLayout.setVerticalGroup(
            painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        partida.getCaminhos().clear();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

    }//GEN-LAST:event_formWindowClosing

    public static void setInstancia(JanelaExecucaoPartida instancia) {
        JanelaExecucaoPartida.instancia = instancia;
    }

    public ControladoraExecucao getControladora() {
        return controladora;
    }

    public void setControladora(ControladoraExecucao controladora) {
        this.controladora = controladora;
    }

    public ImageIcon getPersonagem() {
        return personagem;
    }

    public void setPersonagem(ImageIcon personagem) {
        this.personagem = personagem;
    }

    public ArrayList<Variavel> getVariaveis() {
        return variaveis;
    }

    public void setVariaveis(ArrayList<Variavel> variaveis) {
        this.variaveis = variaveis;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel painelPrincipal;
    // End of variables declaration//GEN-END:variables
}
