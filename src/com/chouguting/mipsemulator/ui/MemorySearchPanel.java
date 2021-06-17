package com.chouguting.mipsemulator.ui;

import com.chouguting.mipsemulator.hardware.Memory;
import com.chouguting.mipsemulator.software.opr.MemoryLocation;

import javax.swing.*;
import java.awt.*;

//用來搜尋內存內的數值
public class MemorySearchPanel extends JPanel {
    static final int MOMERY_ADDRESS_MODE = 0;
    static final int DISPLAY_WORD_MODE = 1;
    static int SEARCH_AMOUNT = 20;
    JTextField searchIndexTextField = new JTextField("0");
    JButton memSearchButton = new JButton("MEM");
    JButton wordSearchButton = new JButton("WORD");
    int displayMode = MOMERY_ADDRESS_MODE;
    long currentSearchIndex = 0;
    JPanel showArea = new JPanel(new BorderLayout());
    private String[][] data = {};
    private String[] addressTitle = {"address", "data"};
    private String[] wordTitle = {"word location", "data"};
    private JTable dataTable = new JTable(data, addressTitle);
    private JScrollPane scrollPaneWithTable = new JScrollPane(dataTable);

    public MemorySearchPanel() {
        this.setLayout(new BorderLayout());
        JPanel searchPanel = new JPanel(new BorderLayout());
        memSearchButton.setEnabled(false);
        getWordSearchButton().setEnabled(false);
        showArea.setBackground(Color.CYAN);
        showArea.add(scrollPaneWithTable, BorderLayout.CENTER);
        searchPanel.add(searchIndexTextField, BorderLayout.CENTER);
        searchPanel.add(memSearchButton, BorderLayout.EAST);
        memSearchButton.setFocusable(false);

        JPanel totalSearchPanel = new JPanel(new BorderLayout());
        totalSearchPanel.add(searchPanel, BorderLayout.CENTER);
        totalSearchPanel.add(wordSearchButton, BorderLayout.EAST);


        this.add(totalSearchPanel, BorderLayout.NORTH);
        this.add(showArea);
    }

    public void updateTable(Memory memory) {
        showArea.remove(scrollPaneWithTable);
        String[][] newData = new String[SEARCH_AMOUNT][];
        if (displayMode == MOMERY_ADDRESS_MODE) {
            for (int i = 0; i < SEARCH_AMOUNT; i++) {
                long value = memory.getData(currentSearchIndex + i);
                newData[i] = new String[]{Long.toString(currentSearchIndex + i), Long.toString(value)};
            }
            dataTable = new JTable(newData, addressTitle);
            scrollPaneWithTable = new JScrollPane(dataTable);
            showArea.add(scrollPaneWithTable, BorderLayout.CENTER);
        } else {
            for (int i = 0; i < SEARCH_AMOUNT; i++) {
                long value = new MemoryLocation(memory, currentSearchIndex + i * 4).getData();
                newData[i] = new String[]{Long.toString(currentSearchIndex + i * 4), Long.toString(value)};
            }
            dataTable = new JTable(newData, wordTitle);
            scrollPaneWithTable = new JScrollPane(dataTable);
            showArea.add(scrollPaneWithTable, BorderLayout.CENTER);
        }
        SwingUtilities.updateComponentTreeUI(showArea);
    }

    public void setCurrentSearchIndex(int currentSearchIndex) {
        this.currentSearchIndex = currentSearchIndex;
    }

    public JTextField getSearchIndexTextField() {
        return searchIndexTextField;
    }

    public JButton getMemSearchButton() {
        return memSearchButton;
    }

    public JButton getWordSearchButton() {
        return wordSearchButton;
    }

    public void setDisplayMode(int displayMode) {
        this.displayMode = displayMode;
    }


}
