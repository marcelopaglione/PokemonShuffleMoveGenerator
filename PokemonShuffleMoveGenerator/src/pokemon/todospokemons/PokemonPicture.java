package pokemon.todospokemons;

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
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import pokemon.todospokemons.gui.PokePic;

/**
 *
 * @author Marcelo Ortiz Paglione Junior
 */
public class PokemonPicture {
    private PokePic panel;
    public static Buffer buffer;
//    private String path = System.getProperty("user.dir") + File.separator + "res" + File.separator;

    class ClassImage implements Runnable {

        private BufferedImage image;
        private BufferedImage resizedImage;
        private String string;
        private PokePic panel;

        public ClassImage(BufferedImage image, BufferedImage resizedImage, String string, PokePic panel) {
            this.image = image;
            this.string = string;
            this.resizedImage = resizedImage;
            this.panel = panel;
            buffer = new Buffer();

        }

        public BufferedImage resize(BufferedImage image, int width, int height) {
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
            Graphics2D g2d = (Graphics2D) bi.createGraphics();
            g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
            g2d.drawImage(image, 0, 0, width, height, null);
            g2d.dispose();
            return bi;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("http://www.serebii.net" + string);
                    image = (ImageIO.read(new URL("http://www.serebii.net" + string)));
                    resizedImage = resize(image, 50, 50);
                    panel.setPic(resizedImage);
                    buffer.get();
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }

    public PokemonPicture(Pokemon pokemon, BufferedImage imageTemp) throws IOException {
        BufferedImage image = imageTemp;
        BufferedImage resizedImage = null;
        try {
            resizedImage = resize(image, 50, 50);
            panel = new PokePic(pokemon.getName(), resizedImage, String.valueOf(pokemon.getBasePower()), pokemon.getAbility(), pokemon);
            Thread t = new Thread(new ClassImage(image, resizedImage, pokemon.getPic(), panel));
            RunTodosPokes.listThreads.add(t);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public JPanel getGui() {
        return panel;
    }

    public BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return bi;
    }
}
