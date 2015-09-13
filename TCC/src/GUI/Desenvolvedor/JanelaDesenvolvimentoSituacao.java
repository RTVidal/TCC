/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Desenvolvedor;

import Controle.ControladoraIdioma;
import GUI.Suporte.SaidasNumericasTbModel;
import GUI.Suporte.SaidasOpcionaisTbModel;
import Modelo.Faixa;
import Modelo.Partida;
import Modelo.Saida;
import Modelo.SaidaNumerica;
import Modelo.SaidaOpcional;
import Modelo.Situacao;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

class MyCustomFilter extends javax.swing.filechooser.FileFilter {

    @Override
    public boolean accept(File file) {
        // Allow only directories, or files with ".txt" extension
        return file.isDirectory() || file.getAbsolutePath().endsWith(".jpg");
    }

    @Override
    public String getDescription() {
        // This description will be displayed in the dialog,
        // hard-coded = ugly, should be done via I18N
        return "Image (*.jpg)";
    }
}

/**
 *
 * @author Rafael
 */
public class JanelaDesenvolvimentoSituacao extends javax.swing.JFrame {

    private JFileChooser fileChooser;
    private final Situacao situacao;
    private final Partida partidaDesenvolvimento;

    private final Saida saida;

    private final ControladoraIdioma idioma;

    //1. Inserir, 2. Editar
    private int acao;

    //private static JanelaDesenvolvimentoSituacao instancia;
    JanelaDesenvolvimentoPartida jdp;

    /**
     * Creates new form JanelaNovaSituacao
     *
     * @param acao
     * @param situacao
     */
    public JanelaDesenvolvimentoSituacao(int acao, Situacao situacao) {
        initComponents();
        //setModal(true);
        this.acao = acao;

        idioma = ControladoraIdioma.getInstancia();

        jdp = JanelaDesenvolvimentoPartida.getInstancia();

        setLocationRelativeTo(jdp);

        

        partidaDesenvolvimento = Partida.getInstancia();
        
        txaFalaAssistente.setWrapStyleWord(true);

        if (acao == 2) {
            this.situacao = situacao;
            saida = situacao.getSaida();
            CarregarSituacao();
        } else {
            this.situacao = new Situacao();
            saida = new Saida();
            saida.setTipoSaida(1);
            this.situacao.setSaida(saida);
        }

        CarregarComboTipoSaida();
        AtualizaTabelaSaidas();
    }

    public final void CarregarComboTipoSaida() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        model.addElement(idioma.Valor("lblOpcional"));
        model.addElement(idioma.Valor("lblNumerica"));

        cbxTipoSaida.setModel(model);

