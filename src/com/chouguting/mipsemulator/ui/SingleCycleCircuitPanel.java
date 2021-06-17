package com.chouguting.mipsemulator.ui;

import com.chouguting.mipsemulator.hardware.PipeliningController;
import com.chouguting.mipsemulator.software.ITypeInstruction;
import com.chouguting.mipsemulator.software.Instruction;
import com.chouguting.mipsemulator.software.RTypeInstruction;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SingleCycleCircuitPanel extends JPanel {
    Instruction currentInstruction;
    JLabel currentInstructionLabel;
    private BufferedImage circuitImage;
    private BufferedImage rTypeGeneralDataPath;
    private BufferedImage iTypeLwDataPath;
    private BufferedImage iTypeSwDataPath;
    private BufferedImage iTypeBeqDataPath;

    public SingleCycleCircuitPanel() {
        this.setLayout(null);
        currentInstructionLabel = new JLabel("Current: ");
        currentInstructionLabel.setBounds(0, 300, 300, 20);

        try {
            circuitImage = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/newPath.png"));
            rTypeGeneralDataPath = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/rTypeAddDataPath.png"));
            iTypeLwDataPath = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/iTypeLwDataPath.png"));
            iTypeSwDataPath = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/iTypeSwDataPath.png"));
            iTypeBeqDataPath = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/iTypeBeqDataPath.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.add(currentInstructionLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(circuitImage, 0, 0, this); // see javadoc for more info on the parameters
        if (currentInstruction == null) return;
        if (currentInstruction.getClass() == RTypeInstruction.class) {
            RTypeInstruction instruction = (RTypeInstruction) currentInstruction;
            g.drawImage(rTypeGeneralDataPath, 0, 0, this);
        } else if (currentInstruction.getClass() == ITypeInstruction.class) {
            ITypeInstruction instruction = (ITypeInstruction) currentInstruction;
            if (instruction.getOpCode() == ITypeInstruction.LWop) {
                g.drawImage(iTypeLwDataPath, 0, 0, this);
            } else if (instruction.getOpCode() == ITypeInstruction.SWop) {
                g.drawImage(iTypeSwDataPath, 0, 0, this);
            } else if (instruction.getOpCode() == ITypeInstruction.BEQop || instruction.getOpCode() == ITypeInstruction.BNEop) {
                g.drawImage(iTypeBeqDataPath, 0, 0, this);
            }
        }
    }

    public void update(PipeliningController pipeliningController) {
        String[] instructionsString = pipeliningController.getCurrentStagingInstructions();
        Instruction[] instructions = pipeliningController.getCurrentInstructions();
        currentInstructionLabel.setText("Current Instruction: " + instructionsString[PipeliningController.EX_STAGE]);
        currentInstruction = instructions[PipeliningController.EX_STAGE];
        repaint();
    }
}
