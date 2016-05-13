/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;

/**
 *
 * @author Marcelo
 */
public class Grid implements Serializable {

    public int x, y, size, gap;
    public float gapStep;
    public float accuracy;

    public Grid() {
        x = 0;
        y = 0;
        size = 100;
        gap = 25;
        gapStep = (float) 0.2;
        accuracy = (float) 0.9;
    }

    public Grid(int x, int y, int size, int gap, float gapStep) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.gap = gap;
        this.gapStep = gapStep;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public void addX() {
        x += 1;
    }

    public void addY() {
        y += 1;
    }

    public void subX() {
        x -= 1;
    }

    public void subY() {
        y -= 1;
    }

    public void addGapStep() {
        gapStep += 0.03;
    }

    public void subGapStep() {
        gapStep -= 0.03;
    }

    public void addSize() {
        size += 1;
    }

    public void subSize() {
        size -= 1;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    public float getGapStep() {
        return gapStep;
    }

    public void setGapStep(float gapStep) {
        this.gapStep = gapStep;
    }

}
