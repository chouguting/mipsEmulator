package com.chouguting.mipsemulator.software.opr;

import com.chouguting.mipsemulator.hardware.Memory;
import com.chouguting.mipsemulator.hardware.Register;

public class MemoryLocation implements Operand {
    Memory memory;  //記憶體來源
    Register baseRegister;  //存取Memory時的基底
    boolean hasBase;  //存取Memory時有沒有基底
    private int locationOffset;  //存取Memory時Offset

    public MemoryLocation(Memory memory, int offset) {
        hasBase = false;
        this.memory = memory;
        this.locationOffset = offset;
    }

    public MemoryLocation(Memory memory, Register baseRegister, int offset) {
        hasBase = true;
        this.memory = memory;
        this.baseRegister = baseRegister;
        this.locationOffset = offset;
    }

    @Override
    public int getData() {
        if (hasBase) {
            return memory.getData(locationOffset + baseRegister.getData());
        } else {
            return memory.getData(locationOffset);
        }

    }

    @Override
    public void setData(int inputData) {
        memory.setData(locationOffset, inputData);
        if (hasBase) {
            memory.setData(baseRegister.getData() + locationOffset, inputData);
        } else {
            memory.setData(locationOffset, inputData);
        }
    }
}
