/*
SELECT assistant class

Used for helping select entries.

Eg. >, < , >=, <=, ==, !=
 */

package project1.conditional;


public abstract class Conditional
{
    public Conditional(String condType, String condValue, String fieldName) {
        this.condType = condType;
        this.condValue = condValue;
        this.fieldName = fieldName;
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

    // Changed modifier to public so DBMS and Table can read values
    public String condType; // what we will compare the given value's types to
    public String condValue; // what we will compare the given values to
    public String fieldName; // the name of the column of value we extract
    public abstract boolean SelectsEntry(String type, String value) throws IncompatibleTypesException;

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
        // this operation does not support VARCHAR
        // TODO: make this check more rigorous
        if (!type.equals(this.condType))
        {
            throw new IncompatibleTypesException(this.condValue, this.condType, value, type, operator, "");
        }
    }

    // TODO: protect against NULL/empty values
}