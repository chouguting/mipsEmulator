package com.chouguting.mipsemulator.exception;
/*
 *組譯沒過就會丟出這個例外 可指定錯誤的指令在哪一行
 */
public class InstructionErrorException extends Exception {
    boolean traceable;
    long errorLocation;

    public InstructionErrorException() {
        traceable = false;
    }

    public InstructionErrorException(long errorLocation) {
        traceable = true;
        this.errorLocation = errorLocation;
    }

    public boolean isTraceable() {
        return traceable;
    }

    public long getErrorLocation() {
        return errorLocation;
    }
}
