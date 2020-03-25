/**
 * Assembler.java: Serves as the central module to process assembly code and output corresponding binary code
 *
 * @author Cameron Colleran
 */

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Assembler
{
    public static void main(String[] args)
    {
        SymbolTable symbolTable = new SymbolTable();
        firstPass("MaxL.asm", symbolTable);
        secondPass("MaxL.asm", symbolTable, "MaxL.hack");
    }

    /**
     * Method which converts base-10 decimal numbers into corresponding binary values (16 bit)
     *
     * @param num the decimal number to be converted
     * @return the final binary conversion (16 bit)
     */
    public static String decimalToBinary(int num)
    {
        int quotient;
        int remainder;
        StringBuilder binary = new StringBuilder();

        // Performing conversion utilizing double dabble method
        do
        {
            quotient = num / 2;
            remainder = num % 2;
            num = quotient;
            binary.append(remainder==0 ? "0" : "1");
        }
        while (quotient != 0);

        // Append zeros to unused indices, up to 16
        int length = binary.length();
        for (int i = 0; i < 16 - length; i++)
        {
            binary.append("0");
        }

        // Reverse string and return it
        binary.reverse();
        return binary.toString();
    }

    /**
     * Method which does a pass through the asm file and adds all labels into a SymbolTable
     *
     * @param assemblyFileName the name of the assembly file which will be accessed
     * @param symbolTable the symbol table which all labels will be added into
     */
    public static void firstPass(String assemblyFileName, SymbolTable symbolTable)
    {
        // Creating new parser object
        // For ease of use, file location is the src file of this project (can easily be changed if need be)
        Parser parser = new Parser("C:\\Users\\Cameron\\IdeaProjects\\CS220 Assembler\\src\\" + assemblyFileName);

        int romIndex = 0;

        // Loop which reads through the entire asm file and adds all label vales and their corresponding rom index to the symbol table
        while (parser.hasMoreCommands())
        {
            parser.cleanLine();
            // If line is a Label
            if (parser.getCommandType() == Command.L_COMMAND)
            {
                String label = parser.symbol();
                // Add label if not already in symbol table
                if (!symbolTable.contains(label))
                {
                    symbolTable.addEntry(label, romIndex);
                }
            }
            // Only increment rom index if A or C Command
            if (parser.getCommandType() == Command.A_COMMAND || parser.getCommandType() == Command.C_COMMAND)
            {
                romIndex++;
            }
        }
    }

    /**
     * Method which performs a second pass through the asm file and outputs all data to a binary file
     * Intended to be used following the "firstPass" method
     *
     * @param assemblyFileName the name of the assembly file which will be accessed
     * @param symbolTable the symbol table which will contain all previously added labels and newly added symbols
     * @param binaryFileName the name of the file which will contain all output binary (machine) code
     */
    public static void secondPass(String assemblyFileName, SymbolTable symbolTable, String binaryFileName)
    {
        // Creating new parser object
        // For ease of use, file location is the src file of this project (can easily be changed if need be)
        Parser parser = new Parser("C:\\Users\\Cameron\\IdeaProjects\\CS220 Assembler\\src\\" + assemblyFileName);
        CInstructionMapper mapper = new CInstructionMapper();

        // Hooking up PrintWriter to output data file
        PrintWriter writer = null;
        try
        {
            writer = new PrintWriter(new FileOutputStream("C:\\Users\\Cameron\\IdeaProjects\\CS220 Assembler\\src\\" + binaryFileName));
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e.getStackTrace());
        }

        int ramIndex = 16;

        // Loop which reads in all lines of assembly file, those that are either valid A or C commands will
        // have appropriate binary translations performed and output to file
        // L instructions are ignored as purpose of "firstLoop" method is to address them
        while (parser.hasMoreCommands())
        {
            parser.cleanLine();

            // If line is an A Command
            if (parser.getCommandType() == Command.A_COMMAND)
            {
                String symbol = parser.symbol();
                boolean isInt = true;

                // Trying to cast symbol to integer to see if integer or string (EX: "@Var" vs "@16")
                try
                {
                    Integer.parseInt(symbol);
                }
                catch (NumberFormatException e)
                {
                    isInt = false;
                }

                // If symbol is a string (i.e. not an int)
                if (!isInt)
                {
                    // If symbol is already in SymbolTable, write its corresponding address to file in binary
                    if (symbolTable.contains(symbol))
                    {
                        writer.println(decimalToBinary(symbolTable.getAddress(symbol)));
                    }
                    else // Otherwise it isn't already in SymbolTable -> write to file with current ram index (decimal to binary)
                    {
                        writer.println(decimalToBinary(ramIndex));
                        // Also add it to the SymbolTable
                        symbolTable.addEntry(symbol, ramIndex);
                        // Increment ram index as that slot is now in use
                        ramIndex++;
                    }
                }
                else // Else symbol is an int, in which case simply write its value to file
                {
                    writer.println(decimalToBinary(Integer.parseInt(symbol)));
                }

            }
            else if (parser.getCommandType() == Command.C_COMMAND) // Else if line is a C Command
            {
                // Extract comp, dest, and jmp values from the line
                String compIn = parser.comp();
                String destIn = parser.dest();
                String jumpIn = parser.jump();
                // Print out standard "111" identifier for C Commands, followed by the proper binary values for each
                // command (comp, dest, and jmp) which are found in the CInstructionMapper hash maps
                writer.println("111" + mapper.comp(compIn) + mapper.dest(destIn) + mapper.jump(jumpIn));
            }
        }

        writer.flush();
        writer.close();
    }
}
