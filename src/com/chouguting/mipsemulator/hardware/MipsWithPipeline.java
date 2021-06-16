package com.chouguting.mipsemulator.hardware;

import com.chouguting.mipsemulator.exception.InstructionErrorException;

/*
 *有pipeline功能的Mips
 */
public class MipsWithPipeline extends Mips {

    PipeliningController pipeliningController;

    public MipsWithPipeline(String inputProgram) throws InstructionErrorException {
        super(inputProgram);
        pipeliningController = new PipeliningController(inputProgram);
    }

    public PipeliningController getPipeliningController() {
        return pipeliningController;
    }

    @Override
    public void step() {
        super.step();
        pipeliningController.stepNext();
    }
}
