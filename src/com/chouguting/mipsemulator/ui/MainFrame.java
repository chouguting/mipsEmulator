package com.chouguting.mipsemulator.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    HDMainScreenPanel hdMainScreenPanel = new HDMainScreenPanel();

    public MainFrame() {
        new UpdateChecker(this).execute();  //檢查有沒有更新版的軟體
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("MIPS emulator");
        this.setPreferredSize(new Dimension(1280, 720));
        this.setMinimumSize(new Dimension(1280, 720));
        this.setResizable(true);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.add(hdMainScreenPanel);
        //this.pack();
        this.setLocationRelativeTo(null);  //設定出現在畫面正中央
        this.setVisible(true);
    }
}
