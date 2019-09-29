package project1;

import java.io.Serializable;
import java.sql.Array;
import project1.conditional.*;
import java.lang.System;
import java.util.*;
import java.util.Map.Entry;


public class Table implements Serializable {

    private String name;
    private ArrayList<String> attributeNames;
    private ArrayList<String> attributeTypes;
    private ArrayList<Integer> pKeyIndices;
    private HashMap<ArrayList<String>,ArrayList<String>> entries; //key is the list of primary keys, value is a list of all the attributes

    public Table(String name, ArrayList<String> attributeNames, ArrayList<String> attributeTypes, ArrayList<Integer> pKeyIndices) {
        this.name = name;
        this.attributeNames = attributeNames;
        this.attributeTypes = attributeTypes;
        this.pKeyIndices = pKeyIndices;
        this.entries = new HashMap<>();
        this.pKeyIndices.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
    }

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
            if (pKeys.contains(cols.get(i)))
            {
                this.pKeyIndices.add(i);
            }
        }
    }

    public Table(Table a) {
        this.name = a.name;
        this.attributeTypes = a.attributeTypes;
        this.attributeNames = a.attributeNames;
        this.pKeyIndices = a.pKeyIndices;
        this.entries = a.entries;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public HashMap<ArrayList<String>, ArrayList<String>> getEntries() {
        return entries;
    }

    public ArrayList<String> getAttributeTypes() {
        return attributeTypes;
    }

    public ArrayList<String> getAttributeNames() { return attributeNames; }

    public void setAttributeNames(ArrayList<String> newNames){ this.attributeNames = newNames; }

    public ArrayList<Integer> getpKeyIndices() { return pKeyIndices; }

    public ArrayList<String> getpKeyNames() {
        ArrayList<String> pKeyNames = new ArrayList<>();
        for(Integer i : pKeyIndices){
            String pKeyName = attributeNames.get(i);
            pKeyNames.add(pKeyName);
        }
        return pKeyNames;
    }



    public void addEntry(ArrayList<String> attributes){
        ArrayList<String> pKeys = new ArrayList<>();
        for(int i = 0 ; i < pKeyIndices.size() ; i++){
            int keyIndex = pKeyIndices.get(i);
            String pKey = attributes.get(keyIndex);
            pKeys.add(pKey);
        }
        entries.put(pKeys, attributes);
    }

    public void deleteEntry(ArrayList<String> key){
        entries.remove(key);
    }

    public ArrayList<Column> getPrimaryKeys() {
        ArrayList<Column> pKeys = new ArrayList<>();
        for (Integer i : pKeyIndices)
        {
            pKeys.add(new Column(attributeNames.get(i), attributeTypes.get(i)));
        }
        return pKeys;
    }

    public ArrayList<Column> getAllColumns() {
        ArrayList<Column> cols = new ArrayList<>();
        for (int i = 0; i < attributeNames.size(); ++i)
        {
            cols.add(new Column(attributeNames.get(i), attributeTypes.get(i)));
        }
        return cols;
    }

    public boolean contains(ArrayList<String> attributes) {
        return entries.containsValue(attributes);
    }

    public Table getAllKeysThatSatisfyConditions(Conditional cond) throws IncompatibleTypesException {
        Table results = new Table("temp", this.getAllColumns(), this.getPrimaryKeys());
        for (Entry<ArrayList<String>,ArrayList<String>> entry : entries.entrySet())
        {
            boolean satisfiesConditions = true;
            ArrayList<String> row = entry.getValue();
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
            if (satisfiesConditions)
            {
                results.addEntry(row);
            }
        }
        return results;
    }



    public String showTable() {
        String toShow = this.name + ":\n";
        for(HashMap.Entry<ArrayList<String>,ArrayList<String>> entry : this.entries.entrySet()){
            for(int j = 0; j < entry.getValue().size(); j++){
                toShow = toShow + entry.getValue().get(j) + " ";
            }
            toShow = toShow + "\n";
        }
        return toShow;
    }

    // for serialization only
    public void setAttributeTypes(ArrayList<String> attributeTypes) {
        this.attributeTypes = attributeTypes;
    }

    public void setpKeyIndices(ArrayList<Integer> pKeyIndices) {
        this.pKeyIndices = pKeyIndices;
    }

    public void setEntries(HashMap<ArrayList<String>, ArrayList<String>> entries) {
        this.entries = entries;

    public ArrayList<String> getAttributeNames(){
        return this.attributeNames;
    }

    public ArrayList<String> getAttributeTypes(){
        return this.attributeTypes;
    }

    public ArrayList<Integer> getpKeyIndices(){
        return this.pKeyIndices;
    }
}