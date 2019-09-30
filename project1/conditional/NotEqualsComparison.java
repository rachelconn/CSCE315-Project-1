/*
SELECT helper method

checks if the value passed in by the command is greater than the value in the table
eg.

SELECT * FROM tabl WHERE (blah != "lul")

blah = incoming_val (because it will be compared to values entering SelectsEntry from the table)
"lul" = condVal (because it is stored in the GreaterThanComparison when the command is loaded)
*/

package project1.conditional;

import java.util.ArrayList;

public class NotEqualsComparison extends Conditional
{
    public NotEqualsComparison(String condType, String condValue, String fieldName) {
        super(condType, condValue, fieldName);
    }

    @Override
    public boolean SelectsEntry(ArrayList<Cell> row) throws IncompatibleTypesException, FieldNotInTableException {
        Cell c = getCellFromRow(row);
        ThrowExceptionIfNotSameType(c.fieldType, c.fieldValue, "!=");

        return !c.fieldValue.equals(this.condValue);
    }
}