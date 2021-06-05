package com.chouguting.mipsemulator.execution;

import com.chouguting.mipsemulator.hardware.Mips;
import com.chouguting.mipsemulator.software.Instruction;
import com.chouguting.mipsemulator.software.JTypeInstruction;

/**
 * 負責處理程式的實際執行結果
 */
public class InstructionExecutor {
    Mips source;

    public InstructionExecutor(Mips source) {
        this.source = source;
    }

    //執行指令
    //會回傳下一步要跑指令庫中的第幾個
    int executeInstruction(int currentIndex, Instruction instruction) {
        int nextIndex = currentIndex + 1;
        if (instruction.getClass() == JTypeInstruction.class) {
            JTypeInstruction jTypeInstruction = (JTypeInstruction) instruction;
            nextIndex = jTypeInstruction.getLabel().getData();
        }
        return nextIndex;
    }


}
