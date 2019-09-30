package project1;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.lang.System.*;

import org.antlr.v4.runtime.tree.ParseTree;
import project1.conditional.*;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;

public class DBMS {
    //CLASS VALUES
    private HashMap<String,Table> tables = new HashMap<>();

    //CLASS FUNCTIONS
    public void addTable(Table t){
        tables.put(t.getName(), t);
    }

    public void openCmd(){}

    public void closeCmd(){}

    public void writeCmd(){}

    public void exitCmd(){}

    // TODO: modify access to private after testing
    public void serializeTables(String filename) throws FileNotFoundException {
        if (!filename.substring(filename.length() - 4).equals(".xml"))
        {
            filename = filename + ".xml";
        }
        XMLEncoder e = new XMLEncoder(
                new BufferedOutputStream(
                        new FileOutputStream(filename)));
        e.writeObject(tables);
        e.close();
    }

    private void serializeTables() throws FileNotFoundException {
        serializeTables("tables.xml");
    }

    // TODO: modify access to private after testing
    public HashMap<String,Table> deserializeTables(String filename) throws Exception, FileNotFoundException {
        if (!filename.substring(filename.length() - 4).equals(".xml"))
        {
            filename = filename + ".xml";
        }
        XMLDecoder d = new XMLDecoder(
                new BufferedInputStream(
                        new FileInputStream(filename)));
        Object result = d.readObject();
        HashMap<String,Table> h_result;
        if (result instanceof HashMap)
        {
            h_result = (HashMap<String,Table>) result;
        }
        else
        {
            throw new Exception("Unknown thing read in");
        }
        d.close();
        return h_result;
    }

    public void showCmd(Table t){
        System.out.println(t.showTable());
    }

