package com.chouguting.mipsemulator.hardware;

import com.chouguting.mipsemulator.exception.InstructionErrorException;
import com.chouguting.mipsemulator.software.ITypeInstruction;
import com.chouguting.mipsemulator.software.Instruction;
import com.chouguting.mipsemulator.software.RTypeInstruction;
import com.chouguting.mipsemulator.software.opr.Operand;

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

    //判斷需不需要前饋
    public boolean exMemForwarding() {
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
    public boolean memWbForwarding() {
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
