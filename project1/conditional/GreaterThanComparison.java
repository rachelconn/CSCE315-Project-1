/*
SELECT helper method

checks if the value passed in by the command is greater than the value in the table
eg.

SELECT * FROM tabl WHERE (blah > 5)

blah = incoming_val (because it will be compared to values entering SelectsEntry from the table)
5 = condVal (because it is stored in the GreaterThanComparison when the command is loaded)
*/

package project1.conditional;

import java.util.ArrayList;

public class GreaterThanComparison extends Conditional
{
    public GreaterThanComparison(String condType, String condValue, String fieldName) {
        super(condType, condValue, fieldName);
    }

    @Override
    public boolean SelectsEntry(ArrayList<Cell> row) throws IncompatibleTypesException, FieldNotInTableException {
        Cell c = getCellFromRow(row);
        ThrowExceptionIfNotIntegersOnly(c.fieldType, c.fieldValue, ">");

        int int_incomingVal = Integer.parseInt(c.fieldValue);
        int int_condVal = Integer.parseInt(this.condValue);

        return int_incomingVal > int_condVal;
    }
}