    public void createTable(String tableName, Table table) {
        tables.put(tableName, table);
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

    public void insertCmd(String tableName, ArrayList<String> attributes){
        Table t = tables.get(tableName);
        t.addEntry(attributes);
    }


    public void insertCmd(String tableName, Table tbl) { //alternative insert command that accounts for relational insertion
        HashMap<ArrayList<String>,ArrayList<String>> entries = tbl.getEntries();
        Table t = tables.get(tableName);
        for(Map.Entry<ArrayList<String>,ArrayList<String>> e : entries.entrySet()){
            t.addEntry(e.getValue());
        }
    }
  
    public void insertCmd(Table tbl){
        tables.put(tbl.getName(), tbl);
    }

    public void deleteCmd(String tableName, Conditional conditionTree) throws IncompatibleTypesException {
        Table toRemove = selectQry(tableName, conditionTree);
        for (Map.Entry<ArrayList<String>, ArrayList<String>> entry : toRemove.getEntries().entrySet()) {
            tables.get("tableName").deleteEntry(entry.getKey());
        }
    }
    
    public Table selectQry(String tableName, Conditional conditionTree) throws IncompatibleTypesException {
        // 1. if conditions are favorable, perform O(C) search
        boolean allHashable = true;
        allHashable = conditionTree.HashableOperation();
        // 1b) We also need to check that ALL primary keys are used. Only the use of all
        // primary keys can guarantee unique identification of an entry (for O(C) fast
        // retrieval)
        Table tableRef = tables.get(tableName);
        ArrayList<Column> primaryKeys = tableRef.getPrimaryKeys();
        ArrayList<Column> keys = tableRef.getAllColumns();
        /*
        if (allHashable && __allPrimaryKeysAndOnlyPrimaryKeysChecked(conditionTree, primaryKeys))
        {
            // TODO: implement O(C) search
            throw new NotImplementedException();
        }*/
        // 2. if conditions are not favorable, perform O(n) search
        return tableRef.filter(conditionTree);
    }

    public Table selectQry(Table tableRef, Conditional conditionTree) throws IncompatibleTypesException {
        // 1. if conditions are favorable, perform O(C) search
        boolean allHashable = true;
        allHashable = conditionTree.HashableOperation();
        // 1b) We also need to check that ALL primary keys are used. Only the use of all
        // primary keys can guarantee unique identification of an entry (for O(C) fast
        // retrieval)
        ArrayList<Column> primaryKeys = tableRef.getPrimaryKeys();
        ArrayList<Column> keys = tableRef.getAllColumns();
        /*
        if (allHashable && __allPrimaryKeysAndOnlyPrimaryKeysChecked(conditionTree, primaryKeys))
        {
            // TODO: implement O(C) search
            throw new NotImplementedException();
        }*/
        // 2. if conditions are not favorable, perform O(n) search
        return tableRef.filter(conditionTree);
    }

    public Table projectQry(Table table, ArrayList<String> attributeNames) {
        HashMap<ArrayList<String>, ArrayList<String>> tableMap = table.getEntries();
        ArrayList<Integer> wantedIndices = new ArrayList<>();
        ArrayList<String> tableAttributes = table.getAttributeNames();
        for (int i = 0; i < tableAttributes.size(); i++) {
            if (attributeNames.contains(tableAttributes.get(i))) {
                wantedIndices.add(i);
            }
        }

        ArrayList<Integer> pKeyIndicesOld = table.getpKeyIndices();
        ArrayList<Integer> pKeyIndices = new ArrayList<>();
        for(Integer i : pKeyIndicesOld){
            if(wantedIndices.contains(i)){
                pKeyIndices.add(i);
            }
        }

        ArrayList<String> oldAttTypes = table.getAttributeTypes();
        ArrayList<String> attributeTypes = new ArrayList<>();
        for(Integer i : wantedIndices) {
            String newAttType = oldAttTypes.get(i);
            attributeTypes.add(newAttType);
        }

        Table projection = new Table("temp", attributeNames, attributeTypes, new ArrayList<Integer>());
        for (Map.Entry<ArrayList<String>, ArrayList<String>> entry : tableMap.entrySet()) {
            ArrayList<String> toAdd = new ArrayList<>();
            //create entry to add containing each desired attribute
            for (int i : wantedIndices) {
                toAdd.add(entry.getValue().get(i));
            }
            if (!projection.contains(toAdd)) {
                projection.addEntry(toAdd);
            }
        }
        return projection;
    }
    public Table renameQry(Table t, ArrayList<String> newNames){
        t.setAttributeNames(newNames);
        return t;
    }

    public Table unionQry(Table a, Table b){
        if(a.getAttributeNames().equals(b.getAttributeNames()) && a.getAttributeTypes().equals(b.getAttributeTypes())){
            Table c = new Table(a);
            HashMap<ArrayList<String>, ArrayList<String>> aEntries = a.getEntries();
            HashMap<ArrayList<String>, ArrayList<String>> bEntries = b.getEntries();
            for(Map.Entry<ArrayList<String>, ArrayList<String>> bEntry : bEntries.entrySet()){
                if(aEntries.get(bEntry.getKey()) == null){ // if entry from b is not in a
                    c.addEntry(bEntry.getValue()); //add it
                }
            }
            return c;
        } else {
            System.out.println("Table types must be union-compatible.");
            return null;
        }
    }
  
    public Table differenceQry(Table a, Table b){
        if(a.getAttributeNames().equals(b.getAttributeNames()) && a.getAttributeTypes().equals(b.getAttributeTypes())) {
            Table c = new Table(a.getName(), a.getAttributeNames(), a.getAttributeTypes(), a.getpKeyIndices());
            for (Map.Entry<ArrayList<String>, ArrayList<String>> entry : a.getEntries().entrySet()) {
                if (!b.contains(entry.getValue())) {
                    c.addEntry(entry.getValue());
                }
            }
            return c;
        }
        return null;
    }

    public Table productQry(Table a, Table b){
        ArrayList<String> attributeNames = a.getAttributeNames();
        attributeNames.addAll(b.getAttributeNames());
        ArrayList<String> attributeTypes = a.getAttributeTypes();
        attributeTypes.addAll(b.getAttributeTypes());
        ArrayList<Integer> pKeyIndices = a.getpKeyIndices();
        ArrayList<Integer> bPKeyIndices = b.getpKeyIndices();
        for(int i = 0; i<bPKeyIndices.size();i++){
            bPKeyIndices.set(i, bPKeyIndices.get(i)+attributeNames.size());
        }
        pKeyIndices.addAll(bPKeyIndices);
        Table myTable = new Table("temp",attributeNames,attributeTypes,pKeyIndices);
        for(HashMap.Entry<ArrayList<String>,ArrayList<String>> entry : a.getEntries().entrySet()){
            for(HashMap.Entry<ArrayList<String>,ArrayList<String>> bEntry : b.getEntries().entrySet()){
                ArrayList<String> entryToAdd = entry.getValue();
                entryToAdd.addAll(bEntry.getValue());
                myTable.addEntry(entryToAdd);
            }
        }
        return myTable;
    }

    public Table naturalJoinQry(Table a, Table b){ return null; }

    //helper functions
    public Table getTable(String tableName){ return tables.get(tableName); }

    public void printTables() {
        System.out.println("DB contains " + tables.size() + " tables");
        for (Map.Entry<String, Table> entry : tables.entrySet()) {
            System.out.println(entry.getValue().showTable());
        }
    }

    private boolean __allPrimaryKeysAndOnlyPrimaryKeysChecked(Conditional cond, ArrayList<Column> cols)
    {
        ArrayList<String> keys1 = new ArrayList<>();
        ArrayList<String> keys2 = new ArrayList<>();

        keys1 = cond.getFieldsChecked();
        if (keys1.size() != cols.size())
        {
            return false;
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