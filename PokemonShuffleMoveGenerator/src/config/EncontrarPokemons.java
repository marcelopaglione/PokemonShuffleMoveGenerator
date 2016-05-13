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
import entity.Histograma;
import entity.Imagem;
import gui.PokemonShuffleMoveGenerator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcelo Ortiz Paglione Junior
 */
public class EncontrarPokemons {

    public static boolean firstTime;
    private boolean proseguir = true;
    private PokemonShuffleMoveGenerator psmg;

    public EncontrarPokemons(PokemonShuffleMoveGenerator psmg) {
        firstTime = true;
        this.psmg = psmg;
    }

    public List<Imagem> encontrar(Imagem[] tab, List<Imagem> pkmnEncontrados) {
        if (pkmnEncontrados != null) {
            preEncher(tab, pkmnEncontrados);
        }
        for (int i = 0; i < tab.length; i++) {
            if (tab[i].getAnalizado() == false) {
                if (pkmnEncontrados == null) {
                    pkmnEncontrados = new ArrayList<>();
                } else {

                }
                Imagem bim = new Imagem();
                bim.setImage(tab[i].getImage());
                bim.setHistogram(tab[i].getHistogram());
                ImageIcon icon = new ImageIcon(bim.getImage());
                String string = (String) JOptionPane.showInputDialog(psmg, "Entre um valor para ele", "Do it",
                        JOptionPane.INFORMATION_MESSAGE, icon, null, "");
                if (string != null) {
                    bim.setMensagem(string);
                    bim.setId(bim + "");
                    pkmnEncontrados.add(bim);

                    for (Imagem tab1 : tab) {
                        if (tab1.getAnalizado() == false) {
                            float score = compare(bim.getHistogram(), tab1.getHistogram());
                            if (score >= 0 && score <= PokemonShuffleMoveGenerator.grid.getAccuracy()) {
                                tab1.setAnalizado(true);
                                tab1.setMensagem(bim.getMensagem());
                            }
                        }
                    }
                } else {
                    proseguir = false;
                    set(pkmnEncontrados);
                    return pkmnEncontrados;
                }

            }

        }
        proseguir = true;
        set(pkmnEncontrados);
        return pkmnEncontrados;
    }

    public void preEncher(Imagem[] tab, List<Imagem> pkmnEncontrados) {
        for (int i = 0; i < tab.length; i++) {
            tab[i].setAnalizado(false);
        }
        for (int i = 0; i < pkmnEncontrados.size(); i++) {
            for (int j = 0; j < tab.length; j++) {
                float score = compare(pkmnEncontrados.get(i).getHistogram(), tab[j].getHistogram());
                if (score >= 0 && score <= PokemonShuffleMoveGenerator.grid.getAccuracy()) {
                    tab[j].setAnalizado(true);
                    tab[j].setMensagem(pkmnEncontrados.get(i).getMensagem());
                }
            }
        }
    }

    private float compare(Histograma h1, Histograma h2) {
        float sum = 0;
        sum += compare(h1.getBlue(), h2.getBlue());
        sum += compare(h1.getGreen(), h2.getGreen());
        sum += compare(h1.getRed(), h2.getRed());
        sum += compare(h1.getInterior(), h2.getInterior());
        sum += compare(h1.getBorder(), h2.getBorder());
        return sum;
    }

    private float compare(float[] h1, float[] h2) {
        float sum = 0;
        for (int i = 0; i < h1.length; i++) {
            sum += Math.abs(Math.log10(h1[i] + 1) / Math.log(2) - Math.log10(h2[i] + 1) / Math.log(2));
        }
        return sum;
    }

    public boolean isProseguir() {
        return proseguir;
    }

    public void set(List<Imagem> imagens) {
        try {
            //System.err.println("salvando dados");
            FileOutputStream arquivoGrav = new FileOutputStream(new File(System.getProperty("user.home") + File.separator + "Shuffle-Move" + File.separator + "data.pkmn"));
            ObjectOutputStream objGravar = new ObjectOutputStream(arquivoGrav);
            objGravar.writeObject(imagens);
            //setImages(objGravarIms, imagens);
            objGravar.flush();
            objGravar.close();
            arquivoGrav.flush();
            arquivoGrav.close();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

}
