package project1;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import javafx.util.Pair;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import project1.antlr4.MyRulesBaseListener;
import project1.antlr4.RulesLexer;
import project1.antlr4.RulesParser;
import project1.conditional.*;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;

public class DBMS {
    //CLASS VALUES
    private HashMap<String,Table> tables = new HashMap<>();

    // including .xml optional
    public void openCmd(String tableName) {
        Table tabl;
        try {
            tabl = deserializeTable(tableName);
        }
        catch (FileNotFoundException ex) {
            System.out.println("File " + tableName + ".xml doesn't exist. Unable to open.");
            System.out.println(ex);
            return;
        }
        catch (Exception ex) {
            System.out.println("We read something, but it was NOT a table");
            System.out.println(ex);
            return;
        }
        addTable(tabl);
    }

    public void closeCmd(String tableName) {
        Table t = tables.get(tableName);
        if (t == null) {
            System.out.println("Invalid table name: " + tableName + ". Unable to close.");
            return;
        }
        try {
            serializeTable(tableName, t);
        }
        catch (FileNotFoundException ex) {
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
        catch (FileNotFoundException ex) {
            System.out.println("Unable to save table " + t.getName() + " into the file");
            System.out.println(ex);
            return;
        }
    }

    public void exitCmd() {
        System.out.println("Thanks for using our system.");
        //System.exit(0);
    }

    private void serializeTable(String filename, Table table) throws FileNotFoundException {
        if (!filename.substring(filename.length() - 4).equals(".xml")) {
            filename = filename + ".xml";
        }
        XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
        e.writeObject(table);
        e.close();
    }

    private void serializeTable(Table table) throws FileNotFoundException {
        serializeTable(table.getName(), table);
    }

    private Table deserializeTable(String filename) throws Exception, FileNotFoundException {
        if (!filename.substring(filename.length() - 4).equals(".xml")) {
            filename = filename + ".xml";
        }
        XMLDecoder d = new XMLDecoder(
                new BufferedInputStream(
                        new FileInputStream(filename)));
        Object result = d.readObject();
        Table h_result;
        if (result instanceof Table) {
            h_result = (Table) result;
        }
        else {
            throw new Exception("Unknown thing read in");
        }
        d.close();
        return h_result;
    }

    public void showCmd(Table t) {
        if (t == null) {
            System.out.println("Attempting to print non-existing table.");
            return;
        }
        System.out.println(t);
    }

    public void createCmd(String tableName, LinkedHashMap<String,String> attributes, ArrayList<String> primaryKeys) {
        ArrayList<Integer> pKeyIndices = new ArrayList<>();
        ArrayList<String> attributeNames = new ArrayList<>();
        ArrayList<String> attributeTypes = new ArrayList<>();
        int i = 0;
        for(Map.Entry<String,String> entry : attributes.entrySet()) {
            attributeNames.add(entry.getKey());
            attributeTypes.add(entry.getValue());
            if(primaryKeys.contains(entry.getKey())) {
                pKeyIndices.add(i);
            }
            i++;
        }
        tables.put(tableName, new Table(tableName, attributeNames, attributeTypes, pKeyIndices));
    }

    public void updateCmd(String tableName, ArrayList<Pair<String,String>> updates, Conditional conditionTree) {
        Table t = tables.get(tableName);
        if(t.getEntries().size() == 0) return;
        HashMap<ArrayList<String>,ArrayList<String>> entries = t.getEntries();
        HashMap<ArrayList<String>,ArrayList<String>> entriesCopy = (HashMap<ArrayList<String>, ArrayList<String>>) entries.clone();
        ArrayList<Integer> indexes = t.getPKeyIndices();
        ArrayList<String> newAttributeNames = t.getAttributeNames();
        for(Map.Entry<ArrayList<String>,ArrayList<String>> entry : entriesCopy.entrySet()) {
            ArrayList<Cell> cells = t.getRow(entry.getValue());
            try {
                if(conditionTree.SelectsEntry(cells)) {
                    t.updateRow(entry.getKey(), updates);
                }
            }
            catch (Exception ex) {
                System.out.println("Exception thrown in updateCmd: ");
                System.out.println(ex);
            }

        }
    }

    public void insertCmd(String tableName, ArrayList<String> attributes) {
        Table t = tables.get(tableName);
        t.addEntry(attributes);
    }


    public void insertCmd(String tableName, Table tbl) {
        HashMap<ArrayList<String>,ArrayList<String>> entries = tbl.getEntries();
        Table t = tables.get(tableName);
        for(Map.Entry<ArrayList<String>,ArrayList<String>> e : entries.entrySet()) {
            t.addEntry(e.getValue());
        }
    }

    public void deleteCmd(String tableName, Conditional conditionTree) throws IncompatibleTypesException {
        Table toRemove = selectQry(tableName, conditionTree);
        if (toRemove.getEntries().size() == 0){
            return;
        }
        for (Map.Entry<ArrayList<String>, ArrayList<String>> entry : toRemove.getEntries().entrySet()) {
            tables.get(tableName).deleteEntry(entry.getKey());
        }
    }

    public Table selectQry(String tableName, Conditional conditionTree) throws IncompatibleTypesException {
        Table tableRef = tables.get(tableName);
        return selectQry(tableRef, conditionTree);
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
        //wantedIndices is the indices from attributeNames
        ArrayList<Integer> wantedIndices = new ArrayList<>();
        ArrayList<String> tableAttributes = table.getAttributeNames();
        for (int i = 0; i < tableAttributes.size(); i++) {
            if (attributeNames.contains(tableAttributes.get(i))) {
                wantedIndices.add(i);
            }
        }

        //Get the new indexes of the primary keys, factoring in those that weren't included in the projection
        ArrayList<Integer> pKeyIndicesOld = table.getPKeyIndices();
        ArrayList<Integer> pKeyIndices = new ArrayList<>();
        int j = 0;
        for(Integer i : wantedIndices) {
            if(pKeyIndicesOld.contains(i)) {
                pKeyIndices.add(j);
            }
            j++;
        }

        //construct the list of attribute types from wantedIndices
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

    public Table renameQry(Table t, ArrayList<String> newNames) {
        t.setAttributeNames(newNames);
        return t;
    }

    public Table unionQry(Table a, Table b) {
        if(a.getAttributeNames().equals(b.getAttributeNames()) && a.getAttributeTypes().equals(b.getAttributeTypes())) {
            Table c = new Table(a);
            HashMap<ArrayList<String>, ArrayList<String>> aEntries = a.getEntries();
            HashMap<ArrayList<String>, ArrayList<String>> bEntries = b.getEntries();
            for(Map.Entry<ArrayList<String>, ArrayList<String>> bEntry : bEntries.entrySet()) {
                if(aEntries.get(bEntry.getKey()) == null) { // if entry from b is not in a
                    c.addEntry(bEntry.getValue()); //add it
                }
            }
            return c;
        } else {
            System.out.println("Table types must be union-compatible.");
            return null;
        }
    }

    public Table differenceQry(Table a, Table b) {
        if(a.getAttributeNames().equals(b.getAttributeNames()) && a.getAttributeTypes().equals(b.getAttributeTypes())) {
            Table c = new Table(a.getName(), a.getAttributeNames(), a.getAttributeTypes(), a.getPKeyIndices());
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

    public Table productQry(Table a, Table b) {
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
        ArrayList<Integer> pKeyIndices = new ArrayList<>(a.getPKeyIndices());
        ArrayList<Integer> bPKeyIndices = new ArrayList<>(b.getPKeyIndices());
        for(int i = 0; i<bPKeyIndices.size();i++) {
            bPKeyIndices.set(i, bPKeyIndices.get(i)+a.getAttributeNames().size());
        }
        pKeyIndices.addAll(bPKeyIndices);
        Table myTable = new Table("temp",attributeNames,attributeTypes,pKeyIndices);
        for(HashMap.Entry<ArrayList<String>,ArrayList<String>> entry : a.getEntries().entrySet()) {
            for(HashMap.Entry<ArrayList<String>,ArrayList<String>> bEntry : b.getEntries().entrySet()) {
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
        for(int i = 0;i<a.getPKeyIndices().size();i++){
            cpKeyIndices.add(a.getPKeyIndices().get(i));
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
        for(int i = 0;i<b.getPKeyIndices().size();i++){
            boolean isCommonIndex = false;
            for(int j = 0;j<commonNamesAndTypes.size();j++){
                if(b.getAttributeNames().get(b.getPKeyIndices().get(i)).equals(commonNamesAndTypes.get(j)))
                    isCommonIndex = true;
            }
            if(isCommonIndex){
                commonCount++;
            } else{
                cpKeyIndices.add(a.getAttributeNames().size()+i-commonCount);
            }
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

    //HELPER FUNCTIONS
    public void addTable(Table t) {
        tables.put(t.getName(), t);
    }

    public Table getTable(String tableName) {
        return tables.get(tableName);
    }

    public void printTables() {
        System.out.println("DB contains " + tables.size() + " tables");
        for (Map.Entry<String, Table> entry : tables.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    private boolean __allPrimaryKeysAndOnlyPrimaryKeysChecked(Conditional cond, ArrayList<Column> cols) {
        ArrayList<String> keys1 = new ArrayList<>();
        ArrayList<String> keys2 = new ArrayList<>();

        keys1 = cond.getFieldsChecked();
        if (keys1.size() != cols.size()) {
            return false;
        }

        for (Column col : cols) {
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

    public Table query(String s) {
        MyRulesBaseListener listener = new MyRulesBaseListener(this);
        CharStream charStream = CharStreams.fromString(s);
        RulesLexer lexer = new RulesLexer(charStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        RulesParser parser = new RulesParser(commonTokenStream);
        RulesParser.ProgramContext programContext = parser.program();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, programContext);
        return listener.getTable();
    }

    public String query5(String actor) {
        String temp1 = "select( actorName == \"" + actor + "\") casts;";
        Table castWithActor = query(temp1);
        Table movies = getTable("movies");
        int max = 0;
        String  actorBestMovieId = "";
        ArrayList<String> movieIDs = castWithActor.getColumn("movieId");
        for (HashMap.Entry<ArrayList<String>, ArrayList<String>> movieEntry: movies.getEntries().entrySet()){
            for(int i = 0;i< movieIDs.size();i++){
                String idFromMovieEntry = movieEntry.getValue().get(0);
                String curId = movieIDs.get(i);
                if(idFromMovieEntry.equals(curId) && Integer.parseInt(movieEntry.getValue().get(2)) > max)//0 is id index and 2 is rating
                {
                    actorBestMovieId = movieEntry.getValue().get(0);
                    max = Integer.parseInt(movieEntry.getValue().get(2));
                }
            }
        }
        String  directorId = "";
        String temp3 = "select( MovieId == " + actorBestMovieId + ") crew;";
        Table movieWithDirector = query(temp3);
        directorId = movieWithDirector.getColumn("DirectorId").get(0);
        String temp2 = "select( DirectorId == " + directorId + ") crew;";
        Table moviesWithDirector = query(temp2);
        int size = moviesWithDirector.getSize();
        int min = 101;
        String directorWorstMovie = "";
        ArrayList<String> movieDirectorIds = moviesWithDirector.getColumn("MovieId");
        for (HashMap.Entry<ArrayList<String>, ArrayList<String>> movieEntry: movies.getEntries().entrySet()){
            for(int i = 0;i<movieDirectorIds.size();i++) {
                if (movieEntry.getValue().get(0).equals(movieDirectorIds.get(i)) && Integer.parseInt(movieEntry.getValue().get(2)) < min)//0 is id index and 2 is rating
                {
                    directorWorstMovie = movieEntry.getValue().get(1);
                    min = Integer.parseInt(movieEntry.getValue().get(2));
                }
            }
        }
            return directorWorstMovie;
    }

    public String query2(String actor,String appearances){
        int appear = Integer.parseInt(appearances);
        HashMap<String,Integer> actors= new HashMap<String,Integer>();
        String temp1 = "select( actorName == \"" + actor + "\") casts;";
        ArrayList<String> moviesActorPlays = query(temp1).getColumn("movieId");
        for(int i = 0;i< moviesActorPlays.size();i++){
            ArrayList<String> movieCast;
            String temp2 = "select( movieId == " + moviesActorPlays.get(i) + ") casts;";
            movieCast = query(temp2).getColumn("actorName");
                for(int j = 0;j<movieCast.size();j++){
                    if(!actors.containsKey(movieCast.get(j))) {
                        actors.put(movieCast.get(j), 1);
                    } else{
                        actors.put(movieCast.get(j), actors.get(movieCast.get(j))+1);
                    }
                }
        }
        ArrayList<String> costars = new ArrayList<String>();
        for(HashMap.Entry<String,Integer> actorEntry: actors.entrySet()){
            if(actorEntry.getValue() == appear){
                if(!actorEntry.getKey().equals(actor)) {
                    costars.add(actorEntry.getKey());
                }
            }
        }
        if(costars.toString().equals("[]")){
            return "no costars meet conditions";
        }
        return costars.toString();
    }

    private static String sanitizeString(String s){
        String s1 = s.replace(" ", "_");
        String s2 = s1.replaceAll("[^a-zA-Z0-9_]", "");
        return s2;
    }

    private static String underscoreToSpace(String s) {
        return s.replace("_"," ");
    }

    public String genreNumberToString(String num) {
        Table t = query("select (id == " + num + ") genres;");
        if (t == null) {
            System.out.println("Invalid genre number: " + num + ".");
            return "";
        }
        return t.getColumn("name").get(0);
    }

    public String getMostPlayedGenre(String actor) {
        //get table of movies an actor has been in
        HashMap<String, Integer> genreCounts = new HashMap<>();
        Table t = query("project (movieId) (select (actorName == \"" + sanitizeString(actor) + "\") casts);");
        if (t == null) {
            System.out.println("Actor " + actor + "not found in database.");
            return "";
        }
        //convert to arraylist
        ArrayList<String> movieIds = t.getColumn("movieId");
        //get genres for each movie
        for (String s : movieIds) {
            t = query("select (movieId == " + s + ") movieGenres;");
            ArrayList<String> genres = t.getColumn("genreId");
            for (String genre : genres) {
                if (!genreCounts.containsKey(genre)) {
                    genreCounts.put(genre, 1);
                } else {
                    genreCounts.put(genre, genreCounts.get(genre) + 1);
                }
            }
        }
        //find genre with most entries
        String mostCommonGenre = "";
        int maxOccurances = 0;
        for (Map.Entry<String, Integer> entry : genreCounts.entrySet()) {
            if (entry.getValue() > maxOccurances) {
                mostCommonGenre = entry.getKey();
                maxOccurances = entry.getValue();
            }
        }
        return underscoreToSpace(genreNumberToString(mostCommonGenre));
    }
    /*
    eg. calling with character name Alex returns table:

    temp:
    actorName
    Timmy_Deters
    Aaron_Costa_Ganis
    Rachel_Sellan
    Anastasios_Soulis
     */
    public String getActorsByCharacterName(String name) {
        Table t = query("project (actorName) (select (character == \"" + sanitizeString(name) + "\") casts);");
        // System.out.println(t);
        String actors = "";
        for(String actor : t.getColumn("actorName")){
            actors = actors + underscoreToSpace(actor) + ", ";
        }
        return actors;
    }

    /// the list of movies is banned
    public static class DeadLineException extends Exception {

        public DeadLineException() {
        }
    }

    // Bacon Search Helper Class
    private class AugmentedActor {
        public String name;
        public String movieRelation;

        public AugmentedActor(String name, String movieRelation) {
            this.name = name;
            this.movieRelation = movieRelation;
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            String cmp = "";
            if (obj instanceof AugmentedActor) {
                cmp = ((AugmentedActor) obj).name;
            }
            else if (obj instanceof String) {
                cmp = (String) obj;
            }
            else {
                return false;
            }
            return cmp.equals(name);
        }
    }

    // Bacon Search Helper Class
    private class AugmentedMovie {
        public String name;
        public String actorRelation;

        public AugmentedMovie(String name, String actorRelation) {
            this.name = name;
            this.actorRelation = actorRelation;
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            String cmp = "";
            if (obj instanceof AugmentedMovie) {
                cmp = ((AugmentedMovie) obj).name;
            }
            else if (obj instanceof String) {
                cmp = (String) obj;
            }
            else {
                return false;
            }
            return cmp.equals(name);
        }
    }

    private class AugmentedObject {
        public String name;
        public String id;

        public AugmentedObject(String name, String id) {
            this.name = name;
            this.id = id;
        }
    }

    public ArrayList<String> getBaconNumber(String actor1, String actor2) {
        String actor1id = getIdByActor(sanitizeString(actor1));
        String actor2id = getIdByActor(sanitizeString(actor2));
        return getBaconNumberByIDs(actor1id, actor2id);
    }

    // the public facing function
    public ArrayList<String> getBaconNumberByIDs(String actor1, String actor2) {
        try {
            LinkedHashSet<AugmentedActor> actorContainer = new LinkedHashSet<>();
            actorContainer.add(new AugmentedActor(actor1, "INITIAL NODE"));
            ArrayList<AugmentedObject> augmentedObjectArrayList = getBaconNumberSearchActor(
                    actorContainer,
                    actor2,
                    new LinkedHashSet<String>(),
                    new LinkedHashSet<String>()
            );

            ArrayList<String> output = new ArrayList<>();
            for (AugmentedObject ao : augmentedObjectArrayList) {
                output.add(ao.name);
            }
            return output;
        }
        catch (DeadLineException de) {
            return null;
        }
    }

    // the version of the recursive search in actors
    private ArrayList<AugmentedObject> getBaconNumberSearchActor(LinkedHashSet<AugmentedActor> actorSearchNodes,
                                                        String actorTarget,
                                                        LinkedHashSet<String> actorsSearched,
                                                        LinkedHashSet<String> moviesSearched) throws DeadLineException{
        // generate the next stack on the cake by getting all the movies.
        // no need to check if we win yet, just make sure no dupe nodes are searched
        LinkedHashSet<AugmentedMovie> moviesToSearch = new LinkedHashSet<>();
        for (AugmentedActor aa : actorSearchNodes) {
            LinkedHashSet<String> movies = getMoviesByActor(aa.name);
            for (String movieName : movies) {
                if (!moviesSearched.contains(movieName))
                {
                    moviesToSearch.add(new AugmentedMovie(movieName, aa.name));
                    moviesSearched.add(movieName);
                }
            }
        }

        // end of the line, kiddo! nothing to look at here!!
        if (moviesToSearch.isEmpty()) {
            throw new DeadLineException();
        }
        /*  // PERFORMANCE DEBUGGING
        System.out.println(String.format(
                "Movies searched: %s, Actors searched %s, Total filters: %s",
                moviesSearched.size(),
                actorsSearched.size(),
                Table.STATS_totalFilters
        ));*/

        ArrayList<AugmentedObject> pathFromEnd = getBaconNumberSearchMovie(
                moviesToSearch,
                actorTarget,
                actorsSearched,
                moviesSearched
        );

        String idOfConnectingActor = pathFromEnd.get(pathFromEnd.size() - 1).id;
        String lastMovieId = FindPrevConnectorOnActor(actorSearchNodes, idOfConnectingActor);
        pathFromEnd.add(new AugmentedObject(getMovieById(lastMovieId), lastMovieId));
        return pathFromEnd;
    }

    // the version of the recursive search in movies
    private ArrayList<AugmentedObject> getBaconNumberSearchMovie(LinkedHashSet<AugmentedMovie> movieSearchNodes,
                                                        String actorTarget,
                                                        LinkedHashSet<String> actorsSearched,
                                                        LinkedHashSet<String> moviesSearched) throws DeadLineException{
        // same as searching actors, but prematurely kill if you find the right actor
        // it all unfolds like a house of cards
        LinkedHashSet<AugmentedActor> actorsToSearch = new LinkedHashSet<>();
        for (AugmentedMovie am : movieSearchNodes) {
            LinkedHashSet<String> actors = getActorsByMovie(am.name);
            for (String actorName : actors) {
                if (actorTarget.equals(actorName))
                {
                    // THE SEARCH IS OVER 🔫🔫🔫
                    // Hamza bin Laden, Son of Qaeda Founder, Is Dead 🔫🔫🔫🔫

                    ArrayList<AugmentedObject> output = new ArrayList<>();
                    output.add(new AugmentedObject(getActorById(actorName), actorName));
                    output.add(new AugmentedObject(getMovieById(am.name), am.name));
                    output.add(new AugmentedObject(getActorById(am.actorRelation), am.actorRelation));
                    return output;
                }
                if (!actorsSearched.contains(actorName))
                {
                    actorsToSearch.add(new AugmentedActor(actorName, am.name));
                    actorsSearched.add(actorName);
                }
            }
        }

        // end of the line, kiddo! nothing to look at here!!
        if (actorsToSearch.isEmpty()) {
            throw new DeadLineException();
        }

        /*  // PERFORMANCE DEBUGGING
        System.out.println(String.format(
                "Movies searched: %s, Actors searched %s, Total filters: %s",
                moviesSearched.size(),
                actorsSearched.size(),
                Table.STATS_totalFilters
        ));*/
        ArrayList<AugmentedObject> pathFromEnd = getBaconNumberSearchActor(
                actorsToSearch,
                actorTarget,
                actorsSearched,
                moviesSearched
        );

        String idOfConnectingMovie = pathFromEnd.get(pathFromEnd.size() - 1).id;
        String lastActorId = FindPrevConnectorOnMovie(movieSearchNodes, idOfConnectingMovie);
        pathFromEnd.add(new AugmentedObject(getActorById(lastActorId), lastActorId));
        return pathFromEnd;
    }

    private LinkedHashSet<String> getMoviesByActor(String actor) {
        Table t = this.tables.get("casts").fastFilter("actorId", actor);
        return new LinkedHashSet<String>(t.getColumn("movieId"));
    }

    private LinkedHashSet<String> getActorsByMovie(String movie) {
        Table t = this.tables.get("casts").fastFilter("movieId", movie);
        return new LinkedHashSet<String>(t.getColumn("actorId"));
    }

    private String getActorById(String actor) {
        Table t = this.tables.get("casts").fastFilter("actorId", actor);
        if (t == null) {
            return "INITIAL NODE";
        }
        return t.getColumn("actorName").get(0);
    }

    private String getMovieById(String movie) {
        Table t = this.tables.get("movies").fastFilter("id", movie);
        if (t == null) {
            return "INITIAL NODE";
        }
        return t.getColumn("title").get(0);
    }

    private String getIdByActor(String actor) {
        Table t = this.tables.get("casts").fastFilter("actorName", actor);
        return t.getColumn("actorId").get(0);
    }

    private String getIdByMovie(String movie) {
        Table t = this.tables.get("movies").fastFilter("title", movie);
        return t.getColumn("id").get(0);
    }

    private String FindPrevConnectorOnActor(LinkedHashSet<AugmentedActor> laa, String actorName) {
        for (AugmentedActor aa : laa) {
            if (aa.name.equals(actorName)) {
                return aa.movieRelation;
            }
        }
        return "\n[FATAL] connector lost\n";
    }

    private String FindPrevConnectorOnMovie(LinkedHashSet<AugmentedMovie> laa, String movieName) {
        for (AugmentedMovie am : laa) {
            if (am.name.equals(movieName)) {
                return am.actorRelation;
            }
        }
        return "\n[FATAL] connector lost\n";
    }
}