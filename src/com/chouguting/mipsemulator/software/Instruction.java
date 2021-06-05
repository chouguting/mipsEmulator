package com.chouguting.mipsemulator.software;

public class Instruction {
    private int locationInProgram;
    protected int opCode;

    public Instruction(int locationInProgram) {
        this.locationInProgram = locationInProgram;
    }

    public int getLocationInProgram() {
        return locationInProgram;
    }
}
