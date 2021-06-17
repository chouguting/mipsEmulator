package com.chouguting.mipsemulator.ui;

import com.chouguting.mipsemulator.EmulatorRunner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class HDSettingDialog extends JDialog implements ActionListener {
    JButton checkUpdateButton=new JButton("檢查更新");
    int updateStatus=UpdateChecker.UNCHECKED;
    JButton closeButton=new JButton("close");
    String[] resolutionList={"1280*720"};
    String newVersionUrl;
    public HDSettingDialog() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(500,400);
        this.setTitle("Settings");

        JPanel verticalSettingGridPanel=new JPanel(new GridLayout(10,1));
        verticalSettingGridPanel.add(new JLabel(""));
        verticalSettingGridPanel.add(new JLabel(""));


        JPanel updateHorizontalPanel=new JPanel(new BorderLayout());
        JLabel versionLabel=new JLabel("   MIPS emulator version:"+ EmulatorRunner.versionCode,JLabel.LEFT);
        updateHorizontalPanel.add(versionLabel,BorderLayout.CENTER);
        versionLabel.setFont(new Font("Dialog",Font.BOLD,20));
        checkUpdateButton.setFocusable(false);
        checkUpdateButton.addActionListener(this);
        updateHorizontalPanel.add(checkUpdateButton,BorderLayout.EAST);
        verticalSettingGridPanel.add(updateHorizontalPanel);

        verticalSettingGridPanel.add(new JLabel(""));
        JPanel resolutionHorizontalPanel=new JPanel(new BorderLayout());
        JLabel resolutionLabel=new JLabel("   screen resolution:");
        resolutionLabel.setFont(new Font("Dialog",Font.BOLD,20));
        JComboBox<String> resolutionChooser=new JComboBox<String>(resolutionList);
        resolutionChooser.setFont(new Font("Dialog",Font.BOLD,20));
        resolutionHorizontalPanel.add(resolutionLabel,BorderLayout.CENTER);
        resolutionHorizontalPanel.add(resolutionChooser,BorderLayout.EAST);
        verticalSettingGridPanel.add(resolutionHorizontalPanel);

        verticalSettingGridPanel.add(new JLabel(""));
        JPanel closeButtonPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
        closeButtonPanel.add(closeButton);
        closeButton.setFocusable(false);
        closeButton.addActionListener(this);
        verticalSettingGridPanel.add(closeButtonPanel);


        this.add(verticalSettingGridPanel,BorderLayout.CENTER);
        JPanel oneByTwoGridPanel=new JPanel(new GridLayout(2,1));
        JLabel nameLabel=new JLabel("by 周固廷,林雅芸,陳昱澤",JLabel.CENTER);
        nameLabel.setFont(new Font("Dialog",Font.BOLD,13));
        oneByTwoGridPanel.add(nameLabel);
        JLabel schoolLabel=new JLabel("2021 國立臺灣海洋大學 資訊工程學系",JLabel.CENTER);
        schoolLabel.setFont(new Font("Dialog",Font.BOLD,13));
        oneByTwoGridPanel.add(schoolLabel);
        this.add(oneByTwoGridPanel,BorderLayout.SOUTH);
        this.setLocationRelativeTo(null);  //設定出現在畫面正中央
        this.setAlwaysOnTop(true);
        this.setModal(true);
        this.setResizable(false);
        this.setVisible(true);
    }

    public JButton getCheckUpdateButton() {
        return checkUpdateButton;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public void setNewVersionUrl(String newVersionUrl) {
        this.newVersionUrl = newVersionUrl;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==checkUpdateButton){
            if(updateStatus==UpdateChecker.UNCHECKED){
                checkUpdateButton.setText("搜尋更新中...");
                updateStatus=UpdateChecker.CHECKING;
                new ManualUpdateChecker(this).execute();
            }
            if(updateStatus==UpdateChecker.HAS_UPDATE){
                URI uri = null;
                try {
                    uri = new URI(newVersionUrl);
                    Desktop dt = Desktop.getDesktop();
                    dt.browse(uri);
                } catch (URISyntaxException | MalformedURLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if(updateStatus==UpdateChecker.NO_UPDATE){
                JOptionPane.showMessageDialog(this,"沒有更新了喔~~~");
            }

        }
        if(e.getSource()==closeButton){
            this.dispose();
        }
    }
}
