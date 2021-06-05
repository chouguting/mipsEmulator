package com.chouguting.mipsemulator.ui;

import com.chouguting.mipsemulator.exception.InstructionErrorException;
import com.chouguting.mipsemulator.hardware.Mips;
import com.chouguting.mipsemulator.hardware.Register;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * MainScreen:這是主程式的使用者介面
 * 包含一個可滾動(JScrollPane)的CodingArea(JTextArea)
 * 包含五個功能按鈕(新檔案、存檔、載入檔案、編譯、執行、逐行跑)
 * 寫好的程式會變成一個Program物件
 */
public class MainScreen extends JFrame implements ActionListener {
    JFileChooser fileChooser = new JFileChooser();
    private JButton openFileButton = new JButton(); //開啟舊檔
    private JButton newFileButton = new JButton();  //新頁面的按鈕
    private JTextArea codingArea = new JTextArea(); //程式編輯區
    private JButton saveFileButton = new JButton();  //儲存檔案的按鈕
    private JButton assembleButton = new JButton(); //編譯按鈕
    private JButton runButton = new JButton(); //執行程式的按鈕
    private JButton stepButton = new JButton(); //一步一步執行的按鈕
    private JScrollPane scrollCodingPart = new JScrollPane(codingArea);

    private RegisterPanel registerPanel = new RegisterPanel();
    private Mips myMIPSEmulator;

    public MainScreen() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1280, 720);

        this.setLayout(null);
        this.setResizable(false);
        this.setTitle("MIPS emulator");

        newFileButton.setBounds(10, 10, 60, 30);
        newFileButton.setFocusable(false);
        newFileButton.setFont(new Font("consolas", Font.BOLD, 11));
        newFileButton.setText("NEW");
        newFileButton.addActionListener(this);

        openFileButton.setBounds(80, 10, 60, 30);
        openFileButton.setFocusable(false);
        openFileButton.setFont(new Font("consolas", Font.BOLD, 11));
        openFileButton.setText("OPEN");
        openFileButton.addActionListener(this);

        saveFileButton.setBounds(150, 10, 60, 30);
        saveFileButton.setFocusable(false);
        saveFileButton.setFont(new Font("consolas", Font.BOLD, 11));
        saveFileButton.setText("SAVE");
        saveFileButton.addActionListener(this);


        assembleButton.setBounds(220,10,60,30);
        assembleButton.setFocusable(false);
        assembleButton.setFont(new Font("consolas",Font.BOLD,11));
        assembleButton.setText("ASEM");
        assembleButton.addActionListener(this);

        runButton.setBounds(290, 10, 60, 30);
        runButton.setFocusable(false);
        runButton.setFont(new Font("consolas", Font.BOLD, 11));
        runButton.setText("RUN");
        runButton.setEnabled(false);

        stepButton.setBounds(360, 10, 60, 30);
        stepButton.setFocusable(false);
        stepButton.setFont(new Font("consolas", Font.BOLD, 11));
        stepButton.setText("STEP");
        stepButton.addActionListener(this);
        stepButton.setEnabled(false);

        codingArea.setFont(new Font("consolas", Font.PLAIN, 18));
        codingArea.setLineWrap(true);
        codingArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                stepButton.setEnabled(false);
                scrollCodingPart.setBorder(null);
                codingArea.getHighlighter().removeAllHighlights();
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        scrollCodingPart.setBounds(10, 50, 410, this.getHeight() - 100);
        registerPanel.setLocation(1060, 0);
        this.add(scrollCodingPart);
        this.add(openFileButton);
        this.add(newFileButton);
        this.add(saveFileButton);
        this.add(assembleButton);
        this.add(runButton);
        this.add(stepButton);
        this.add(registerPanel);
        this.setVisible(true);
    }

    void refreshRegister() {
        for (int i = 0; i < Register.REGISTER_COUNT; i++) {
            registerPanel.getRegisterView(i).setText(Integer.toString(myMIPSEmulator.getRegister(i).getData()));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveFileButton) {
            fileChooser.setFileFilter(new FileNameExtensionFilter("Assembly Code (*.asm)", "asm"));
            int userSelection = fileChooser.showSaveDialog(new JFrame());
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File saveFile = fileChooser.getSelectedFile();
                String filePath = saveFile.getAbsolutePath();
                if (!filePath.endsWith(".asm"))
                    filePath += ".asm";
                try {
                    FileWriter fileWriter = new FileWriter(filePath);
                    fileWriter.append(codingArea.getText());
                    fileWriter.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }

        if (e.getSource() == openFileButton) {
            fileChooser.setFileFilter(new FileNameExtensionFilter("Assembly Code (*.asm)", "asm"));
            int userSelection = fileChooser.showOpenDialog(new JFrame());
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File saveFile = fileChooser.getSelectedFile();
                try {
                    String content = Files.readString(Path.of(saveFile.getAbsolutePath()), StandardCharsets.UTF_8);
                    codingArea.setText(content);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }

        if (e.getSource() == newFileButton) {
            int answer = JOptionPane.showConfirmDialog(this, "建立新檔案將會清除掉未儲存的資料\n確定繼續?", "建立新檔案", JOptionPane.YES_NO_CANCEL_OPTION);
            if (answer == 0) codingArea.setText("");
        }

        //處理組譯按鈕
        if (e.getSource() == assembleButton) {
            stepButton.setEnabled(true);

            try {
                myMIPSEmulator = new Mips(codingArea.getText()); //建立一個新的MIPS INSTANCE
            } catch (InstructionErrorException instructionErrorException) { //如果組譯過程中有錯
                if (instructionErrorException.isTraceable())
                    InstructionUIHandler.paintLine(codingArea, Color.red, instructionErrorException.getErrorLocation());
                JOptionPane.showMessageDialog(this, "組譯錯誤");
                return;
            }
            scrollCodingPart.setBorder(new LineBorder(Color.CYAN, 5));
            scrollCodingPart.getVerticalScrollBar().setValue(0);
            if (!codingArea.getText().endsWith("\\n")) codingArea.setText(codingArea.getText() + "\n");  //最後留一行空白行
            InstructionUIHandler.paintLine(codingArea, Color.cyan, myMIPSEmulator.getProgram().getCurrentInstructionLocation());

        }

        if (e.getSource() == stepButton) {
            refreshRegister();
            if (myMIPSEmulator.getProgram().isEnded()) {  //如果程式已經跑完
                //codingArea.getHighlighter().removeAllHighlights();
                InstructionUIHandler.paintLine(codingArea, Color.cyan, myMIPSEmulator.getProgram().getCurrentInstructionLocation() + 1);
            } else { //如果程式還沒跑完 就STEP下一步
                myMIPSEmulator.getProgram().step();
                if (myMIPSEmulator.getProgram().isEnded()) {
                    InstructionUIHandler.paintLine(codingArea, Color.cyan, myMIPSEmulator.getProgram().getCurrentInstructionLocation() + 1);
                } else {
                    InstructionUIHandler.paintLine(codingArea, Color.cyan, myMIPSEmulator.getProgram().getCurrentInstructionLocation());
                }
            }
        }
    }


}
