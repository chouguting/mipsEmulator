package com.chouguting.mipsemulator;

import com.chouguting.mipsemulator.ui.MainFrame;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;


/**
 * 主程式的進入點
 */
public class EmulatorRunner {
    public static final String versionCode="v0.6";
    public static void main(String[] args) {
        new MainFrame();
    }
}
