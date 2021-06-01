package com.chouguting.mipsemulator.software.opr;

import com.chouguting.mipsemulator.exception.InstructionErrorException;
import com.chouguting.mipsemulator.software.Instruction;

import java.util.ArrayList;

public class Label extends Instruction implements Operand {

    private int location;
    String name;

    //跑到這個CONSTRUCTOR代表這個LABEL是真正的標籤
    public Label(int locationInProgram, ArrayList<Label> labelList, String name) throws InstructionErrorException {
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
    }

    //跑到這個CONSTRUCTOR代表這個LABEL在指令中(是個OPERAND)
    public Label(ArrayList<Label> labelList, String name) throws InstructionErrorException {
        super(0);
        boolean notFound = true;
        for (Label label : labelList) {
            if (label.name.toLowerCase().equals(name)) {
                notFound = false;
                location = label.getLocationInProgram();
            }
        }
        if (notFound) throw new InstructionErrorException(); //operand中的label必須是存在的
        this.name = name;
    }

    @Override
    public int getData() {
        return location;
    }

    @Override
    public void setData(int inputData) {
        location = inputData;
    }
}
