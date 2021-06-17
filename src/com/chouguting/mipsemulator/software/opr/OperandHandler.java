package com.chouguting.mipsemulator.software.opr;

import com.chouguting.mipsemulator.exception.InstructionErrorException;
import com.chouguting.mipsemulator.hardware.Mips;
import com.chouguting.mipsemulator.hardware.Register;

import java.util.Arrays;

public class OperandHandler {
    public static Register stringToRegister(Mips source, String inputStr) throws InstructionErrorException {
        if (inputStr.startsWith("$")) inputStr = inputStr.substring(1);
        inputStr = inputStr.toLowerCase();
        if (!Arrays.asList(Register.registerList).contains(inputStr)) {
            throw new InstructionErrorException();
        } else {
            return source.getRegister(Arrays.asList(Register.registerList).indexOf(inputStr));
        }
    }
}
