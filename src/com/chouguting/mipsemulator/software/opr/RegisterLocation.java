package com.chouguting.mipsemulator.software.opr;

import com.chouguting.mipsemulator.hardware.Register;

public class RegisterLocation implements Operand {
    Register theRegister;

    RegisterLocation(Register inputRegister) {
        theRegister = inputRegister;
    }

    @Override
    public long getData() {
        return 0;
    }

    @Override
    public void setData(long inputData) {

    }


}
