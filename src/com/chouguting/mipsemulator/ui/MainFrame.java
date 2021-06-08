package com.chouguting.mipsemulator.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    HDMainScreenPanel hdMainScreenPanel = new HDMainScreenPanel();

    public MainFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1280, 720);
        this.setTitle("MIPS emulator");
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.add(hdMainScreenPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }
}
