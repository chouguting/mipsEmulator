package com.chouguting.mipsemulator.ui;

import com.chouguting.mipsemulator.execution.Program;

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
    private JButton openFileButton = new JButton();
    private JButton newFileButton = new JButton();
    private JTextArea codingArea = new JTextArea();
    private JButton saveFileButton = new JButton();
    private JButton assembleButton = new JButton();
    private JButton runButton = new JButton();
    private JButton stepButton = new JButton();
    private JScrollPane scrollCodingPart = new JScrollPane(codingArea);

    private Program myProgram;

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

        this.add(scrollCodingPart);
        this.add(openFileButton);
        this.add(newFileButton);
        this.add(saveFileButton);
        this.add(assembleButton);
        this.add(runButton);
        this.add(stepButton);
        this.setVisible(true);
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

        if (e.getSource() == assembleButton) {
            stepButton.setEnabled(true);
            scrollCodingPart.setBorder(new LineBorder(Color.CYAN, 5));
            scrollCodingPart.getVerticalScrollBar().setValue(0);
            myProgram = new Program(codingArea.getText());
            instructionUIHandler.paintLine(codingArea, myProgram.getCurrentInstructionLocation());
        }

        if (e.getSource() == stepButton) {
            if (myProgram.isEnded()) {
                instructionUIHandler.paintLine(codingArea, myProgram.getCurrentInstructionLocation() + 1);
            } else {
                myProgram.step();
                instructionUIHandler.paintLine(codingArea, myProgram.getCurrentInstructionLocation());
            }
        }
    }
}
