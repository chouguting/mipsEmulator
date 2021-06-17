package com.chouguting.mipsemulator.software.opr;

public class Immediate implements Operand {
    private long immediateData;

    public Immediate(long immediateData) {
        this.immediateData = immediateData;
    }

    @Override
    public long getData() {
        return immediateData;
    }

    @Override
    public void setData(long inputData) {
        immediateData = inputData;
    }
}
