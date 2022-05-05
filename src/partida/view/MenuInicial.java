package partida.view;

import control.Controlador;
import control.ControladorEngine;
import java.awt.CardLayout;
import server.view.InputSocketCliente;
import server.view.InputSocketServidor;

public class MenuInicial extends javax.swing.JFrame {

    CardLayout cardLayout;
    final String cardJogar = "cardJogar", cardInicial = "cardInicial", cardOnline = "cardOnline", cardOffline = "cardOffline";
    
    public MenuInicial() {
        initComponents();
        cardLayout = (CardLayout) panelContainer.getLayout();
    }

    @SuppressWarnings("all")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelErechess = new javax.swing.JLabel();
        panelContainer = new javax.swing.JPanel();
        panelInicial = new javax.swing.JPanel();
        buttonJogar = new javax.swing.JButton();
        buttonInformacoes = new javax.swing.JButton();
        buttonSair = new javax.swing.JButton();
        panelJogar = new javax.swing.JPanel();
        buttonOnline = new javax.swing.JButton();
        buttonOffline = new javax.swing.JButton();
        buttonVoltar = new javax.swing.JButton();
        panelOnline = new javax.swing.JPanel();
        buttonCriar = new javax.swing.JButton();
        buttonConectar = new javax.swing.JButton();
        buttonVoltarPanelJogar = new javax.swing.JButton();
        panelOffline = new javax.swing.JPanel();
        buttonAmigo = new javax.swing.JButton();
        buttonComputador = new javax.swing.JButton();
        buttonVoltarPanelJogar1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        labelErechess.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        labelErechess.setText("Erechess");

        panelContainer.setBackground(new java.awt.Color(204, 204, 204));
        panelContainer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelContainer.setLayout(new java.awt.CardLayout());

