package project1.conditional;

public class Cell {
    public String fieldName;
    public String fieldType;
    public String fieldValue;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Cell))
        {
            return false;
        }
        Cell other = (Cell) obj;
        return other.fieldValue.equals(fieldName) && other.fieldType.equals(fieldType) && other.fieldValue.equals(fieldValue);
    }

    @Override
    public String toString() {
        return "Cell{" +
                "fieldName='" + fieldName + '\'' +
                ", fieldType='" + fieldType + '\'' +
                ", fieldValue='" + fieldValue + '\'' +
                '}';
    }
}
