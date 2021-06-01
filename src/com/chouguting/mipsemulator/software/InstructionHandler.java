package com.chouguting.mipsemulator.software;

import com.chouguting.mipsemulator.exception.InstructionErrorException;
import com.chouguting.mipsemulator.hardware.Mips;
import com.chouguting.mipsemulator.software.opr.Label;
import com.chouguting.mipsemulator.software.opr.OperandHandler;

import java.util.ArrayList;

public class InstructionHandler {
    //把整段文字轉換成一個指令所組成的陣列
    public static ArrayList<Instruction> stringToInstructions(Mips source, String allText) throws InstructionErrorException {
        ArrayList<Instruction> resultList = new ArrayList<Instruction>();
        String[] lines = allText.split("\\n");
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].isBlank()) continue;
            resultList.add(singleLineToInstruction(source, lines[i], i));
        }
        return resultList;
    }

    //把單一行字串轉換成一條指令
    private static Instruction singleLineToInstruction(Mips source, String line, int location) throws InstructionErrorException {
        Instruction resultInstruction = new Instruction(location);
        line = line.toLowerCase().strip();

        if (line.endsWith(":")) {
            return new Label(location, source.getProgram().getLabelList(), line.replaceAll(":", "").toLowerCase());
        }

        String[] splittedLine = line.split("\\s+", 2); //用空白分開 指令和運算元
        String operation;
        String operandLine;
        try {
            operation = splittedLine[0];
            operandLine = splittedLine[1];
        } catch (Exception e) {
            throw new InstructionErrorException();
        }
        operation.replaceAll("\\s+", "").toLowerCase();

        //讀取R-Type指令
        if (operation.equals("add")) {
            try {
                String[] buff = operandLine.split(",", 3);
                String addTo = buff[0];
                addTo = addTo.replaceAll("\\s+", "").toLowerCase();
                String addFrom1 = buff[1];
                addFrom1 = addFrom1.replaceAll("\\s+", "").toLowerCase();
                String addFrom2 = buff[2];
                addFrom2 = addFrom2.replaceAll("\\s+", "").toLowerCase();
                return new RTypeInstruction(location, RTypeInstruction.ADDop, OperandHandler.stringToOperand(source, addFrom1),
                        OperandHandler.stringToOperand(source, addFrom2), OperandHandler.stringToOperand(source, addTo),
                        RTypeInstruction.ADDfunc);
            } catch (Exception e) {
                throw new InstructionErrorException();
            }
        }

        if (operation.equals("sub")) {
            try {
                String[] buff = operandLine.split(",", 3);
                String addTo = buff[0];
                addTo = addTo.replaceAll("\\s+", "").toLowerCase();
                String addFrom1 = buff[1];
                addFrom1 = addFrom1.replaceAll("\\s+", "").toLowerCase();
                String addFrom2 = buff[2];
                addFrom2 = addFrom2.replaceAll("\\s+", "").toLowerCase();
                return new RTypeInstruction(location, RTypeInstruction.SUBop, OperandHandler.stringToOperand(source, addFrom1),
                        OperandHandler.stringToOperand(source, addFrom2), OperandHandler.stringToOperand(source, addTo),
                        RTypeInstruction.SUBfunc);
            } catch (Exception e) {
                throw new InstructionErrorException();
            }
        }

        if (operation.equals("and")) {
            try {
                String[] buff = operandLine.split(",", 3);
                String addTo = buff[0];
                addTo = addTo.replaceAll("\\s+", "").toLowerCase();
                String addFrom1 = buff[1];
                addFrom1 = addFrom1.replaceAll("\\s+", "").toLowerCase();
                String addFrom2 = buff[2];
                addFrom2 = addFrom2.replaceAll("\\s+", "").toLowerCase();
                return new RTypeInstruction(location, RTypeInstruction.SUBop, OperandHandler.stringToOperand(source, addFrom1),
                        OperandHandler.stringToOperand(source, addFrom2), OperandHandler.stringToOperand(source, addTo),
                        RTypeInstruction.SUBfunc);
            } catch (Exception e) {
                throw new InstructionErrorException();
            }
        }


        if (operation.equals("or")) {
            try {
                String[] buff = operandLine.split(",", 3);
                String addTo = buff[0];
                addTo = addTo.replaceAll("\\s+", "").toLowerCase();
                String addFrom1 = buff[1];
                addFrom1 = addFrom1.replaceAll("\\s+", "").toLowerCase();
                String addFrom2 = buff[2];
                addFrom2 = addFrom2.replaceAll("\\s+", "").toLowerCase();
                return new RTypeInstruction(location, RTypeInstruction.SUBop, OperandHandler.stringToOperand(source, addFrom1),
                        OperandHandler.stringToOperand(source, addFrom2), OperandHandler.stringToOperand(source, addTo),
                        RTypeInstruction.SUBfunc);
            } catch (Exception e) {
                throw new InstructionErrorException();
            }
        }

        if (operation.equals("nor")) {
            try {
                String[] buff = operandLine.split(",", 3);
                String addTo = buff[0];
                addTo = addTo.replaceAll("\\s+", "").toLowerCase();
                String addFrom1 = buff[1];
                addFrom1 = addFrom1.replaceAll("\\s+", "").toLowerCase();
                String addFrom2 = buff[2];
                addFrom2 = addFrom2.replaceAll("\\s+", "").toLowerCase();
                return new RTypeInstruction(location, RTypeInstruction.SUBop, OperandHandler.stringToOperand(source, addFrom1),
                        OperandHandler.stringToOperand(source, addFrom2), OperandHandler.stringToOperand(source, addTo),
                        RTypeInstruction.SUBfunc);
            } catch (Exception e) {
                throw new InstructionErrorException();
            }
        }

        if (operation.equals("sll")) {
            try {
                String[] buff = operandLine.split(",", 3);
                String addTo = buff[0];
                addTo = addTo.replaceAll("\\s+", "").toLowerCase();
                String addFrom1 = buff[1];
                addFrom1 = addFrom1.replaceAll("\\s+", "").toLowerCase();
                String addFrom2 = buff[2];
                addFrom2 = addFrom2.replaceAll("\\s+", "").toLowerCase();
                return new RTypeInstruction(location, RTypeInstruction.SUBop, OperandHandler.stringToOperand(source, addFrom1),
                        OperandHandler.stringToOperand(source, addFrom2), OperandHandler.stringToOperand(source, addTo),
                        RTypeInstruction.SUBfunc);
            } catch (Exception e) {
                throw new InstructionErrorException();
            }
        }

        if (operation.equals("srl")) {
            try {
                String[] buff = operandLine.split(",", 3);
                String addTo = buff[0];
                addTo = addTo.replaceAll("\\s+", "").toLowerCase();
                String addFrom1 = buff[1];
                addFrom1 = addFrom1.replaceAll("\\s+", "").toLowerCase();
                String addFrom2 = buff[2];
                addFrom2 = addFrom2.replaceAll("\\s+", "").toLowerCase();
                return new RTypeInstruction(location, RTypeInstruction.SUBop, OperandHandler.stringToOperand(source, addFrom1),
                        OperandHandler.stringToOperand(source, addFrom2), OperandHandler.stringToOperand(source, addTo),
                        RTypeInstruction.SUBfunc);
            } catch (Exception e) {
                throw new InstructionErrorException();
            }
        }

        if (operation.equals("slt")) {
            try {
                String[] buff = operandLine.split(",", 3);
                String addTo = buff[0];
                addTo = addTo.replaceAll("\\s+", "").toLowerCase();
                String addFrom1 = buff[1];
                addFrom1 = addFrom1.replaceAll("\\s+", "").toLowerCase();
                String addFrom2 = buff[2];
                addFrom2 = addFrom2.replaceAll("\\s+", "").toLowerCase();
                return new RTypeInstruction(location, RTypeInstruction.SUBop, OperandHandler.stringToOperand(source, addFrom1),
                        OperandHandler.stringToOperand(source, addFrom2), OperandHandler.stringToOperand(source, addTo),
                        RTypeInstruction.SUBfunc);
            } catch (Exception e) {
                throw new InstructionErrorException();
            }
        }

        if (operation.equals("jr")) {
            try {
                String[] buff = operandLine.split(",", 3);
                String addTo = buff[0];
                addTo = addTo.replaceAll("\\s+", "").toLowerCase();
                String addFrom1 = buff[1];
                addFrom1 = addFrom1.replaceAll("\\s+", "").toLowerCase();
                String addFrom2 = buff[2];
                addFrom2 = addFrom2.replaceAll("\\s+", "").toLowerCase();
                return new RTypeInstruction(location, RTypeInstruction.SUBop, OperandHandler.stringToOperand(source, addFrom1),
                        OperandHandler.stringToOperand(source, addFrom2), OperandHandler.stringToOperand(source, addTo),
                        RTypeInstruction.SUBfunc);
            } catch (Exception e) {
                throw new InstructionErrorException();
            }
        }

        //J-Type指令
        if (operation.equals("j")) {
            try {
                return new JTypeInstruction(location, new Label(source.getProgram().getLabelList(), operandLine));
            } catch (Exception e) {
                throw new InstructionErrorException();
            }
        }


        if (operation.equals("addi")) {

        }
        return resultInstruction;
    }


}
