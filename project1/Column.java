/*
Table helper

simple structure
 */


package project1;

import java.util.Objects;

public class Column {
    public String colName;
    public String colType;

    public Column(String colName, String colType) {
        this.colName = colName;
        this.colType = colType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Column column = (Column) o;
        return Objects.equals(colName, column.colName) &&
                Objects.equals(colType, column.colType);
    }
}
