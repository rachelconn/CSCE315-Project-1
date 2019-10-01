/*
SELECT helper method

checks if the value passed in by the command is greater than the value in the table
eg.

SELECT * FROM tabl WHERE (blah == "lul")

blah = incoming_val (because it will be compared to values entering SelectsEntry from the table)
"lul" = condVal (because it is stored in the GreaterThanComparison when the command is loaded)
*/

package project1.conditional;

import java.util.ArrayList;

public class EqualsComparison extends Conditional
{

    public EqualsComparison(TypedData left, TypedData right) {
        super(left, right);
    }

    @Override
    public boolean SelectsEntry(ArrayList<Cell> row) throws IncompatibleTypesException, FieldNotInTableException {
        if (!left.TryDefineState(row))
        {
            throw new FieldNotInTableException("[FATAL] Field " + left.stateInformation + " was not in row");
        }
        if (!right.TryDefineState(row))
        {
            throw new FieldNotInTableException("[FATAL] Field " + right.stateInformation + " was not in row");
        }
        ThrowExceptionIfNotSameType(left, right, "==");

        return left.stateInformation.equals(right.stateInformation);
    }

    @Override
    public boolean HashableOperation()
    {
        return true;
    }
}