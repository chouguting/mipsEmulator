package com.chouguting.mipsemulator.hardware;

import com.chouguting.mipsemulator.software.opr.Operand;

public class Register implements Operand {
    public static final String[] registerList = {
            "zero", "at", "v0", "v1", "a0", "a1", "a2", "a3",
            "t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7",
            "s0", "s1", "s2", "s3", "s4", "s5", "s6", "s7",
            "t8", "t9", "k0", "k1", "gp", "sp", "fp", "ra"
    };
    public static int ZERO = 0;
    public static int AT = 1;
    public static int v0 = 2;
    public static int v1 = 3;
    public static int A0 = 4;
    public static int A1 = 5;
    public static int A2 = 6;
    public static int A3 = 7;
    public static int T0 = 8;
    public static int T1 = 9;
    public static int T2 = 10;
    public static int T3 = 11;
    public static int T4 = 12;
    public static int T5 = 13;
    public static int T6 = 14;
    public static int T7 = 15;
    public static int S0 = 16;
    public static int S1 = 17;
    public static int S2 = 18;
    public static int S3 = 19;
    public static int S4 = 20;
    public static int S5 = 21;
    public static int S6 = 22;
    public static int S7 = 23;
    public static int T8 = 24;
    public static int T9 = 25;
    public static int K0 = 26;
    public static int K1 = 27;
    public static int GP = 28;
    public static int SP = 29;
    public static int FP = 30;
    public static int RA = 31;

    private int storedValue;

    @Override
    public int getData() {
        return 0;
    }

    @Override
    public void setData(int inputData) {

    }
}
