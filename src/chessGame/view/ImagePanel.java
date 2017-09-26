package chessGame.view;

import javax.swing.*;
import java.awt.*;

/**
 * ImagePanel extension
 */
public class ImagePanel extends JPanel {

    /**
     * The background image
     */
    private Image img;

    /**
     * Constructor with path string
     *
     * @param img
     */
    public ImagePanel(String img) {
        this(new ImageIcon(img).getImage());
    }

    /**
     * Constructor with image
     *
     * @param img
     */
    public ImagePanel(Image img) {
        this.img = img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    /**
     * Override paint component
     *
     * @param g
     */
    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

}
