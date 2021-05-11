package com.chouguting.mipsemulator.execution;

import com.chouguting.mipsemulator.software.Instruction;
import com.chouguting.mipsemulator.software.InstructionHandler;

import java.util.ArrayList;

/**
 * Program:也就是用戶執行的程式整體
 */
public class Program {
    private ArrayList<Instruction> myInstructions;
    private int currentInstruction;
    private boolean Ended;

    public Program(String allInstructionText) {
        myInstructions = InstructionHandler.stringToInstructions(allInstructionText);
        currentInstruction = 0;
        if (currentInstruction + 1 >= myInstructions.size()) {
            Ended = true;
        } else {
            Ended = false;
        }
    }

    //跑下一行
    public void step() {
        if (currentInstruction + 1 >= myInstructions.size()) {
            Ended = true;
            return;
        }
        currentInstruction += 1;
    }

    //取得現在要跑的程式在原先檔案的哪一行
    public int getCurrentInstructionLocation() {
        return myInstructions.get(currentInstruction).getLocationInProgram();
    }

    //程式是否已經結束
    public boolean isEnded() {
        return Ended;
    }
}
