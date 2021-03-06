package com.chouguting.mipsemulator.hardware;

import com.chouguting.mipsemulator.exception.InfiniteLoopException;
import com.chouguting.mipsemulator.exception.InstructionErrorException;
import com.chouguting.mipsemulator.execution.Program;

import javax.swing.*;

/**
 * 模擬一個MIPS架構
 */
public class Mips {
    private final Memory memory = new Memory();
    private final Register[] registers = new Register[Register.REGISTER_COUNT];
    private Program program;


    public Mips(String inputProgram) throws InstructionErrorException {
        program = new Program(this);
        for (int i = 0; i < Register.REGISTER_COUNT; i++) {
            registers[i] = new Register(0);
        }
        program.loadProgram(inputProgram);
    }

    public void step() {
        program.step();
    }

    public void run() throws InfiniteLoopException{
        int counter=0;
        while(!program.isEnded()){
            if(counter>10000){
                throw new InfiniteLoopException();
            }
            program.step();
            counter++;
        }

    }

    public Program getProgram() {
        return program;
    }

    public Register[] getRegisters() {
        return registers;
    }

    public Register getRegister(int index) {
        return registers[index];
    }

    public Memory getMemory() {
        return memory;
    }
}
