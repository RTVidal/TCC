/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Jogador;

import Modelo.Situacao;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextArea;

/**
 *
 * @author Rafael
 */
public class JanelaSituacaoJogo extends javax.swing.JFrame {

    private PainelImagem imgFundo;
    private PainelImagem imgAvatar;
    private PainelImagem imgBalao;

    private ImageIcon imagemFundo;
    private ImageIcon imagemAvatar;
    private ImageIcon imagemBalao;

    private JTextArea textoBalao;

    private ArrayList<JButton> bts;

    private JButton btn;
    
    public JanelaSituacaoJogo(Situacao situacao) {
        initComponents();
        bts = new ArrayList<JButton>();
        
        setLocationRelativeTo(null);

        imagemAvatar = new ImageIcon("./Recursos/avatar.gif");
        imagemBalao = new ImageIcon("./Recursos/balao.gif");

        imgFundo = new PainelImagem(situacao.getFundoSituacao().getImage());

        imgFundo.setLayout(null);

        imgFundo.setSize(1024, 768);

        painelPrincipal.setSize(1024, 768);

        painelPrincipal.add(imgFundo);

        
        
        //Exibe o avatar
        imgAvatar = new PainelImagem(imagemAvatar.getImage());

        imgAvatar.setSize(300, 300);
        imgAvatar.setLocation(700, 500);

        imgFundo.add(imgAvatar);

        //Exibe o balão
        imgBalao = new PainelImagem(imagemBalao.getImage());

        imgBalao.setLocation(245, 185);
        imgBalao.setLayout(null);

        //Exibe o texto do balão
        textoBalao = new JTextArea();
        String texto = "No menu está uma variedade de sanduíches com preparos, "
                + "apresentações e acompanhamentos criativos. Há hambúrguer de "
                + "kafta, joelho de porco, falafel, feijoada, barreado, "
                + "couve-flor e diversos cortes de carnes, inclusive premium. "
                + "Nos pães também há grande variedade. Tem pão folha, "
                + "integral, de aveia, batata, milho, brioche e de aipim, "
                + "apenas para citar alguns. Os acompanhamentos são outras "
                + "atrações. Além da tradicional batata frita, há guacamole, "
                + "nuggets de banana, palmito assado, minipastel de geleia de "
                + "pimenta, babaganoush e chips de banana.";

        textoBalao.setText(texto);
        textoBalao.setSize(660, 230);
        textoBalao.setLocation(315, 230);
        textoBalao.setEditable(false);
        textoBalao.setAutoscrolls(true);
        textoBalao.setDragEnabled(false);
        //textoBalao.setEnabled(false);
        textoBalao.setFont(new Font("Serif", Font.ITALIC, 16));
        textoBalao.setLineWrap(true);
        textoBalao.setWrapStyleWord(true);
        textoBalao.setForeground(Color.black);
        
        imgBalao.setLayout(new FlowLayout());
        imgFundo.add(textoBalao);        
        
        imgFundo.add(imgBalao);       
        
//        btn = new JButton("Teste");
//        btn.setLocation(0, 0);
//        btn.setSize(20, 30);
//        painelPrincipal.add(btn);
        
//        Graphics g = null;

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel painelPrincipal;
    // End of variables declaration//GEN-END:variables
}
