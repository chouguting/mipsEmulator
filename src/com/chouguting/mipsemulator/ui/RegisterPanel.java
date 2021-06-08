package com.chouguting.mipsemulator.ui;

import com.chouguting.mipsemulator.hardware.Mips;
import com.chouguting.mipsemulator.hardware.Register;

import javax.swing.*;
import java.awt.*;

/**
 * 專門負責顯示暫存器的值
 */
public class RegisterPanel extends JPanel {
    private final TextField[] RegisterView = new TextField[Register.REGISTER_COUNT];

    public RegisterPanel() {
        this.setVisible(true);
        setSize(250, 450);
        this.setLayout(new GridLayout(Register.REGISTER_COUNT / 2, 2));
        for (int i = 0; i < Register.REGISTER_COUNT; i++) {
            RegisterView[i] = new TextField("0", 3);
            RegisterView[i].setEditable(false);
            RegisterView[i].setBackground(Color.WHITE);
            RegisterView[i].setFont(new Font("Consolas", Font.PLAIN, 15));
            JLabel singleRegisterLabel = new JLabel();
            singleRegisterLabel.setText(String.format("%4s ", Register.registerList[i]));
            singleRegisterLabel.setFont(new Font("Consolas", Font.BOLD, 17));
            JPanel singleRegisterPanel = new JPanel(new BorderLayout());

            //singleRegisterPanel.setPreferredSize(new Dimension(15,30));
            singleRegisterPanel.add(singleRegisterLabel, BorderLayout.WEST);
            singleRegisterPanel.add(RegisterView[i], BorderLayout.CENTER);
            this.add(singleRegisterPanel);
        }

    }

    public TextField getRegisterView(int index) {
        return RegisterView[index];
    }

    public TextField[] getRegisterView() {
        return RegisterView;
    }

    //畫面重新整理
    void refreshRegister(Mips source) {
        for (int i = 0; i < Register.REGISTER_COUNT; i++) {
            RegisterView[i].setText(Long.toString(source.getRegister(i).getData()));
        }
    }


}
