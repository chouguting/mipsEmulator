package com.chouguting.mipsemulator.software.opr;

public class Immediate implements Operand {
    private int immediateData;

    public Immediate(int immediateData) {
        this.immediateData = immediateData;
    }

    @Override
    public int getData() {
        return immediateData;
    }

    @Override
    public void setData(int inputData) {
        immediateData = inputData;
    }
}
