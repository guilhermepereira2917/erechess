package partida.view;

import enums.Cores;
import enums.EstadosPartida;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import partida.model.Partida;

public class FramePartida extends JFrame implements Observer {

    Partida partida;
    PanelTabuleiro panelTabuleiro;
    
    public FramePartida(Partida partida) {
        setVisible(true);
        initComponents();

        this.partida = partida;
        setup();
    }

    private void setup() {

        partida.addObserver(this);

        panelTabuleiro = new PanelTabuleiro(partida.getTabuleiro());
        panelTabuleiro.desenharTabuleiro(partida.getTabuleiro().getTurno());

        panelContainer.removeAll();
        panelContainer.add(panelTabuleiro);

        panelTabuleiro.resize();

        setTitle("Erechess - " + partida.getEstado());
    }

    public String escolherPecaCoroacao() {
        String[] opcoes = new String[]{"Dama", "Torre", "Cavalo", "Bispo"};
        int opcaoEscolhida = JOptionPane.showOptionDialog(
                this,
                "Escolha em qual peça ele se transformará!",
                "Coroação!",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]);

        if (opcaoEscolhida == JOptionPane.CLOSED_OPTION) {
            return null;
        }

        return opcoes[opcaoEscolhida];
    }

    public boolean confirmarCriacaoNovaPartida() {
        return JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Tem certeza que deseja criar uma nova partida?", "Nova partida", JOptionPane.YES_NO_OPTION);
    }

    public boolean confirmarVoltarAoMenuPrincipal() {
        return JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Tem certeza que deseja voltar ao menu principal?", "Voltar ao menu principal", JOptionPane.YES_NO_OPTION);
    }
    
    @Override
    public void update(Observable observable, Object arg) {
        if (observable instanceof Partida) {
            partida = (Partida) observable;
            EstadosPartida estado = partida.getEstado();

            setTitle("Erechess - " + partida.getEstado());
            if (null != estado) {
                switch (estado) {
                    case BRANCAS_VENCERAM:
                        notificaoXequeMate("Brancas");
                        break;
                    case NEGRAS_VENCERAM:
                        notificaoXequeMate("Negras");
                        break;
                    case EMPATADA:
                        notificacaoEmpate();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void notificaoXequeMate(String lado) {
        JOptionPane.showMessageDialog(null, lado + " ganharam!", "Xeque-Mate", JOptionPane.INFORMATION_MESSAGE);
    }

    public void notificacaoEmpate() {
        JOptionPane.showMessageDialog(null, "A partida empatou.", "Empate", JOptionPane.INFORMATION_MESSAGE);
    }

    @SuppressWarnings("all")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        panelContainer = new javax.swing.JPanel();
        barraMenu = new javax.swing.JMenuBar();
        menuEditar = new javax.swing.JMenu();
        itemNovaPartida = new javax.swing.JMenuItem();
        separador1 = new javax.swing.JPopupMenu.Separator();
        checkBoxGirarPerspectiva = new javax.swing.JCheckBoxMenuItem();
        checkBoxDestacarUltimoMovimento = new javax.swing.JCheckBoxMenuItem();
        separador2 = new javax.swing.JPopupMenu.Separator();
        itemVoltarAoMenuPrincipal = new javax.swing.JMenuItem();

        jMenu1.setText("jMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Erechess");

        panelContainer.setBackground(new java.awt.Color(204, 204, 204));
        panelContainer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelContainer.setPreferredSize(new java.awt.Dimension(600, 600));

        javax.swing.GroupLayout panelContainerLayout = new javax.swing.GroupLayout(panelContainer);
        panelContainer.setLayout(panelContainerLayout);
        panelContainerLayout.setHorizontalGroup(
            panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 598, Short.MAX_VALUE)
        );
        panelContainerLayout.setVerticalGroup(
            panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 596, Short.MAX_VALUE)
        );

        menuEditar.setText("Editar");

        itemNovaPartida.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        itemNovaPartida.setText("Nova Partida");
        menuEditar.add(itemNovaPartida);
        menuEditar.add(separador1);

        checkBoxGirarPerspectiva.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        checkBoxGirarPerspectiva.setText("Girar Tabuleiro Automaticamente");
        checkBoxGirarPerspectiva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxGirarPerspectivaActionPerformed(evt);
            }
        });
        menuEditar.add(checkBoxGirarPerspectiva);

        checkBoxDestacarUltimoMovimento.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        checkBoxDestacarUltimoMovimento.setSelected(true);
        checkBoxDestacarUltimoMovimento.setText("Destacar Ultimo Movimento");
        checkBoxDestacarUltimoMovimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxDestacarUltimoMovimentoActionPerformed(evt);
            }
        });
        menuEditar.add(checkBoxDestacarUltimoMovimento);
        menuEditar.add(separador2);

        itemVoltarAoMenuPrincipal.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        itemVoltarAoMenuPrincipal.setText("Voltar ao Menu Principal");
        menuEditar.add(itemVoltarAoMenuPrincipal);

        barraMenu.add(menuEditar);

        setJMenuBar(barraMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(panelContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelContainer, 598, 598, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void checkBoxGirarPerspectivaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxGirarPerspectivaActionPerformed
        panelTabuleiro.setGirarTabuleiro(checkBoxGirarPerspectiva.isSelected());
    }//GEN-LAST:event_checkBoxGirarPerspectivaActionPerformed

    private void checkBoxDestacarUltimoMovimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxDestacarUltimoMovimentoActionPerformed
        panelTabuleiro.setDestacarUltimoMovimento(checkBoxDestacarUltimoMovimento.isSelected());
    }//GEN-LAST:event_checkBoxDestacarUltimoMovimentoActionPerformed

    public PanelTabuleiro getPanelTabuleiro() {
        return panelTabuleiro;
    }

    public javax.swing.JMenuItem getItemNovaPartida() {
        return itemNovaPartida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;

        setup();
    }

    public javax.swing.JMenuItem getItemVoltarAoMenuPrincipal() {
        return itemVoltarAoMenuPrincipal;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar barraMenu;
    private javax.swing.JCheckBoxMenuItem checkBoxDestacarUltimoMovimento;
    private javax.swing.JCheckBoxMenuItem checkBoxGirarPerspectiva;
    private javax.swing.JMenuItem itemNovaPartida;
    private javax.swing.JMenuItem itemVoltarAoMenuPrincipal;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu menuEditar;
    private javax.swing.JPanel panelContainer;
    private javax.swing.JPopupMenu.Separator separador1;
    private javax.swing.JPopupMenu.Separator separador2;
    // End of variables declaration//GEN-END:variables

}
