package com.chouguting.mipsemulator.hardware;

import com.chouguting.mipsemulator.exception.InstructionErrorException;
import com.chouguting.mipsemulator.software.Instruction;

/**
 * 處理Pipeline的內容及控制
 */
public class PipeliningController {

    public static int IF_STAGE = 0;
    public static int ID_STAGE = 1;
    public static int EX_STAGE = 2;
    public static int MEM_STAGE = 3;
    public static int WB_STAGE = 4;
    Mips parallelRunner;
    private ForwardingUnit forwardingUnit = new ForwardingUnit();
    Instruction ifStage = null;
    Instruction idStage = null;
    Instruction exStage = null;
    Instruction memStage = null;
    Instruction wbStage = null;



    public PipeliningController(String programString) {
        try {
            parallelRunner = new Mips(programString);
        } catch (InstructionErrorException e) {

        }
        for (int i = 0; i < 3; i++) {
            if (parallelRunner.getProgram().isEnded()) {
                pushInstruction(null);
            } else {
                pushInstruction(parallelRunner.getProgram().getCurrentInstruction());
                parallelRunner.getProgram().step();
            }

        }
    }

    //把下一個指令推入流水線
    public void stepNext() {
        if (parallelRunner.getProgram().isEnded()) {
            pushInstruction(null);
        } else {
            pushInstruction(parallelRunner.getProgram().getCurrentInstruction());
            parallelRunner.getProgram().step();
        }
    }

    public void pushInstruction(Instruction instruction) {
        wbStage = memStage;
        memStage = exStage;
        exStage = idStage;
        idStage = ifStage;
        ifStage = instruction;
    }

    public void pushToExecution(Instruction instruction, int currentLocation) {
        wbStage = memStage;
        memStage = exStage;
        exStage = instruction;
    }

    public void pushToFetch(Instruction instruction, int currentLocation) {
        wbStage = memStage;
        memStage = exStage;
        exStage = instruction;
    }

    //取得現在正在五階流水線的指令字串
    public String[] getCurrentStagingInstructions() {
        String[] str = new String[5];
        if (ifStage == null) {
            str[IF_STAGE] = "null";
        } else {
            str[IF_STAGE] = ifStage.getInstructionString();
        }

        if (idStage == null) {
            str[ID_STAGE] = "null";
        } else {
            str[ID_STAGE] = idStage.getInstructionString();
        }

        if (exStage == null) {
            str[EX_STAGE] = "null";
        } else {
            str[EX_STAGE] = exStage.getInstructionString();
        }

        if (memStage == null) {
            str[MEM_STAGE] = "null";
        } else {
            str[MEM_STAGE] = memStage.getInstructionString();
        }

        if (wbStage == null) {
            str[WB_STAGE] = "null";
        } else {
            str[WB_STAGE] = wbStage.getInstructionString();
        }
        return str;
    }

    //取得現在正在五階流水線的指令字串
    public Instruction[] getCurrentInstructions() {
        Instruction[] instructions = new Instruction[5];
        if (ifStage == null) {
            instructions[IF_STAGE] = null;
        } else {
            instructions[IF_STAGE] = ifStage;
        }

        if (idStage == null) {
            instructions[ID_STAGE] = null;
        } else {
            instructions[ID_STAGE] = idStage;
        }

        if (exStage == null) {
            instructions[EX_STAGE] = null;
        } else {
            instructions[EX_STAGE] = exStage;
        }

        if (memStage == null) {
            instructions[MEM_STAGE] = null;
        } else {
            instructions[MEM_STAGE] = memStage;
        }

        if (wbStage == null) {
            instructions[WB_STAGE] = null;
        } else {
            instructions[WB_STAGE] = wbStage;
        }
        return instructions;
    }

    //HazardDecection

    //判斷需不需要前饋
    public boolean exMemForwarding() {
        return forwardingUnit.exMemForwarding(exStage, memStage);
    }

    //判斷需不需要前饋
    public boolean memWbForwarding() {
        return forwardingUnit.memWbForwarding(exStage, memStage, wbStage);
    }


}
