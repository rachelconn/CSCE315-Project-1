package project1;

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


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

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
        primaryStage.getIcons().add(new Image("computerIcon.png")); //<https://icons8.com/icons/set/computer">
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
            if(nameField1.getText().isBlank() || nameField2.getText().isBlank()) {
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
                result.getIcons().add(new Image("resultIcon.png")); //<"https://icons8.com/icons/set/report-card">
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
            if(nameField1.getText().isBlank() || nameField2.getText().isBlank()) {
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
                result.getIcons().add(new Image("resultIcon.png")); //<"https://icons8.com/icons/set/report-card">
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
            if(nameField1.getText().isBlank()) {
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
                result.getIcons().add(new Image("resultIcon.png")); //<"https://icons8.com/icons/set/report-card">
                result.setResizable(false);
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
            if(nameField1.getText().isBlank()) {
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

                Label resultText = new Label(nameField1.getText());
                //Set the above constructor to the call for Query 4. Ensure that the function returns a string
                //If the text goes out of the window uncomment below code
                //resultText.setWrapText(true);

                VBox layout = new VBox(20);
                layout.getChildren().addAll(title,resultText);
                layout.setAlignment(Pos.CENTER);

                Scene answer = new Scene(layout,400,400);

                result.setScene(answer);
                result.setTitle("Cover Roles Result");
                result.getIcons().add(new Image("resultIcon.png")); //<"https://icons8.com/icons/set/report-card">
                result.setResizable(false);
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
            if(nameField1.getText().isBlank()) {
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
                result.getIcons().add(new Image("resultIcon.png")); //<"https://icons8.com/icons/set/report-card">
                result.setResizable(false);
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
}
