package project1;

import java.util.*;

public class Table {
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

    public void deleteEntry(){

    }

}
