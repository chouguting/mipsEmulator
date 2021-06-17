package com.chouguting.mipsemulator.software;

import com.chouguting.mipsemulator.software.opr.Operand;

public class JTypeInstruction extends Instruction {
    Operand label; //J Type 只要管跳到哪
    public static int Jop = 0b000010;

    public JTypeInstruction(String instructionString, int locationInProgram, int opCode, Operand label) {
        super(locationInProgram);
        this.opCode = opCode;
        this.label = label;
        this.instructionString = instructionString;
    }

    public Operand getLabel() {
        return label;
    }
}
