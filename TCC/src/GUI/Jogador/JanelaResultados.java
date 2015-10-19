/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Jogador;

import Controle.ControladoraIdioma;
import GUI.Suporte.CaminhosTbModel;
import GUI.Suporte.VariaveisResultadoTbModel;
import Modelo.Partida;
import Modelo.Variavel;
import Persistencia.IOExportaTXT;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rafael
 */
public class JanelaResultados extends javax.swing.JFrame {

    private final Partida partida;
    private final ControladoraIdioma idioma;
    
    /**
     * Creates new form JanelaResultados
     * @param jep
     */
    public JanelaResultados(JanelaExecucaoPartida jep) {
        initComponents();
        
        setLocationRelativeTo(jep);
        
        partida = Partida.getInstancia();
        idioma = ControladoraIdioma.getInstancia();
        
        setTitle(idioma.Valor("tituloResultados"));
        
        CarregarIdioma();
        
        CarregarVariaveis();
        CarregarCaminho();
    }
    
    public final void CarregarIdioma()
    {
        //Botões
        btnExportarTXT.setText(idioma.Valor("btnExportarTXT"));
        btnFechar.setText(idioma.Valor("btnFechar"));
        
        //Abas
        painelResultados.setTitleAt(0, idioma.Valor("abaVariaveis"));
        painelResultados.setTitleAt(1, idioma.Valor("abaCaminhos"));
    }
    
    public final void CarregarVariaveis()
    {
        //Separa as variáveis auto-definidas das padrões para que as auto definidas sejam exibidas ao fim da tabela
        ArrayList<Variavel> padroes = new ArrayList<>();
        ArrayList<Variavel> autodefinidas = new ArrayList<>();

        for (Variavel v : partida.getVariaveis()) {
            if (v.isAutodefinida()) {
                autodefinidas.add(v);
            } else {
                padroes.add(v);
            }
        }

        ArrayList<Variavel> todas = new ArrayList();
        todas.addAll(padroes);
        todas.addAll(autodefinidas);

        VariaveisResultadoTbModel model = new VariaveisResultadoTbModel(todas);

        tblVariaveis.setModel(model);

        //Esconder a coluna contendo o objeto da variável
        tblVariaveis.getColumnModel().getColumn(0).setMinWidth(0);
        tblVariaveis.getColumnModel().getColumn(0).setMaxWidth(0);
        tblVariaveis.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
    
    public final void CarregarCaminho()
    {
        CaminhosTbModel model = new CaminhosTbModel(partida.getCaminhos());
        
        tblCaminhos.setModel(model);
    }
    
    /**
     * Monta um arquivo TXT com as informações e permite o usuário salvá-lo
     * @throws java.io.IOException
     */
    public void ExportarTXT() throws IOException
    {
        IOExportaTXT iot = new IOExportaTXT();
        iot.SalvarTXT();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelResultados = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVariaveis = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCaminhos = new javax.swing.JTable();
        btnFechar = new javax.swing.JButton();
        btnExportarTXT = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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
        jScrollPane1.setViewportView(tblVariaveis);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                .addContainerGap())
        );

        painelResultados.addTab("lblVariaveis", jPanel1);

        tblCaminhos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblCaminhos);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                .addContainerGap())
        );

        painelResultados.addTab("lblCaminho", jPanel2);

        btnFechar.setText("btnlFechar");
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });

        btnExportarTXT.setText("btnExportarTXT");
        btnExportarTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarTXTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelResultados, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnExportarTXT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnFechar)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelResultados, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExportarTXT)
                    .addComponent(btnFechar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
      
        dispose();
        
    }//GEN-LAST:event_btnFecharActionPerformed

    private void btnExportarTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarTXTActionPerformed
        
        try {
            ExportarTXT();
        } catch (IOException ex) {
            Logger.getLogger(JanelaResultados.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btnExportarTXTActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportarTXT;
    private javax.swing.JButton btnFechar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane painelResultados;
    private javax.swing.JTable tblCaminhos;
    private javax.swing.JTable tblVariaveis;
    // End of variables declaration//GEN-END:variables
}
