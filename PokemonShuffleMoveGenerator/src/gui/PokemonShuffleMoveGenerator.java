package gui;

import config.Controle;
import entity.Grid;
import entity.Imagem;
import gui.PlayAnimatedGif.GiffButton;
import gui.board.Board;
import gui.savedpokemons.FoundPokemons;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import pokemon.todospokemons.RunTodosPokes;

/**
 *
 * @author Marcelo
 */
public class PokemonShuffleMoveGenerator extends javax.swing.JFrame {

    private FoundPokemons pokeLabelsGui = new FoundPokemons();
    public static JPanel pokeLabelsPanel;
    private Controle controll;
    public static Grid grid;
    public static final String DATAPATH = System.getProperty("user.home") + File.separator + "Shuffle-Move" + File.separator + "data.pkmn";
    public static final String GRIDPATH = System.getProperty("user.home") + File.separator + "Shuffle-Move" + File.separator + "grid.pkmn";

    public PokemonShuffleMoveGenerator() {
        initComponents();
        script();
        pokeLabelsGui = new FoundPokemons();
        pokeLabelsGui.setLocationByPlatform(true);
        grid = getGrid();
        pokeLabelsPanel = pokeLabelsGui.getPanel();
        controll = new Controle(grid, this);
        getContentPane().setBackground(Color.black);
        //jMenuBar1.setBackground(Color.lightGray);
        //jMenuBar1.setForeground(Color.white);
        try {
            BufferedImage img = ImageIO.read(getClass().getResource("/gui/alakazam.png"));
            jMenuItem3.setIcon(new ImageIcon(resize(img, 32, 32)));
            img = ImageIO.read(getClass().getResource("/gui/absol.png"));
            jMenuItem4.setIcon(new ImageIcon(resize(img, 32, 32)));

        } catch (Exception ex) {
            Logger.getLogger(PokemonShuffleMoveGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 5);
        slider.setMinorTickSpacing(5);
        slider.setMajorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setLabelTable(slider.createStandardLabels(20));
        slider.setValue((int) (grid.getAccuracy() * 100));
        slider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                float value = slider.getValue();
                grid.setAccuracy((value /= 100));
                System.out.println(grid.getAccuracy());
            }
        });
        System.out.println("grid.getAccuracy()" + grid.getAccuracy());
        jMenuItem2.add(slider);
    }

    public static List<Imagem> getData() {
        FileInputStream arquivoLeitura;
        ObjectInputStream objLeitura;
        try {
            arquivoLeitura = new FileInputStream(new File(DATAPATH));
            objLeitura = new ObjectInputStream(arquivoLeitura);
            List<Imagem> imagens = (List<Imagem>) objLeitura.readObject();
            objLeitura.close();
            arquivoLeitura.close();
            return imagens;
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }
    }

    public void script() {
        try {
            File file = new File(System.getProperty("user.home") + File.separator + "Shuffle-Move" + File.separator + "calc.vbs");
            if (!file.exists()) {
                BufferedWriter buffWrite = new BufferedWriter(new FileWriter(System.getProperty("user.home") + File.separator + "Shuffle-Move" + File.separator + "calc.vbs"));
                buffWrite.append("Option Explicit\n"
                        + "Dim WshShell\n"
                        + "Set WshShell = WScript.CreateObject(\"WScript.Shell\")\n"
                        + "WScript.Sleep 100\n"
                        + "WshShell.AppActivate(\"Shuffle Move\")\n"
                        + "' WshShell.SendKeys \"% r\"");
                buffWrite.close();
            }
        } catch (Exception e) {

        }
        try {
            File file = new File(System.getProperty("user.home") + File.separator + "Shuffle-Move" + File.separator + "gen.vbs");
            if (!file.exists()) {
                BufferedWriter buffWrite = new BufferedWriter(new FileWriter(System.getProperty("user.home") + File.separator + "Shuffle-Move" + File.separator + "gen.vbs"));
                buffWrite.append("Option Explicit\n"
                        + "Dim WshShell\n"
                        + "Set WshShell = WScript.CreateObject(\"WScript.Shell\")\n"
                        + "WScript.Sleep 100\n"
                        + "WshShell.AppActivate(\"Pokemon Shuffle Move Generator\")\n"
                        + "' WshShell.SendKeys \"% r\"");
                buffWrite.close();
            }
        } catch (Exception e) {

        }
    }

    public static Grid getGrid() {
        FileInputStream arquivoLeitura;
        ObjectInputStream objLeitura;
        try {
            arquivoLeitura = new FileInputStream(new File(GRIDPATH));
            objLeitura = new ObjectInputStream(arquivoLeitura);
            Grid grid = (Grid) objLeitura.readObject();
            objLeitura.close();
            arquivoLeitura.close();
            return grid;
        } catch (IOException | ClassNotFoundException ex) {
            return new Grid();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new GiffButton(5);
        jButton2 = new GiffButton(6);
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemPokemons = new javax.swing.JMenuItem();
        jMenuItemScreenPosition = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pokemon Shuffle Move Generator");
        setForeground(java.awt.Color.magenta);

        jButton1.setText("<html><font color=#ffffff><b><u> R</u>eset </b></font></html>");
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/brilho.png"))); // NOI18N
        jButton1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("<html><font color=#ffffff><b><u> S</u>art </b></font></html>");
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/brilho.png"))); // NOI18N
        jButton2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jMenuBar1.setBackground(new java.awt.Color(51, 51, 51));
        jMenuBar1.setForeground(new java.awt.Color(255, 255, 255));
        jMenuBar1.setOpaque(false);

        jMenuFile.setText("File");

        jMenuItemPokemons.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, 0));
        jMenuItemPokemons.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/474456.png"))); // NOI18N
        jMenuItemPokemons.setText("<html>\n<b><u> P</u>okedex </b>\n</html>");
        jMenuItemPokemons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPokemonsActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemPokemons);

        jMenuItemScreenPosition.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, 0));
        jMenuItemScreenPosition.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/474457.png"))); // NOI18N
        jMenuItemScreenPosition.setText("<html>\n<b><u>B</u>oard </b>\n</html>");
        jMenuItemScreenPosition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemScreenPositionActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemScreenPosition);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, 0));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/474458.png"))); // NOI18N
        jMenuItem1.setText("<html>\n<b><u>L</u>abels </b>\n</html>");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItem1);
        jMenuFile.add(jSeparator1);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, 0));
        jMenuItem3.setText("<html> <b><u> S</u>tart </b> </html>");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItem3);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, 0));
        jMenuItem4.setText("<html> <b><u> R</u>eset </b> </html>");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItem4);
        jMenuFile.add(jSeparator2);

        jMenuItem2.setPreferredSize(new java.awt.Dimension(200, 70));
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItem2);

        jMenuBar1.add(jMenuFile);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton2});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemScreenPositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemScreenPositionActionPerformed
        Board b = new Board();
        b.setVisible(true);
    }//GEN-LAST:event_jMenuItemScreenPositionActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        if (pokeLabelsGui.isVisible()) {
            pokeLabelsGui.setVisible(false);
        } else {
            pokeLabelsGui.setVisible(true);
        }
        this.requestFocus();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItemPokemonsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPokemonsActionPerformed
        Thread t = new Thread(new RunTodosPokes());
        t.start();
    }//GEN-LAST:event_jMenuItemPokemonsActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        start();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        reset();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        start();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        reset();
    }//GEN-LAST:event_jButton1ActionPerformed

    public void reset() {
        int i = JOptionPane.showConfirmDialog(this, "Você tem certeza disto?", "Aviso", JOptionPane.YES_NO_OPTION);
        if (i == JOptionPane.YES_OPTION) {
            controll = new Controle(grid, this);
            controll.setPokemons(null);
            controll.getEncontrarPokemons().set(new ArrayList<>());
            controll.updateSavedPkmns();
        }
    }

    public void start() {
        controll.setGrid(grid);
        Thread thread = new Thread(controll);
        thread.start();
    }

    public BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return dimg;
    }

    public static void main(String args[]) throws Exception {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PokemonShuffleMoveGenerator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        java.awt.EventQueue.invokeLater(() -> {
            new PokemonShuffleMoveGenerator().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItemPokemons;
    private javax.swing.JMenuItem jMenuItemScreenPosition;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    // End of variables declaration//GEN-END:variables
}
