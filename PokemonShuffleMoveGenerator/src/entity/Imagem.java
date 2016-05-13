package entity;

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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.imageio.ImageIO;

/**
 *
 * @author Marcelo Ortiz Paglione Junior
 */
public class Imagem implements Serializable, Comparable<Imagem> {

    private static final long serialVersionUID = 1L;
    private String id;
    private boolean flag;
    private float score;
    private String string;
    private transient BufferedImage image;
    private Histograma histogram;
    private int x, y, gap, size;

    public Imagem() {
        super();
        histogram = new Histograma();
        flag = false;
        score = 0;
        string = "";
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Imagem(BufferedImage image) {
        this();
        this.image = image;
        histogram.calcHist(image);
    }

    public String getMensagem() {
        return string;
    }

    public void setMensagem(String string) {
        this.string = string;
    }

    public boolean getAnalizado() {
        return flag;
    }

    public void setAnalizado(boolean flag) {
        this.flag = flag;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Histograma getHistogram() {
        return histogram;
    }

    public void setHistogram(Histograma histogram) {
        this.histogram = histogram;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
//        out.writeInt(images.size()); // how many images are serialized?
//        for (BufferedImage eachImage : images) {
        ImageIO.write(image, "png", out); // png is lossless
//        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        image = (ImageIO.read(in));
    }

    @Override
    public int compareTo(Imagem o) {
        if (o.getMensagem().compareTo(string) == 0) {
            return 0;
        } else if (o.getMensagem().compareTo(string) > 1) {
            return 1;
        } else {
            return -1;
        }
    }

}
