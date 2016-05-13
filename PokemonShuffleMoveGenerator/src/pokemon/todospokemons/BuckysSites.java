package pokemon.todospokemons;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author marcelopaglione
 */
public class BuckysSites {

    private String path = System.getProperty("user.dir") + File.separator;
    private List<Pokemon> pokemonData;

    public List<Pokemon> getPokemonData() {
        return pokemonData;
    }

    public void setPokemonData(List<Pokemon> pokemonData) {
        this.pokemonData = pokemonData;
    }

    public void init()  {
        pokemonData = new ArrayList<>();
        Document doc = null;
        File input = null;
        try {
            htmlCleaner();
            input = new File(path + "pokes.html");
            doc = Jsoup.parse(input, "UTF-8");
            input.delete();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ler html " + e.getMessage());
        }

        Elements links = doc.select("table.dextable");

        StringBuilder stringBuilder = new StringBuilder();
        for (Element string : links) {
            stringBuilder.append(string.toString());
        }
        String[] tags = stringBuilder.toString().split("\n");
        int pularLinhas = 8;
        List<String> pokemons = new ArrayList<>();
        for (int i = 0; i < tags.length; i++) {
            if (tags[i].length() == 0) {

            } else {
                int j = 0;
                while (tags[i].charAt(j) == ' ') {
                    j++;
                }
                tags[i] = tags[i].substring(j, tags[i].length());
                String result = html2text(tags[i]);
                if (!result.equals("")) {
                    if (pularLinhas-- <= 0) {
                        pokemons.add(result);
                        //System.out.println(result);
                    }
                }
            }
        }

        for (int i = 0; i < pokemons.size(); i++) {
            if (pokemons.get(i).startsWith("#")) {
                try {
                    Pokemon pokemon = new Pokemon();

                    pokemon.setNo(pokemons.get(i++));
                    pokemon.setPic(pokemons.get(i++));
                    if (pokemons.get(i).startsWith("#")) {
                        continue;
                    }
                    String[] nomeTipo = new String[2];
                    nomeTipo = pokemons.get(i).split(" ");

                    pokemon.setName(nomeTipo[0]);
                    if (nomeTipo[0].contains("Mega")) {
                        continue;
                    }
                    i++;
                    pokemon.setType(pokemons.get(i++));
                    if (pokemons.get(i).startsWith("#")) {
                        continue;
                    }
                    pokemon.setBasePower(pokemons.get(i++));
                    if (pokemons.get(i).startsWith("#")) {
                        continue;
                    }
                    pokemon.setAbility(pokemons.get(i++));
                    if (pokemons.get(i).startsWith("#")) {
                        continue;
                    }
                    pokemon.setLocation(pokemons.get(i++));
                    if (pokemons.get(i).startsWith("#")) {
                        continue;
                    }
                    pokemon.setCaptureRate(pokemons.get(i));
                    this.pokemonData.add(pokemon);
                    //System.out.println(pokemonData);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }

            }
        }
    }

    public void htmlCleaner() throws IOException {
        CleanerProperties props = new CleanerProperties();
        props.setTranslateSpecialEntities(true);
        props.setTransResCharsToNCR(true);
        props.setOmitComments(true);
        TagNode tagNode = new HtmlCleaner(props).clean(
                new URL("http://www.serebii.net/shuffle/pokemon.shtml")
        );
        new PrettyXmlSerializer(props).writeToFile(
                tagNode, path + "pokes.html", "utf-8"
        );
    }

    public static String html2text(String html) {
//        return html;
        if (html.contains("img src=\"/shuffle/pokemon")) {
            int i, j;
            for (i = 0; i < html.length(); i++) {
                if (html.charAt(i) == 'c') {
                    i += 3;
                    break;
                }
            }
            for (j = i; j < html.length(); j++) {
                if (html.charAt(j) == 'g') {
                    j++;
                    break;
                }
            }
            return html.substring(i, j);
        }
        if (html.contains("img src=\"/pokedex-bw/type")) {
            int i, j;
            for (i = 0; i < html.length(); i++) {
                if (html.charAt(i) == 'p') {
                    i += 16;
                    break;
                }
            }
            for (j = i; j < html.length(); j++) {
                if (html.charAt(j) == '=') {
                    j -= 12;
                    break;
                }
            }
            //System.out.println(html.substring(i, j));
            return html.substring(i, j);
        }

        return Jsoup.parse(html).text();
    }

    public static String stripTags(final String html) {

        final StringBuilder sbText = new StringBuilder();
        final StringBuilder sbHtml = new StringBuilder();

        boolean isText = true;

        for (char ch : html.toCharArray()) {
            if (isText) { // outside html
                if (ch != '<') {
                    sbText.append(ch);
                } else {   // switch mode             
                    isText = false;
                    sbHtml.append(ch);
                }
            } else // inside html
             if (ch != '>') {
                    sbHtml.append(ch);
                } else {      // switch mode    
                    isText = true;
                    sbHtml.append(ch);
                }
        }

        return sbText.toString();
    }
}
