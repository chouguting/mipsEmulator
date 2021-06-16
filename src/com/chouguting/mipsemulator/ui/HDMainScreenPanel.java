package com.chouguting.mipsemulator.ui;

import com.chouguting.mipsemulator.exception.InstructionErrorException;
import com.chouguting.mipsemulator.hardware.MipsWithPipeline;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
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
public class HDMainScreenPanel extends JPanel implements ActionListener {
    JFileChooser fileChooser = new JFileChooser();
    private JButton openFileButton; //開啟舊檔
    private JButton newFileButton;  //新頁面的按鈕
    private JTextArea codingArea = new JTextArea(); //程式編輯區
    private JButton saveFileButton;  //儲存檔案的按鈕
    private JButton assembleButton = new JButton(); //編譯按鈕
    private JButton runButton = new JButton(); //執行程式的按鈕
    private JButton stepButton = new JButton(); //一步一步執行的按鈕
    private JScrollPane scrollCodingPart = new JScrollPane(codingArea);

    private ImageIcon newButtonNormalImage;
    private ImageIcon newButtonFocusImage;
    private ImageIcon openButtonNormalImage;
    private ImageIcon openButtonFocusImage;
    private ImageIcon saveButtonNormalImage;
    private ImageIcon saveButtonFocusImage;

    private RegisterPanel registerPanel = new RegisterPanel();
    MemorySearchPanel memorySearchPanel = new MemorySearchPanel();
    PipelinedCircuitPanel pipeliningArea = new PipelinedCircuitPanel();
    SingleCycleCircuitPanel singleCycleCircuitArea = new SingleCycleCircuitPanel();

    private MipsWithPipeline myMIPSEmulator;

    public HDMainScreenPanel() {
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1280, 720);
        this.setPreferredSize(new Dimension(1280, 720));
        this.setLayout(null);
        //this.setResizable(false);
        //this.setTitle("MIPS emulator");


