package com.chouguting.mipsemulator.execution;

import com.chouguting.mipsemulator.exception.InstructionErrorException;
import com.chouguting.mipsemulator.hardware.Mips;
import com.chouguting.mipsemulator.software.Instruction;
import com.chouguting.mipsemulator.software.InstructionHandler;
import com.chouguting.mipsemulator.software.opr.Label;

import java.util.ArrayList;

/**
 * Program:也就是用戶執行的程式整體
 */
public class Program {
    ArrayList<Label> labelList;
    Mips source;
    private ArrayList<Instruction> myInstructions;
    private int currentInstruction;
    private InstructionExecutor instructionExecutor;
    private boolean Ended;

    public Program(Mips source) {
        this.source = source;
        labelList = new ArrayList<>();
        currentInstruction = 0;
        instructionExecutor = new InstructionExecutor(source);
    }

    //載入程式
    public void loadProgram(String allInstructionText) throws InstructionErrorException {
        myInstructions = InstructionHandler.stringToInstructions(source, allInstructionText);
        currentInstruction = 0;
        if (currentInstruction + 1 >= myInstructions.size()) {
            Ended = true;
        } else {
            Ended = false;
        }
    }

    //跑下一行
    public void step() {
        if (instructionExecutor.executeInstruction(currentInstruction, myInstructions.get(currentInstruction)) >= myInstructions.size()) {
            Ended = true;
            return;
        }
        currentInstruction = instructionExecutor.executeInstruction(currentInstruction, myInstructions.get(currentInstruction));
    }

    public ArrayList<Label> getLabelList() {
        return labelList;
    }

    //取得現在要跑的程式在原先檔案的哪一行
    public int getCurrentInstructionLocation() {
        if (myInstructions.size() == 0) return 0;
        return myInstructions.get(currentInstruction).getLocationInProgram();
    }

    //程式是否已經結束
    public boolean isEnded() {
        return Ended;
    }
}
