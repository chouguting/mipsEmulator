package com.chouguting.mipsemulator.ui;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;

public class instructionUIHandler {
    public static int nextstep(JTextArea codingArea, int currentProgramCounterLine) {
        int lastLine = currentProgramCounterLine;
        do {
            if (currentProgramCounterLine + 1 >= codingArea.getLineCount()) {
                paintLine(codingArea, lastLine + 1);
                return lastLine;
            }
            currentProgramCounterLine += 1;
        } while (isBlankLine(codingArea, currentProgramCounterLine));

        paintLine(codingArea, currentProgramCounterLine);
        return currentProgramCounterLine;
    }

    public static void paintLine(JTextArea codingArea, int line) {
        try {
            int startIndex = codingArea.getLineStartOffset(line);
            int endIndex = codingArea.getLineEndOffset(line);
            Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.cyan);
            codingArea.getHighlighter().removeAllHighlights();
            codingArea.getHighlighter().addHighlight(startIndex, endIndex, painter);
        } catch (BadLocationException ble) {
            ble.printStackTrace();
        }
    }

    private static boolean isBlankLine(JTextArea codingArea, int lineToCheck) {
        String[] lines = codingArea.getText().split("\\n");
        if (lineToCheck < 0 || lineToCheck >= lines.length) {
            return true;
        }
        if (lines[lineToCheck].isBlank()) return true;
        return false;
    }
}
