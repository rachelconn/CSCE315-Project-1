package project1;

import java.io.Serializable;
import java.sql.Array;
import project1.conditional.*;
import java.lang.System;
import java.util.*;
import java.util.Map.Entry;


public class Table implements Serializable {

    //CLASS FIELDS
    private String name;
    private ArrayList<String> attributeNames;
    private ArrayList<String> attributeTypes;

    public ArrayList<Integer> getPKeyIndices() {
        return pKeyIndices;
    }

    public void setPKeyIndices(ArrayList<Integer> pKeyIndices) {
        this.pKeyIndices = pKeyIndices;
    }

    private ArrayList<Integer> pKeyIndices;
    private HashMap<ArrayList<String>,ArrayList<String>> entries; //key is the list of primary keys, value is a list of all the attributes

    public int getSize()
    {
        return entries.size();
    }
  
    //CLASS CONSTRUCTORS
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


    //GETTERS AND SETTERS
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<ArrayList<String>, ArrayList<String>> getEntries() {
        return entries;
    }

    public void setEntries(HashMap<ArrayList<String>, ArrayList<String>> entries) { this.entries = entries; }

    public ArrayList<String> getAttributeTypes() {
        return attributeTypes;
    }

    public void setAttributeTypes(ArrayList<String> attributeTypes) {
        this.attributeTypes = attributeTypes;
    }

    public ArrayList<String> getAttributeNames() { return attributeNames; }

    public void setAttributeNames(ArrayList<String> newNames){ this.attributeNames = newNames; }

    public ArrayList<Integer> getpKeyIndices() { return pKeyIndices; }

    public void setpKeyIndices(ArrayList<Integer> pKeyIndices) { this.pKeyIndices = pKeyIndices; }

    public ArrayList<String> getpKeyNames() {
        ArrayList<String> pKeyNames = new ArrayList<>();
        for(Integer i : pKeyIndices){
            String pKeyName = attributeNames.get(i);
            pKeyNames.add(pKeyName);
        }
        return pKeyNames;
    }

    //CLASS FUNCTIONS
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

    public Table filter(Conditional cond) throws IncompatibleTypesException {
        Table results = new Table("temp", this.getAllColumns(), this.getPrimaryKeys());
        for (Entry<ArrayList<String>,ArrayList<String>> entry : entries.entrySet())
        {
            ArrayList<String> row = entry.getValue();
            int fieldIndex = attributeNames.indexOf(cond.fieldName);
            // SelectsEntry checks the type and makes sure the entry passes the condition
            boolean satisfiesConditions = cond.SelectsEntry(attributeTypes.get(fieldIndex),row.get(fieldIndex));
            if (satisfiesConditions)
            {
                results.addEntry(row);
            }
        }
        return results;
    }

    public String showTable() {
        String toShow = this.name + ":\n";
        for(String s : attributeNames) {
            toShow = toShow + s + " ";
        }
        toShow += "\n";
        for(HashMap.Entry<ArrayList<String>,ArrayList<String>> entry : this.entries.entrySet()){
            for(int j = 0; j < entry.getValue().size(); j++){
                toShow = toShow + entry.getValue().get(j) + " ";
            }
            toShow = toShow + "\n";
        }
        return toShow;
    }
}