package com.chouguting.mipsemulator.ui;

import com.chouguting.mipsemulator.hardware.PipeliningController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    JButton dataHazardButton = new JButton();
    JButton controlHazardButton = new JButton();
    JButton exMemForwardingButton;
    private ImageIcon dataHazardNormal;
    private ImageIcon dataHazardFocus;
    private ImageIcon controlHazardNormal;
    private ImageIcon controlHazardFocus;
    
    JButton exWbForwardingButton;
    
    private ImageIcon exMemForwardingNormal;  //mem階段的Forwarding
    private ImageIcon exMemForwardingFocus;
    private ImageIcon exWbForwardingNormal;   //wb階段的Forwarding
    private ImageIcon exWbForwardingFocus;

    public PipelinedCircuitPanel() {
        this.setLayout(null);

        try {
            pipeLineImage = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("resources/images/pipelineHD.png"));
            dataHazardNormal = new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("dataHazardNormal.png")));
            dataHazardFocus = new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("dataHazardFocus.png")));
            controlHazardNormal = new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("controlHazardNormal.png")));
            controlHazardFocus = new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("controlHazardFocus.png")));
            exMemForwardingNormal = new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("resources/images/memForwardNormal.png")));
            exMemForwardingFocus = new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("resources/images/memForwardFocus.png")));
            exWbForwardingNormal = new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("resources/images/wbForwardNormal.png")));
            exWbForwardingFocus = new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("resources/images/wbForwardFocus.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        dataHazardButton = new JButton(dataHazardNormal);
        dataHazardButton.setBorderPainted(false);
        dataHazardButton.setContentAreaFilled(false);
        dataHazardButton.setFocusPainted(false);
        dataHazardButton.setFocusable(false);
        dataHazardButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                dataHazardButton.setIcon(dataHazardFocus);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                dataHazardButton.setIcon(dataHazardNormal);
            }
        });
        dataHazardButton.setBounds(140, 240, 113, 60);
        dataHazardButton.setVisible(false);
        this.add(dataHazardButton);

        controlHazardButton = new JButton(controlHazardNormal);
        controlHazardButton.setBorderPainted(false);
        controlHazardButton.setContentAreaFilled(false);
        controlHazardButton.setFocusPainted(false);
        controlHazardButton.setFocusable(false);
        controlHazardButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                controlHazardButton.setIcon(controlHazardFocus);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                controlHazardButton.setIcon(controlHazardNormal);
            }
        });
        controlHazardButton.setBounds(270, 240, 113, 60);
        controlHazardButton.setVisible(false);
        this.add(controlHazardButton);

        exMemForwardingButton = new JButton(exMemForwardingNormal);
        exMemForwardingButton.setBorderPainted(false);
        exMemForwardingButton.setContentAreaFilled(false);
        exMemForwardingButton.setFocusPainted(false);
        exMemForwardingButton.setFocusable(false);
        exMemForwardingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                exMemForwardingButton.setIcon(exMemForwardingFocus);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                exMemForwardingButton.setIcon(exMemForwardingNormal);
            }
        });
        exMemForwardingButton.setBounds(305, 120, 88, 55);
        exMemForwardingButton.setVisible(false);
        this.add(exMemForwardingButton);

        exWbForwardingButton = new JButton(exWbForwardingNormal);
        exWbForwardingButton.setBorderPainted(false);
        exWbForwardingButton.setContentAreaFilled(false);
        exWbForwardingButton.setFocusPainted(false);
        exWbForwardingButton.setFocusable(false);
        exWbForwardingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                exWbForwardingButton.setIcon(exWbForwardingFocus);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                exWbForwardingButton.setIcon(exWbForwardingNormal);
            }
        });
        exWbForwardingButton.setBounds(310, 180, 198, 57);
        exWbForwardingButton.setVisible(false);
        this.add(exWbForwardingButton);


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
        //if (hasExMemForwardingImage) g.drawImage(exMemForwardingImage, 0, 0, this);
        //if (hasMemWbForwardingImage) g.drawImage(memWbForwardingImage, 0, 0, this);
    }

    //更新PIPELINE的畫面顯示
    void updateLabel(PipeliningController pipeliningController) {
        String[] instructions = pipeliningController.getCurrentStagingInstructions();
        ifLabel.setText(instructions[PipeliningController.IF_STAGE]);
        idLabel.setText(instructions[PipeliningController.ID_STAGE]);
        exLabel.setText(instructions[PipeliningController.EX_STAGE]);
        memLabel.setText(instructions[PipeliningController.MEM_STAGE]);
        wbLabel.setText(instructions[PipeliningController.WB_STAGE]);
        if (pipeliningController.dataHazard()) {
            dataHazardButton.setVisible(true);
        } else {
            dataHazardButton.setVisible(false);
        }
        if (pipeliningController.controlHazardHappening()) {
            controlHazardButton.setVisible(true);
        } else {
            controlHazardButton.setVisible(false);
        }
        if (pipeliningController.exMemForwarding()) {
            exMemForwardingButton.setVisible(true);
        } else {
            exMemForwardingButton.setVisible(false);
        }
        if (pipeliningController.exWbForwarding()) {
            exWbForwardingButton.setVisible(true);
        } else {
            exWbForwardingButton.setVisible(false);
        }
        hasExMemForwardingImage = pipeliningController.exMemForwarding();
        hasMemWbForwardingImage = pipeliningController.exWbForwarding();
        this.repaint();
    }


}
