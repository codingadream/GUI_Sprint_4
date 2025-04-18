package com.example.market;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

import java.util.HashMap;
import java.util.Map;
import java.awt.Point;


public class MarketController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    public Boolean redTurn = true;
    public Boolean blueTurn = true;

    public char player;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("YAY REPLAY THIS SOS GAME!");
    }

    @FXML
    public CheckBox simpleGame;
    public CheckBox generalGame;
    public Map<Point, Character> grid = new HashMap<>();

    public int sScore;
    public int oScore;

    public CheckBox bluePlayerS;
    public CheckBox bluePlayerO;
    public CheckBox redPlayerS;
    public CheckBox redPlayerO;

    @FXML
    private ChoiceBox<Integer> choiceBox;

    @FXML
    public GridPane gridPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        simpleGame.setSelected(true);


        bluePlayerS.setSelected(true);
        redPlayerO.setSelected(true);

        choiceBox.setValue(8);
        choiceBox.getItems().addAll(2, 3, 4, 5, 6, 7, 8);

        choiceBox.setOnAction(event -> gridSize());


        gridSize();

    }

    @FXML
    protected void SimpleGameChecked() {
        generalGame.setSelected(false);
        gridSize();


        int size = choiceBox.getValue();

    }
    @FXML
    protected void onSimpleGameChecked(char winner) {
        generalGame.setSelected(false);
        gridSize();


        int size = choiceBox.getValue();


        System.out.println("THE GAME IS OVER!! PLAYER  " + winner + " HAS WON THIS GAME!!");
        welcomeText.setText("THE GAME IS OVER!! PLAYER  " + winner + " HAS WON THIS GAME!!");
    }

    @FXML
    protected void bSChecked() {
        if (bluePlayerS.isSelected()) {
            redPlayerO.setSelected(true);

            bluePlayerO.setSelected(false);
            redPlayerS.setSelected(false);


        }
    }

    @FXML
    protected void rSChecked() {
        if(redPlayerS.isSelected()){

          redPlayerO.setSelected(false);
          bluePlayerS.setSelected(false);
          bluePlayerO.setSelected(true);

      }

    }

    @FXML
    protected void rOChecked() {
        if(redPlayerO.isSelected()){

          redPlayerS.setSelected(false);
          bluePlayerO.setSelected(false);
          bluePlayerS.setSelected(true);

       }

    }



    @FXML
    protected void bOChecked(){
        if(bluePlayerO.isSelected()) {

        bluePlayerS.setSelected(false);
        redPlayerO.setSelected(false);

        redPlayerS.setSelected(true);
        }
    }




    @FXML
    protected void onGeneralGameChecked() {
        simpleGame.setSelected(false);
        gridSize();

        String whoWon = "";

        if(sScore > oScore){
            whoWon = "S";
            welcomeText.setText("WOW PLAYER " + whoWon + " WON THE GAME BY FORMING THE MOST SOS's!!! CONGRATS!!");
        } else if (sScore < oScore) {
            whoWon = "O";
            welcomeText.setText("WOW PLAYER " + whoWon + " WON THE GAME BY FORMING THE MOST SOS's!!! CONGRATS!!");
        }

        System.out.println("WOW PLAYER " + whoWon + " WON THE GAME BY FORMING THE MOST SOS's!!! CONGRATS!!");





    }

    @FXML
    boolean isThereSOS(int r, int c){
        char playerChar = grid.get(new Point(r,c));

        if (playerChar == 'O'){
            return path(r, c, -1, 0, 'S') && path(r, c, 1, 0, 'S') ||
                    path(r, c, 0, -1, 'S') && path(r, c, 0, 1, 'S') ||
                    path(r, c, -1, -1, 'S') && path(r, c, 1, 1, 'S') ||
                    path(r, c, -1, 1, 'S') && path(r, c, 1, -1, 'S');
        }
        if (playerChar == 'S') {
            return (path(r, c, 1, 0, 'O') && path(r, c, 2, 0, 'S')) ||
                    (path(r, c, -1, 0, 'O') && path(r, c, -2, 0, 'S')) ||
                    (path(r, c, 0, 1, 'O') && path(r, c, 0, 2, 'S')) ||
                    (path(r, c, 0, -1, 'O') && path(r, c, 0, -2, 'S')) ||
                    (path(r, c, 1, 1, 'O') && path(r, c, 2, 2, 'S')) ||
                    (path(r, c, -1, -1, 'O') && path(r, c, -2, -2, 'S')) ||
                    (path(r, c, 1, -1, 'O') && path(r, c, 2, -2, 'S')) ||
                    (path(r, c, -1, 1, 'O') && path(r, c, -2, 2, 'S'));
        }

        return false;

    }

    @FXML
    boolean path(int r, int c, int pr, int pc, char expected) {
        int nRow = r + pr;
        int nCol = c + pc;
        Point p = new Point(nRow, nCol);
        return grid.getOrDefault(p, ' ') == expected;
    }





    @FXML
    protected void gridSize() {
        int selected = choiceBox.getValue();

        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getChildren().clear();

        for (int size = 0; size < selected; size++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100.0 / selected);
            gridPane.getColumnConstraints().add(col);
        }

        for (int size = 0; size < selected; size++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100.0 / selected);
            gridPane.getRowConstraints().add(row);
        }

        for (int row = 0; row < selected; row++) {
            for (int column = 0; column < selected; column++) {
                StackPane cell = new StackPane();
                cell.setStyle("-fx-border-color: black");
                cell.setPrefSize(40, 40);

                gridPane.add(cell, column, row);

                final int Frow = row;
                final int Fcolumn = column;




                cell.setOnMouseClicked(event -> WhenCellisClicked(Frow, Fcolumn, cell));

            }
        }
    }

    @FXML
    protected void WhenCellisClicked(int row, int column, StackPane cell) {


        Character player = 'M';
        if (redTurn == true && redPlayerS.isSelected()) {
            redTurn = false;
            player = 'S';

            System.out.println(redTurn);
        } else if (!redTurn && bluePlayerO.isSelected()) {
            redTurn = true;
            player = 'O';

            System.out.println(redTurn);
        }

        if (redTurn == true && redPlayerO.isSelected()) {
            redTurn = false;
            player = 'O';

            System.out.println(redTurn);
        } else if (!redTurn && bluePlayerS.isSelected()) {
            redTurn = true;
            player = 'S';

            System.out.println(redTurn);
        }

        grid.put(new Point(row, column), player);


        if (isThereSOS(row, column)) {
            //Boolean gameOver = true;
            System.out.println("Player '" + player + "' wins by forming SOS!");

            if(generalGame.isSelected()) {

                if (player == 'S') {
                    sScore++;

                } else if (player == 'O') {
                    oScore++;

                }
            }

            if(simpleGame.isSelected()) {

                onSimpleGameChecked(player);
            }
        }



        System.out.println(sScore + "___" + oScore );

        String forLabel = "";
        if (player == 'S') {
            forLabel = "S";
        } else if (player == 'O') {
            forLabel = "O";
        }

        //I created manual reactive size for player peices depending on the size of the grid
        Label label = new Label(forLabel);
        int selected = choiceBox.getValue();
        if (selected == 2){
            label.setStyle("-fx-font-size: 40px;");
        } else if (selected == 3){
            label.setStyle("-fx-font-size: 30px;");
        } else if (selected == 4){
            label.setStyle("-fx-font-size: 25px;");
        }else if(selected == 5){
                label.setStyle("-fx-font-size: 20px;");
        }else if (selected == 6){
            label.setStyle("-fx-font-size: 19px;");
        }else if (selected == 7){
            label.setStyle("-fx-font-size: 16px;");
        }else if (selected == 8){
            label.setStyle("-fx-font-size: 15px;");
        }

        cell.getChildren().add(label);

        boolean isFull = fullGrid();
        if(generalGame.isSelected() && isFull == true){
            onGeneralGameChecked();
        }



    }

    @FXML
    boolean fullGrid() {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof StackPane cell) {

                if (cell.getChildren().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
}
