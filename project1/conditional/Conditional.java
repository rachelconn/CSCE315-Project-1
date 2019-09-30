/*
SELECT assistant class

Used for helping select entries.

Eg. >, < , >=, <=, ==, !=
 */

package project1.conditional;

import java.util.*;


public abstract class Conditional
{
    public Conditional() {
        this.condType = "";
        this.condValue = "";
        this.fieldName = "";
    }

    public Conditional(String condType, String condValue, String fieldName) {
        this.condType = condType;
        this.condValue = sanitizeFieldName(condValue);
        this.fieldName = sanitizeFieldName(fieldName);
    }

    public String getCondType() {
        return condType;
    }

    public String getCondValue() {
        return condValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public ArrayList<String> getFieldsChecked() {
        ArrayList<String> output = new ArrayList<>();
        output.add(fieldName);
        return output;
    }

    // Changed modifier to public so DBMS and Table can read values
    public String condType; // what we will compare the given value's types to
    public String condValue; // what we will compare the given values to
    public String fieldName; // the name of the column of value we extract
    public abstract boolean SelectsEntry(ArrayList<Cell> row) throws IncompatibleTypesException, FieldNotInTableException;

    Cell getCellFromRow(ArrayList<Cell> row) throws FieldNotInTableException {
        for (Cell c : row)
        {
            if (c.fieldName.equals(this.fieldName))
            {
                return c;
            }
        }
        throw new FieldNotInTableException("The field [" + this.fieldName + "] is not in the passed in row: " + row.toString());
    }

    public boolean HashableOperation()
    {
        return false;  // only overridden for ==
    }

    void ThrowExceptionIfNotIntegersOnly(String type, String value, String operator) throws IncompatibleTypesException
    {
        // this operation does not support VARCHAR
        // TODO: make this check more rigorous
        if (!type.equals("INTEGER") || !this.condType.equals("INTEGER") || !type.equals(this.condType))
        {
            throw new IncompatibleTypesException(this.condValue, this.condType, value, type, operator, "");
        }
    }

    void ThrowExceptionIfNotSameType(String type, String value, String operator) throws IncompatibleTypesException
    {
        // all strings are equal (the same type) in this glorious and progressive land
        if (type.contains("VARCHAR") && this.condType.contains("VARCHAR"))
        {
            return;
        }
        // TODO: make this check more rigorous
        if (!type.equals(this.condType))
        {
            System.out.println(type.contains("VARCHAR"));
            System.out.println(type.equals("VARCHAR"));
            System.out.println(this.condType.contains("VARCHAR"));
            throw new IncompatibleTypesException(this.condValue, this.condType, value, type, operator, "");
        }
    }

    public static String getType(String input)
    {
        if (tryParseInt(input))
        {
            return "INTEGER";
        }
        else
        {
            return "VARCHAR";
        }
    }

    public static String sanitizeFieldName(String input)
    {
        return input.replace("\"", "");
    }

    public static boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // TODO: protect against NULL/empty values
    // TODO: add universal VARCHAR for query checks
}