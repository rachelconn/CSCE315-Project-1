package project1.conditional;

import java.util.ArrayList;

public class TypedData{
    public ParsedDataType initializationType;
    public String initializationInformation;

    public ParsedDataType stateType;
    public String stateInformation;

    public TypedData(ParsedDataType initializationType, String initializationInformation) {
        this.initializationType = initializationType;
        this.initializationInformation = initializationInformation;

        if (initializationType != ParsedDataType.FIELD)
        {
            stateType = initializationType;
            stateInformation = initializationInformation;
        }
    }

    public boolean TryDefineState(ArrayList<Cell> row)
    {
        if (initializationType != ParsedDataType.FIELD)
        {
            return true;
        }
        for (Cell c : row)
        {
            if (!c.fieldName.isEmpty() && c.fieldName.equals(this.initializationInformation))
            {
                if (c.fieldType.contains("VARCHAR"))
                {
                    this.stateType = ParsedDataType.VARCHAR;
                }
                else if (c.fieldType.equals("INTEGER"))
                {
                    this.stateType = ParsedDataType.INTEGER;
                }
                else
                {
                    System.out.println("[FATAL] TrackedCell did not recognize type: " + c.fieldType);
                }
                this.stateInformation = c.fieldValue;
                return true;
            }
            else if (c.fieldName.isEmpty())
            {
                System.out.println("[FATAL] TrackedCell discovered a column with no name while self-completing.");
            }
        }
        return false;
    }

    public static ParsedDataType getType(String input)
    {
        if (Utilities.tryParseInt(input))
        {
            return ParsedDataType.INTEGER;
        }
        else
        {
            return ParsedDataType.VARCHAR;
        }
    }
}
