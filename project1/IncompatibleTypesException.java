/*
SELECT helper class

triggered if a VARCHAR(20) is compared with a VARCHAR(8) or INTEGER
 */

package project1;
public class IncompatibleTypesException extends Exception
{
    public IncompatibleTypesException(String baseType,
                                      String baseValue,
                                      String incomingType,
                                      String incomingValue,
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