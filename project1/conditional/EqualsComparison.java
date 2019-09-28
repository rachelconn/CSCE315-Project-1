/*
SELECT helper method

checks if the value passed in by the command is greater than the value in the table
eg.

SELECT * FROM tabl WHERE (blah == "lul")

blah = incoming_val (because it will be compared to values entering SelectsEntry from the table)
"lul" = condVal (because it is stored in the GreaterThanComparison when the command is loaded)
*/

package project1.conditional;

public class EqualsComparison extends Conditional
{
    public EqualsComparison(String condType, String condValue, String fieldName) {
        super(condType, condValue, fieldName);
    }

    @Override
    boolean SelectsEntry(String type, String value) throws IncompatibleTypesException {
        ThrowExceptionIfNotSameType(type, value, "==");

        return value.equals(this.condValue);
    }

    @Override
    public boolean HashableOperation()
    {
        return true;
    }
}