package com.chouguting.mipsemulator.software;

import com.chouguting.mipsemulator.software.opr.Operand;

public class JTypeInstruction extends Instruction {
    Operand label; //J Type 只要管跳到哪

    public JTypeInstruction(int locationInProgram, Operand label) {
        super(locationInProgram);
        this.label = label;
    }
}
