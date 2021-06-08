package com.chouguting.mipsemulator.software.opr;

import com.chouguting.mipsemulator.hardware.Memory;
import com.chouguting.mipsemulator.hardware.Register;

public class MemoryLocation implements Operand {
    Memory memory;  //記憶體來源
    Register baseRegister;  //存取Memory時的基底
    boolean hasBase;  //存取Memory時有沒有基底
    private long locationOffset;  //存取Memory時Offset

    public MemoryLocation(Memory memory, long offset) {
        hasBase = false;
        this.memory = memory;
        this.locationOffset = offset;
    }

    public MemoryLocation(Memory memory, Register baseRegister, long offset) {
        hasBase = true;
        this.memory = memory;
        this.baseRegister = baseRegister;
        this.locationOffset = offset;
    }

    /*
     * Mips是Big Endian
     * */
    @Override
    public long getData() {
        if (hasBase) {
            long total = memory.getData(locationOffset + baseRegister.getData()) * 16777216L;
            total = total + memory.getData(locationOffset + baseRegister.getData() + 1) * 65536L;
            total = total + memory.getData(locationOffset + baseRegister.getData() + 2) * 256;
            total = total + memory.getData(locationOffset + baseRegister.getData() + 3) * 1;
            return total;
        } else {
            long total = memory.getData(locationOffset) * 16777216L;
            total = total + memory.getData(locationOffset + 1) * 65536L;
            total = total + memory.getData(locationOffset + 2) * 256;
            total = total + memory.getData(locationOffset + 3) * 1;
            return total;
        }

    }

    @Override
    public void setData(long inputData) {
        memory.setData(locationOffset, inputData);
        if (hasBase) {
            for (int i = 3; i >= 0; i--) {
                long remainder = inputData % 256;
                memory.setData(baseRegister.getData() + locationOffset + i, remainder);
                inputData /= 256;
            }
        } else {
            memory.setData(locationOffset, inputData);
        }
    }
}
