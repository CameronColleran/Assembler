/**
 * SymbolTable.java: Class which stores a Hash Map of values for Symbols of A Instructions or Labels for L Instructions
 * and either's respective ROM values as keys
 *
 * @author Cameron Colleran
 */

import java.util.HashMap;

public class SymbolTable
{
    /** Creating HashMap with a String (Key) and Integer (Value) input */
    private HashMap<String, Integer> symbolTable;

    /**
     * Constructor for SymbolTable
     * Initializes HashMap object and adds the predefined symbols and labels that are in accordance with the Hack
     * programming language
     */
    public SymbolTable()
    {
        symbolTable = new HashMap();

        symbolTable.put("SP", 0);
        symbolTable.put("LCL", 1);
        symbolTable.put("ARG", 2);
        symbolTable.put("THIS", 3);
        symbolTable.put("THAT", 4);
        symbolTable.put("R0", 0);
        symbolTable.put("R1", 1);
        symbolTable.put("R2", 2);
        symbolTable.put("R3", 3);
        symbolTable.put("R4", 4);
        symbolTable.put("R5", 5);
        symbolTable.put("R6", 6);
        symbolTable.put("R7", 7);
        symbolTable.put("R8", 8);
        symbolTable.put("R9", 9);
        symbolTable.put("R10", 10);
        symbolTable.put("R11", 11);
        symbolTable.put("R12", 12);
        symbolTable.put("R13", 13);
        symbolTable.put("R14", 14);
        symbolTable.put("R15", 15);
        symbolTable.put("SCREEN", 16384);
        symbolTable.put("KBD", 24576);
    }

    /**
     * Method which adds a new symbol and it's corresponding address to the symbol table
     *
     * @param symbol the symbol to be added to the SymbolTable (key)
     * @param address the address to be mapped with the symbol (value)
     */
    public void addEntry(String symbol, int address)
    {
        symbolTable.put(symbol,address);
    }

    /**
     * Method which returns a boolean dependent on presence of the symbol input it is given
     *
     * @param symbol the symbol which the method will search for
     * @return boolean dependent on if the SymbolTable contains the symbol
     */
    public boolean contains(String symbol)
    {
        return symbolTable.containsKey(symbol);
    }

    /**
     * Method which returns the address of the symbol input its given
     *
     * @param symbol the symbol value to look for
     * @return the address of the symbol value
     */
    public int getAddress(String symbol)
    {
        return symbolTable.get(symbol);
    }
}
