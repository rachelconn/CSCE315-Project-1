/*
SELECT assistant class

Used for helping select entries.

Eg. >, < , >=, <=, ==, !=
 */

// TODO: helper strings are horribly outdated

package project1.conditional;

import java.util.*;


public abstract class Conditional
{
    TypedData left;
    TypedData right;

    public Conditional() {
    }

    public Conditional(TypedData left, TypedData right) {
        this.left = left;
        this.right = right;
    }

    public ArrayList<String> getFieldsChecked() {
        ArrayList<String> output = new ArrayList<>();
        if (left.stateType == ParsedDataType.FIELD)
        {
            output.add(left.initializationInformation);
        }
        if (right.stateType == ParsedDataType.FIELD)
        {
            output.add(right.initializationInformation);
        }
        return output;
    }

    public abstract boolean SelectsEntry(ArrayList<Cell> row) throws IncompatibleTypesException, FieldNotInTableException;

    public boolean HashableOperation()
    {
        return false;  // only overridden for ==
    }

    void ThrowExceptionIfNotIntegersOnly(TypedData a, TypedData b, String operator) throws IncompatibleTypesException
    {
        // this operation does not support VARCHAR
        // TODO: make this check more rigorous
        if (a.stateType != ParsedDataType.INTEGER || b.stateType != ParsedDataType.INTEGER)
        {
            throw new IncompatibleTypesException(a.stateInformation, a.stateType.toString(), b.stateInformation, b.stateType.toString(), operator, "");
        }
    }

    void ThrowExceptionIfNotSameType(TypedData a, TypedData b, String operator) throws IncompatibleTypesException
    {
        // check enums for equality
        if (a.stateType != b.stateType)
        {
            throw new IncompatibleTypesException(a.stateInformation, a.stateType.toString(), b.stateInformation, b.stateType.toString(), operator, "");
        }
    }

    public static String getType(String input)
    {
        if (Utilities.tryParseInt(input))
        {
            return "INTEGER";
        }
        else
        {
            return "VARCHAR";
        }
    }


    // TODO: protect against NULL/empty values
    // TODO: add universal VARCHAR for query checks

}