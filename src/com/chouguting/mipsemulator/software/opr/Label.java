package com.chouguting.mipsemulator.software.opr;

public class Label implements Operand {
    private int location;

    @Override
    public int getData() {
        return location;
    }

    @Override
    public void setData(int inputData) {
        location = inputData;
    }
}