        buttonJogar.setText("Jogar");
        buttonJogar.setFocusable(false);
        buttonJogar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonJogarActionPerformed(evt);
            }
        });

        buttonInformacoes.setText("Informações");
        buttonInformacoes.setFocusable(false);

        buttonSair.setText("Sair");
        buttonSair.setFocusable(false);
        buttonSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelInicialLayout = new javax.swing.GroupLayout(panelInicial);
        panelInicial.setLayout(panelInicialLayout);
        panelInicialLayout.setHorizontalGroup(
            panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInicialLayout.createSequentialGroup()
                .addContainerGap(77, Short.MAX_VALUE)
                .addGroup(panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonSair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonInformacoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonJogar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        panelInicialLayout.setVerticalGroup(
            panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInicialLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonJogar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonInformacoes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonSair)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelContainer.add(panelInicial, "cardInicial");

        buttonOnline.setText("Online");
        buttonOnline.setFocusable(false);
        buttonOnline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOnlineActionPerformed(evt);
            }
        });

        buttonOffline.setText("Offline");
        buttonOffline.setFocusable(false);
        buttonOffline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOfflineActionPerformed(evt);
            }
        });

        buttonVoltar.setText("Voltar");
        buttonVoltar.setFocusable(false);
        buttonVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonVoltarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelJogarLayout = new javax.swing.GroupLayout(panelJogar);
        panelJogar.setLayout(panelJogarLayout);
        panelJogarLayout.setHorizontalGroup(
            panelJogarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJogarLayout.createSequentialGroup()
                .addContainerGap(89, Short.MAX_VALUE)
                .addGroup(panelJogarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(buttonOnline, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonOffline, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonVoltar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        panelJogarLayout.setVerticalGroup(
            panelJogarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelJogarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonOnline)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonOffline)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonVoltar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelContainer.add(panelJogar, "cardJogar");

        buttonCriar.setText("Criar");
        buttonCriar.setFocusable(false);
        buttonCriar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCriarActionPerformed(evt);
            }
        });

        buttonConectar.setText("Conectar");
        buttonConectar.setFocusable(false);
        buttonConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonConectarActionPerformed(evt);
            }
        });

        buttonVoltarPanelJogar.setText("Voltar");
        buttonVoltarPanelJogar.setFocusable(false);
        buttonVoltarPanelJogar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonVoltarPanelJogarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelOnlineLayout = new javax.swing.GroupLayout(panelOnline);
        panelOnline.setLayout(panelOnlineLayout);
        panelOnlineLayout.setHorizontalGroup(
            panelOnlineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOnlineLayout.createSequentialGroup()
                .addContainerGap(87, Short.MAX_VALUE)
                .addGroup(panelOnlineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(buttonCriar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonConectar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonVoltarPanelJogar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(87, Short.MAX_VALUE))
        );
        panelOnlineLayout.setVerticalGroup(
            panelOnlineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOnlineLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonCriar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonConectar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonVoltarPanelJogar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelContainer.add(panelOnline, "cardOnline");

        buttonAmigo.setText("Jogar contra um amigo");
        buttonAmigo.setFocusable(false);
        buttonAmigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAmigoActionPerformed(evt);
            }
        });

        buttonComputador.setText("Jogar contra a máquina");
        buttonComputador.setFocusable(false);
        buttonComputador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonComputadorActionPerformed(evt);
            }
        });

        buttonVoltarPanelJogar1.setText("Voltar");
        buttonVoltarPanelJogar1.setFocusable(false);
        buttonVoltarPanelJogar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonVoltarPanelJogar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelOfflineLayout = new javax.swing.GroupLayout(panelOffline);
        panelOffline.setLayout(panelOfflineLayout);
        panelOfflineLayout.setHorizontalGroup(
            panelOfflineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOfflineLayout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addGroup(panelOfflineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(buttonAmigo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonComputador, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonVoltarPanelJogar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        panelOfflineLayout.setVerticalGroup(
            panelOfflineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOfflineLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonAmigo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonComputador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonVoltarPanelJogar1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelContainer.add(panelOffline, "cardOffline");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(labelErechess)
                        .addGap(34, 34, 34)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(labelErechess)
                .addGap(18, 18, 18)
                .addComponent(panelContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonJogarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonJogarActionPerformed
        cardLayout.show(panelContainer, cardJogar);
    }//GEN-LAST:event_buttonJogarActionPerformed

    private void buttonSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSairActionPerformed
        System.exit(0);
    }//GEN-LAST:event_buttonSairActionPerformed

    private void buttonVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonVoltarActionPerformed
        cardLayout.show(panelContainer, cardInicial);
    }//GEN-LAST:event_buttonVoltarActionPerformed

    private void buttonOfflineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOfflineActionPerformed
        cardLayout.show(panelContainer, cardOffline);
    }//GEN-LAST:event_buttonOfflineActionPerformed

    private void buttonConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonConectarActionPerformed
        dispose();
        InputSocketCliente.conectarPartida();
    }//GEN-LAST:event_buttonConectarActionPerformed

    private void buttonVoltarPanelJogarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonVoltarPanelJogarActionPerformed
        cardLayout.show(panelContainer, cardJogar);
    }//GEN-LAST:event_buttonVoltarPanelJogarActionPerformed

    private void buttonOnlineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOnlineActionPerformed
        cardLayout.show(panelContainer, cardOnline);
    }//GEN-LAST:event_buttonOnlineActionPerformed

    private void buttonCriarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCriarActionPerformed
        InputSocketServidor.criarServidor();
    }//GEN-LAST:event_buttonCriarActionPerformed

    private void buttonAmigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAmigoActionPerformed
        dispose();
        new Controlador();
    }//GEN-LAST:event_buttonAmigoActionPerformed

    private void buttonComputadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonComputadorActionPerformed
        dispose();
        new ControladorEngine();
    }//GEN-LAST:event_buttonComputadorActionPerformed

    private void buttonVoltarPanelJogar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonVoltarPanelJogar1ActionPerformed
        cardLayout.show(panelContainer, cardJogar);
    }//GEN-LAST:event_buttonVoltarPanelJogar1ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuInicial().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAmigo;
    private javax.swing.JButton buttonComputador;
    private javax.swing.JButton buttonConectar;
    private javax.swing.JButton buttonCriar;
    private javax.swing.JButton buttonInformacoes;
    private javax.swing.JButton buttonJogar;
    private javax.swing.JButton buttonOffline;
    private javax.swing.JButton buttonOnline;
    private javax.swing.JButton buttonSair;
    private javax.swing.JButton buttonVoltar;
    private javax.swing.JButton buttonVoltarPanelJogar;
    private javax.swing.JButton buttonVoltarPanelJogar1;
    private javax.swing.JLabel labelErechess;
    private javax.swing.JPanel panelContainer;
    private javax.swing.JPanel panelInicial;
    private javax.swing.JPanel panelJogar;
    private javax.swing.JPanel panelOffline;
    private javax.swing.JPanel panelOnline;
    // End of variables declaration//GEN-END:variables
}
