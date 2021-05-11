package com.chouguting.mipsemulator.software;

import java.util.ArrayList;

public class InstructionHandler {
    public static ArrayList<Instruction> stringToInstructions(String allText) {
        ArrayList<Instruction> resultList = new ArrayList<Instruction>();
        String[] lines = allText.split("\\n");
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].isBlank()) continue;
            resultList.add(new Instruction(i));
        }
        return resultList;
    }


}
