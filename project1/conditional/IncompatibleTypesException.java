/*
SELECT helper class

triggered if a VARCHAR(20) is compared with a VARCHAR(8) or INTEGER
 */

package project1.conditional;

public class IncompatibleTypesException extends Exception
{
    public IncompatibleTypesException(String baseValue,
                                      String baseType,
                                      String incomingValue,
                                      String incomingType,
                                      String operation,
                                      String message) {
        super(
                "Attempt to compare \"" +
                        baseValue +
                        "\", type: " +
                        baseType +
                        " (from command) with \"" +
                        incomingValue +
                        "\", type: " +
                        incomingType +
                        " (from table) with operation" +
                        operation +
                        "\n" +
                        message

        );
    }
}