package com.chouguting.mipsemulator.software;

import com.chouguting.mipsemulator.software.opr.Operand;

public class ITypeInstruction extends Instruction {
    public static int ADDIop = 0b001000;
    public static int LWop = 0b100011;
    public static int SWop = 0b101011;
    public static int ANDIop = 0b001100;
    public static int ORIop = 0b001101;
    public static int BEQop = 0b000100;
    public static int BNEop = 0b000101;
    public static int SLTIop = 0b001010;

    private Operand rsOperand;  //rs
    private Operand rtOperand;  //rt 通常是目標
    private Operand immOperand; //address or immediate

    public ITypeInstruction(int locationInProgram, int operation, Operand oprRS, Operand oprRT, Operand immField) {
        super(locationInProgram);
        this.opCode = operation;
        this.rsOperand = oprRS;
        this.rtOperand = oprRT;
        this.immOperand = immField;
    }

    public Operand getRsOperand() {
        return rsOperand;
    }

    public Operand getRtOperand() {
        return rtOperand;
    }

    public Operand getImmOperand() {
        return immOperand;
    }
}
