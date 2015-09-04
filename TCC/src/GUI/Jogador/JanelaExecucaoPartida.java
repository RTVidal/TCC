/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Jogador;

import Controle.ControladoraExecucao;
import Controle.ControladoraIdioma;
import GUI.Suporte.PainelImagem;
import Modelo.Assistente;
import Modelo.Partida;
import Modelo.SaidaNumerica;
import Modelo.SaidaOpcional;
import Modelo.Situacao;
import java.awt.Color;
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

    private ControladoraIdioma idioma;

    private Assistente assistente;

    private Situacao situacao;

    private JPanel imgFundo;
    private JPanel imgAvatar;
    private JPanel imgBalao;
    private JPanel painelBotoes;
    private JPanel painelVariaveis;

    private ImageIcon imagemAvatar;
    private ImageIcon imagemBalao;

    private JTextArea textoBalao;

    private JTable tblVariaveis;

    private ArrayList<JButton> botoesSaidas;

    //public static int valorSelecionado;
    private JSlider jslSaidaNumerica;

    private JButton btn;

    private JLabel lblValorSelecionado;

    private ControladoraExecucao controladora;

    private static JanelaExecucaoPartida instancia;

    public JanelaExecucaoPartida() {
        initComponents();
        //setModal(true);
        idioma = ControladoraIdioma.getInstancia();

        botoesSaidas = new ArrayList<>();

        setLocationRelativeTo(null);
        setResizable(false);

        assistente = Partida.getInstancia().getAssistente();

        CarregaAssistente();
        CarregaVariaveis();
        CarregaBalaoAssistente();
        CarregaPainelSaida();

    }

    public void RecarregarComponentes() {

        textoBalao.repaint();
        imgBalao.repaint();
        btn.repaint();
        painelBotoes.repaint();
        imgFundo.repaint();
        painelPrincipal.repaint();

    }

    public void CarregaImagemFundo(Situacao situacao) {

        if (imgFundo != null) {
            painelPrincipal.remove(imgFundo);
        }

        //Ajusta o tamanho da tela
        painelPrincipal.setSize(1024, 768);
        painelPrincipal.setOpaque(false);
        painelPrincipal.repaint();

        //Caso não haja imagem de fundo, adiciona uma imagem genérica
        if (situacao.getFundoSituacao().getDescription().equals("")) {
            ImageIcon fundoGenerico = new ImageIcon("./Recursos/fundo.jpg");
            imgFundo = new PainelImagem(fundoGenerico.getImage());
        } else {
            //Desenha a imagem de fundo
            imgFundo = new PainelImagem(situacao.getFundoSituacao().getImage());
        }

        //imgFundo.setLayout(null);
        imgFundo.setOpaque(false);
        imgFundo.setSize(1024, 768);

        //Adiciona a imagem de fundo à tela
        painelPrincipal.add(imgFundo);

        System.out.println("carregou " + situacao.getFundoSituacao().getDescription());

    }

    public void CarregaPainelSaida() {

        painelBotoes = new JPanel();

        painelBotoes.setOpaque(true);
        painelBotoes.setSize(500, 100);
        painelBotoes.setLocation(100, 600);

        painelPrincipal.add(painelBotoes);

    }

    /**
     * Carrega assistente
     */
    public void CarregaAssistente() {
        //Obtem o avatar do assistente (caso não haja assistente/avatar, preenche com um avatar genérico)
        if (assistente.getAvatarAssistente().equals("")) {
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

    /**
     * Carrega as variáveis
     */
    public void CarregaVariaveis() {

        //JList listaVariaveis = new JList();
        tblVariaveis = new JTable();

        DefaultTableModel model = (DefaultTableModel)tblVariaveis.getModel();   

        model.addColumn("variavel");
        model.addColumn("valor");
        
        //model.setNumRows(0);
        model.addRow(new Object[]{"Teste", "11"});
        model.addRow(new Object[]{"TesAAte", "642"});
        model.addRow(new Object[]{"Teste555", "764"});

        //tblVariaveis.setModel(model);
        tblVariaveis.setSize(150, 100);
        tblVariaveis.setLocation(10, 10);
        tblVariaveis.setEnabled(false);
        tblVariaveis.setShowGrid(false);
        tblVariaveis.setFont(new Font("Verdana", Font.BOLD, 14));
        tblVariaveis.setForeground(Color.ORANGE);
        tblVariaveis.setBackground(new Color(0, 0, 255, 150));

        tblVariaveis.getColumnModel().getColumn(0).setPreferredWidth(100);
        tblVariaveis.getColumnModel().getColumn(1).setPreferredWidth(50);


        painelPrincipal.add(tblVariaveis);

    }

    /**
     * Adiciona texto ao balão
     *
     */
    public void CarregaBalaoAssistente() {
        //Obtem a imagem do balão
        imagemBalao = new ImageIcon("./Recursos/balao.gif");

        //Exibe o balão
        imgBalao = new PainelImagem(imagemBalao.getImage());

        imgBalao.setLocation(245, 185);
        imgBalao.setLayout(null);

        //Exibe o texto do balão
        textoBalao = new JTextArea();

        textoBalao.setSize(660, 230);
        textoBalao.setLocation(50, 50);
        textoBalao.setEditable(false);
        textoBalao.setAutoscrolls(true);
        textoBalao.setDragEnabled(false);
        textoBalao.setFont(new Font("Serif", Font.ITALIC, 16));
        textoBalao.setLineWrap(true);
        textoBalao.setWrapStyleWord(true);
        textoBalao.setForeground(Color.black);

        //Adiciona o texto ao balão
        imgBalao.add(textoBalao);

        //Adiciona o balao à imagem de fundo
        painelPrincipal.add(imgBalao);

        textoBalao.repaint();
        imgBalao.repaint();
    }

    public void CarregaFalaAssistente(String texto) {
        textoBalao.setText(texto);
    }

    /**
     * Gerar as saídas da situação
     *
     * @param situacao
     */
    public void GerarSaidas(Situacao situacao) {

        //painelPrincipal.remove(painelBotoes);
        switch (situacao.getSaida().getTipoSaida()) {
            case 0:
                //Não há tipo de saida definido
                break;
            case 1: //Saida opcional
                GerarSaidaOpcional(situacao.getSaida().getsaidasOpcao());
                break;
            case 2:
                GerarSaidaNumerica(situacao.getSaida().getSaidasNumerica());
                break;
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

        painelBotoes.add(btn);

        btn.addActionListener((java.awt.event.ActionEvent e) -> {
            TratarSaidaNumerica(saidas);
        });

        jslSaidaNumerica.repaint();
        lblValorSelecionado.repaint();
        btn.repaint();

    }

    public void GerarSaidaSituacao(Situacao situacaoDestino) {

        painelBotoes.removeAll();

        painelBotoes.revalidate();

        btn = new JButton(idioma.Valor("btnVoltar"));
        btn.setLocation(0, 0);
        btn.setSize(20, 30);
        btn.addActionListener((java.awt.event.ActionEvent e) -> {
            //Carrega novamente a situação
            CarregaSituacao(situacao, 2);
        });
        painelBotoes.add(btn);

        btn = new JButton(idioma.Valor("btnContinuar"));
        btn.setLocation(0, 0);
        btn.setSize(20, 30);
        btn.addActionListener((java.awt.event.ActionEvent e) -> {
            //Carrega novamente a situação
            CarregaSituacao(situacaoDestino, 2);
        });
        painelBotoes.add(btn);

    }

    public void TratarSaidaNumerica(ArrayList<SaidaNumerica> saidas) {
        int valorSelecionado = jslSaidaNumerica.getValue();

        //Verifica em qual saída o valor selecionado se enquadra
        for (SaidaNumerica s : saidas) {

            if (valorSelecionado >= s.getFaixa().getLimiteInferior() && valorSelecionado <= s.getFaixa().getLimiteSuperior()) {
                //JanelaConfirmacaoSaida jcs = new JanelaConfirmacaoSaida(s.getFalaAssistente());
                //jcs.setVisible(true);
                break;
            }
        }
    }

    public void CarregarTextoSaida(int tipoSaida, Object saida) {

        if (tipoSaida == 1) {
            SaidaOpcional saidaOpcional = (SaidaOpcional) saida;
            CarregaFalaAssistente(saidaOpcional.getFalaAssistente());

            GerarSaidaSituacao(saidaOpcional.getSituacaoDestino());
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

        CarregaImagemFundo(situacao);
        RecarregarComponentes();

    }

    public void CarregarPreviaSituacao(Situacao situacao, Assistente assistente) {

        this.assistente = assistente;
        CarregaSituacao(situacao, 2);

    }

    public static JanelaExecucaoPartida getInstancia() {

        if (instancia == null) {
            instancia = new JanelaExecucaoPartida();
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

        javax.swing.GroupLayout painelPrincipalLayout = new javax.swing.GroupLayout(painelPrincipal);
        painelPrincipal.setLayout(painelPrincipalLayout);
        painelPrincipalLayout.setHorizontalGroup(
            painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1024, Short.MAX_VALUE)
        );
        painelPrincipalLayout.setVerticalGroup(
            painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 768, Short.MAX_VALUE)
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel painelPrincipal;
    // End of variables declaration//GEN-END:variables
}
