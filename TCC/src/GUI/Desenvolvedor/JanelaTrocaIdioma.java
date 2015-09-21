/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Desenvolvedor;

import Controle.ControladoraIdioma;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Rafael
 */
public class JanelaTrocaIdioma extends javax.swing.JDialog {

    private final ControladoraIdioma idioma;
    private final JanelaDesenvolvimentoPartida jdp;
    /**
     * Creates new form JanelaTrocaIdioma
     */
    public JanelaTrocaIdioma() {
        initComponents();
        setModal(true);
        
        idioma = ControladoraIdioma.getInstancia();
        jdp = JanelaDesenvolvimentoPartida.getInstancia();
        
        setLocationRelativeTo(jdp);
        
        PreencheComboIdiomas();
    }
    
    public final void PreencheComboIdiomas() {
        
        int idiomaSelecionado = 0;
        switch(idioma.getIdiomaAtual())
        {
            case "English":
                
                idiomaSelecionado = 0;
                break;
                
            case "Español":
                
                idiomaSelecionado = 1;
                break;
                
            case "Português":
                    
                idiomaSelecionado = 2;
                break;
        }
        
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addElement("English");
        model.addElement("Español");
        model.addElement("Português");
        cbxIdiomas.setModel(model);
        cbxIdiomas.setSelectedIndex(idiomaSelecionado);
        
    }
    
    public void SalvarIdioma()
    {
        idioma.DefineIdioma((String) cbxIdiomas.getSelectedItem());
        
        jdp.CarregaIdioma();
        
        dispose();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbxIdiomas = new javax.swing.JComboBox();
        btnConfirmar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        cbxIdiomas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnConfirmar.setText("btnConfirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        jLabel1.setText("lblSelecioneIdioma");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(cbxIdiomas, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbxIdiomas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnConfirmar)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        
        SalvarIdioma();
        
    }//GEN-LAST:event_btnConfirmarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JComboBox cbxIdiomas;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
