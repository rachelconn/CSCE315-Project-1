package project1;

import java.sql.Array;
import java.util.*;
import java.util.Map.Entry;

public class Table {
    private String name;
    private ArrayList<String> attributeNames;
    private ArrayList<String> attributeTypes;
    private ArrayList<Integer> pKeyIndices;
    private HashMap<ArrayList<String>,ArrayList<String>> entries; //key is the list of primary keys, value is the other attributes

    public Table(String name, ArrayList<String> attributeNames, ArrayList<String> attributeTypes, ArrayList<Integer> pKeyIndices) {
        this.name = name;
        this.attributeNames = attributeNames;
        this.pKeyIndices = pKeyIndices;
        this.entries = new HashMap<>();
    }

    /*
    Constructor created for convenience, using Columns instead
     */
    public Table(String name, ArrayList<Column> cols, ArrayList<Column> pKeys)
    {
        this.name = name;
        this.pKeyIndices = new ArrayList<>();
        this.attributeNames = new ArrayList<>();
        this.attributeTypes = new ArrayList<>();
        this.entries = new HashMap<>();

        for (int i = 0; i < cols.size(); ++i)
        {
            attributeNames.add(cols.get(i).colName);
            attributeTypes.add(cols.get(i).colType);
        }
    }

    public void addEntry(ArrayList<String> row){

    }

    // vanity method
    public void addEntry(ArrayList<String> row, ArrayList<Column> columnOrder) {

    }

    public void deleteEntry(){

    }

    /*
    Returns an ArrayList of Columns (structure containing column name and type)
    containing all primary keys
     */
    public ArrayList<Column> getPrimaryKeys() {
        ArrayList<Column> pKeys = new ArrayList<>();
        for (Integer i : pKeyIndices)
        {
            pKeys.add(new Column(attributeNames.get(i), attributeTypes.get(i)));
        }
        return pKeys;
    }

    /*
    Returns an ArrayList of Columns (structure containing column name and type)
    containing all keys
     */
    public ArrayList<Column> getAllColumns() {
        ArrayList<Column> cols = new ArrayList<>();
        for (int i = 0; i < attributeNames.size(); ++i)
        {
            cols.add(new Column(attributeNames.get(i), attributeTypes.get(i)));
        }
        return cols;
    }

    /*
    Basically an implementation of O(n) search from DBMS.SelectQry
    implemented in Table since that's where the data is
    no need to copy stuff around this way
     */
    public Table getAllKeysThatSatisfyConditions(ArrayList<Conditional> conds) throws IncompatibleTypesException {
        Table results = new Table("temp", this.getAllColumns(), this.getPrimaryKeys());
        for (Entry<ArrayList<String>,ArrayList<String>> entry : entries.entrySet())
        {
            boolean satisfiesConditions = true;
            ArrayList<String> row = entry.getValue();
            for (Conditional cond : conds)
            {
                // SelectsEntry checks to make sure the entry passes the conditions
                // Eg. SELECT * FROM tabl WHERE dog == 1
                // if field dog really is 1 (and the types match), it returns true
                int fieldIndex = attributeNames.indexOf(cond.fieldName);
                satisfiesConditions = satisfiesConditions &&
                        cond.SelectsEntry(
                                row.get(fieldIndex),
                                attributeTypes.get(fieldIndex)
                        );
                if (!satisfiesConditions) {break;}
            }
            if (satisfiesConditions)
            {
                results.addEntry(row);
            }
        }
        return results;
    }
}
