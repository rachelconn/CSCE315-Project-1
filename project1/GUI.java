package project1;

import java.io.File;
import java.io.IOException;
import java.lang.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import csce315.project1.Credits;
import csce315.project1.Movie;
import csce315.project1.MovieDatabaseParser;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GUI extends Application {

    private Scene mainMenu, bacon, constellation, typecasting, coverRoles, bodwod;
    private DBMS myDBMS;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        myDBMS = new DBMS();
        List<Movie> moviesList = MovieDatabaseParser.deserializeMovies("./data/movies.json");
        generateMovieTable(moviesList, myDBMS);

        List<Credits> creditsList = MovieDatabaseParser.deserializeCredits("./data/credits.json");
        generateCastTable(creditsList, myDBMS);
        generateCrewTable(creditsList,myDBMS);

        Label title = new Label("Howdy!");
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFont(new Font(25));
        title.setAlignment(Pos.CENTER);

        Label title2 = new Label("Click any button to switch to your desired function.");
        title2.setTextAlignment(TextAlignment.CENTER);
        title2.setFont(new Font(15));
        title2.setAlignment(Pos.CENTER);

        Button baconBut = new Button("Bacon Number");
        baconBut.setOnAction(actionEvent -> buildBaconScene(primaryStage));

        Button constBut = new Button("Constellation of Co-Stars");
        constBut.setOnAction(actionEvent -> buildConstScene(primaryStage));

        Button typeBut = new Button("Typecasting");
        typeBut.setOnAction(actionEvent -> buildTypeScene(primaryStage));

        Button coverBut = new Button("Cover Roles");
        coverBut.setOnAction(actionEvent -> buildCoverScene(primaryStage));

        Button bodBut = new Button("Best of Days, Worst of Days");
        bodBut.setOnAction(actionEvent -> buildBodScene(primaryStage));

        HBox menu1 = new HBox(20,baconBut,constBut,typeBut);
        menu1.setAlignment(Pos.CENTER);
        HBox menu2 = new HBox(20,coverBut,bodBut);
        menu2.setAlignment(Pos.CENTER);
        VBox menu = new VBox(20,title,title2,menu1,menu2);
        menu.setPadding(new Insets(25,25,25,25));
        menu.setAlignment(Pos.CENTER);
        mainMenu = new Scene(menu,450,250);

        primaryStage.setScene(mainMenu);
        String url = new File("./project1/computerIcon.png").toURI().toURL().toString();
        primaryStage.getIcons().add(new Image(url)); //<https://icons8.com/icons/set/computer">
        primaryStage.setTitle("GUI TESTING");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void buildBaconScene(Stage primaryStage) {
        Label baconTitle = new Label("Bacon Number : Query 1");
        baconTitle.setFont(new Font(30));
        baconTitle.setAlignment(Pos.CENTER);

        Label description = new Label("Enter 2 actors to find the degree of film career separation between them.");
        description.setFont(new Font(15));
        description.setAlignment(Pos.CENTER);

        Button mainBaconBut = new Button("Return to Main Menu");
        mainBaconBut.setOnAction(actionEvent -> primaryStage.setScene(mainMenu));

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20,20,20,20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);
        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
        Label nameLabel1 = new Label("Actor 1: ");
        gridPane.add(nameLabel1,0,0);
        Label nameLabel2 = new Label("Actor 2: ");
        gridPane.add(nameLabel2,0,1);
        TextField nameField1 = new TextField();
        nameField1.setPrefHeight(40);
        gridPane.add(nameField1,1,0);
        TextField nameField2 = new TextField();
        nameField2.setPrefHeight(40);
        gridPane.add(nameField2,1,1);
        Label error = new Label("");
        gridPane.add(error,1,2);
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(actionEvent -> {
            if(nameField1.getText().isEmpty() || nameField2.getText().isEmpty()) {
                error.setText("One or both fields are blank! Enter actor names.");
                error.setTextFill(Color.color(1,0,0));
            }
            else {
                error.setText("");
                final Stage result = new Stage();
                result.initModality(Modality.NONE);
                result.initOwner(primaryStage);

                Label title = new Label("Result: ");
                title.setFont(new Font(20));

                Label resultText = new Label(nameField1.getText() + " " + nameField2.getText());
                //Set the above constructor to the call for Query 1. Ensure that the function returns a string
                //If the text goes out of the window uncomment below code
                //resultText.setWrapText(true);

                VBox layout = new VBox(20);
                layout.getChildren().addAll(title,resultText);
                layout.setAlignment(Pos.CENTER);

                Scene answer = new Scene(layout,400,400);

                result.setScene(answer);
                String resultURL = "";
                try {
                    resultURL = new File("./project1/resultIcon.png").toURI().toURL().toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                result.getIcons().add(new Image(resultURL)); //<"https://icons8.com/icons/set/report-card">
                result.setTitle("Bacon Number Result");
                result.setResizable(false);
                result.show();
            }
        });
        gridPane.add(submitButton,0,2);

        VBox baconMenu = new VBox(20);
        baconMenu.getChildren().addAll(baconTitle,description,gridPane,mainBaconBut);
        baconMenu.setPadding(new Insets(25,25,25,25));
        baconMenu.setAlignment(Pos.TOP_CENTER);

        bacon = new Scene(baconMenu,800,450);
        primaryStage.setScene(bacon);
    }

    private void buildConstScene(Stage primaryStage){
        Label constTitle = new Label("Constellation of Co-Stars : Query 2");
        constTitle.setFont(new Font(30));
        constTitle.setAlignment(Pos.CENTER);

        Label description = new Label("Enter an actor and the number of co-star appearances to find the number of times that two actors co-starred together.");
        description.setTextAlignment(TextAlignment.CENTER);
        description.setWrapText(true);
        description.setFont(new Font(15));
        description.setAlignment(Pos.CENTER);

        Button mainConstBut = new Button("Return to Main Menu");
        mainConstBut.setOnAction(actionEvent -> primaryStage.setScene(mainMenu));

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20,20,20,20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        ColumnConstraints columnOneConstraints = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);
        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
        Label nameLabel1 = new Label("Actor: ");
        gridPane.add(nameLabel1,0,1);
        Label nameLabel2 = new Label("# of co-star appearances: ");
        gridPane.add(nameLabel2,0,2);
        TextField nameField1 = new TextField();
        nameField1.setPrefHeight(40);
        gridPane.add(nameField1,1,1);
        TextField nameField2 = new TextField();
        nameField2.setPrefHeight(40);
        gridPane.add(nameField2,1,2);
        Label error = new Label("");
        gridPane.add(error,1,3);
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(actionEvent -> {
            if(nameField1.getText().isEmpty() || nameField2.getText().isEmpty()) {
                error.setText("One or both fields are blank! Enter an actor name and the number of co-star appearances.");
                error.setTextFill(Color.color(1,0,0));
            }
            else {
                error.setText("");
                //System.out.println(nameField1.getText() + " " + nameField2.getText());
                final Stage result = new Stage();
                result.initModality(Modality.NONE);
                result.initOwner(primaryStage);

                Label title = new Label("Result: ");
                title.setFont(new Font(20));

                Label resultText = new Label(nameField1.getText() + " " + nameField2.getText());
                //Set the above constructor to the call for Query 2. Ensure that the function returns a string
                //If the text goes out of the window uncomment below code
                //resultText.setWrapText(true);

                VBox layout = new VBox(20);
                layout.getChildren().addAll(title,resultText);
                layout.setAlignment(Pos.CENTER);

                Scene answer = new Scene(layout,400,400);

                result.setScene(answer);
                result.setTitle("Constellation Result");
                String resultURL = "";
                try {
                    resultURL = new File("./project1/resultIcon.png").toURI().toURL().toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                result.getIcons().add(new Image(resultURL)); //<"https://icons8.com/icons/set/report-card">
                result.setResizable(false);
                result.show();
            }
        });
        gridPane.add(submitButton,0,3);

        VBox constMenu = new VBox(20);
        constMenu.getChildren().addAll(constTitle,description,gridPane,mainConstBut);
        constMenu.setPadding(new Insets(25,25,25,25));
        constMenu.setAlignment(Pos.TOP_CENTER);
        constellation = new Scene(constMenu,800,450);
        primaryStage.setScene(constellation);
    }

    private void buildTypeScene(Stage primaryStage) {
        Label typeTitle = new Label("Typecasting : Query 3");
        typeTitle.setFont(new Font(30));
        typeTitle.setAlignment(Pos.CENTER);

        Button mainTypeBut = new Button("Return to Main Menu");
        mainTypeBut.setOnAction(actionEvent -> primaryStage.setScene(mainMenu));

        Label description = new Label("Enter an actor to find the types of roles that an actor is most typecasted in.");
        description.setTextAlignment(TextAlignment.CENTER);
        description.setWrapText(true);
        description.setFont(new Font(15));
        description.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20,20,20,20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        ColumnConstraints columnOneConstraints = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);
        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
        Label nameLabel1 = new Label("Actor: ");
        gridPane.add(nameLabel1,0,1);
        TextField nameField1 = new TextField();
        gridPane.add(nameField1,1,1);
        Label error = new Label("");
        gridPane.add(error,1,2);
        nameField1.setPrefHeight(40);
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(actionEvent -> {
            if(nameField1.getText().isEmpty()) {
                error.setText("Field is blank! Enter an actor name.");
                error.setTextFill(Color.color(1,0,0));
            }
            else {
                error.setText("");
                System.out.println(nameField1.getText());
                final Stage result = new Stage();
                result.initModality(Modality.NONE);
                result.initOwner(primaryStage);

                Label title = new Label("Result: ");
                title.setFont(new Font(20));

                Label resultText = new Label(nameField1.getText());
                //Set the above constructor to the call for Query 3. Ensure that the function returns a string
                //If the text goes out of the window uncomment below code
                //resultText.setWrapText(true);

                VBox layout = new VBox(20);
                layout.getChildren().addAll(title,resultText);
                layout.setAlignment(Pos.CENTER);

                Scene answer = new Scene(layout,400,400);

                result.setScene(answer);
                result.setTitle("Typecasting Result");

                String resultURL = "";
                try {
                    resultURL = new File("./project1/resultIcon.png").toURI().toURL().toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                result.getIcons().add(new Image(resultURL)); //<"https://icons8.com/icons/set/report-card">                result.setResizable(false);
                result.show();
            }
        });
        gridPane.add(submitButton,0,2);

        VBox typeMenu = new VBox(20);
        typeMenu.getChildren().addAll(typeTitle,description,gridPane,mainTypeBut);
        typeMenu.setPadding(new Insets(25,25,25,25));
        typeMenu.setAlignment(Pos.TOP_CENTER);
        typecasting = new Scene(typeMenu,800,450);
        primaryStage.setScene(typecasting);
    }

    private void buildCoverScene(Stage primaryStage) {
        Label coverTitle = new Label("Cover Roles : Query 4");
        coverTitle.setFont(new Font(30));
        coverTitle.setAlignment(Pos.CENTER);

        Label description = new Label("Enter a character name to find the actors who played the same characters (or at least the name of those characters).");
        description.setTextAlignment(TextAlignment.CENTER);
        description.setWrapText(true);
        description.setFont(new Font(15));
        description.setAlignment(Pos.CENTER);

        Button mainCoverBut = new Button("Return to Main Menu");
        mainCoverBut.setOnAction(actionEvent -> primaryStage.setScene(mainMenu));

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20,20,20,20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);
        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
        Label nameLabel1 = new Label("Character name: ");
        gridPane.add(nameLabel1,0,1);
        TextField nameField1 = new TextField();
        nameField1.setPrefHeight(40);
        gridPane.add(nameField1,1,1);
        Label error = new Label("");
        gridPane.add(error,1,2);
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(actionEvent -> {
            if(nameField1.getText().isEmpty()) {
                error.setText("Field is blank! Enter a Character name.");
                error.setTextFill(Color.color(1,0,0));
            }
            else {
                error.setText("");
                final Stage result = new Stage();
                result.initModality(Modality.NONE);
                result.initOwner(primaryStage);

                Label title = new Label("Result: ");
                title.setFont(new Font(20));

                Label resultText = new Label(myDBMS.getActorsByCharacterName(nameField1.getText()));
                //Set the above constructor to the call for Query 4. Ensure that the function returns a string
                //If the text goes out of the window uncomment below code
                resultText.setWrapText(true);
                resultText.setTextAlignment(TextAlignment.CENTER);

                VBox layout = new VBox(20);
                layout.getChildren().addAll(title,resultText);
                layout.setAlignment(Pos.CENTER);

                Scene answer = new Scene(layout,400,400);

                result.setScene(answer);
                result.setTitle("Cover Roles Result");

                String resultURL = "";
                try {
                    resultURL = new File("./project1/resultIcon.png").toURI().toURL().toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                result.getIcons().add(new Image(resultURL)); //<"https://icons8.com/icons/set/report-card">
                // result.setResizable(false);
                result.show();
            }
        });
        gridPane.add(submitButton,0,2);

        VBox coverMenu = new VBox(20);
        coverMenu.getChildren().addAll(coverTitle,description,gridPane,mainCoverBut);
        coverMenu.setPadding(new Insets(25,25,25,25));
        coverMenu.setAlignment(Pos.TOP_CENTER);
        coverRoles = new Scene(coverMenu,800,450);
        primaryStage.setScene(coverRoles);
    }

    private void buildBodScene(Stage primaryStage) {
        Label bodTitle = new Label("Best of Days, Worst of Days : Query 5");
        bodTitle.setFont(new Font(30));
        bodTitle.setAlignment(Pos.CENTER);

        Label description = new Label("Enter an actor name to find the worst-ranked movie of the director of that actor's best-ranked movie.");
        description.setTextAlignment(TextAlignment.CENTER);
        description.setWrapText(true);
        description.setFont(new Font(15));
        description.setAlignment(Pos.CENTER);

        Button mainBodBut = new Button("Return to Main Menu");
        mainBodBut.setOnAction(actionEvent -> primaryStage.setScene(mainMenu));

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20,20,20,20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        ColumnConstraints columnOneConstraints = new ColumnConstraints(200, 200, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);
        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
        Label nameLabel1 = new Label("Actor: ");
        gridPane.add(nameLabel1,0,1);
        TextField nameField1 = new TextField();
        gridPane.add(nameField1,1,1);
        nameField1.setPrefHeight(40);
        Label error = new Label("");
        gridPane.add(error,1,2);
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(actionEvent -> {
            if(nameField1.getText().isEmpty()) {
                error.setText("Field is blank! Enter an actor name.");
                error.setTextFill(Color.color(1,0,0));
            }
            else {
                error.setText("");
                final Stage result = new Stage();
                result.initModality(Modality.NONE);
                result.initOwner(primaryStage);

                Label title = new Label("Result: ");
                title.setFont(new Font(20));

                Label resultText = new Label(nameField1.getText());
                //Set the above constructor to the call for Query 1. Ensure that the function returns a string
                //If the text goes out of the window uncomment below code
                //resultText.setWrapText(true);

                VBox layout = new VBox(20);
                layout.getChildren().addAll(title,resultText);
                layout.setAlignment(Pos.CENTER);

                Scene answer = new Scene(layout,400,400);

                result.setScene(answer);
                result.setTitle("Best of Days, Worst of Days Result");

                String resultURL = "";
                try {
                    resultURL = new File("./project1/resultIcon.png").toURI().toURL().toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                result.getIcons().add(new Image(resultURL)); //<"https://icons8.com/icons/set/report-card">                result.setResizable(false);
                result.show();
            }
        });
        gridPane.add(submitButton,0,2);

        VBox bodMenu = new VBox(20);
        bodMenu.getChildren().addAll(bodTitle,description,gridPane,mainBodBut);
        bodMenu.setPadding(new Insets(25,25,25,25));
        bodMenu.setAlignment(Pos.TOP_CENTER);
        bodwod = new Scene(bodMenu,800,450);
        primaryStage.setScene(bodwod);
    }

    static void generateMovieTable(List<Movie> moviesList, DBMS myDBMS) {
        //Define MOVIES table
        ArrayList<String> attNames = new ArrayList<>();
        attNames.add("id"); attNames.add("title"); attNames.add("rating");
        ArrayList<String> attTypes = new ArrayList<>();
        attTypes.add("INTEGER"); attTypes.add("VARCHAR(50)"); attTypes.add("INTEGER");
        ArrayList<Integer> pKeys = new ArrayList<>();
        pKeys.add(0);
        Table movies = new Table("movies",attNames, attTypes, pKeys);
        myDBMS.addTable(movies);

        //define GENRES table
        ArrayList<String> gNames = new ArrayList<>();
        gNames.add("id"); gNames.add("name");
        ArrayList<String> gTypes = new ArrayList<>();
        gTypes.add("INTEGER"); gTypes.add("VARCHAR(20)");
        ArrayList<Integer> gPKeys = new ArrayList<>();
        gPKeys.add(0);
        Table genresTable = new Table("genres", gNames, gTypes, gPKeys);
        myDBMS.addTable(genresTable);

        //define MOVIEGENRES table
        ArrayList<String> mgNames = new ArrayList<>();
        mgNames.add("movieId"); mgNames.add("genreId");
        ArrayList<String> mgTypes = new ArrayList<>();
        mgTypes.add("INTEGER"); mgTypes.add("INTEGER");
        ArrayList<Integer> mgKeys = new ArrayList<>();
        mgKeys.add(0); mgKeys.add(1);
        Table movieGenres = new Table("movieGenres", mgNames, mgTypes, mgKeys);
        myDBMS.addTable(movieGenres);

        //parse through each movie in the list
        for(Movie m : moviesList){
            ArrayList<String> attributes = new ArrayList<>();
            String mId = Integer.toString(m.getId());
            attributes.add(mId);
            String title = m.getTitle();
            title = sanitizeString(title);
            attributes.add(title);
            //rating is on a scale 1-10 with one decimal point, multiply by 10 to get integer between 1-100
            //round to get rid of any floating point precision error
            int rating = (int) Math.round(m.getVote_average() * 10);
            attributes.add(Integer.toString(rating));
            myDBMS.insertCmd("movies", attributes);

            List<Movie.Genre> genreList = m.getGenres();
            //parse through list of genres given
            for(Movie.Genre g : genreList){
                String gId = Integer.toString(g.getId());
                String gName = g.getName();

                //add the genre lookup to the genres table
                ArrayList<String> gAtts = new ArrayList<>();
                gAtts.add(gId); gAtts.add(gName);
                myDBMS.insertCmd("genres", gAtts);
                //add the movie-genre relation to the table
                ArrayList<String> mgAtts = new ArrayList<>();
                mgAtts.add(mId); mgAtts.add(gId);
                myDBMS.insertCmd("movieGenres", mgAtts);
            }
        }
    }

    public static void generateCastTable(List<Credits> creditsList, DBMS myDBMS){
        //define CASTS table
        ArrayList<String> cNames = new ArrayList<>();
        cNames.add("movieId");
        cNames.add("actorId");
        cNames.add("actorName");
        cNames.add("character");
        ArrayList<String> cTypes = new ArrayList<>();
        cTypes.add("INTEGER");
        cTypes.add("INTEGER");
        cTypes.add("VARCHAR(50)");
        cTypes.add("VARCHAR(50)");
        ArrayList<Integer> cKeys = new ArrayList<>();
        cKeys.add(0); cKeys.add(1);
        Table castTable = new Table("casts", cNames, cTypes, cKeys);
        myDBMS.addTable(castTable);

        for(Credits c : creditsList){
            String movieId = c.getId();
            List<Credits.CastMember> cast = c.getCastMember();
            for(Credits.CastMember cm : cast){
                String actorId = Integer.toString(cm.getId());
                String name = sanitizeString(cm.getName());
                String character = sanitizeString(cm.getCharacter());

                ArrayList<String> castAtts = new ArrayList<>();
                castAtts.add(movieId); castAtts.add(actorId);
                castAtts.add(name); castAtts.add(character);
                myDBMS.insertCmd("casts", castAtts);
            }

        }

    }

    public static void generateCrewTable(List<Credits> creditsList, DBMS myDBMS){
        //define CASTS table
        ArrayList<String> cNames = new ArrayList<>();
        cNames.add("movieId");
        cNames.add("DirectorId");
        cNames.add("DirectorName");
        ArrayList<String> cTypes = new ArrayList<>();
        cTypes.add("INTEGER");
        cTypes.add("INTEGER");
        cTypes.add("VARCHAR(50)");
        ArrayList<Integer> cKeys = new ArrayList<>();
        cKeys.add(0); cKeys.add(1);
        Table crewTable = new Table("crew", cNames, cTypes, cKeys);
        myDBMS.addTable(crewTable);

        for(Credits c : creditsList){
            String movieId = c.getId();
            List<Credits.CrewMember> crew = c.getCrewMember();
            boolean done = false;
            for(Credits.CrewMember cm : crew){
                if(done)
                    break;
                if(cm.getJob().equals("Director")) {
                    done = true;
                    String DirectorId = Integer.toString(cm.getId());
                    String name = sanitizeString(cm.getName());

                    ArrayList<String> crewAtts = new ArrayList<>();
                    crewAtts.add(movieId);
                    crewAtts.add(DirectorId);
                    crewAtts.add(name);
                    myDBMS.insertCmd("crew", crewAtts);
                }
            }

        }

    }

    private static String sanitizeString(String s){
        String s1 = s.replace(" ", "_");
        String s2 = s1.replaceAll("[^a-zA-Z0-9_]", "");
        return s2;
    }
}
