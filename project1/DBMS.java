package project1;

import java.lang.reflect.Array;
import java.util.*;
import java.lang.System.*;
import project1.conditional.*;

public class DBMS {
    //CLASS VALUES
    private HashMap<String,Table> tables = new HashMap<>();

    //CLASS FUNCTIONS
    public void openCmd(){}

    public void closeCmd(){}

    public void writeCmd(){}

    public void exitCmd(){}

    public void showCmd(){}

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

    public void insertCmd(String tableName, ArrayList<String> attributes){
        Table t = tables.get(tableName);
        t.addEntry(attributes);
    }

    public void insertCmd(String tableName, Table tbl){} //alternative insert command that accounts for relational insertion

    public void deleteCmd(String tableName, ArrayList<Conditional> conditions) throws NotImplementedException, IncompatibleTypesException {
        boolean allHashable = true;
        for (Conditional cnd : conditions)
        {
            allHashable = allHashable && cnd.HashableOperation();
        }
        // 1b) We also need to check that ALL primary keys are used. Only the use of all
        // primary keys can guarantee unique identification of an entry (for O(C) fast
        // retrieval)
        // 2. if conditions are not favorable, perform O(n) search
        Table toRemove = selectQry(tableName, conditions);
        for (Map.Entry<ArrayList<String>, ArrayList<String>> entry : toRemove.asHashMap().entrySet()) {
            tables.get("tableName").deleteEntry(entry.getKey());
        }
    }

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
        Table tableRef = tables.get(tableName);
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

    public Table projectQry(Table table, ArrayList<String> attributeNames, ArrayList<String> attributeTypes, ArrayList<Integer> pKeyIndices) {
        HashMap<ArrayList<String>, ArrayList<String>> tableMap = table.asHashMap();
        Table projection = new Table("temp", attributeNames, attributeTypes, new ArrayList<Integer>());
        for (Map.Entry<ArrayList<String>, ArrayList<String>> entry : tableMap.entrySet()) {
            ArrayList<String> values = entry.getValue();
            if (!projection.contains(values)) {
                projection.addEntry(entry.getValue());
            }
        }
        return projection;
    }

    public Table renameQry(){ return null; }

    public Table unionQry(){ return null; }

    public Table differenceQry(){ return null; }

    public Table productQry(){ return null; }

    public Table naturalJoinQry(){ return null; }

    //helper functions
    public void printTables() {
        System.out.println("DB contains " + tables.size() + " tables");
        for (Map.Entry<String, Table> entry : tables.entrySet()) {
            System.out.println(entry.getValue().showTable());
        }
    }

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
}
