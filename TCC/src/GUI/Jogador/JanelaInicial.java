/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Jogador;

import Controle.ControladoraIdioma;
import GUI.Desenvolvedor.JanelaDesenvolvimentoPartida;
import Modelo.Assistente;
import Modelo.Partida;
import Modelo.Saida;
import Modelo.SaidaOpcao;
import Modelo.Situacao;
import Persistencia.IOPartida;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

/**
 *
 * @author Rafael
 */
public class JanelaInicial extends javax.swing.JFrame {

    private JanelaDesenvolvimentoPartida jdp;
    private ControladoraIdioma ci;
    private String idiomaSelecionado;
    
    private final ControladoraIdioma idioma;
    /**
     * Creates new form JanelaInicial
     */
    public JanelaInicial() {
        initComponents();
        setLocationRelativeTo(null);
        idioma = ControladoraIdioma.getInstancia();
        PreencheComboIdiomas();
        idiomaSelecionado = "Português";
        DefineIdioma();
    }
    
    public void CarregaRecursos()
    {
        btnAbrirJogo.setText(idioma.Valor("principalBtnAbrirJogo"));
        btnEditarJogo.setText(idioma.Valor("principalBtnEditarJogo"));
        btnNovoJogo.setText(idioma.Valor("principalBtnNovoJogo"));
        lblTituloPrincipal.setText(idioma.Valor("tituloTelaPrincipal"));
        lblIdioma.setText(idioma.Valor("principalLblIdioma"));
    }
    
    public final void PreencheComboIdiomas()
    {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        
        model.addElement("Português");
        model.addElement("English");
        model.addElement("Español");
        
        cbxIdiomas.setModel(model);
    }
    
    public void DefineIdioma()
    {
        switch(idiomaSelecionado)
        {
            case "Português":
                idioma.DefineIdioma(1);
                break;
            case "English":
                idioma.DefineIdioma(2);
                break;
            case "Español":
                idioma.DefineIdioma(3);
                break;
        }
        
        CarregaRecursos();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        btnAbrirJogo = new javax.swing.JButton();
        btnNovoJogo = new javax.swing.JButton();
        btnEditarJogo = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        cbxIdiomas = new javax.swing.JComboBox();
        lblIdioma = new javax.swing.JLabel();
        lblTituloPrincipal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        btnAbrirJogo.setText("Abrir Jogo");

        btnNovoJogo.setText("Novo Jogo");
        btnNovoJogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoJogoActionPerformed(evt);
            }
        });

        btnEditarJogo.setText("Editar Jogo");
        btnEditarJogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarJogoActionPerformed(evt);
            }
        });

        jButton2.setText("Situação exemplo");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        cbxIdiomas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxIdiomas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxIdiomasActionPerformed(evt);
            }
        });

        lblIdioma.setText("Idioma");

        lblTituloPrincipal.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTituloPrincipal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloPrincipal.setText("Faça seu Jogo!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblIdioma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAbrirJogo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTituloPrincipal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                    .addComponent(btnEditarJogo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNovoJogo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxIdiomas, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloPrincipal)
                .addGap(18, 18, 18)
                .addComponent(btnAbrirJogo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditarJogo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNovoJogo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addComponent(lblIdioma)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxIdiomas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoJogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoJogoActionPerformed

        //Cria uma instancia da janela de desenvolvimento de partida
        jdp = JanelaDesenvolvimentoPartida.getInstancia();
        
        jdp.setVisible(true);
        
    }//GEN-LAST:event_btnNovoJogoActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        try
        {
            //Preenche a instancia do assistente
            Assistente assistente = new Assistente();

            assistente.setNome("Joselito");
            assistente.setApresentacao("Oi amiguinho");
            assistente.setAvatarAssistente("avatar1");

            Assistente.setInstancia(assistente);

            Situacao situacao = new Situacao();

            File file = new File("./recursos/teste.jpg");
            Image img = ImageIO.read(file);
            
            ImageIcon image = new ImageIcon("./recursos/teste.jpg");

            situacao.setFundoSituacao(image);
            situacao.setFalaAssistente("No menu está uma variedade de sanduíches com preparos, "
                    + "apresentações e acompanhamentos criativos. Há hambúrguer de "
                    + "kafta, joelho de porco, falafel, feijoada, barreado, "
                    + "couve-flor e diversos cortes de carnes, inclusive premium. "
                    + "Nos pães também há grande variedade. Tem pão folha, "
                    + "integral, de aveia, batata, milho, brioche e de aipim, "
                    + "apenas para citar alguns. Os acompanhamentos são outras "
                    + "atrações. Além da tradicional batata frita, há guacamole, "
                    + "nuggets de banana, palmito assado, minipastel de geleia de "
                    + "pimenta, babaganoush e chips de banana.");

            SaidaOpcao saida1 = new SaidaOpcao();
            saida1.setNome("Saida 1");
            saida1.setFalaAssistente("Você escolheu saida 1");

            SaidaOpcao saida2 = new SaidaOpcao();
            saida2.setNome("Saida 2");
            saida2.setFalaAssistente("É a saída 2 que você escolheu");

            SaidaOpcao saida3 = new SaidaOpcao();
            saida3.setNome("Saída 3");
            saida3.setFalaAssistente("Você quer a saída 3?");

            SaidaOpcao saida4 = new SaidaOpcao();
            saida4.setNome("Saida 4");
            saida4.setFalaAssistente("Saída 4 então");

            ArrayList<SaidaOpcao> saidasOpcao = new ArrayList<>();

            saidasOpcao.add(saida1);
            saidasOpcao.add(saida2);
            saidasOpcao.add(saida3);
            saidasOpcao.add(saida4);

            Saida saida = new Saida();
            saida.setTipoSaida(1);
            saida.setsaidasOpcao(saidasOpcao);
            
            situacao.setSaida(saida);
            
            JanelaSituacaoJogo jsj = new JanelaSituacaoJogo(situacao, assistente);

            jsj.setVisible(true);
            jsj.setSize(1024, 768);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnEditarJogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarJogoActionPerformed
        
        IOPartida iop = new IOPartida();
        
        Partida partidaDesenvolvimento = iop.LePartida();
        
        Partida.setInstancia(partidaDesenvolvimento);
        
        jdp = JanelaDesenvolvimentoPartida.getInstancia();
        
        jdp.setVisible(true);
        
    }//GEN-LAST:event_btnEditarJogoActionPerformed

    private void cbxIdiomasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxIdiomasActionPerformed
        
        idiomaSelecionado = (String)cbxIdiomas.getSelectedItem();
        DefineIdioma();
        System.out.println("idioma selecionado " + idiomaSelecionado);
        
    }//GEN-LAST:event_cbxIdiomasActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbrirJogo;
    private javax.swing.JButton btnEditarJogo;
    private javax.swing.JButton btnNovoJogo;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbxIdiomas;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel lblIdioma;
    private javax.swing.JLabel lblTituloPrincipal;
    // End of variables declaration//GEN-END:variables
}
