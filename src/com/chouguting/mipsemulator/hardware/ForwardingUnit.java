package com.chouguting.mipsemulator.hardware;

import com.chouguting.mipsemulator.software.ITypeInstruction;
import com.chouguting.mipsemulator.software.Instruction;
import com.chouguting.mipsemulator.software.RTypeInstruction;
import com.chouguting.mipsemulator.software.opr.Operand;

public class ForwardingUnit {
    public boolean exMemForwarding(Instruction exStage, Instruction memStage) {
        if (exStage == null || memStage == null) return false;
        if (memStage.getClass() == RTypeInstruction.class) {
            if (exStage.getClass() == RTypeInstruction.class) {
                if (((RTypeInstruction) memStage).getRdOperand() == ((RTypeInstruction) exStage).getRsOperand() ||
                        ((RTypeInstruction) memStage).getRdOperand() == ((RTypeInstruction) exStage).getRtOperand()) {
                    return true;
                }
            }
            if (exStage.getClass() == ITypeInstruction.class) {
                if (((RTypeInstruction) memStage).getRdOperand() == ((ITypeInstruction) exStage).getRsOperand()) {
                    return true;
                }
            }
        }
        if (memStage.getClass() == ITypeInstruction.class) {
            if (exStage.getClass() == RTypeInstruction.class) {
                if (((ITypeInstruction) memStage).getRtOperand() == ((RTypeInstruction) exStage).getRsOperand() ||
                        ((ITypeInstruction) memStage).getRtOperand() == ((RTypeInstruction) exStage).getRtOperand()) {
                    return true;
                }
            }
            if (exStage.getClass() == ITypeInstruction.class) {
                if (((ITypeInstruction) memStage).getRtOperand() == ((ITypeInstruction) exStage).getRsOperand()) {
                    return true;
                }
            }
        }
        return false;
    }

    //判斷需不需要前饋
    public boolean exWbForwarding(Instruction exStage, Instruction memStage, Instruction wbStage) {
        if (exStage == null || wbStage == null) return false;

        //捼果前面那一層已經前饋一樣的東西 這邊就不用了
        Operand memRdOperand = null;
        if (memStage.getClass() == RTypeInstruction.class) {
            memRdOperand = ((RTypeInstruction) memStage).getRdOperand();
        } else if (memStage.getClass() == ITypeInstruction.class) {
            memRdOperand = ((ITypeInstruction) memStage).getRtOperand();
        }
        if (wbStage.getClass() == RTypeInstruction.class) {
            if (((RTypeInstruction) wbStage).getRdOperand() == memRdOperand) return false;
            if (exStage.getClass() == RTypeInstruction.class) {
                if (((RTypeInstruction) wbStage).getRdOperand() == ((RTypeInstruction) exStage).getRsOperand() ||
                        ((RTypeInstruction) wbStage).getRdOperand() == ((RTypeInstruction) exStage).getRtOperand()) {
                    return true;
                }
            }
            if (exStage.getClass() == ITypeInstruction.class) {
                if (((RTypeInstruction) wbStage).getRdOperand() == ((ITypeInstruction) exStage).getRsOperand()) {
                    return true;
                }
            }
        }
        if (wbStage.getClass() == ITypeInstruction.class) {
            if (((ITypeInstruction) wbStage).getRtOperand() == memRdOperand) return false;
            if (exStage.getClass() == RTypeInstruction.class) {
                if (((ITypeInstruction) wbStage).getRtOperand() == ((RTypeInstruction) exStage).getRsOperand() ||
                        ((ITypeInstruction) wbStage).getRtOperand() == ((RTypeInstruction) exStage).getRtOperand()) {
                    return true;
                }
            }
            if (exStage.getClass() == ITypeInstruction.class) {
                if (((ITypeInstruction) wbStage).getRtOperand() == ((ITypeInstruction) exStage).getRsOperand()) {
                    return true;
                }
            }
        }
        return false;
    }
}
