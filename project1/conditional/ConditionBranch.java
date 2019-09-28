package project1.conditional;

public class ConditionBranch extends Conditional {
    ConditionType condType;
    Conditional left;
    Conditional right;

    public ConditionBranch(ConditionType condType, Conditional left, Conditional right) {
        this.condType = condType;
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean SelectsEntry(String type, String value) throws IncompatibleTypesException {
        if (condType == ConditionType.AND)
        {
            return left.SelectsEntry(type, value) && right.SelectsEntry(type, value);
        }
        else
        {
            return left.SelectsEntry(type, value) || right.SelectsEntry(type, value);
        }
    }
}