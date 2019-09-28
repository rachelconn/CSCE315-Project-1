package project1;

import java.lang.reflect.Array;
import java.util.*;

public class DBMS {

    private HashMap<String,Table> tables = new HashMap<>();

    public void openCmd(){

    }

    public void closeCmd(){

    }

    public void writeCmd(){

    }

    public void exitCmd(){

    }

    public void showCmd(){

    }

    public void createCmd(String tableName, LinkedHashMap<String,String> attributes, ArrayList<String> primaryKeys){
        ArrayList<Integer> pKeyIndices = new ArrayList<>();
        ArrayList<String> attributeNames = new ArrayList<>();
        ArrayList<String> attributeTypes = new ArrayList<>();
        int i = 0;
        for(Map.Entry<String,String> entry : attributes.entrySet()) {
            attributeNames.add(entry.getKey());
            attributeTypes.add(entry.getValue());
            if(primaryKeys.contains(entry.getKey())){ //TODO:
                pKeyIndices.add(i);
            }
            i++;
        }
        tables.put(tableName, new Table(tableName, attributeNames, attributeTypes, pKeyIndices));
    }

    public void updateCmd(){

    }

    public void insertCmd(){}

    public void deleteCmd(){}

    public Table selectQry(String tableName, ArrayList<Conditional> conditions) throws NotImplementedException, IncompatibleTypesException {
        // 1. if conditions are favorable, perform O(C) search
        boolean allHashable = true;
        for (Conditional cnd : conditions)
        {
            allHashable = allHashable && cnd.HashableOperation();
        }
        // 1b) We also need to check that ALL primary keys are used. Only the use of all
        // primary keys can guarantee unique identification of an entry (for O(C) fast
        // retrieval)
        Table tableRef = tables.get(tableName)
        ArrayList<Column> primaryKeys = tableRef.getPrimaryKeys();
        ArrayList<Column> keys = tableRef.getAllColumns();
        if (allHashable && __allPrimaryKeysAndOnlyPrimaryKeysChecked(conditions, primaryKeys))
        {
            // TODO: implement O(C) search
            throw new NotImplementedException();
        }

        // 2. if conditions are not favorable, perform O(n) search
        return tableRef.getAllKeysThatSatisfyConditions(conditions);
    }

    // TODO: unit test
    /*
    Returns true if all Conditionals in conds refer only to primary keys and that all
    (primary) keys listed in cols are in conds.
    Returns false otherwise.

    Refactored into separate method to improve readability of method selectQry
     */
    private boolean __allPrimaryKeysAndOnlyPrimaryKeysChecked(ArrayList<Conditional> conds, ArrayList<Column> cols)
    {
        if (conds.size() != cols.size())
        {
            return false;
        }
        ArrayList<String> keys1 = new ArrayList<>();
        ArrayList<String> keys2 = new ArrayList<>();

        for (Conditional cond : conds)
        {
            keys1.add(cond.getFieldName());
        }
        for (Column col : cols)
        {
            keys2.add(col.colName);
        }
        Comparator<String> strCmp = new Comparator<String>() {
            @Override
            public int compare(String obj1, String obj2) {
                if (obj1 == obj2) {
                    return 0;
                }
                if (obj1 == null) {
                    return -1;
                }
                if (obj2 == null) {
                    return 1;
                }
                return obj1.compareTo(obj2);
            }
        };
        keys1.sort(strCmp);
        keys2.sort(strCmp);
        return keys1.equals(keys2);
    }

    public Table projectQry(){}

    public Table renameQry(){}

    public Table unionQry(){}

    public Table differenceQry(){}

    public Table productQry(){}

    public Table naturalJoinQry(){}






}
