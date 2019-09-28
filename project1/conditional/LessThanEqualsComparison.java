/*
SELECT helper method

checks if the value passed in by the command is greater than the value in the table
eg.

SELECT * FROM tabl WHERE (blah < 5)

blah = incoming_val (because it will be compared to values entering SelectsEntry from the table)
5 = condVal (because it is stored in the GreaterThanComparison when the command is loaded)
*/

package project1.conditional;

public class LessThanEqualsComparison extends Conditional
{
    public LessThanEqualsComparison(String condType, String condValue, String fieldName) {
        super(condType, condValue, fieldName);
    }

    @Override
    boolean SelectsEntry(String type, String value) throws IncompatibleTypesException {
        ThrowExceptionIfNotIntegersOnly(type, value, "<=");

        int int_incomingVal = Integer.parseInt(value);
        int int_condVal = Integer.parseInt(this.condValue);

        return int_incomingVal <= int_condVal;
    }
}