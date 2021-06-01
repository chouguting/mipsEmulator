package com.chouguting.mipsemulator.hardware;

import com.chouguting.mipsemulator.exception.InstructionErrorException;
import com.chouguting.mipsemulator.execution.Program;

public class Mips {
    private final Memory memory = new Memory();
    private final Register[] registers = new Register[32];
    private Program program;


    public Mips(String inputProgram) throws InstructionErrorException {
        program = new Program(this);
        program.loadProgram(inputProgram);
    }

    public Program getProgram() {
        return program;
    }

    public Register getRegister(int index) {
        return registers[index];
    }
}
