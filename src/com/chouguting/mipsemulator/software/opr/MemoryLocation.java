package com.chouguting.mipsemulator.software.opr;

import com.chouguting.mipsemulator.hardware.Register;

public class MemoryLocation implements Operand {
    private int locationIndex;

    public MemoryLocation(Register register, int offset) {
        this.locationIndex = locationIndex;
    }

    @Override
    public int getData() {
        return locationIndex;
    }

    @Override
    public void setData(int inputData) {
        locationIndex = inputData;
    }
}
