package com.chouguting.mipsemulator.ui;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import com.chouguting.mipsemulator.EmulatorRunner;
import com.chouguting.mipsemulator.ui.MainFrame;
import org.json.*;

import javax.swing.*;

/**
 * 會檢查有沒有新版本
 */

public class UpdateChecker extends SwingWorker<Object,Object> {

    boolean foundNewVersion=false;
    String newVersionUrl;
    String versionInfo;
    String versionString;

    MainFrame mainFrame;
    public UpdateChecker(MainFrame mainFrame){
        this.mainFrame=mainFrame;
    }


    @Override
    protected Object doInBackground() throws Exception {
        System.out.println("check start");
        String command =
                "curl -H Accept: application/vnd.github.v3+json per_page: 1 https://api.github.com/repos/chouguting/mipsEmulator/releases";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("get finish");
        InputStream inputStream=process.getInputStream();
        try {
            String jsonString = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
            jsonString=jsonString.replaceAll("\\[","");
            jsonString=jsonString.replaceAll("\\]","");
            JSONObject obj = new JSONObject(jsonString);
            newVersionUrl = obj.getString("html_url");
            versionString=obj.getString("tag_name");
            versionInfo=obj.getString("body");
            System.out.println(newVersionUrl);
            System.out.println(versionString);
            System.out.println(versionInfo);
            System.out.println("check finish");
            double onlineVersion=Double.parseDouble(versionString.substring(1));
            double currentVersion=Double.parseDouble(EmulatorRunner.versionCode.substring(1));
            if(onlineVersion>currentVersion){
                foundNewVersion=true;
            }
        }catch (Exception e){

        }


        return null;
    }

    @Override
    protected void done() {
        super.done();
        if(!foundNewVersion)return;
        String messageString="";
        messageString+="發現一個新版本的 MIPS emulator\n";
        messageString+="您目前的版本:"+EmulatorRunner.versionCode+"\n";
        messageString+="新的版本:"+versionString+"\n";
        messageString+="新版本說明:"+versionInfo+"\n";

        int choice=JOptionPane.showConfirmDialog(mainFrame,
                messageString+"\n Would you like to install now? ",
                "New Version Found",
                JOptionPane.YES_NO_OPTION);
        if(choice==0){
            URI uri = null;
            try {
                uri = new URI(newVersionUrl);
                Desktop dt = Desktop.getDesktop();
                dt.browse(uri);
            } catch (URISyntaxException | MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        mainFrame.setTitle(mainFrame.getTitle()+"-有可用的新版本");
    }
}
