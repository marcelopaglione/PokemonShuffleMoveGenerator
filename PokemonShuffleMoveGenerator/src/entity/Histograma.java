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
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 *
 * @author Marcelo Ortiz Paglione Junior
 */
public class Histograma implements Serializable {

    private static final long serialVersionUID = 1L;
    public static int bits = 64;

    private float[] green, red, blue, border, interior;

    public Histograma(){
        
    }
    
    public float[] getBorder() {
        return border;
    }

    public void setBorder(float[] border) {
        this.border = border;
    }

    public float[] getInterior() {
        return interior;
    }

    public void setInterior(float[] interior) {
        this.interior = interior;
    }

    public float[] getGreen() {
        return green;
    }

    public void setGreen(float[] green) {
        this.green = green;
    }

    public float[] getRed() {
        return red;
    }

    public void setRed(float[] red) {
        this.red = red;
    }

    public float[] getBlue() {
        return blue;
    }

    public void setBlue(float[] blue) {
        this.blue = blue;
    }

    public void calcHist(BufferedImage image) {
        red = calc(image, 0);
        green = calc(image, 1);
        blue = calc(image, 2);
        border = calc(getInterior(bic(image)));
        interior = calc(getImageBorder(bic(image)));

    }

    private int[][] getImageBorder(int[][] image) {
        int[][] newImage = new int[image.length][image.length];
        for (int i = 1; i < image.length - 1; i++) {
            for (int j = 1; j < image[i].length - 1; j++) {
                int value = image[i][j];
                if ((value == image[i + 1][j]) && (value == image[i][j + 1]) && (value == image[i - 1][j])
                        && (value == image[i][j - 1])) {
                    newImage[i][j] = value;
                } else {
                    newImage[i][j] = 0;
                }
            }
        }
        return newImage;
    }

    private int[][] getInterior(int[][] image) {
        int[][] newImage = new int[image.length][image.length];
        for (int i = 1; i < image.length - 1; i++) {
            for (int j = 1; j < image[i].length - 1; j++) {
                int value = image[i][j];
                if ((value == image[i + 1][j]) && (value == image[i][j + 1]) && (value == image[i - 1][j])
                        && (value == image[i][j - 1])) {
                    newImage[i][j] = 0;
                } else {
                    newImage[i][j] = value;
                }
            }
        }
        return newImage;
    }

    private int[][] bic(BufferedImage image) {
        int[][] newImage = new int[image.getHeight()][image.getWidth()];
        int mask = 192; // 11000000
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                Color pixel = new Color(image.getRGB(i, j));
                int value = or(and(pixel.getRed(), mask) >>> 2, and(pixel.getGreen(), mask) >>> 4,
                        and(pixel.getBlue(), mask) >>> 6);
                newImage[i][j] = value;
            }
        }
        return newImage;
    }

    private static int or(int a, int b, int c) {
        return a | b | c;
    }

    private static int and(int a, int b) {
        return a & b;
    }

    private float[] calc(int[][] image) {
        float[] histograma = new float[bits];
        for (int[] image1 : image) {
            for (int image2 : image1) {
                int value = image2;
                histograma[value] += 1;
            }
        }
        for (int i = 0; i < histograma.length; i++) {
            histograma[i] /= (image.length * image.length);
        }
        return histograma;
    }

    private float[] calc(BufferedImage image, int n) {
        float[] histograma = new float[bits];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                Color pixel = new Color(image.getRGB(i, j));
                int value;
                switch (n) {
                    case 1:
                        value = requantizar(pixel.getGreen());
                        histograma[value] += 1;
                        break;
                    case 0:
                        value = requantizar(pixel.getRed());
                        histograma[value] += 1;
                        break;

                    default:
                        value = requantizar(pixel.getBlue());
                        histograma[value] += 1;
                }
            }
        }
        for (int i = 0; i < histograma.length; i++) {
            histograma[i] /= (image.getHeight() * image.getWidth());
        }
        return histograma;
    }

    private int requantizar(int n) {
        return (bits - 1) * n / 255;
    }

}
