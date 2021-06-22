package com.chouguting.mipsemulator.software;

import com.chouguting.mipsemulator.exception.InstructionErrorException;
import com.chouguting.mipsemulator.hardware.Mips;
import com.chouguting.mipsemulator.software.opr.Immediate;
import com.chouguting.mipsemulator.software.opr.Label;
import com.chouguting.mipsemulator.software.opr.MemoryLocation;
import com.chouguting.mipsemulator.software.opr.OperandHandler;

import java.util.ArrayList;

public class InstructionHandler {
    //把整段文字轉換成一個指令所組成的陣列
    public static ArrayList<Instruction> stringToInstructions(Mips source, String allText) throws InstructionErrorException {
        ArrayList<Instruction> resultList = new ArrayList<Instruction>();
        String[] lines = allText.split("\\n");
        ArrayList<String> ackLabelList=new ArrayList<>();
        int counter=0;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].isBlank()) continue;
            if(!singleLineToInstructionLabelOnly(source, lines[i], i, counter,ackLabelList)){
                counter++;
            }
        }
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].isBlank()) continue;
            Instruction thisLiseOfInstruction = singleLineToInstruction(source, lines[i], i, resultList.size(),ackLabelList);
            if (thisLiseOfInstruction != null)
                resultList.add(thisLiseOfInstruction);
        }
        return resultList;
    }


    private static boolean singleLineToInstructionLabelOnly(Mips source, String line, int location, int indexInInstructionList, ArrayList<String> ackLabelList) throws InstructionErrorException{
        line = line.toLowerCase().strip();
        String originalInstructionString = line;
        //實際的Label
        if (line.endsWith(":")) {
            new Label(location, source.getProgram().getLabelList(), line.replaceAll(":", "").toLowerCase(), indexInInstructionList,ackLabelList);
            return true;
        }

        return false;
    }


    //把單一行字串轉換成一條指令
    private static Instruction singleLineToInstruction(Mips source, String line, int location, int indexInInstructionList, ArrayList<String> ackLabelList) throws InstructionErrorException {
        Instruction resultInstruction = new Instruction(location);
        line = line.toLowerCase().strip();
        String originalInstructionString = line;

        if (line.endsWith(":")) {
            return null;
            //return new Label(location, source.getProgram().getLabelList(), line.replaceAll(":", "").toLowerCase(), indexInInstructionList,ackLabelList);
        }

        String[] splittedLine = line.split("\\s+", 2); //用空白分開 指令和運算元
        String operation;
        String operandLine;
        try {
            operation = splittedLine[0];
            operandLine = splittedLine[1];
        } catch (Exception e) {
            throw new InstructionErrorException(location);
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
                return new RTypeInstruction(originalInstructionString, location, RTypeInstruction.ADDop, OperandHandler.stringToRegister(source, addFrom1),
                        OperandHandler.stringToRegister(source, addFrom2), OperandHandler.stringToRegister(source, addTo),
                        RTypeInstruction.ADDfunc);
            } catch (Exception e) {
                throw new InstructionErrorException(location);
            }
        }

        if (operation.equals("sub")) {
            try {
                String[] buff = operandLine.split(",", 3);
                String subTo = buff[0];
                subTo = subTo.replaceAll("\\s+", "").toLowerCase();
                String subFrom = buff[1];
                subFrom = subFrom.replaceAll("\\s+", "").toLowerCase();
                String subWith = buff[2];
                subWith = subWith.replaceAll("\\s+", "").toLowerCase();
                return new RTypeInstruction(originalInstructionString, location, RTypeInstruction.SUBop, OperandHandler.stringToRegister(source, subFrom),
                        OperandHandler.stringToRegister(source, subWith), OperandHandler.stringToRegister(source, subTo),
                        RTypeInstruction.SUBfunc);
            } catch (Exception e) {
                throw new InstructionErrorException(location);
            }
        }

        if (operation.equals("and")) {
            try {
                String[] buff = operandLine.split(",", 3);
                String resultTo = buff[0];
                resultTo = resultTo.replaceAll("\\s+", "").toLowerCase();
                String resultFrom1 = buff[1];
                resultFrom1 = resultFrom1.replaceAll("\\s+", "").toLowerCase();
                String resultFrom2 = buff[2];
                resultFrom2 = resultFrom2.replaceAll("\\s+", "").toLowerCase();
                return new RTypeInstruction(originalInstructionString, location, RTypeInstruction.ANDop, OperandHandler.stringToRegister(source, resultFrom1),
                        OperandHandler.stringToRegister(source, resultFrom2), OperandHandler.stringToRegister(source, resultTo),
                        RTypeInstruction.ANDfunc);
            } catch (Exception e) {
                throw new InstructionErrorException(location);
            }
        }


        if (operation.equals("or")) {
            try {
                String[] buff = operandLine.split(",", 3);
                String resultTo = buff[0];
                resultTo = resultTo.replaceAll("\\s+", "").toLowerCase();
                String resultFrom1 = buff[1];
                resultFrom1 = resultFrom1.replaceAll("\\s+", "").toLowerCase();
                String resultFrom2 = buff[2];
                resultFrom2 = resultFrom2.replaceAll("\\s+", "").toLowerCase();
                return new RTypeInstruction(originalInstructionString, location, RTypeInstruction.ORop, OperandHandler.stringToRegister(source, resultFrom1),
                        OperandHandler.stringToRegister(source, resultFrom2), OperandHandler.stringToRegister(source, resultTo),
                        RTypeInstruction.ORfunc);
            } catch (Exception e) {
                throw new InstructionErrorException(location);
            }
        }

        if (operation.equals("nor")) {
            try {
                String[] buff = operandLine.split(",", 3);
                String resultTo = buff[0];
                resultTo = resultTo.replaceAll("\\s+", "").toLowerCase();
                String resultFrom1 = buff[1];
                resultFrom1 = resultFrom1.replaceAll("\\s+", "").toLowerCase();
                String resultFrom2 = buff[2];
                resultFrom2 = resultFrom2.replaceAll("\\s+", "").toLowerCase();
                return new RTypeInstruction(originalInstructionString, location, RTypeInstruction.NORop, OperandHandler.stringToRegister(source, resultFrom1),
                        OperandHandler.stringToRegister(source, resultFrom2), OperandHandler.stringToRegister(source, resultTo),
                        RTypeInstruction.NORfunc);
            } catch (Exception e) {
                throw new InstructionErrorException(location);
            }
        }

        if (operation.equals("sll")) {
            try {
                String[] buff = operandLine.split(",", 3);
                String resultTo = buff[0];
                resultTo = resultTo.replaceAll("\\s+", "").toLowerCase();
                String resultFrom = buff[1];
                resultFrom = resultFrom.replaceAll("\\s+", "").toLowerCase();
                String shiftAmountStr = buff[2];
                int shiftAmount = Integer.parseInt(shiftAmountStr.replaceAll("\\s+", ""));
                return new RTypeInstruction(originalInstructionString, location, RTypeInstruction.SLLop, OperandHandler.stringToRegister(source, resultFrom),
                        OperandHandler.stringToRegister(source, resultTo), shiftAmount, RTypeInstruction.SLLfunc);
            } catch (Exception e) {
                throw new InstructionErrorException(location);
            }
        }

        if (operation.equals("srl")) {
            try {
                String[] buff = operandLine.split(",", 3);
                String resultTo = buff[0];
                resultTo = resultTo.replaceAll("\\s+", "").toLowerCase();
                String resultFrom = buff[1];
                resultFrom = resultFrom.replaceAll("\\s+", "").toLowerCase();
                String shiftAmountStr = buff[2];
                int shiftAmount = Integer.parseInt(shiftAmountStr.replaceAll("\\s+", ""));
                return new RTypeInstruction(originalInstructionString, location, RTypeInstruction.SRLop, OperandHandler.stringToRegister(source, resultFrom),
                        OperandHandler.stringToRegister(source, resultTo), shiftAmount, RTypeInstruction.SRLfunc);
            } catch (Exception e) {
                throw new InstructionErrorException(location);
            }
        }

        if (operation.equals("slt")) {
            try {
                String[] buff = operandLine.split(",", 3);
                String resultTo = buff[0];
                resultTo = resultTo.replaceAll("\\s+", "").toLowerCase(); //rd
                String compareFrom = buff[1];
                compareFrom = compareFrom.replaceAll("\\s+", "").toLowerCase(); //rs
                String compareTo = buff[2];
                compareTo = compareTo.replaceAll("\\s+", "").toLowerCase(); //rt
                return new RTypeInstruction(originalInstructionString, location, RTypeInstruction.SLTop, OperandHandler.stringToRegister(source, compareFrom),
                        OperandHandler.stringToRegister(source, compareTo), OperandHandler.stringToRegister(source, resultTo), RTypeInstruction.SLTfunc);
            } catch (Exception e) {
                throw new InstructionErrorException(location);
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
                return new RTypeInstruction(originalInstructionString, location, RTypeInstruction.SUBop, OperandHandler.stringToRegister(source, addFrom1),
                        OperandHandler.stringToRegister(source, addFrom2), OperandHandler.stringToRegister(source, addTo),
                        RTypeInstruction.SUBfunc);
            } catch (Exception e) {
                throw new InstructionErrorException(location);
            }
        }

        //J-Type指令
        if (operation.equals("j")) {
            try {
                operandLine.replaceAll("\\s+", "");
                return new JTypeInstruction(originalInstructionString, location, JTypeInstruction.Jop, new Label(source.getProgram().getLabelList(), operandLine,ackLabelList));
            } catch (Exception e) {
                throw new InstructionErrorException(location);
            }
        }

        //I-Type指令
        if (operation.equals("addi")) {
            try {
                String[] buff = operandLine.split(",", 3);
                String addTo = buff[0];
                addTo = addTo.replaceAll("\\s+", "").toLowerCase();
                String addFrom1 = buff[1];
                addFrom1 = addFrom1.replaceAll("\\s+", "").toLowerCase();
                String addFrom2 = buff[2];
                addFrom2 = addFrom2.replaceAll("\\s+", "");
                long addFromImmediate = Integer.parseInt(addFrom2);
                return new ITypeInstruction(originalInstructionString, location, ITypeInstruction.ADDIop, OperandHandler.stringToRegister(source, addFrom1)
                        , OperandHandler.stringToRegister(source, addTo), new Immediate(addFromImmediate));
            } catch (Exception e) {
                throw new InstructionErrorException(location);
            }
        }

        if (operation.equals("lw")) {
            try {
                String[] buff = operandLine.split(",", 2);
                String saveTo = buff[0];
                saveTo = saveTo.replaceAll("\\s+", "").toLowerCase();
                String[] offsetAndBase = buff[1].split("\\(", 2);
                int offset = Integer.parseInt(offsetAndBase[0].replaceAll("\\s+", "")); //把offset取出
                String[] baseTemp = offsetAndBase[1].split("\\)");
                String base = baseTemp[0].replaceAll("\\s+", "").toLowerCase(); //剩下的這個Register是BASE

                return new ITypeInstruction(originalInstructionString, location, ITypeInstruction.LWop, OperandHandler.stringToRegister(source, base)
                        , OperandHandler.stringToRegister(source, saveTo), new MemoryLocation(source.getMemory(), OperandHandler.stringToRegister(source, base), offset));
            } catch (Exception e) {
                throw new InstructionErrorException(location);
            }
        }

        if (operation.equals("sw")) {
            try {
                String[] buff = operandLine.split(",", 2);
                String saveFrom = buff[0];
                saveFrom = saveFrom.replaceAll("\\s+", "").toLowerCase();


                String[] offsetAndBase = buff[1].split("\\(", 2);
                int offset = Integer.parseInt(offsetAndBase[0].replaceAll("\\s+", "")); //把offset取出
                String[] baseTemp = offsetAndBase[1].split("\\)");
                String base = baseTemp[0].replaceAll("\\s+", "").toLowerCase();

                return new ITypeInstruction(originalInstructionString, location, ITypeInstruction.SWop, OperandHandler.stringToRegister(source, base)
                        , OperandHandler.stringToRegister(source, saveFrom), new MemoryLocation(source.getMemory(), OperandHandler.stringToRegister(source, base), offset));
            } catch (Exception e) {
                throw new InstructionErrorException(location);
            }
        }

        if (operation.equals("andi")) {
            try {
                String[] buff = operandLine.split(",", 3);
                String andTo = buff[0];
                andTo = andTo.replaceAll("\\s+", "").toLowerCase();
                String andFrom1 = buff[1];
                andFrom1 = andFrom1.replaceAll("\\s+", "").toLowerCase();
                String andFrom2 = buff[2];
                andFrom2 = andFrom2.replaceAll("\\s+", "");
                long andFromImmediate = Integer.parseInt(andFrom2);
                return new ITypeInstruction(originalInstructionString, location, ITypeInstruction.ANDIop, OperandHandler.stringToRegister(source, andFrom1)
                        , OperandHandler.stringToRegister(source, andTo), new Immediate(andFromImmediate));
            } catch (Exception e) {
                throw new InstructionErrorException(location);
            }
        }

        if (operation.equals("ori")) {
            try {
                String[] buff = operandLine.split(",", 3);
                String orTo = buff[0];
                orTo = orTo.replaceAll("\\s+", "").toLowerCase();
                String orFrom1 = buff[1];
                orFrom1 = orFrom1.replaceAll("\\s+", "").toLowerCase();
                String orFrom2 = buff[2];
                orFrom2 = orFrom2.replaceAll("\\s+", "");
                long orFromImmediate = Integer.parseInt(orFrom2);
                return new ITypeInstruction(originalInstructionString, location, ITypeInstruction.ORIop, OperandHandler.stringToRegister(source, orFrom1)
                        , OperandHandler.stringToRegister(source, orTo), new Immediate(orFromImmediate));
            } catch (Exception e) {
                throw new InstructionErrorException(location);
            }
        }

        if (operation.equals("beq")) {
            try {
                String[] buff = operandLine.split(",", 3);
                String compareFrom = buff[0];
                compareFrom = compareFrom.replaceAll("\\s+", "").toLowerCase();
                String compareTo = buff[1];
                compareTo = compareTo.replaceAll("\\s+", "").toLowerCase();
                String labelName = buff[2];
                labelName = labelName.replaceAll("\\s+", "");

                return new ITypeInstruction(originalInstructionString, location, ITypeInstruction.BEQop, OperandHandler.stringToRegister(source, compareFrom)
                        , OperandHandler.stringToRegister(source, compareTo), new Label(source.getProgram().getLabelList(), labelName,ackLabelList));
            } catch (Exception e) {
                throw new InstructionErrorException(location);
            }
        }

        if (operation.equals("bne")) {
            try {
                String[] buff = operandLine.split(",", 3);
                String compareFrom = buff[0];
                compareFrom = compareFrom.replaceAll("\\s+", "").toLowerCase();
                String compareTo = buff[1];
                compareTo = compareTo.replaceAll("\\s+", "").toLowerCase();
                String labelName = buff[2];
                labelName = labelName.replaceAll("\\s+", "");

                return new ITypeInstruction(originalInstructionString, location, ITypeInstruction.BNEop, OperandHandler.stringToRegister(source, compareFrom)
                        , OperandHandler.stringToRegister(source, compareTo), new Label(source.getProgram().getLabelList(), labelName,ackLabelList));
            } catch (Exception e) {
                throw new InstructionErrorException(location);
            }
        }

        if (operation.equals("slti")) {
            try {
                String[] buff = operandLine.split(",", 3);
                String resultTo = buff[0];
                resultTo = resultTo.replaceAll("\\s+", "").toLowerCase();
                String compareFrom1 = buff[1];
                compareFrom1 = compareFrom1.replaceAll("\\s+", "").toLowerCase();
                String compareTo = buff[2];
                compareTo = compareTo.replaceAll("\\s+", "");
                long orFromImmediate = Integer.parseInt(compareTo);
                return new ITypeInstruction(originalInstructionString, location, ITypeInstruction.SLTIop, OperandHandler.stringToRegister(source, compareFrom1)
                        , OperandHandler.stringToRegister(source, resultTo), new Immediate(orFromImmediate));
            } catch (Exception e) {
                throw new InstructionErrorException(location);
            }
        }

        throw new InstructionErrorException(location);
    }


}
