package com.chouguting.mipsemulator.software.opr;

public class Immediate implements Operand {
    private int immediateData;

    @Override
    public int getData() {
        return immediateData;
    }

    @Override
    public void setData(int inputData) {
        immediateData = inputData;
    }
}
