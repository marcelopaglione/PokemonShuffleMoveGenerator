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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author Marcelo Ortiz Paglione Junior
 */
public class RunTodosPokes extends JFrame implements Runnable {

    private List<Pokemon> listPokemons;
    private List<List<Pokemon>> listPokemonsByType;
    private List<String> listTypes;
    public static List<Thread> listThreads;
    public static JProgressBar progressBar;

    private void addTypes() {
        listTypes = new ArrayList<>();
        listTypes.add("bug");
        listTypes.add("dark");
        listTypes.add("dragon");
        listTypes.add("ELECTRIC");
        listTypes.add("fairy");
        listTypes.add("FIGHTING");
        listTypes.add("FIRE");
        listTypes.add("FLYING");
        listTypes.add("GHOST");
        listTypes.add("GRASS");
        listTypes.add("GROUND");
        listTypes.add("ice");
        listTypes.add("NORMAL");
        listTypes.add("POISON");
        listTypes.add("psychic");
        listTypes.add("ROCK");
        listTypes.add("STEEL");
        listTypes.add("WATER");
        listTypes.add("WOOD");
    }

    public RunTodosPokes() {
        listThreads = new ArrayList<>();
        listPokemonsByType = new ArrayList<>();
        addTypes();

    }

    public static void main(String[] args) {
        new RunTodosPokes().init();
    }

    public void init() {

        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setPreferredSize(new Dimension(700, 500));
            setTitle("Pokedex - disponível em http://serebii.net/shuffle/pokemon.shtml");
            JTabbedPane tabbedpane = new JTabbedPane();
            tabbedpane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
            add(tabbedpane, BorderLayout.CENTER);
            JLabel bottomLabel = new JLabel();
            add(bottomLabel, BorderLayout.SOUTH);
            pack();
            setLocationRelativeTo(null);
            setVisible(true);

            bottomLabel.setText("\t\t   Obtendo conteudo online...");
            BuckysSites buckysSites = new BuckysSites();
            buckysSites.init();
            bottomLabel.setText("");

            listPokemons = buckysSites.getPokemonData();
            progressBar = new JProgressBar(0, listPokemons.size());
            progressBar.setPreferredSize(new Dimension(700, 20));
            progressBar.setStringPainted(true);
            add(progressBar, BorderLayout.SOUTH);

            filterPokemonsByType();

            BufferedImage defaultImage = (ImageIO.read(new URL("http://a.deviantart.net/avatars/r/e/red-pikachu.png?3")));
            for (List<Pokemon> pokemons : listPokemonsByType) {
                if (!pokemons.isEmpty()) {
                    JPanel internalPanel = new JPanel();
                    internalPanel.setLayout(new GridLayout(0, 5, 3, 3));
                    Collections.sort(pokemons);
                    for (Pokemon pokemon : pokemons) {
                        internalPanel.add(new PokemonPicture(pokemon, defaultImage).getGui());
                    }
                    internalPanel.setBackground(Color.BLACK);
                    JScrollPane scroll = new JScrollPane(internalPanel);
                    scroll.getVerticalScrollBar().setUnitIncrement(50);
                    scroll.setAutoscrolls(true);
                    tabbedpane.addTab(pokemons.get(0).getType().toUpperCase(), null, scroll, pokemons.get(0).getType().toUpperCase());
                }
            }

        } catch (HeadlessException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Type: " + ex);
        }

        for (int i = 0; i < listThreads.size(); i++) {
            listThreads.get(i).start();
        }
    }

    private void filterPokemonsByType() {
        for (String type : listTypes) {
            List<Pokemon> aux = new ArrayList<>();
            for (Pokemon pokemon : listPokemons) {
                if (pokemon.getType().contains(type.toLowerCase())) {
                    aux.add(pokemon);
                }
            }
            listPokemonsByType.add(aux);
        }
    }

    @Override
    public void run() {
        init();
    }
}
