/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PlayAnimatedGif {

    public static void main(String[] args) {
        new PlayAnimatedGif();
    }

    public PlayAnimatedGif() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                //frame.add(new GiffButton(getClass().getResource("/gui/Ho-Oh_Shiny_XY.gif")));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public static class GiffButton extends JButton {

        private AnimatedGif ag;

        public GiffButton(int i) {
            URL url = null;
            switch (i) {
                case 1:
                    url = getClass().getResource("/gui/Ho-Oh_Shiny_XY.gif");
                    break;

                case 2:
                    url = getClass().getResource("/gui/Lugia_XY.gif");
                    break;

                case 3:
                    url = getClass().getResource("/gui/xerneas-2.gif");
                    break;

                case 6:
                    url = getClass().getResource("/gui/alakazam-mega.gif");
                    break;

                case 5:
                    url = getClass().getResource("/gui/absol-mega.gif");
                    break;
            }
            try {
                ag = new AnimatedGif(this, url);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            ag.play();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            BufferedImage currentFrame = ag.getCurrentFrame();
            if (currentFrame != null) {
                int x = (getWidth() - currentFrame.getWidth()) / 2;
                int y = (getHeight() - currentFrame.getHeight()) / 2;
                g2d.drawImage(currentFrame, x, y, this);
            }
            g2d.dispose();
        }
    }

    public static class AnimatedGif {

        public enum DisposalMethod {
            RESTORE_TO_BACKGROUND,
            RESTORE_TO_PREVIOUS,
            DO_NOT_DISPOSE,
            UNSPECIFIED;

            public static DisposalMethod find(String text) {

                DisposalMethod dm = UNSPECIFIED;

                switch (text) {
                    case "restoreToBackgroundColor":
                        dm = RESTORE_TO_BACKGROUND;
                        break;
                }

                return dm;

            }
        }

        private List<ImageFrame> frames;
        private int frame;
        private Timer playTimer;

        private JComponent player;

        protected AnimatedGif(JComponent value) {
            this.player = value;
            frames = new ArrayList<>(25);
            playTimer = new Timer(0, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame++;
                    if (frame >= frames.size()) {
                        frame = 0;
                    }
                    player.repaint();
                    playTimer.setDelay(frames.get(0).getGraphicControlExtension().getDelayTime());
                }
            });
        }

        public AnimatedGif(JComponent player, URL url) throws IOException {
            this(player);
            try (InputStream is = url.openStream(); ImageInputStream stream = ImageIO.createImageInputStream(is)) {
                Iterator readers = ImageIO.getImageReaders(stream);
                if (!readers.hasNext()) {
                    throw new RuntimeException("no image reader found");
                }
                ImageReader reader = (ImageReader) readers.next();
                reader.setInput(stream);            // don't omit this line!
                int n = reader.getNumImages(true);  // don't use false!
                for (int i = 0; i < n; i++) {
                    BufferedImage image = reader.read(i);
                    ImageFrame imageFrame = new ImageFrame(image);

                    IIOMetadata imd = reader.getImageMetadata(i);
                    Node tree = imd.getAsTree("javax_imageio_gif_image_1.0");
                    NodeList children = tree.getChildNodes();

                    for (int j = 0; j < children.getLength(); j++) {
                        Node nodeItem = children.item(j);
                        NamedNodeMap attr = nodeItem.getAttributes();
                        switch (nodeItem.getNodeName()) {
                            case "ImageDescriptor":
                                ImageDescriptor id = new ImageDescriptor(
                                        getIntValue(attr.getNamedItem("imageLeftPosition")),
                                        getIntValue(attr.getNamedItem("imageTopPosition")),
                                        getIntValue(attr.getNamedItem("imageWidth")),
                                        getIntValue(attr.getNamedItem("imageHeight")),
                                        getBooleanValue(attr.getNamedItem("interlaceFlag")));
                                imageFrame.setImageDescriptor(id);
                                break;
                            case "GraphicControlExtension":
                                GraphicControlExtension gc = new GraphicControlExtension(
                                        DisposalMethod.find(getNodeValue(attr.getNamedItem("disposalMethod"))),
                                        getBooleanValue(attr.getNamedItem("userInputFlag")),
                                        getBooleanValue(attr.getNamedItem("transparentColorFlag")),
                                        getIntValue(attr.getNamedItem("delayTime")) * 10,
                                        getIntValue(attr.getNamedItem("transparentColorIndex")));
                                imageFrame.setGraphicControlExtension(gc);
                                break;
                        }
                    }
                    frames.add(imageFrame);
                }
            } finally {
            }
        }

        public BufferedImage getCurrentFrame() {
            // If this was a optimised GIF, we would need to be 
            // merging frames together to produce the current frame
            // This would then need to be reset each time we cycle...
            return frames.isEmpty() ? null : frames.get(frame).getImage();
        }

        public void play() {
            if (!frames.isEmpty()) {
                frame = 0;
                playTimer.setDelay(frames.get(0).getGraphicControlExtension().getDelayTime());
                playTimer.start();
                player.repaint();
            }
        }

        public void stop() {
            playTimer.stop();
        }

        protected String getNodeValue(Node node) {
            return node == null ? null : node.getNodeValue();
        }

        protected int getIntValue(Node node) {
            return node == null ? 0 : getIntValue(node.getNodeValue());
        }

        protected boolean getBooleanValue(Node node) {
            return node == null ? false : getBooleanValue(node.getNodeValue());
        }

        protected int getIntValue(String value) {
            return value == null ? 0 : Integer.parseInt(value);
        }

        protected boolean getBooleanValue(String value) {
            return value == null ? false : Boolean.parseBoolean(value);
        }

        public class ImageFrame {

            private BufferedImage image;
            private ImageDescriptor imageDescriptor;
            private GraphicControlExtension graphicControlExtension;

            public ImageFrame(BufferedImage image) {
                this.image = image;
            }

            protected void setImageDescriptor(ImageDescriptor imageDescriptor) {
                this.imageDescriptor = imageDescriptor;
            }

            protected void setGraphicControlExtension(GraphicControlExtension graphicControlExtension) {
                this.graphicControlExtension = graphicControlExtension;
            }

            public GraphicControlExtension getGraphicControlExtension() {
                return graphicControlExtension;
            }

            public BufferedImage getImage() {
                return image;
            }

            public ImageDescriptor getImageDescriptor() {
                return imageDescriptor;
            }

        }

        public class GraphicControlExtension {

            private DisposalMethod disposalMethod;
            private boolean userInputFlag;
            private boolean transparentColorFlag;
            private int delayTime;
            private int transparentColorIndex;

            public GraphicControlExtension(DisposalMethod disposalMethod, boolean userInputFlag, boolean transparentColorFlag, int delayTime, int transparentColorIndex) {
                this.disposalMethod = disposalMethod;
                this.userInputFlag = userInputFlag;
                this.transparentColorFlag = transparentColorFlag;
                this.delayTime = delayTime;
                this.transparentColorIndex = transparentColorIndex;
            }

            public int getDelayTime() {
                return delayTime;
            }

            public DisposalMethod getDisposalMethod() {
                return disposalMethod;
            }

            public int getTransparentColorIndex() {
                return transparentColorIndex;
            }

            public boolean isTransparentColorFlag() {
                return transparentColorFlag;
            }

            public boolean isUserInputFlag() {
                return userInputFlag;
            }

        }

        public class ImageDescriptor {

            private int imageLeftPosition;
            private int imageTopPosition;
            private int imageHeight;
            private int imageWeight;
            private boolean interlaced;

            public ImageDescriptor(int imageLeftPosition, int imageTopPosition, int imageHeight, int imageWeight, boolean interlaced) {
                this.imageLeftPosition = imageLeftPosition;
                this.imageTopPosition = imageTopPosition;
                this.imageHeight = imageHeight;
                this.imageWeight = imageWeight;
                this.interlaced = interlaced;
            }

            public int getImageHeight() {
                return imageHeight;
            }

            public int getImageLeftPosition() {
                return imageLeftPosition;
            }

            public int getImageTopPosition() {
                return imageTopPosition;
            }

            public int getImageWeight() {
                return imageWeight;
            }

            public boolean isInterlaced() {
                return interlaced;
            }

        }

    }

}
