package com.chouguting.mipsemulator;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {
    public MainScreen(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1280,720);

        this.setLayout(null);
        //this.setResizable(false);
        this.setTitle("MIPS emulator");


        JButton openFileButton=new JButton();
        openFileButton.setBounds(10,10,60,30);
        openFileButton.setFocusable(false);
        openFileButton.setFont(new Font("consolas",Font.BOLD,11));
        openFileButton.setText("OPEN");

        JButton newFileButton=new JButton();
        newFileButton.setBounds(80,10,60,30);
        newFileButton.setFocusable(false);
        newFileButton.setFont(new Font("consolas",Font.BOLD,11));
        newFileButton.setText("NEW");

        JButton saveFileButton=new JButton();
        saveFileButton.setBounds(150,10,60,30);
        saveFileButton.setFocusable(false);
        saveFileButton.setFont(new Font("consolas",Font.BOLD,11));
        saveFileButton.setText("SAVE");

        JButton assembleButton=new JButton();
        assembleButton.setBounds(220,10,60,30);
        assembleButton.setFocusable(false);
        assembleButton.setFont(new Font("consolas",Font.BOLD,11));
        assembleButton.setText("ASEM");

        JButton runButton=new JButton();
        runButton.setBounds(290,10,60,30);
        runButton.setFocusable(false);
        runButton.setFont(new Font("consolas",Font.BOLD,11));
        runButton.setText("RUN");

        JButton stepButton=new JButton();
        stepButton.setBounds(360,10,60,30);
        stepButton.setFocusable(false);
        stepButton.setFont(new Font("consolas",Font.BOLD,11));
        stepButton.setText("STEP");


        JTextArea codingArea=new JTextArea();
        codingArea.setFont(new Font("consolas",Font.PLAIN,18));
        codingArea.setLineWrap(true);
        JScrollPane scrollCodingPart = new JScrollPane(codingArea);
        scrollCodingPart.setBounds(10,50,410,600);

        this.add(scrollCodingPart);
        this.add(openFileButton);
        this.add(newFileButton);
        this.add(saveFileButton);
        this.add(assembleButton);
        this.add(runButton);
        this.add(stepButton);
        this.setVisible(true);

    }
}
