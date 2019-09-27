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

    public Table selectQry(){}

    public Table projectQry(){}

    public Table renameQry(){}

    public Table unionQry(){}

    public Table differenceQry(){}

    public Table productQry(){}

    public Table naturalJoinQry(){}






}
