package com.chouguting.mipsemulator.software;

import com.chouguting.mipsemulator.software.opr.Operand;

public class RTypeInstruction extends Instruction {

    public static int ADDop = 0b000000;
    public static int SUBop = 0b000000;
    public static int ANDop = 0b000000;
    public static int ORop = 0b000000;
    public static int SLTop = 0b000000;

    public static int ADDfunc = 0b100000;
    public static int SUBfunc = 0b100010;
    public static int ANDfunc = 0b100100;
    public static int ORfunc = 0b100101;
    public static int SLTfunc = 0b101010;


    private Operand rsOperand;  //rs
    private Operand rtOperand;  //rs
    private Operand rdOperand;  //rd
    private int shamt;          //shift amount
    private int funcCode;       //function code

    public RTypeInstruction(int locationInProgram, int operation, Operand oprRS, Operand oprRT, Operand oprRD, int funcCode) {
        super(locationInProgram);

    }

}
