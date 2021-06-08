package com.chouguting.mipsemulator.ui;

import com.chouguting.mipsemulator.hardware.Register;

import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel {
    private final TextField[] RegisterView = new TextField[Register.REGISTER_COUNT];

    public RegisterPanel() {
        this.setVisible(true);
        setSize(200, 450);
        this.setLayout(new GridLayout(Register.REGISTER_COUNT / 2, 2));
        for (int i = 0; i < Register.REGISTER_COUNT; i++) {
            RegisterView[i] = new TextField("0", 3);
            RegisterView[i].setEditable(false);
            RegisterView[i].setBackground(Color.WHITE);
            RegisterView[i].setFont(new Font("Consolas", Font.PLAIN, 15));
            JLabel singleRegisterLabel = new JLabel(Register.registerList[i]);
            singleRegisterLabel.setFont(new Font("Consolas", Font.PLAIN, 18));
            JPanel singleRegisterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

            //singleRegisterPanel.setPreferredSize(new Dimension(15,30));
            singleRegisterPanel.add(singleRegisterLabel);
            singleRegisterPanel.add(RegisterView[i]);
            this.add(singleRegisterPanel);
        }

    }

    public TextField getRegisterView(int index) {
        return RegisterView[index];
    }

    public TextField[] getRegisterView() {
        return RegisterView;
    }


}
