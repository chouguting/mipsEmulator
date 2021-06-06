package com.chouguting.mipsemulator.software;

import com.chouguting.mipsemulator.software.opr.Operand;

public class RTypeInstruction extends Instruction {

    public static int R_TYPEop = 0b000000;
    public static int ADDop = 0b000000;
    public static int SUBop = 0b000000;
    public static int ANDop = 0b000000;
    public static int ORop = 0b000000;
    public static int NORop = 0b000000;
    public static int SLTop = 0b000000;
    public static int SLLop = 0b000000;
    public static int SRLop = 0b000000;


    public static int ADDfunc = 0b100000;
    public static int SUBfunc = 0b100010;
    public static int ANDfunc = 0b100100;
    public static int ORfunc = 0b100101;
    public static int NORfunc = 0b100111;
    public static int SLTfunc = 0b101010;
    public static int SLLfunc = 0b000010;
    public static int SRLfunc = 0b000110;


    private Operand rsOperand;  //rs
    private Operand rtOperand;  //rs
    private Operand rdOperand;  //rd 通常是運算結果存的地方
    private int shamt;          //shift amount 給 sll , srl用的
    private int funcCode;       //function code

    public RTypeInstruction(int locationInProgram, int operation, Operand oprRS, Operand oprRT, Operand oprRD, int funcCode) {
        super(locationInProgram);
        this.opCode = operation;
        this.rsOperand = oprRS;
        this.rtOperand = oprRT;
        this.rdOperand = oprRD;
        this.funcCode = funcCode;
    }

    public RTypeInstruction(int locationInProgram, int operation, Operand oprRT, Operand oprRD, int shamt, int funcCode) {
        super(locationInProgram);
        this.opCode = operation;
        this.shamt = shamt;
        this.funcCode = funcCode;
        this.rdOperand = oprRD;
        this.rtOperand = oprRT;
    }

    public int getFuncCode() {
        return funcCode;
    }

    public Operand getRsOperand() {
        return rsOperand;
    }

    public Operand getRtOperand() {
        return rtOperand;
    }

    public Operand getRdOperand() {
        return rdOperand;
    }

    public int getShamt() {
        return shamt;
    }
}
