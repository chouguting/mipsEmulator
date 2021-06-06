package com.chouguting.mipsemulator.execution;

import com.chouguting.mipsemulator.hardware.Mips;
import com.chouguting.mipsemulator.software.ITypeInstruction;
import com.chouguting.mipsemulator.software.Instruction;
import com.chouguting.mipsemulator.software.JTypeInstruction;
import com.chouguting.mipsemulator.software.RTypeInstruction;

/**
 * 負責處理程式的實際執行結果
 */
public class InstructionExecutor {
    Mips source;

    public InstructionExecutor(Mips source) {
        this.source = source;
    }

    int getNextExecutionPoint(int currentIndex, Instruction instruction){
        int nextIndex = currentIndex + 1;
        if (instruction.getClass() == JTypeInstruction.class) {
            JTypeInstruction jTypeInstruction = (JTypeInstruction) instruction;
            nextIndex = jTypeInstruction.getLabel().getData();
        }
        return nextIndex;
    }

    //執行指令
    //會回傳下一步要跑指令庫中的第幾個
    int executeInstruction(int currentIndex, Instruction instruction) {
        int nextIndex = currentIndex + 1;
        if (instruction.getClass() == JTypeInstruction.class) {
            JTypeInstruction jTypeInstruction = (JTypeInstruction) instruction;
            nextIndex = jTypeInstruction.getLabel().getData();
        }else if(instruction.getClass()== RTypeInstruction.class){
            rTypeFetcher((RTypeInstruction) instruction);
        }else if(instruction.getClass()== ITypeInstruction.class){
            iTypeFetcher((ITypeInstruction) instruction);
        }
        return nextIndex;
    }

    //專門處理 Rtype指令
    void rTypeFetcher(RTypeInstruction instruction){
        if(instruction.getFuncCode()==RTypeInstruction.ADDfunc){
            instruction.getRdOperand().setData(instruction.getRsOperand().getData()+instruction.getRtOperand().getData());
            return;
        }

        if(instruction.getFuncCode()==RTypeInstruction.SUBfunc){
            instruction.getRdOperand().setData(instruction.getRsOperand().getData()-instruction.getRtOperand().getData());
            return;
        }

        if(instruction.getFuncCode()==RTypeInstruction.ANDfunc){
            instruction.getRdOperand().setData(instruction.getRsOperand().getData()&instruction.getRtOperand().getData());
            return;
        }

        if(instruction.getFuncCode()==RTypeInstruction.ORfunc){
            instruction.getRdOperand().setData(instruction.getRsOperand().getData()|instruction.getRtOperand().getData());
            return;
        }

        if(instruction.getFuncCode()==RTypeInstruction.NORfunc){
            instruction.getRdOperand().setData(-1*((instruction.getRsOperand().getData()|instruction.getRtOperand().getData())+1));
            return;
        }

        if(instruction.getFuncCode()==RTypeInstruction.SLLfunc){
            instruction.getRdOperand().setData(instruction.getRtOperand().getData()*(int)Math.pow(2,instruction.getShamt()));
            return;
        }

        if(instruction.getFuncCode()==RTypeInstruction.SRLfunc){
            instruction.getRdOperand().setData((int)(instruction.getRtOperand().getData()*Math.pow(2,-1*instruction.getShamt())));
            return;
        }

        if(instruction.getFuncCode()==RTypeInstruction.SLTfunc){
            if(instruction.getRsOperand().getData()<instruction.getRtOperand().getData()){
                instruction.getRdOperand().setData(1);
            }else{
                instruction.getRdOperand().setData(0);
            }
            return;
        }
    }


    //專門處理 Itype指令
    void iTypeFetcher(ITypeInstruction instruction){
        if(instruction.getOpCode()==ITypeInstruction.ADDIop){
            int rsData=instruction.getRsOperand().getData();
            int immediate=instruction.getImmOperand().getData();
            int rtData=instruction.getRtOperand().getData();
            instruction.getRtOperand().setData(rsData+immediate);
        }
    }


}
