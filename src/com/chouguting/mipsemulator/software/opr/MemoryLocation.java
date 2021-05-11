package com.chouguting.mipsemulator.software.opr;

public class MemoryLocation implements Operand {
    private int locationIndex;

    @Override
    public int getData() {
        return locationIndex;
    }

    @Override
    public void setData(int inputData) {
        locationIndex = inputData;
    }
}
