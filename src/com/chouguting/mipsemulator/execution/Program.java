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
    private long currentInstruction;
    private InstructionExecutor instructionExecutor;

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
    }

    //跑下一行
    public void step() {
        currentInstruction = instructionExecutor.executeInstruction(currentInstruction, myInstructions.get((int) currentInstruction));
    }

    public ArrayList<Label> getLabelList() {
        return labelList;
    }

    //取得現在要跑的程式在原先檔案的哪一行
    public long getCurrentInstructionLocation() {
        if (myInstructions.size() == 0) return 0;
        if (isEnded())
            return myInstructions.get(myInstructions.size() - 1).getLocationInProgram() + 1; //如果程式已經結束 就跑到所有程式碼後的下一行
        return myInstructions.get((int) currentInstruction).getLocationInProgram();
    }

    //程式是否已經結束
    public boolean isEnded() {
        if (currentInstruction >= myInstructions.size()) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Instruction> getInstructionsList() {
        return myInstructions;
    }

    public Instruction getCurrentInstruction() {
        return myInstructions.get((int) currentInstruction);
    }

}
