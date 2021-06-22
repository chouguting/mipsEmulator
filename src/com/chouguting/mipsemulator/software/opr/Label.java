package com.chouguting.mipsemulator.software.opr;

import com.chouguting.mipsemulator.exception.InstructionErrorException;
import com.chouguting.mipsemulator.software.Instruction;

import java.util.ArrayList;

public class Label extends Instruction implements Operand {

    private long location; //記錄自己在第幾行
    String name;
    private int nextExecutionIndex; //紀錄這個LABEL的下一行指令是指令庫中的第幾個

    //跑到這個CONSTRUCTOR代表這個LABEL是真正的標籤
    public Label(long locationInProgram, ArrayList<Label> labelList, String name, int nextExecutionIndex,ArrayList<String> ackLabelList) throws InstructionErrorException {
        super(locationInProgram);
        location = locationInProgram;
        boolean notFound = true;
        for (Label label : labelList) {
            if (label.name.toLowerCase().equals(name)) {
                notFound = false;
            }
        }
        if (!notFound) throw new InstructionErrorException(); //label必須獨一無二
        this.name = name;
        labelList.add(this);
        this.nextExecutionIndex = nextExecutionIndex;
    }

    //跑到這個CONSTRUCTOR代表這個LABEL在指令中(是個OPERAND)
    public Label(ArrayList<Label> labelList, String name,ArrayList<String> ackLabelList) throws InstructionErrorException {
        super(0);
        boolean notFound = true;
        for (Label label : labelList) {
            if (label.name.toLowerCase().equals(name)) {
                notFound = false;
                location = label.getNextExecutionIndex();
            }
        }
        if (notFound) throw new InstructionErrorException(this.location); //operand中的label必須是存在的
        this.name = name;
    }

    public int getNextExecutionIndex() {
        return nextExecutionIndex;
    }

    @Override
    public long getData() {
        return location;
    }

    @Override
    public void setData(long inputData) {
        location = inputData;
    }
}
