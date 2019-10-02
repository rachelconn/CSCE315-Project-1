package project1;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import javafx.util.Pair;
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

    // including .xml optional
    public void openCmd(String tableName){
        Table tabl;
        try {
            tabl = deserializeTable(tableName);
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("File " + tableName + ".xml doesn't exist. Unable to open.");
            System.out.println(ex);
            return;
        }
        catch (Exception ex)
        {
            System.out.println("We read something, but it was NOT a table");
            System.out.println(ex);
            return;
        }
        addTable(tabl);
    }

    public void closeCmd(String tableName){
        Table t = tables.get(tableName);
        if (t == null) {
            System.out.println("Invalid table name: " + tableName + ". Unable to close.");
            return;
        }
        try {
            serializeTable(tableName, t);
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("Unable to save table " + tableName + " into the file");
            System.out.println(ex);
            return;
        }
        tables.remove(tableName);
    }

    public void writeCmd(String tableName){
        Table t = tables.get(tableName);
        if (t == null) {
            System.out.println("Invalid table name: " + tableName + ". Unable to write.");
            return;
        }
        try {
            serializeTable(tableName, t);
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("Unable to save table " + t.getName() + " into the file");
            System.out.println(ex);
            return;
        }
    }

    public void exitCmd(){
        /*
        for (Map.Entry<> t : tables.entrySet())
        {

        }*/
    }

    // TODO: modify access to private after testing
    public void serializeTable(String filename, Table table) throws FileNotFoundException {
        if (!filename.substring(filename.length() - 4).equals(".xml"))
        {
            filename = filename + ".xml";
        }
        XMLEncoder e = new XMLEncoder(
                new BufferedOutputStream(
                        new FileOutputStream(filename)));
        e.writeObject(table);
        e.close();
    }

    private void serializeTable(Table table) throws FileNotFoundException {
        serializeTable(table.getName(), table);
    }

    // TODO: modify access to private after testing
    public Table deserializeTable(String filename) throws Exception, FileNotFoundException {
        if (!filename.substring(filename.length() - 4).equals(".xml"))
        {
            filename = filename + ".xml";
        }
        XMLDecoder d = new XMLDecoder(
                new BufferedInputStream(
                        new FileInputStream(filename)));
        Object result = d.readObject();
        Table h_result;
        if (result instanceof Table)
        {
            h_result = (Table) result;
        }
        else
        {
            throw new Exception("Unknown thing read in");
        }
        d.close();
        return h_result;
    }

    public void showCmd(Table t){
        if (t == null) {
            System.out.println("Attempting to print non-existing table.");
            return;
        }
        System.out.println(t.showTable());
    }

    public void createTable(String tableName, Table table) {
        table.setName(tableName);
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
            if(primaryKeys.contains(entry.getKey())){
                pKeyIndices.add(i);
            }
            i++;
        }
        tables.put(tableName, new Table(tableName, attributeNames, attributeTypes, pKeyIndices));
    }

    public void updateCmd(String tableName, ArrayList<Pair<String,String>> updates, Conditional conditionTree){

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
        ArrayList<Integer> wantedIndices = new ArrayList<>();
        ArrayList<String> tableAttributes = table.getAttributeNames();
        for (int i = 0; i < tableAttributes.size(); i++) {
            if (attributeNames.contains(tableAttributes.get(i))) {
                wantedIndices.add(i);
            }
        }

        ArrayList<Integer> pKeyIndicesOld = table.getpKeyIndices();
        ArrayList<Integer> pKeyIndices = new ArrayList<>();
        int j = 0;
        for(Integer i : wantedIndices){
            if(pKeyIndicesOld.contains(i)) {
                pKeyIndices.add(j);
            }
            j++;
        }

        ArrayList<String> oldAttTypes = table.getAttributeTypes();
        ArrayList<String> attributeTypes = new ArrayList<>();
        for(Integer i : wantedIndices) {
            String newAttType = oldAttTypes.get(i);
            attributeTypes.add(newAttType);
        }

        Table projection = new Table("temp", attributeNames, attributeTypes, pKeyIndices);
        HashMap<ArrayList<String>, ArrayList<String>> tableMap = table.getEntries();
        for (Map.Entry<ArrayList<String>, ArrayList<String>> entry : tableMap.entrySet()) {
            ArrayList<String> attributes = new ArrayList<>();
            //create entry to add containing each desired attribute
            for (int i : wantedIndices) {
                attributes.add(entry.getValue().get(i));
            }
            projection.addEntry(attributes);
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
        System.out.println("Table types must be union-compatible.");
        return null;
    }

    public Table productQry(Table a, Table b){
        ArrayList<String> attributeNames = new ArrayList<>(a.getAttributeNames());
        for (String s : attributeNames) {
            if (b.getAttributeNames().contains(s)) {
                System.out.println("Attempting to get product of incompatible tables:" + a.getName() + " and " + b.getName());
                return null;
            }
        }
        attributeNames.addAll(b.getAttributeNames());
        ArrayList<String> attributeTypes = new ArrayList<>(a.getAttributeTypes());
        attributeTypes.addAll(b.getAttributeTypes());
        ArrayList<Integer> pKeyIndices = new ArrayList<>(a.getpKeyIndices());
        ArrayList<Integer> bPKeyIndices = new ArrayList<>(b.getpKeyIndices());
        for(int i = 0; i<bPKeyIndices.size();i++){
            bPKeyIndices.set(i, bPKeyIndices.get(i)+a.getAttributeNames().size());
        }
        pKeyIndices.addAll(bPKeyIndices);
        Table myTable = new Table("temp",attributeNames,attributeTypes,pKeyIndices);
        for(HashMap.Entry<ArrayList<String>,ArrayList<String>> entry : a.getEntries().entrySet()){
            for(HashMap.Entry<ArrayList<String>,ArrayList<String>> bEntry : b.getEntries().entrySet()){
                ArrayList<String> entryToAdd = new ArrayList<>(entry.getValue());
                entryToAdd.addAll(bEntry.getValue());
                myTable.addEntry(entryToAdd);
            }
        }
        return myTable;
    }

    public Table naturalJoinQry(Table a, Table b){
        ArrayList<String> commonNamesAndTypes= new ArrayList<String>();//all common names and types of a,b
        ArrayList<Integer> aCommonNameIndex = new ArrayList<Integer>();//indexes of attributename list that are common ones
        ArrayList<Integer> bCommonNameIndex = new ArrayList<Integer>();//same as above but for table b
        ArrayList<String> cAttributeNames = new ArrayList<String>();//next 3 are table c properties
        ArrayList<String> cAttributeTypes = new ArrayList<String>();
        ArrayList<Integer> cpKeyIndices = new ArrayList<Integer>();
        ArrayList<Integer> aCommonNameIndexCorrespond = new ArrayList<Integer>();
        ArrayList<Integer> bCommonNameIndexCorrespond = new ArrayList<Integer>();// index 0 of both have the index of the same attributeName
        for(int i = 0;i<a.getAttributeNames().size();i++){
            cAttributeNames.add(a.getAttributeNames().get(i));
            cAttributeTypes.add(a.getAttributeTypes().get(i));
        }
        for(int i = 0;i<a.getpKeyIndices().size();i++){
            cpKeyIndices.add(a.getpKeyIndices().get(i));
        }
        for(int i = 0;i<b.getAttributeNames().size();i++){
            if(!a.getAttributeNames().contains(b.getAttributeNames().get(i))){
                cAttributeNames.add(b.getAttributeNames().get(i));
                cAttributeTypes.add(b.getAttributeTypes().get(i));
            } else {
                commonNamesAndTypes.add(b.getAttributeNames().get(i));
            }
        }
        int commonCount = 0;
        for(int i = 0;i<b.getpKeyIndices().size();i++){
            boolean isCommonIndex = false;
            for(int j = 0;j<commonNamesAndTypes.size();j++){
                if(b.getAttributeNames().get(b.getpKeyIndices().get(i)).equals(commonNamesAndTypes.get(j)))
                    isCommonIndex = true;
            }
            if(isCommonIndex){
                commonCount++;
            } else{
                cpKeyIndices.add(a.getAttributeNames().size()+i-commonCount);
            }
        }
        for(int i = 0;i<cpKeyIndices.size();i++) {
            System.out.println(cpKeyIndices.get(i));
        }
        //done making cattributename cattributetype and cpkeyIndices
        //now need to construct aCommonNameIndexCorrespond and bCommon..Correspond
        //The idea is that index n of a and b will store indexes of a.getAttributeNames and b.getAttibuteNames
        // that point to common a common attribute name
        for(int i = 0;i<a.getAttributeNames().size();i++){
            for(int j = 0; j<commonNamesAndTypes.size();j++){
                if(a.getAttributeNames().get(i).equals(commonNamesAndTypes.get(j))) {
                    aCommonNameIndex.add(i);
                }
            }
        }
        for(int i = 0;i<b.getAttributeNames().size();i++){
            for(int j = 0; j<commonNamesAndTypes.size();j++){
                if(b.getAttributeNames().get(i).equals(commonNamesAndTypes.get(j))) {
                    bCommonNameIndex.add(i);
                }
            }
        }
        for(int i =0;i< aCommonNameIndex.size();i++){
            for(int j = 0; j< bCommonNameIndex.size();j++) {
                if (a.getAttributeNames().get(aCommonNameIndex.get(i)).equals(b.getAttributeNames().get(bCommonNameIndex.get(j)))){
                    aCommonNameIndexCorrespond.add(aCommonNameIndex.get(i));
                    bCommonNameIndexCorrespond.add(bCommonNameIndex.get(j));
                }
            }
        }
       /* for(int i = 0;i<aCommonNameIndexCorrespond.size();i++){
            System.out.println(aCommonNameIndexCorrespond.get(i));
            System.out.println(bCommonNameIndexCorrespond.get(i));
        }*/
        Table c = new Table("c",cAttributeNames,cAttributeTypes,cpKeyIndices);
        for(HashMap.Entry<ArrayList<String>,ArrayList<String>> aEntry: a.getEntries().entrySet()){
            for(HashMap.Entry<ArrayList<String>,ArrayList<String>> bEntry: b.getEntries().entrySet()){
                boolean shouldAdd = true;
                for(int i = 0;i<aCommonNameIndexCorrespond.size();i++) {
                    if (!aEntry.getValue().get(aCommonNameIndexCorrespond.get(i)).equals(bEntry.getValue().get(bCommonNameIndexCorrespond.get(i)))){
                            shouldAdd = false;
                    }
                }
                if(shouldAdd){
                    //construct this pKeyAtt and entry values
                    System.out.println("adding");

                    ArrayList<String> cEntry = new ArrayList<String>();
                    for(int i = 0;i< aEntry.getValue().size();i++){
                       cEntry.add(aEntry.getValue().get(i));
                    }
                    for(int i = 0;i<bEntry.getValue().size();i++){
                        if(!bCommonNameIndexCorrespond.contains(i)){
                            cEntry.add(bEntry.getValue().get(i));
                        }
                    }
                    c.addEntry(cEntry);
                }
            }
        }
        return c;
    }

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