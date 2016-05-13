package config;

/*
 * Copyright (C) 2016 Paglione
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
import entity.Grid;
import entity.Imagem;
import gui.PokemonShuffleMoveGenerator;
import java.awt.AWTException;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import static gui.PokemonShuffleMoveGenerator.pokeLabelsPanel;

/**
 *
 * @author Marcelo Ortiz Paglione Junior
 */
public final class Controle implements Runnable {

    private Imagem[] imagensCortadas;
    private List<Imagem> pokemons;
    private PreencherPokemons typeWord;
    private EncontrarPokemons encontrarPokemons;
    private int size, gap, x, xAux, y;
    private BufferedImage image;
    private PokemonShuffleMoveGenerator psmg;

    public Controle(Grid grid, PokemonShuffleMoveGenerator psmg) {
        this.psmg = psmg;
        setGrid(grid);
        updateSavedPkmns();
        try {
            typeWord = new PreencherPokemons();
        } catch (AWTException ex) {
            Logger.getLogger(Controle.class.getName()).log(Level.SEVERE, null, ex);
        }
        pokemons = new ArrayList<>();
        encontrarPokemons = new EncontrarPokemons(psmg);
    }

    public void setGrid(Grid grid) {
        x = grid.getX();
        y = grid.getY();
        size = grid.getSize();
        gap = grid.getGap();
    }

    public List<Imagem> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Imagem> pokemons) {
        this.pokemons = pokemons;
    }

    public EncontrarPokemons getEncontrarPokemons() {
        return encontrarPokemons;
    }

    public void setEncontrarPokemons(EncontrarPokemons encontrarPokemons) {
        this.encontrarPokemons = encontrarPokemons;
    }

    public void calculate() {
        imagensCortadas = cortarImagens();
        pokemons = get();
        pokemons = encontrarPokemons.encontrar(imagensCortadas, pokemons);
        updateSavedPkmns();
        if (encontrarPokemons.isProseguir()) {
            typeWord.setTab(imagensCortadas);
            new Thread(typeWord).start();
        }
    }

    public BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return dimg;
    }

    public void updateSavedPkmns() {
        pokeLabelsPanel.removeAll();
        pokemons = get();
        //Collections.sort(pokemons);
        for (int i = 0; i < pokemons.size(); i++) {
            JButton button = new JButton(new ImageIcon(resize(pokemons.get(i).getImage(), 30, 30)));
            button.setPreferredSize(new Dimension(80, 40));
            button.setText("'" + pokemons.get(i).getMensagem() + "'");
            button.setName(pokemons.get(i).getId() + "");
            button.setToolTipText("Pressione para remover");
            button.addActionListener((ActionEvent e) -> {
                for (int j = 0; j < pokemons.size(); j++) {
                    if (pokemons.get(j).getId().equals(button.getName())) {
                        pokemons.remove(j);
                        break;
                    }
                }
                for (Component jpanel : pokeLabelsPanel.getComponents()) {
                    if (jpanel.getName().equals(button.getName())) {
                        pokeLabelsPanel.remove(jpanel);
                        pokeLabelsPanel.updateUI();
                    }
                }
                set(pokemons);
            });
            pokeLabelsPanel.add(button);
        }
        pokeLabelsPanel.revalidate();
        pokeLabelsPanel.repaint();

    }

    public List<Imagem> get() {
        FileInputStream arquivoLeitura;
        ObjectInputStream objLeitura;
        try {
            arquivoLeitura = new FileInputStream(new File(System.getProperty("user.home") + File.separator + "Shuffle-Move" + File.separator + "data.pkmn"));
            objLeitura = new ObjectInputStream(arquivoLeitura);
            List<Imagem> imagens = (List<Imagem>) objLeitura.readObject();
            objLeitura.close();
            arquivoLeitura.close();
            return imagens;
        } catch (IOException | ClassNotFoundException ex) {
            return new ArrayList<>();
        }
    }

    public void set(List<Imagem> imagens) {
        try {
            FileOutputStream arquivoGrav = new FileOutputStream(new File(System.getProperty("user.home") + File.separator + "Shuffle-Move" + File.separator + "data.pkmn"));
            ObjectOutputStream objGravar = new ObjectOutputStream(arquivoGrav);
            objGravar.writeObject(imagens);
            objGravar.flush();
            objGravar.close();
            arquivoGrav.flush();
            arquivoGrav.close();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public Imagem[] cortarImagens() {
        Imagem[] imgCortadas = new Imagem[36];

        try {
            image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        } catch (AWTException ex) {
            Logger.getLogger(Controle.class.getName()).log(Level.SEVERE, null, ex);
        }

        x += gap;
        xAux = x;
        y += gap;

        for (int i = 0; i < imgCortadas.length; i++) {
            BufferedImage bim = image.getSubimage(x, y, size, size);
            imgCortadas[i] = new Imagem(bim);
            x += size + 2 * gap + 1;
            if ((i + 1) % 6 == 0) {
                x = xAux;
                y += size + 2 * gap + 1;
            }
        }

        return imgCortadas;
    }

    /*
    private File getLatestFilefromDir(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }

        File lastModifiedFile = files[0];
        for (int i = 1; i < files.length; i++) {
            if (files[i].getAbsoluteFile().toString().contains(SCREEN_SHOTS_BASE_NAME)) {
                if (lastModifiedFile.lastModified() < files[i].lastModified()) {
                    lastModifiedFile = files[i];
                }
            }
        }
        return lastModifiedFile.getAbsoluteFile();
    }*/
    @Override
    public void run() {
        calculate();
    }

}
