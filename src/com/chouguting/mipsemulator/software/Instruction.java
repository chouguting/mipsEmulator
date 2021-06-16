package com.chouguting.mipsemulator.software;

public class Instruction {
    private long locationInProgram;
    protected int opCode;
    protected String instructionString;

    public Instruction(long locationInProgram) {
        this.locationInProgram = locationInProgram;
    }

    public long getLocationInProgram() {
        return locationInProgram;
    }

    public int getOpCode() {
        return opCode;
    }

    public String getInstructionString() {
        return instructionString;
    }
}
