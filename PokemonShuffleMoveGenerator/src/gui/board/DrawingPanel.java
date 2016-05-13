package gui.board;

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
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

@SuppressWarnings("serial")
public class DrawingPanel extends JPanel {

    private static final Color DRAWING_COLOR = new Color(255, 100, 200);

    private BufferedImage backgroundImg;
    private Point startPt = null;
    private Point endPt = null;
    private Point currentPt = null;
    private int prefW;
    private int prefH;
    private int wi, hi;
    private int x, y, gap, size;
    private Rectangle screenSize;

    public DrawingPanel() {
        try {
            screenSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage bImg = new Robot().createScreenCapture(screenSize);
            prefW = bImg.getWidth();
            prefH = bImg.getHeight();
            backgroundImg = new BufferedImage(prefW, prefH,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics g = backgroundImg.getGraphics();
            g.drawImage(bImg, 0, 0, this);
            g.dispose();
            MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
            addMouseMotionListener(myMouseAdapter);
            addMouseListener(myMouseAdapter);
        } catch (AWTException ex) {
            System.exit(-1);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImg != null) {
            g.drawImage(backgroundImg, 0, 0, this);
        }

        if (startPt != null && currentPt != null) {
            g.setColor(DRAWING_COLOR);
            x = Math.min(startPt.x, currentPt.x);
            y = Math.min(startPt.y, currentPt.y);
            int width = Math.abs(startPt.x - currentPt.x);
            int height = Math.abs(startPt.y - currentPt.y);
            if (width != height) {
                if (width > height) {
                    height = width;
                } else if (height > width) {
                    width = height;
                }
            }
            wi = width;
            hi = height;

            this.size = (int) (width / 6);
            this.gap = (int) (size * 0.2);
            size -= 2 * gap;
            repaint();

            g.drawRect(x, y, width, height);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(prefW, prefH);
    }

    private class MyMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDragged(MouseEvent mEvt) {
            currentPt = mEvt.getPoint();
            DrawingPanel.this.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent mEvt) {
            endPt = mEvt.getPoint();
            currentPt = null;
            repaint();
            BufferedImage bim = backgroundImg.getSubimage(x, y, wi, hi);

            ImageIcon icon = new ImageIcon(bim);
            int op = JOptionPane.showConfirmDialog(null, "Você está\ncerto disto?", "Confirmação",
                    JOptionPane.YES_NO_OPTION, 0, icon);

            if (op == JOptionPane.YES_NO_OPTION) {
                Board.grid.setGap(gap);
                Board.grid.setSize(size);
                Board.grid.setX(x);
                Board.grid.setY(y);
                Board.saveGrid(Board.grid);
                Board.frame.dispose();
            }
        }

        @Override
        public void mousePressed(MouseEvent mEvt) {
            startPt = mEvt.getPoint();
        }
    }
}
