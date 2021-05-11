package com.chouguting.mipsemulator.software;

import com.chouguting.mipsemulator.software.opr.Operand;

public class Instruction {
    private int locationInProgram;
    private int opCode;
    private Operand operand1;
    private Operand operand2;
    private Operand operand3;

    public Instruction(int locationInProgram) {
        this.locationInProgram = locationInProgram;
    }

    public int getLocationInProgram() {
        return locationInProgram;
    }
}
