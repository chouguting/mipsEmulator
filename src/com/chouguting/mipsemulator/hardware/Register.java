package com.chouguting.mipsemulator.hardware;

import com.chouguting.mipsemulator.software.opr.Operand;

/**
 * 暫存器類別
 */
public class Register implements Operand {
    public static final int REGISTER_COUNT = 32;
    public static final String[] registerList = {
            "zero", "at", "v0", "v1", "a0", "a1", "a2", "a3",
            "t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7",
            "s0", "s1", "s2", "s3", "s4", "s5", "s6", "s7",
            "t8", "t9", "k0", "k1", "gp", "sp", "fp", "ra"
    };
    public static final int ZERO = 0;
    public static final int AT = 1;
    public static final int v0 = 2;
    public static final int v1 = 3;
    public static final int A0 = 4;
    public static final int A1 = 5;
    public static final int A2 = 6;
    public static final int A3 = 7;
    public static final int T0 = 8;
    public static final int T1 = 9;
    public static final int T2 = 10;
    public static final int T3 = 11;
    public static final int T4 = 12;
    public static final int T5 = 13;
    public static final int T6 = 14;
    public static final int T7 = 15;
    public static final int S0 = 16;
    public static final int S1 = 17;
    public static final int S2 = 18;
    public static final int S3 = 19;
    public static final int S4 = 20;
    public static final int S5 = 21;
    public static final int S6 = 22;
    public static final int S7 = 23;
    public static final int T8 = 24;
    public static final int T9 = 25;
    public static final int K0 = 26;
    public static final int K1 = 27;
    public static final int GP = 28;
    public static final int SP = 29;
    public static final int FP = 30;
    public static final int RA = 31;

    private long storedValue;

    public Register(long storedValue) {
        this.storedValue = storedValue;
    }

    @Override
    public long getData() {
        return storedValue;
    }

    @Override
    public void setData(long inputData) {
        storedValue = inputData;
    }
}
