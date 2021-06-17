package com.chouguting.mipsemulator.hardware;

import com.chouguting.mipsemulator.software.ITypeInstruction;
import com.chouguting.mipsemulator.software.Instruction;
import com.chouguting.mipsemulator.software.RTypeInstruction;

public class HazardDetectionUnit {
    public boolean hasDataHazard(Instruction idStage, Instruction exStage) {
        if (idStage == null || exStage == null) return false;
        if (exStage.getClass() != ITypeInstruction.class) return false;
        if (((ITypeInstruction) exStage).getOpCode() != ITypeInstruction.LWop) return false;
        if (idStage.getClass() == RTypeInstruction.class) {
            if (((ITypeInstruction) exStage).getRtOperand() == ((RTypeInstruction) idStage).getRsOperand() ||
                    ((ITypeInstruction) exStage).getRtOperand() == ((RTypeInstruction) idStage).getRtOperand()) {
                return true;
            }
        } else if (idStage.getClass() == ITypeInstruction.class) {
            if (((ITypeInstruction) exStage).getRtOperand() == ((ITypeInstruction) idStage).getRsOperand()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasControlHazard(Instruction ifStage) {
        if (ifStage == null) return false;
        if (ifStage.getClass() != ITypeInstruction.class) return false;
        if(ifStage.getOpCode()==ITypeInstruction.BEQop||ifStage.getOpCode()==ITypeInstruction.BNEop){
            return true;
        }
        return false;
    }
}
