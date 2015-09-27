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
import Modelo.Assistente;
import Modelo.Avaliacao;
import Modelo.Partida;
import Modelo.SaidaNumerica;
import Modelo.SaidaOpcional;
import Modelo.Situacao;
import Modelo.Variavel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
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

    private Assistente assistente;

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

        assistente = partida.getAssistente();
        variaveis = partida.getVariaveis();

        CarregaAssistente();
        CarregarTabelaVariaveis();
        CarregaPainelSaida();
        ResetarVariaveis();

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

    public void CarregaImagemFundo(Situacao situacao) {

        if (imgFundo != null) {
            painelPrincipal.remove(imgFundo);
        }

        //Ajusta o tamanho da tela
        painelPrincipal.setSize(1024, 700);
        painelPrincipal.setOpaque(false);

        //Caso não haja imagem de fundo, adiciona uma imagem genérica
        if (situacao.getFundoSituacao().getDescription().equals("")) {
            ImageIcon fundoGenerico = new ImageIcon("./Recursos/fundo.jpg");
            imgFundo = new PainelImagem(fundoGenerico.getImage());
        } else {
            //Desenha a imagem de fundo
            imgFundo = new PainelImagem(situacao.getFundoSituacao().getImage());
        }

        imgFundo.setOpaque(false);
        //imgFundo.setSize(1024, 768);

        //Adiciona a imagem de fundo à tela
        painelPrincipal.add(imgFundo);

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
     */
    public void CarregaAssistente() {
        //Obtem o avatar do assistente (caso não haja assistente/avatar, preenche com um avatar genérico)
        if (!assistente.getAvatarAssistente().equals("")) {
            imagemAvatar = new ImageIcon(assistente.getAvatarAssistente());
        } else {
            imagemAvatar = new ImageIcon("./Recursos/avatar1.gif");
        }

        //Exibe o balão
        imgAvatar = new PainelImagem(imagemAvatar.getImage());

        imgAvatar.setOpaque(false);

        //Exibe o avatar do assistente
        imgAvatar.setLocation(700, 500);

        painelPrincipal.add(imgAvatar);
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
        
        for (Variavel v : variaveis) {
            //Adicionar na lista apenas caso a variável não seja oculta
            if (!v.isOculta()) {
                model.addRow(new Object[]{v.getNome(), v.getValor()});
                
                if ((v.getNome().length()*2) > largura)
                {
                    largura = v.getNome().length()*10;
                }
            }
        }

        //Calcula a altura da tabela de variáveis
        int altura = variaveis.size() * 16;
        
        tblVariaveis.setModel(model);
        tblVariaveis.setSize(largura + 50, altura);
        tblVariaveis.setLocation(10, 10);

        tblVariaveis.setModel(model);

        tblVariaveis.getColumnModel().getColumn(0).setPreferredWidth(largura);
        tblVariaveis.getColumnModel().getColumn(1).setPreferredWidth(50);

        tblVariaveis.revalidate();

        painelPrincipal.add(tblVariaveis);

    }

    public void CarregaFalaAssistente(String texto) {

        if (imgBalao != null) {
            painelPrincipal.remove(imgBalao);
        }

        imagemBalao = new ImageIcon("./Recursos/balao.gif");

        imgBalao = new PainelImagem(imagemBalao.getImage());

        //Exibe o texto do balão
        textoBalao = new JTextArea();

        textoBalao.setLocation(40, 40);
        textoBalao.setEditable(false);
        textoBalao.setDragEnabled(false);
        textoBalao.setFont(new Font("Serif", Font.ITALIC, 16));
        textoBalao.setLineWrap(true);
        textoBalao.setWrapStyleWord(true);
        textoBalao.setForeground(Color.black);
        textoBalao.setOpaque(false);

        textoBalao.setText(texto);

        int caracteres = texto.length();

        int altura = (caracteres / 50) * 25;
        
        int largura = 700; //Largura máxima

        if (altura < 16) {
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

        imgBalao = new PainelImagem(imgRedimensionada.getImage());

        //Posicionar o balão sempre acima do avatar e mais a direita possível
        int yBalao = yAvatar - imgBalao.getHeight();
        int xBalao;

        //Se a largura for menor do que o máximo (600), posicionar o balão acima do avatar
        if (largura < 700) {
            //Gerar o final do balão sempre rente a 2/3 do tamanho do avatar
            xBalao = (imgAvatar.getX() + (2 * (imgAvatar.getWidth() / 3))) - imgBalao.getWidth();

        } else {

            xBalao = (xAvatar - imgBalao.getWidth()) + 30;

        }

        imgBalao.setLocation(xBalao, yBalao);
        imgBalao.setLayout(null);

        imgBalao.add(textoBalao);

        painelPrincipal.add(imgBalao);

        //O balão precisa ser adicionado antes da imagem de fundo para ser exibido
        if (imgFundo != null) {
            painelPrincipal.remove(imgFundo);
            painelPrincipal.add(imgFundo);
        }

    }

    /**
     * Gerar as saídas da situação
     *
     * @param situacao
     */
    public void GerarSaidas(Situacao situacao) {

        boolean naoHaSaidas = false;

        tipoSaida = situacao.getSaida().getTipoSaida();
        //painelPrincipal.remove(painelBotoes);
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
            if (partida.getAvaliacoes().isEmpty()) {
                System.out.println("Não tem avaliações");
                GerarSaidaFinal();
            } else {
                System.out.println("Tem avaliações");
                VerificarAvaliacoes();

                //GerarSaidaAvaliacao(partida.getAvaliacoes().get(0), 0, false);
            }

        }
    }

    /**
     * Gera a saída da apresentação do assistente
     */
    public void GerarSaidaApresentacao() {

        btn = new JButton(idioma.Valor("btnContinuar"));
        btn.setLocation(0, 0);
        btn.setSize(20, 30);
        btn.addActionListener((java.awt.event.ActionEvent e) -> {
            controladora.IniciarJogo();
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

            dispose();

        });

        btn.setLocation(0, 0);
        btn.setSize(20, 30);

        painelBotoes.add(btn);
        btn.repaint();

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
            CarregaFalaAssistente(avaliacao.getTexto());
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

        jslSaidaNumerica = new JSlider();
        lblValorSelecionado = new JLabel();

        //jslSaidaNumerica.setOpaque(false);
        jslSaidaNumerica.setSize(800, 100);
        jslSaidaNumerica.setLocation(100, 600);
        lblValorSelecionado.setLocation(225, 700);
        lblValorSelecionado.setSize(50, 50);

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
        });

        btn = new JButton(idioma.Valor("btnConfirmar"));
        btn.setLocation(620, 600);
        btn.setSize(40, 30);
        btn.addActionListener((java.awt.event.ActionEvent e) -> {
            TratarSaidaNumerica(saidas);
        });

        painelBotoes.add(btn);

        jslSaidaNumerica.repaint();
        lblValorSelecionado.repaint();

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
                    CarregaSituacao(situacao, 2);
                });
                painelBotoes.add(btn);
            }

            btn = new JButton(idioma.Valor("btnVamosLa"));
            btn.setLocation(0, 0);
            btn.setSize(20, 30);
            btn.addActionListener((java.awt.event.ActionEvent e) -> {

                ExecutarAcoesSaida(saida);

                //Carrega a situação seguinte
                CarregaSituacao(situacaoDestino, 2);
            });
            painelBotoes.add(btn);

        } else {

            ExecutarAcoesSaida(saida);
            CarregaSituacao(situacaoDestino, 2);

        }
    }

    public void TratarSaidaNumerica(ArrayList<SaidaNumerica> saidas) {
        int valorSelecionado = jslSaidaNumerica.getValue();

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
            CarregaFalaAssistente(saidaOpcional.getFalaAssistente());

            GerarSaidaSituacao(saida, saidaOpcional.getSituacaoDestino(), tipoSaida);
        } else {

            SaidaNumerica saidaNumerica = (SaidaNumerica) saida;
            CarregaFalaAssistente(saidaNumerica.getFalaAssistente());

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

    public void CarregaSituacao(Situacao situacao, int etapa) {

        this.situacao = situacao;

        //1. Início, 2. Continuação
        switch (etapa) {

            case 1:
                CarregaFalaAssistente(assistente.getApresentacao());
                GerarSaidaApresentacao();
                break;

            case 2:

                CarregaFalaAssistente(situacao.getFalaAssistente());
                GerarSaidas(situacao);
                break;
        }

        CarregaVariaveis();
        CarregaImagemFundo(situacao);
        RecarregarComponentes();

    }

    public void CarregarPreviaSituacao(Situacao situacao, Assistente assistente) {

        this.assistente = assistente;
        CarregaSituacao(situacao, 2);

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

    public static void setInstancia(JanelaExecucaoPartida instancia) {
        JanelaExecucaoPartida.instancia = instancia;
    }

    public ControladoraExecucao getControladora() {
        return controladora;
    }

    public void setControladora(ControladoraExecucao controladora) {
        this.controladora = controladora;
    }

    public Assistente getAssistente() {
        return assistente;
    }

    public void setAssistente(Assistente assistente) {
        this.assistente = assistente;
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
