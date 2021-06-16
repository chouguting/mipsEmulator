package com.chouguting.mipsemulator.ui;

import com.chouguting.mipsemulator.hardware.PipeliningController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PipelinedCircuitPanel extends JPanel {
    boolean hasExMemForwardingImage = false;
    boolean hasMemWbForwardingImage = false;
    JLabel ifLabel = new JLabel("null");
    JLabel idLabel = new JLabel("null");
    JLabel exLabel = new JLabel("null");
    JLabel memLabel = new JLabel("null");
    JLabel wbLabel = new JLabel("null");
    private BufferedImage pipeLineImage; //底圖
    private BufferedImage exMemForwardingImage; //mem階段的Forwarding
    private BufferedImage memWbForwardingImage; //wb階段的Forwarding

    public PipelinedCircuitPanel() {
        try {
            pipeLineImage = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("resources/images/pipelineHD.png"));
            exMemForwardingImage = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("resources/images/exMemForwarding.png"));
            memWbForwardingImage = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("resources/images/memWbForwarding.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setLayout(null);
        ifLabel.setFont(new Font("consolas", Font.PLAIN, 13));
        ifLabel.setBounds(10, 5, 130, 20);
        ifLabel.setBackground(Color.white);
        ifLabel.setOpaque(true);
        this.add(ifLabel);

        idLabel.setFont(new Font("consolas", Font.PLAIN, 13));
        idLabel.setBounds(140, 30, 130, 20);
        idLabel.setBackground(Color.white);
        idLabel.setOpaque(true);
        this.add(idLabel);

        exLabel.setFont(new Font("consolas", Font.PLAIN, 13));
        exLabel.setBounds(260, 55, 130, 20);
        exLabel.setBackground(Color.white);
        exLabel.setOpaque(true);
        this.add(exLabel);

        memLabel.setFont(new Font("consolas", Font.PLAIN, 13));
        memLabel.setBounds(380, 80, 130, 20);
        memLabel.setBackground(Color.white);
        memLabel.setOpaque(true);
        this.add(memLabel);

        wbLabel.setFont(new Font("consolas", Font.PLAIN, 13));
        wbLabel.setBounds(490, 105, 100, 20);
        wbLabel.setBackground(Color.white);
        wbLabel.setOpaque(true);
        this.add(wbLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(pipeLineImage, 0, 0, this); // see javadoc for more info on the parameters
        if (hasExMemForwardingImage) g.drawImage(exMemForwardingImage, 0, 0, this);
        if (hasMemWbForwardingImage) g.drawImage(memWbForwardingImage, 0, 0, this);
    }

    //更新PIPELINE的畫面顯示
    void updateLabel(PipeliningController pipeliningController) {
        String[] instructions = pipeliningController.getCurrentStagingInstructions();
        ifLabel.setText(instructions[PipeliningController.IF_STAGE]);
        idLabel.setText(instructions[PipeliningController.ID_STAGE]);
        exLabel.setText(instructions[PipeliningController.EX_STAGE]);
        memLabel.setText(instructions[PipeliningController.MEM_STAGE]);
        wbLabel.setText(instructions[PipeliningController.WB_STAGE]);

        hasExMemForwardingImage = pipeliningController.exMemForwarding();
        hasMemWbForwardingImage = pipeliningController.memWbForwarding();
        this.repaint();
    }


}
