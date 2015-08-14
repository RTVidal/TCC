/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Jogador;

import GUI.Desenvolvedor.JanelaDesenvolvimentoPartida;
import Modelo.Assistente;
import Modelo.Saida;
import Modelo.Situacao;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Rafael
 */
public class JanelaInicial extends javax.swing.JFrame {

    /**
     * Creates new form JanelaInicial
     */
    public JanelaInicial() {
        initComponents();
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAbrirJogo = new javax.swing.JButton();
        btnNovoJogo = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnAbrirJogo.setText("Abrir Jogo");

        btnNovoJogo.setText("Novo Jogo");
        btnNovoJogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoJogoActionPerformed(evt);
            }
        });

        jButton1.setText("Editar Jogo");

        jButton2.setText("Situação exemplo");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAbrirJogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNovoJogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(79, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(33, 33, 33))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnAbrirJogo)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(btnNovoJogo)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoJogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoJogoActionPerformed

        //Cria uma instancia da janela de desenvolvimento de partida
        JanelaDesenvolvimentoPartida jdp = JanelaDesenvolvimentoPartida.getInstancia();
        
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
            BufferedImage img = ImageIO.read(file);

            situacao.setFundoSituacao(img);
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

            Saida saida1 = new Saida();
            saida1.setNome("Saida 1");
            saida1.setFalaAssistente("Você escolheu saida 1");

            Saida saida2 = new Saida();
            saida2.setNome("Saida 2");
            saida2.setFalaAssistente("É a saída 2 que você escolheu");

            Saida saida3 = new Saida();
            saida3.setNome("Saída 3");
            saida3.setFalaAssistente("Você quer a saída 3?");

            Saida saida4 = new Saida();
            saida4.setNome("Saida 4");
            saida4.setFalaAssistente("Saída 4 então");

            ArrayList<Saida> saidas = new ArrayList<>();

            saidas.add(saida1);
            saidas.add(saida2);
            saidas.add(saida3);
            saidas.add(saida4);

            situacao.setSaidas(saidas);
            
            JanelaSituacaoJogo jsj = new JanelaSituacaoJogo(situacao, assistente);

            jsj.setVisible(true);
            jsj.setSize(1024, 768);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbrirJogo;
    private javax.swing.JButton btnNovoJogo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    // End of variables declaration//GEN-END:variables
}
