package com.chouguting.mipsemulator.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SingleCycleCircuitPanel extends JPanel {
    private BufferedImage image;
    //happy holiday
    public SingleCycleCircuitPanel() {
        try {
            image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/newPath.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters
    }
}
