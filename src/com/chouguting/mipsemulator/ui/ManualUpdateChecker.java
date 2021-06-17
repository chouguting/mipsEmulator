package com.chouguting.mipsemulator.ui;

import com.chouguting.mipsemulator.EmulatorRunner;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class ManualUpdateChecker extends UpdateChecker{
    HDSettingDialog dialog;
    public ManualUpdateChecker(MainFrame mainFrame) {
        super(mainFrame);
    }

    public ManualUpdateChecker(HDSettingDialog dialog) {
        super(null);
        this.dialog=dialog;
    }


    @Override
    protected void done() {
        if(!foundNewVersion){
            dialog.getCheckUpdateButton().setText("已是最新版本");
            dialog.setUpdateStatus(UpdateChecker.NO_UPDATE);
            dialog.setNewVersionUrl(this.newVersionUrl);
            return;
        }
        dialog.getCheckUpdateButton().setText("有可用的更新");
        dialog.setUpdateStatus(UpdateChecker.HAS_UPDATE);
    }
}
