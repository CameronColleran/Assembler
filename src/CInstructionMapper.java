/**
 * CInstructionMapper.java: Class which stores predefined binary values for the three types of C
 * Instruction (comp, dest, jmp) and outputs those binary values dependent on input
 *
 * @author Cameron Colleran
 */

import java.util.HashMap;

public class CInstructionMapper
{
    /** Creating three HashMaps which store the predefined comp, dest, and jmp keys and their binary values */
    private HashMap<String, String> compCodes;
    private HashMap<String, String> destCodes;
    private HashMap<String, String> jumpCodes;

    /**
     * Constructor for CInstructionMapper
     * Adds the predefined comp, dest, and jmp keys and their binary values to the three HashMaps
     */
    public CInstructionMapper()
    {
        compCodes = new HashMap();

        compCodes.put("0", "0101010");
        compCodes.put("1", "0111111");
        compCodes.put("-1", "0111010");
        compCodes.put("D", "0001100");
        compCodes.put("A", "0110000");
        compCodes.put("M", "1110000");
        compCodes.put("!D", "0001101");
        compCodes.put("!A", "0110001");
        compCodes.put("!M", "1110001");
        compCodes.put("-D", "0001111");
        compCodes.put("-A", "0110011");
        compCodes.put("-M", "1110011");
        compCodes.put("D+1", "0011111");
        compCodes.put("A+1", "0110111");
        compCodes.put("M+1", "1110111");
        compCodes.put("D-1", "0001110");
        compCodes.put("A-1", "0110010");
        compCodes.put("M-1", "1110010");
        compCodes.put("D+A", "0000010");
        compCodes.put("D+M", "1000010");
        compCodes.put("D-A", "0010011");
        compCodes.put("D-M", "1010011");
        compCodes.put("A-D", "0000111");
        compCodes.put("M-D", "1000111");
        compCodes.put("D&A", "0000000");
        compCodes.put("D&M", "1000000");
        compCodes.put("D|A", "0010101");
        compCodes.put("D|M", "1010101");

        destCodes = new HashMap();
        destCodes.put("", "000");
        destCodes.put("M", "001");
        destCodes.put("D", "010");
        destCodes.put("MD", "011");
        destCodes.put("A", "100");
        destCodes.put("AM", "101");
        destCodes.put("AD", "110");
        destCodes.put("AMD", "111");

        jumpCodes = new HashMap();
        jumpCodes.put("", "000");
        jumpCodes.put("JGT", "001");
        jumpCodes.put("JEQ", "010");
        jumpCodes.put("JGE", "011");
        jumpCodes.put("JLT", "100");
        jumpCodes.put("JNE", "101");
        jumpCodes.put("JLE", "110");
        jumpCodes.put("JMP", "111");
    }

    /**
     * Outputs keys of comp codes in the compCodes HashMap
     *
     * @param mnemonic the comp code mnemonic value
     * @return the binary key of the comp code mnemonic value
     */
    public String comp(String mnemonic)
    {
        return compCodes.get(mnemonic);
    }

    /**
     * Outputs keys of dest codes in the destCodes HashMap
     *
     * @param mnemonic the dest code mnemonic value
     * @return the binary key of the dest code mnemonic value
     */
    public String dest(String mnemonic)
    {
        return destCodes.get(mnemonic);
    }

    /**
     * Outputs keys of jmp codes in the jumpCodes HashMap
     *
     * @param mnemonic the jmp code mnemonic value
     * @return the binary key of the jmp code mnemonic value
     */
    public String jump(String mnemonic)
    {
        return jumpCodes.get(mnemonic);
    }
}
