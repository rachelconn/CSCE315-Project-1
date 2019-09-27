package project1;

import java.util.*;

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

    public void addEntry(){

    }

    public void deleteEntry(){

    }

}
