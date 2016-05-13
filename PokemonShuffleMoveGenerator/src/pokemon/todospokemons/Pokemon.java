/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemon.todospokemons;

/**
 *
 * @author marcelopaglione
 */
public class Pokemon implements Comparable<Pokemon>{

    private String no;
    private String pic;
    private String name;
    private String type;
    private String basePower;
    private String ability;
    private String location;
    private String captureRate;

    public Pokemon() {
    }

    public Pokemon(String no, String pic, String name, String type, String basePower, String ability, String location, String captureRate) {
        this.no = no;
        this.pic = pic;
        this.name = name;
        this.type = type;
        this.basePower = basePower;
        this.ability = ability;
        this.location = location;
        this.captureRate = captureRate;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBasePower() {
        return basePower;
    }

    public void setBasePower(String basePower) {
        this.basePower = basePower;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCaptureRate() {
        return captureRate;
    }

    public void setCaptureRate(String captureRate) {
        this.captureRate = captureRate;
    }

    @Override
    public String toString() {
        return "PokemonData{" + "no=" + no + ", pic=" + pic + ", name=" + name + ", type=" + type + ", basePower=" + basePower + ", ability=" + ability + ", location=" + location + ", captureRate=" + captureRate + '}';
    }

    @Override
    public int compareTo(Pokemon o) {
        if (Integer.parseInt(basePower) > Integer.parseInt(o.getBasePower())) {
            return -1;
        }
        if (Integer.parseInt(basePower) < Integer.parseInt(o.getBasePower())) {
            return 1;
        }
        return 0;
    }
    
    

}
