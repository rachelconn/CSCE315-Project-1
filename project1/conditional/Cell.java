package project1.conditional;

public class Cell {
    public String fieldName;
    public String fieldType;
    public String fieldValue;

    public Cell(String fieldName, String fieldType, String fieldValue) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.fieldValue = fieldValue;
    }

    public Cell() {
    }

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
