package project1;

import java.lang.reflect.Array;
import java.util.*;

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

    public void deleteCmd(){}

    public Table selectQry(){ return null; }

    public Table projectQry(){ return null; }

    public Table renameQry(){ return null; }

    public Table unionQry(){ return null; }

    public Table differenceQry(){ return null; }

    public Table productQry(){ return null; }

    public Table naturalJoinQry(){ return null; }

}
