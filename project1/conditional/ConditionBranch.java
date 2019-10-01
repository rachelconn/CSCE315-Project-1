package project1.conditional;

import java.util.ArrayList;

public class ConditionBranch extends Conditional {
    ConditionType condType;
    Conditional _left;
    Conditional _right;

    public ConditionBranch(ConditionType condType, Conditional _left, Conditional _right) {
        this.condType = condType;
        this._left = _left;
        this._right = _right;
    }

    @Override
    public ArrayList<String> getFieldsChecked() {
        ArrayList<String> output = new ArrayList<>();
        for (String s : _left.getFieldsChecked())
        {
            if (!output.contains(s))
            {
                output.add(s);
            }
        }
        for (String s : _right.getFieldsChecked())
        {
            if (!output.contains(s))
            {
                output.add(s);
            }
        }
        return output;
    }

    @Override
    public boolean SelectsEntry(ArrayList<Cell> row) throws IncompatibleTypesException, FieldNotInTableException {
        if (condType == ConditionType.AND)
        {
            return _left.SelectsEntry(row) && _right.SelectsEntry(row);
        }
        else
        {
            return _left.SelectsEntry(row) || _right.SelectsEntry(row);
        }
    }

    @Override
    public boolean HashableOperation() {
        if (condType == ConditionType.OR)
        {
            return false;
        }
        return _left.HashableOperation() && _right.HashableOperation();
    }
}