/**
 * Parser.java: Class which reads in raw assembly files and makes them readable for the Assembler
 *
 * @author Cameron Colleran
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser
{
    /** Instance variables representing the initial raw line, its cleaned version, and the Scanner to access the assembly file */
    private String rawLine;
    private String cleanLine;
    private Scanner inputFile;

    /**
     * Constructor for Parser
     * Initializes a Scanner with an assembly file
     *
     * @param fileName the name of the assembly file
     */
    public Parser(String fileName)
    {
        try
        {
            inputFile = new Scanner(new FileInputStream(fileName), "UTF-8");
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error finding file: " + e.getStackTrace());
        }
    }

    /**
     * Method which checks if the parser has more lines to read
     *
     * @return boolean dependent of if the parser has more lines to read
     */
    public boolean hasMoreCommands()
    {
        return inputFile.hasNextLine();
    }

    /**
     * Method which takes in a raw line from the assembly file and converts it into a readable line for the Assembler
     *
     * @return the new Assembler friendly version of the raw line
     */
    public String cleanLine()
    {
        rawLine = inputFile.nextLine();

        int index = rawLine.indexOf("//");

        if (index > 0)
        {
            rawLine = rawLine.substring(0, index);
        }

        cleanLine = rawLine.trim();
        return cleanLine;
    }

    /**
     * Method which outputs the Command type of a cleaned line in the assembly file
     *
     * @return the Command type of the line (A, C, L, or NO COMMAND)
     */
    public Command getCommandType()
    {
        if (cleanLine.length() > 0)
        {
            // If whole line is a comment
            if (cleanLine.charAt(0) == '/')
            {
                return Command.NO_COMMAND;
            }
            else if (cleanLine.contains("@")) // If A Command
            {
                return Command.A_COMMAND;
            }
            else if (cleanLine.contains("(") && cleanLine.contains(")")) // If L Command
            {
                return Command.L_COMMAND;
            }
            else if (cleanLine.contains("=") || cleanLine.contains(";")) // If C Command
            {
                return Command.C_COMMAND;
            }
        }
        return Command.NO_COMMAND;
    }

    /**
     * Method which isolates the symbol/label in both A and L Commands
     *
     * @return the isolated symbol/label in the A or L Command
     */
    public String symbol()
    {
        String out = cleanLine;

        if (getCommandType() == Command.A_COMMAND)
        {
            return out.replace("@", "");
        }
        else
        {
            return out.replaceAll("[()]","");
        }
    }

    /**
     * Method which isolates the "dest" value in the C Instruction
     *
     * @return the isolated "dest" value
     */
    public String dest()
    {
        String out = cleanLine;

        if (out.contains("="))
        {
            return out.substring(0, out.indexOf("="));
        }
        else
        {
            return "";
        }
    }

    /**
     * Method which isolates the "comp" value in the C Instruction
     *
     * @return the isolated "comp" value
     */
    public String comp()
    {
        String out = cleanLine;
        if (out.contains(";"))
        {
            return out.substring(out.indexOf("=") + 1, out.indexOf(";"));
        }
        else
        {
            return out.substring(out.indexOf("=") + 1);
        }
    }

    /**
     * Method which isolates the "jmp" value in the C Instruction
     *
     * @return the isolated "jmp" value
     */
    public String jump()
    {
        String out = cleanLine;
        if (out.contains(";"))
        {
            return out.substring(out.indexOf(";") + 1);
        }
        else
        {
            return "";
        }
    }
}