        if (saida.getTipoSaida() > 0) {
            cbxTipoSaida.setSelectedIndex(saida.getTipoSaida() - 1);
        }
    }

    public final void CarregarSituacao() {
        txtNomeSituacao.setText(situacao.getNome());
        txaFalaAssistente.setText(situacao.getFalaAssistente());
        txtArquivo.setText(situacao.getFundoSituacao().getDescription());
        chbSituacaoInicial.setSelected(situacao.isSituacaoInicial());

        AtualizaTabelaSaidas();

    }

    public final void AtualizaTabelaSaidas() {
        //Atualiza a tabela conforme o tipo de saída
        boolean naoHaSaidas = saida.getSaidasNumerica().isEmpty() && saida.getSaidasOpcao().isEmpty();
        System.out.println(saida.getSaidasNumerica().isEmpty() + " e " + saida.getSaidasOpcao().isEmpty());

        //Habilitar o combobox apenas quando não houverem saídas cadastradas
        cbxTipoSaida.setEnabled(naoHaSaidas);

        switch (saida.getTipoSaida()) {
            case 1:

                btnEditarSaida.setEnabled(!saida.getSaidasOpcao().isEmpty());
                btnExcluirSaida.setEnabled(!saida.getSaidasOpcao().isEmpty());

                SaidasOpcionaisTbModel modelSO = new SaidasOpcionaisTbModel(saida.getSaidasOpcao());
                tblSaidas.setModel(modelSO);
                cbxTipoSaida.setSelectedIndex(0);

                break;
            case 2:

                btnEditarSaida.setEnabled(!saida.getSaidasNumerica().isEmpty());
                btnExcluirSaida.setEnabled(!saida.getSaidasNumerica().isEmpty());

                SaidasNumericasTbModel modelSN = new SaidasNumericasTbModel(saida.getSaidasNumerica());
                tblSaidas.setModel(modelSN);
                cbxTipoSaida.setSelectedIndex(1);

                break;
        }

        //Esconder a coluna contendo o objeto da saída
        tblSaidas.getColumnModel().getColumn(0).setMinWidth(0);
        tblSaidas.getColumnModel().getColumn(0).setMaxWidth(0);
        tblSaidas.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    /**
     * Excluir a saída selecionada
     */
    public void ExcluirSaida() {
        boolean continuar = false;

        String mensagem = idioma.Valor("msgExclusaoAvaliacao");
        int opcao = JOptionPane.showConfirmDialog(null, mensagem, idioma.Valor("lblAviso"), JOptionPane.YES_NO_OPTION);

        if (opcao == 0) {

            continuar = true;

        }

        if (continuar) {

            int index = tblSaidas.getSelectedRow();

            switch (saida.getTipoSaida()) {
                case 0: //Não há tipo de saída definido, não faz nada

                    break;

                case 1:

                    SaidaOpcional saidaO = (SaidaOpcional) tblSaidas.getValueAt(index, 0);
                    saida.getsaidasOpcao().remove(saidaO);
                    break;

                case 2:

                    SaidaNumerica saidaN = (SaidaNumerica) tblSaidas.getValueAt(index, 0);
                    saida.getSaidasNumerica().remove(saidaN);
                    break;
            }
        }
        AtualizaTabelaSaidas();

    }

    /**
     * Salva a situação inserida/editada
     *
     * @return
     */
    public boolean SalvaSituacao(boolean fecharJanela) {

        boolean ok = ValidaDados();
        boolean continuar = true;

        if (ok) {
            situacao.setFalaAssistente(txaFalaAssistente.getText());
            situacao.setNome(txtNomeSituacao.getText());

            //Caso a ação seja iserir, adiciona a situação à lista de situações da partida
            if (acao == 1) {
                partidaDesenvolvimento.getSituacoes().add(situacao);
            }

            jdp.AtualizarDados();

            if (fecharJanela) {
                dispose();
            }

        }

        return ok;

    }

    /**
     * Valida os campos
     *
     * @return
     */
    public boolean ValidaDados() {

        boolean ok = true;
        String mensagem;
        ArrayList<String> mensagens = new ArrayList<>();

        //Caso a saída seja numérica, valida os valores
        if (situacao.getSaida().getTipoSaida() == 2) {
            ArrayList<String> mensagensErro = ValidarValoresSN();
            
            if(!mensagensErro.isEmpty())
            {
                ok = false;
                mensagens.addAll(mensagensErro);
            }
        }

        if (txtNomeSituacao.getText().isEmpty()) {
            ok = false;
            mensagem = "msgNomeSituacaoObrigatorio";
            mensagens.add(mensagem);
        }
        if (txaFalaAssistente.getText().isEmpty()) {
            ok = false;
            mensagem = "msgFalaAssistenteObrigatoria";
            mensagens.add(mensagem);
        }

        //Imprime as mensgens
        if (!ok) {
            String mensagemJanela = "";

            for (String s : mensagens) {
                mensagemJanela += s + "\n";
            }

            JOptionPane.showMessageDialog(this, mensagemJanela, idioma.Valor("lblAviso"), JOptionPane.OK_OPTION);

        } else {

            //Exibe avisos
            if (txtArquivo.getText().isEmpty()) {
                int opcao = JOptionPane.showConfirmDialog(null, "msgSemImagemFundo", idioma.Valor("lblAviso"),
                        JOptionPane.YES_NO_OPTION);

                if (opcao == 1) {

                    ok = false;

                }
            }

            //Caso já exista situação inicial
            if (chbSituacaoInicial.isSelected() && partidaDesenvolvimento.getSituacaoInicial() != null) {

                int opcao = JOptionPane.showConfirmDialog(null, "msgJaExisteSituacaoInicial", idioma.Valor("Aviso"),
                        JOptionPane.YES_NO_OPTION);

                if (opcao == 0) {

                    //Marca todas as situações como não inicial
                    for (Situacao s : partidaDesenvolvimento.getSituacoes()) {
                        s.setSituacaoInicial(false);
                    }

                    //Define a situação como inicial
                    partidaDesenvolvimento.setSituacaoInicial(situacao);
                    situacao.setSituacaoInicial(true);

                } else {

                    ok = false;

                }

            }
        }
        return ok;
    }

    /**
     * Valida os valores da saída numérica
     *
     * @return
     */
    public ArrayList<String> ValidarValoresSN() {

        ArrayList<String> mensagens = new ArrayList<>();
        String mensagem;
        boolean emSequencia = false;
        ArrayList<Faixa> sequencias = new ArrayList<>();
        Faixa sequenciaAtual = new Faixa();

        //Recupera o menor e o maior valor
        Integer valorMinimo = 0;
        Integer valorMaximo = 0;
        int cont = 0;

        for (SaidaNumerica s : saida.getSaidasNumerica()) {
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
        
        //Verifica se o valor pertence a alguma faixa
        for (int valor = valorMinimo; valor < valorMaximo; valor++) {

            boolean temSaida = false;
            for (SaidaNumerica s : saida.getSaidasNumerica()) {

                if (valor >= s.getFaixa().getLimiteInferior() && valor <= s.getFaixa().getLimiteSuperior()) {
                    temSaida = true;
                    
                    //Caso tenha sequencia em aberto, fecha
                    if(emSequencia)
                    {                        
                        sequencias.add(sequenciaAtual);
                    }
                    
                    emSequencia = false;
                    
                    //Assume o valor como o ultimo da faixa, uma vez que não há necessidade de testar os de dentro da faixa
                    valor = s.getFaixa().getLimiteSuperior();
                    break;
                }

            }
            
            if (!temSaida) {

                if (emSequencia) {
                    
                    //Cresce a sequência
                    sequenciaAtual.setLimiteSuperior(valor);

                } else {

                    //Cria uma nova sequência
                    sequenciaAtual = new Faixa();
                    sequenciaAtual.setLimiteInferior(valor);
                    sequenciaAtual.setLimiteSuperior(valor);
                    emSequencia = true;

                }
            }
        }

        for (Faixa f : sequencias) {
            
            int diferenca = f.getLimiteSuperior() - f.getLimiteInferior();
            
            switch(diferenca)
            {
                case 0: //Sequência com apenas um valor                 
                    mensagem = idioma.Valor("msgValUnicoForaSeq1") + " " +
                        f.getLimiteSuperior() + " " +
                        idioma.Valor("msgValUnicoForaSeq2");               
                    break;
                
                case 1: //Sequência com dois valores
                    mensagem = idioma.Valor("msgValForaSeq1") + " " +
                        f.getLimiteInferior() + " " + 
                        idioma.Valor("msgValForaSeqE") + " " + 
                        f.getLimiteSuperior() + " " +
                        idioma.Valor("msgValForSeq2");
                    break;
                    
                default: //Sequência com mais de dois valores
                    mensagem = idioma.Valor("msgValForaSeq1") + " " +
                        f.getLimiteInferior() + " " +
                        idioma.Valor("msgValForaSeqA") + " " + 
                        f.getLimiteSuperior() + " " +
                        idioma.Valor("msgValForSeq2");
                    break;
            }
            mensagens.add(mensagem);
                        
        }

        return mensagens;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        lblNome = new javax.swing.JLabel();
        txtNomeSituacao = new javax.swing.JTextField();
        lblFalaAssistente = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaFalaAssistente = new javax.swing.JTextArea();
        btnConfirmar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtArquivo = new javax.swing.JTextField();
        btnSelImagem = new javax.swing.JButton();
        chbSituacaoInicial = new javax.swing.JCheckBox();
        btnVisualizarImagem = new javax.swing.JButton();
        btnEditarSaida = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSaidas = new javax.swing.JTable();
        btnNovaSaida = new javax.swing.JButton();
        cbxTipoSaida = new javax.swing.JComboBox();
        btnExcluirSaida = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblNome.setText("lblNome");

        lblFalaAssistente.setText("lblFalaAssistente");

        txaFalaAssistente.setColumns(20);
        txaFalaAssistente.setLineWrap(true);
        txaFalaAssistente.setRows(5);
        jScrollPane1.setViewportView(txaFalaAssistente);

        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel3.setText("lblImgFundo");

        txtArquivo.setEnabled(false);

        btnSelImagem.setText("Selecionar Imagem");
        btnSelImagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelImagemActionPerformed(evt);
            }
        });

        chbSituacaoInicial.setText("Situação Inicial");
        chbSituacaoInicial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbSituacaoInicialActionPerformed(evt);
            }
        });

        btnVisualizarImagem.setText("Visualizar Imagem");

        btnEditarSaida.setText("btnEditarSaida");
        btnEditarSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarSaidaActionPerformed(evt);
            }
        });

        tblSaidas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tblSaidas);

        btnNovaSaida.setText("btnNovaSaida");
        btnNovaSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovaSaidaActionPerformed(evt);
            }
        });

        cbxTipoSaida.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxTipoSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTipoSaidaActionPerformed(evt);
            }
        });

        btnExcluirSaida.setText("btnExcluirSaida");
        btnExcluirSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirSaidaActionPerformed(evt);
            }
        });

        jLabel5.setText("lblTipoSaida");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSelImagem)
                                .addGap(144, 144, 144)
                                .addComponent(btnVisualizarImagem))
                            .addComponent(chbSituacaoInicial, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(txtArquivo))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblNome)
                            .addComponent(lblFalaAssistente))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNomeSituacao, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(cbxTipoSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNovaSaida, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnExcluirSaida)
                                    .addGap(77, 77, 77)
                                    .addComponent(btnEditarSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(40, 40, 40))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNome)
                    .addComponent(txtNomeSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxTipoSaida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNovaSaida)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblFalaAssistente)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarSaida)
                    .addComponent(btnExcluirSaida))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSelImagem)
                    .addComponent(btnVisualizarImagem))
                .addGap(30, 30, 30)
                .addComponent(chbSituacaoInicial)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirmar)
                    .addComponent(btnCancelar))
                .addGap(37, 37, 37))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSelImagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelImagemActionPerformed

        fileChooser = new javax.swing.JFileChooser();

        fileChooser.setDialogTitle(idioma.Valor("lblSelImagem"));
        fileChooser.setFileFilter(new MyCustomFilter());
        
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                txtArquivo.setText(file.getName());

                Image image = ImageIO.read(file);
                ImageIcon imagec = new ImageIcon();
                imagec.setImage(image);
                imagec.setDescription(file.getName());

                situacao.setFundoSituacao(imagec);

            } catch (Exception ex) {
                System.out.println("problem accessing file" + file.getAbsolutePath());
            }
        }

    }//GEN-LAST:event_btnSelImagemActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed

        SalvaSituacao(true);

    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void chbSituacaoInicialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbSituacaoInicialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chbSituacaoInicialActionPerformed

    private void btnEditarSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarSaidaActionPerformed

        JanelaDesenvolvimentoSaida jds;

        switch (saida.getTipoSaida()) {

            case 1:
                SaidaOpcional saidaOpcional = (SaidaOpcional) tblSaidas.getValueAt(tblSaidas.getSelectedRow(), 0);
                jds = new JanelaDesenvolvimentoSaida(this, 2, situacao, saidaOpcional);
                jds.setVisible(true);
                break;

            case 2:
                SaidaNumerica saidaNumerica = (SaidaNumerica) tblSaidas.getValueAt(tblSaidas.getSelectedRow(), 0);
                jds = new JanelaDesenvolvimentoSaida(this, 2, situacao, saidaNumerica);
                jds.setVisible(true);
                break;
        }


    }//GEN-LAST:event_btnEditarSaidaActionPerformed

    private void btnNovaSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovaSaidaActionPerformed

        boolean ok = true;

        //Caso esteja no modo insert, salva a situação e altera o modo para edição
        if (acao == 1) {
            ok = SalvaSituacao(false);

        }

        if (ok) {
            acao = 2;
            JanelaDesenvolvimentoSaida jds;

            switch (saida.getTipoSaida()) {

                case 1:
                    SaidaOpcional saidaOpcional = new SaidaOpcional();
                    jds = new JanelaDesenvolvimentoSaida(this, 1, situacao, saidaOpcional);
                    jds.setVisible(true);
                    break;

                case 2:
                    SaidaNumerica saidaNumerica = new SaidaNumerica();
                    jds = new JanelaDesenvolvimentoSaida(this, 1, situacao, saidaNumerica);
                    jds.setVisible(true);
                    break;

            }
        }

    }//GEN-LAST:event_btnNovaSaidaActionPerformed

    private void cbxTipoSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTipoSaidaActionPerformed

        switch (cbxTipoSaida.getSelectedIndex()) {
            case 0:
                saida.setTipoSaida(1);
                break;
            case 1:
                saida.setTipoSaida(2);
                break;
        }


    }//GEN-LAST:event_cbxTipoSaidaActionPerformed

    private void btnExcluirSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirSaidaActionPerformed

        ExcluirSaida();

    }//GEN-LAST:event_btnExcluirSaidaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnEditarSaida;
    private javax.swing.JButton btnExcluirSaida;
    private javax.swing.JButton btnNovaSaida;
    private javax.swing.JButton btnSelImagem;
    private javax.swing.JButton btnVisualizarImagem;
    private javax.swing.JComboBox cbxTipoSaida;
    private javax.swing.JCheckBox chbSituacaoInicial;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lblFalaAssistente;
    private javax.swing.JLabel lblNome;
    private javax.swing.JTable tblSaidas;
    private javax.swing.JTextArea txaFalaAssistente;
    private javax.swing.JTextField txtArquivo;
    private javax.swing.JTextField txtNomeSituacao;
    // End of variables declaration//GEN-END:variables
}
