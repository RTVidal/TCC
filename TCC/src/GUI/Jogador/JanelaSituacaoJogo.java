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
import javax.swing.JTextArea;

/**
 *
 * @author Rafael
 */
public final class JanelaSituacaoJogo extends javax.swing.JDialog {

    private ControladoraIdioma idioma;
    
    private Assistente assistente;

    private JPanel imgFundo;
    private JPanel imgAvatar;
    private JPanel imgBalao;

    private ImageIcon imagemAvatar;
    private ImageIcon imagemBalao;

    private JTextArea textoBalao;

    private JPanel painelBotoes;

    private ArrayList<JButton> botoesSaidas;

    //public static int valorSelecionado;
    private JSlider jslSaidaNumerica;

    private JButton btn;

    private JLabel lblValorSelecionado;

    private ControladoraExecucao controladora;

    private static JanelaSituacaoJogo instancia;

    public JanelaSituacaoJogo() {
        initComponents();
        setModal(true);
        idioma = ControladoraIdioma.getInstancia();

        botoesSaidas = new ArrayList<>();

        setLocationRelativeTo(null);
        setResizable(false);

        //Ajusta o tamanho da tela
        painelPrincipal.setSize(1024, 768);
        painelPrincipal.setOpaque(false);

        painelBotoes = new JPanel();

    }

    public void CarregaImagemFundo(Situacao situacao) {
        
        //Caso não haja imagem de fundo, adiciona uma imagem genérica
        if (situacao.getFundoSituacao().getDescription().equals("")) {
            ImageIcon fundoGenerico = new ImageIcon("./Recursos/fundo.jpg");
            imgFundo = new PainelImagem(fundoGenerico.getImage());
        } else {
            //Desenha a imagem de fundo
            imgFundo = new PainelImagem(situacao.getFundoSituacao().getImage());
        }

        imgFundo.setLayout(null);
        imgFundo.setOpaque(false);
        imgFundo.setSize(1024, 768);

        //Adiciona a imagem de fundo à tela
        painelPrincipal.add(imgFundo);

        
    }

    /**
     * Carrega a apresentação do assistente
     */
    public void ApresentaAssistente() {

    }

    /**
     * Carrega assistente
     */
    public void CarregaAssistente(Assistente assistente) {
        //Obtem o avatar do assistente (caso não haja assistente/avatar, preenche com um avatar genérico)
        if (assistente.getAvatarAssistente().equals("")) {
            imagemAvatar = new ImageIcon(assistente.getAvatarAssistente());
        } else {
            imagemAvatar = new ImageIcon("./Recursos/avatar1.gif");
        }

        //Exibe o balão
        imgAvatar = new PainelImagem(imagemAvatar.getImage());
        System.out.println("carregando " + imagemAvatar.getImage());
        
        
        imgAvatar.setOpaque(false);

        //Exibe o avatar do assistente
        imgAvatar.setLocation(700, 500);

        imgFundo.add(imgAvatar);
    }

    /**
     * Adiciona texto ao balão
     *
     * @param texto
     */
    public void CarregaFalaAssistente(String texto) {
        //Obtem a imagem do balão
        imagemBalao = new ImageIcon("./Recursos/balao.gif");

        //Exibe o balão
        imgBalao = new PainelImagem(imagemBalao.getImage());

        imgBalao.setLocation(245, 185);
        imgBalao.setLayout(null);

        //Exibe o texto do balão
        textoBalao = new JTextArea();

        textoBalao.setText(texto);
        textoBalao.setSize(660, 230);
        textoBalao.setLocation(50, 50);
        textoBalao.setEditable(false);
        textoBalao.setAutoscrolls(true);
        textoBalao.setDragEnabled(false);
        //textoBalao.setEnabled(false);
        textoBalao.setFont(new Font("Serif", Font.ITALIC, 16));
        textoBalao.setLineWrap(true);
        textoBalao.setWrapStyleWord(true);
        textoBalao.setForeground(Color.black);

        //Adiciona o texto ao balão
        imgBalao.add(textoBalao);

        //Adiciona o balao à imagem de fundo
        imgFundo.add(imgBalao);
    }

    /**
     * Gerar as saídas da situação
     *
     * @param situacao
     */
    public void GerarSaidas(Situacao situacao) {

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
    public void GerarSaidaApresentacao()
    {
        
    }

    /**
     * Gerar dinâmicamente os botões da saída
     *
     * @param saidas
     */
    public void GerarSaidaOpcional(ArrayList<SaidaOpcional> saidas) {

        painelBotoes.setOpaque(false);
        //painelBotoes.setT
        painelBotoes.setSize(500, 100);
        painelBotoes.setLocation(100, 600);

        imgFundo.add(painelBotoes);

        for (SaidaOpcional s : saidas) {
            btn = new JButton(s.getNome());
            btn.setLocation(0, 0);
            btn.setSize(20, 30);
            btn.addActionListener((java.awt.event.ActionEvent e) -> {
                JanelaConfirmacaoSaida jcs = new JanelaConfirmacaoSaida(s.getFalaAssistente());
                jcs.setVisible(true);
            });
            botoesSaidas.add(btn);
            painelBotoes.add(btn);
        }
    }

    public void GerarSaidaNumerica(ArrayList<SaidaNumerica> saidas) {
        jslSaidaNumerica = new JSlider();
        lblValorSelecionado = new JLabel();

        //jslSaidaNumerica.setOpaque(false);
        jslSaidaNumerica.setSize(500, 100);
        jslSaidaNumerica.setLocation(100, 600);
        lblValorSelecionado.setLocation(225, 700);
        lblValorSelecionado.setSize(50, 50);

        imgFundo.add(jslSaidaNumerica);
        imgFundo.add(lblValorSelecionado);

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

        imgFundo.add(btn);

        btn.addActionListener((java.awt.event.ActionEvent e) -> {
            TratarSaidaNumerica(saidas);
        });

    }

    public void TratarSaidaNumerica(ArrayList<SaidaNumerica> saidas) {
        int valorSelecionado = jslSaidaNumerica.getValue();

        //Verifica em qual saída o valor selecionado se enquadra
        for (SaidaNumerica s : saidas) {

            if (valorSelecionado >= s.getFaixa().getLimiteInferior() && valorSelecionado <= s.getFaixa().getLimiteSuperior()) {
                JanelaConfirmacaoSaida jcs = new JanelaConfirmacaoSaida(s.getFalaAssistente());
                jcs.setVisible(true);
                break;
            }
        }
    }

    public void CarregaSituacao(Situacao situacao, boolean inicio) {
        
        CarregaImagemFundo(situacao);
        CarregaAssistente(assistente);
        
        if(inicio)
        {
            CarregaFalaAssistente(assistente.getApresentacao());
        } else 
        {
            GerarSaidas(situacao);
        }
        
    }

    public void CarregarPreviaSituacao(Situacao situacao, Assistente assistente) {
        CarregaImagemFundo(situacao);
        CarregaAssistente(assistente);
        CarregaFalaAssistente(situacao.getFalaAssistente());
        GerarSaidas(situacao);
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

    public static JanelaSituacaoJogo getInstancia() {

        if (instancia == null) {
            instancia = new JanelaSituacaoJogo();
        }

        return instancia;
    }

    public static void setInstancia(JanelaSituacaoJogo instancia) {
        JanelaSituacaoJogo.instancia = instancia;
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