        try {
            newButtonNormalImage=new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("resources/images/newButtonNormal.png")));
            newButtonFocusImage=new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("resources/images/newButtonFocus.png")));
            openButtonFocusImage=new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("resources/images/openButtonFocus.png")));
            openButtonNormalImage=new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("resources/images/openButtonNormal.png")));
            saveButtonNormalImage=new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("resources/images/saveButtonNormal.png")));
            saveButtonFocusImage=new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("resources/images/saveButtonFocus.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        newFileButton = new JButton(newButtonNormalImage);
        newFileButton.setToolTipText("New File");
        newFileButton.setBorderPainted(false);
        newFileButton.setContentAreaFilled(false);
        newFileButton.setFocusPainted(false);
        newFileButton.setBounds(10, 0, 45, 45);
        newFileButton.setFocusable(false);
        newFileButton.addActionListener(this);
        newFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                newFileButton.setIcon(newButtonFocusImage);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                newFileButton.setIcon(newButtonNormalImage);
            }
        });


        openFileButton=new JButton(openButtonNormalImage);
        openFileButton.setToolTipText("Open File");
        openFileButton.setBorderPainted(false);
        openFileButton.setContentAreaFilled(false);
        openFileButton.setFocusPainted(false);
        openFileButton.setBounds(55, 0, 45, 45);
        openFileButton.setFocusable(false);
        openFileButton.addActionListener(this);
        openFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                openFileButton.setIcon(openButtonFocusImage);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                openFileButton.setIcon(openButtonNormalImage);
            }
        });

        saveFileButton=new JButton(saveButtonNormalImage);
        saveFileButton.setToolTipText("Save File");
        saveFileButton.setBorderPainted(false);
        saveFileButton.setContentAreaFilled(false);
        saveFileButton.setFocusPainted(false);
        saveFileButton.setBounds(100, 0, 45, 45);
        saveFileButton.setFocusable(false);
        saveFileButton.addActionListener(this);
        saveFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                saveFileButton.setIcon(saveButtonFocusImage);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                saveFileButton.setIcon(saveButtonNormalImage);
            }
        });


        assembleButton.setBounds(220, 10, 60, 30);
        assembleButton.setFocusable(false);
        assembleButton.setFont(new Font("consolas", Font.BOLD, 11));
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

        codingArea.setFont(new Font("consolas", Font.PLAIN, 25));
        codingArea.setLineWrap(true);
        codingArea.addKeyListener(new KeyListener() {   //當編輯區域有改變文字時
            @Override
            public void keyTyped(KeyEvent e) {
                stepButton.setEnabled(false);
                memorySearchPanel.getMemSearchButton().setEnabled(false);
                memorySearchPanel.getWordSearchButton().setEnabled(false);
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

        //暫存器顯示數值區域
        registerPanel.setLocation(this.getWidth() - 270, 5);


        //single cycle circuit顯示區域
        singleCycleCircuitArea.setBackground(Color.white);
        singleCycleCircuitArea.setBounds(425, 10, 585, 350);
        this.add(singleCycleCircuitArea);

        //pipeline dataPath 顯示區域
        pipeliningArea.setBackground(new Color(227, 191, 60));
        pipeliningArea.setBounds(425, 365, 585, 305);
        this.add(pipeliningArea);

        //內存搜尋區域
        memorySearchPanel.getMemSearchButton().addActionListener(this);
        memorySearchPanel.getWordSearchButton().addActionListener(this);
        memorySearchPanel.setBounds(this.getWidth() - 265, 460, 245, 210);
        this.add(memorySearchPanel);


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
            try {
                myMIPSEmulator = new MipsWithPipeline(codingArea.getText()); //建立一個新的MIPS INSTANCE
            } catch (InstructionErrorException instructionErrorException) { //如果組譯過程中有錯
                if (instructionErrorException.isTraceable())
                    InstructionUIHandler.paintLine(codingArea, Color.red, (int) instructionErrorException.getErrorLocation());
                JOptionPane.showMessageDialog(this, "組譯錯誤");
                return;
            }
            registerPanel.refreshRegister(myMIPSEmulator);
            pipeliningArea.updateLabel(myMIPSEmulator.getPipeliningController());
            stepButton.setEnabled(true);

            //內存搜尋區域重置
            memorySearchPanel.getMemSearchButton().setEnabled(true);
            memorySearchPanel.getWordSearchButton().setEnabled(true);
            memorySearchPanel.getSearchIndexTextField().setText("0");
            memorySearchPanel.updateTable(myMIPSEmulator.getMemory());
            scrollCodingPart.setBorder(new LineBorder(Color.CYAN, 5));
            scrollCodingPart.getVerticalScrollBar().setValue(0);
            if (!codingArea.getText().endsWith("\n                "))
                codingArea.setText(codingArea.getText() + "\n                ");  //最後留一行空白行
            InstructionUIHandler.paintLine(codingArea, Color.cyan, (int) myMIPSEmulator.getProgram().getCurrentInstructionLocation());

        }

        if (e.getSource() == stepButton) {
            if (myMIPSEmulator.getProgram().isEnded()) {  //如果程式已經跑完
                codingArea.getHighlighter().removeAllHighlights();
                myMIPSEmulator.getPipeliningController().stepNext(); //流水線還是可以繼續推下去
                //InstructionUIHandler.paintLine(codingArea, Color.cyan, myMIPSEmulator.getProgram().getCurrentInstructionLocation());
            } else { //如果程式還沒跑完 就STEP下一步
                myMIPSEmulator.step();
                if (myMIPSEmulator.getProgram().isEnded()) {
                    InstructionUIHandler.paintLine(codingArea, Color.cyan, (int) myMIPSEmulator.getProgram().getCurrentInstructionLocation());
                } else {
                    InstructionUIHandler.paintLine(codingArea, Color.cyan, (int) myMIPSEmulator.getProgram().getCurrentInstructionLocation());
                }
            }
            memorySearchPanel.updateTable(myMIPSEmulator.getMemory());
            pipeliningArea.updateLabel(myMIPSEmulator.getPipeliningController());
            registerPanel.refreshRegister(myMIPSEmulator);
        }

        //處理搜尋內存的事件 單純搜尋Address
        if (e.getSource() == memorySearchPanel.getMemSearchButton()) {
            memorySearchPanel.setDisplayMode(MemorySearchPanel.MOMERY_ADDRESS_MODE);
            memorySearchPanel.setCurrentSearchIndex(Integer.parseInt(memorySearchPanel.getSearchIndexTextField().getText())); //先設定要搜尋的位置
            memorySearchPanel.updateTable(myMIPSEmulator.getMemory());  //更新畫面
        }

        //處理搜尋內存的事件 以WORD為單位
        if (e.getSource() == memorySearchPanel.getWordSearchButton()) {
            if (Long.parseLong(memorySearchPanel.getSearchIndexTextField().getText()) % 4 != 0) {
                JOptionPane.showMessageDialog(this, "請輸入4的倍數");
                return;
            }
            memorySearchPanel.setDisplayMode(MemorySearchPanel.DISPLAY_WORD_MODE);
            memorySearchPanel.setCurrentSearchIndex(Integer.parseInt(memorySearchPanel.getSearchIndexTextField().getText())); //先設定要搜尋的位置
            memorySearchPanel.updateTable(myMIPSEmulator.getMemory());  //更新畫面
        }
    }


}
