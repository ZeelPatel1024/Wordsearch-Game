package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.HashMap;
import java.util.Map;

import java.util.Timer;
import java.util.TimerTask;

public class HelloController {

    public HelloController(){

    }

    @FXML
    Label lblTurn, lblResult, lblShowMatching, lblWordSelected,lblWinOrLose, lblStartLetter, lblEndLetter,lblNotice,lblLetterChosen,lblWordCheck;
    @FXML
    Button btnStart,btnStart2,btnFxLetterChange,btnHandleHolidays,btnHandleBillionares,btnHandleGreekMythology,btnHandleCountries;


    @FXML
    GridPane gdpPlayGrid;

    @FXML
    GridPane gdpPlayGrid2;

    @FXML
    ImageView imgBackground;


    @FXML
    private ListView lstListOfWords, lstWordsFound, lstPlayerOne, lstPlayerTwo,lstOfLetters,lstDefinitions,lstDefinitionForTestWords;

    @FXML
    private TextField txtEnterPlayerOneName,txtEnterPlayerTwoName,txtEnterPlayerOneAge,txtEnterPlayerTwoAge,txtSetDimention,txtFindLetter;
    //create dimention var
    int dimention1 = 10;
    //Creates a 2D array of the GridSpot Class that represent one spot on a Tic Tac Toe Board
    private GridSpot [][] board;
    //Creates a 2D array of Buttons

    private Button [][] boardSpotsBTN;//you can change these for dynamic.

    int turn, rowIndex, colIndex;


    private ArrayList<Integer> directions = new ArrayList<>();

//    private ArrayList<String> words = new ArrayList<>();

    private String[] words = {"CAT","DOG","ANIMALS","POTATO","MOON","NEPTUNE","ANJALI","ZEEL","CHALOPHA"};
    private String[] faultyWords = {"WORLD","CHS","BUTTON","BEZOS","HELLO","MARVEL","PECAN","WORDSEARCH"};

    private ArrayList<String> candyLandWords = new ArrayList<String>(Arrays.asList("kitkat","aero","lollipop","hersheys","almondjoy","twix","reeses","milkyway","m&m"));
    private ArrayList<String> animalTopiaWords = new ArrayList<String>(Arrays.asList("rabbit","ocotpus","owl","kangaroo","lion","whale","zebra","peakock","wolf"));
    private ArrayList<String> holidayWords = new ArrayList<String>(Arrays.asList("newyears","memorial","independence","laborday","columbus","valentines","christmas","halloween","thanksgiving"));
    private ArrayList<String> billionareWords = new ArrayList<String>(Arrays.asList("jeffbezoz","musk","gates","zuckerberg","buffet","ellision","page","brin","ambani"));
    private ArrayList<String> greekMythologyWords = new ArrayList<String>(Arrays.asList("zeus","posidon","hermes","iris","hecate","gaea","psyche","persephone","kratos"));
    private ArrayList<String> countryWords = new ArrayList<String>(Arrays.asList("america","brazil","belgium","canada","chile","denmark","egypt","france","germany"));

    private ArrayList<String> testWords = new ArrayList<String>(Arrays.asList("ambitious","banana","destiny","quest","children"));

    private ArrayList<String> wordsUsedInWordsearch = new ArrayList<String>();

    String genreChoosen = " ";

//    private int[][] blah = new int[2][3];

    char[] letters = new char[26];

    boolean foundHighlight = false;

    String wordSelectedStart = "";
    String wordSelectedEnd = "";

    private Integer[] startNum = new Integer[2];
    private Integer[] endNum = new Integer[2];

    Map<String, String> numberMapping = new HashMap<>();

    int spotCheck = 0;

    boolean twoPlayerModeActive = false;
    boolean clickOnChangeLetterButton = false;

    Player p1 = new Player("Unknown",0," ",0);
//    Player p2 = new Player("Unknown",0," ",0);

    int level = 0;

    String subLetter = " ";

    int secondsPassed = 0;

    int timeStart = 0;
    int timeEnd = 0;

    int timePassed = 0;

    String wordSelected = " ";
    int randSpot = 0;


//    String theWord = "cat";

    //tie is used to check if there is a tie or a win
    private boolean tie = false;

    Timer myTimer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            secondsPassed++;
            System.out.println("time passed: " + secondsPassed);
        }
    };

    @FXML
    private void start(){

        myTimer.scheduleAtFixedRate(task,1000,1000);

        //after adding the grdipane in scenebuilder, modify the fxml manually to eliminate rows and columns
        //these are some sample properties to change how the gridpane looks

//        System.out.println("*");

        lstDefinitionForTestWords.getItems().add("Having or showing a strong desire and determination to succeed.");
        lstDefinitionForTestWords.getItems().add("A long curved friut which grows in clusters and has soft pulpy flesh and yellow skin when ripe.");
        lstDefinitionForTestWords.getItems().add("The events that will necessarily happen to a particular person or thing in the future.");
        lstDefinitionForTestWords.getItems().add("A long or arduous search for something.");
        lstDefinitionForTestWords.getItems().add("A young human being below the age of puberty or below the legal age of majority.");

        gdpPlayGrid.setHgap(10);
        gdpPlayGrid.setVgap(10);
        gdpPlayGrid.setPadding(new Insets(10));
        gdpPlayGrid.setGridLinesVisible(true);
        gdpPlayGrid.setAlignment(Pos.CENTER);


        lstPlayerOne.getItems().add("Name: " + p1.getName());
        lstPlayerOne.getItems().add("Age: " + p1.getAge());
        lstPlayerOne.getItems().add("Points: " + p1.getPoints());

//        lstPlayerTwo.getItems().add("Name: " + p2.getName());
//        lstPlayerTwo.getItems().add("Age: " + p2.getAge());
//        lstPlayerTwo.getItems().add("Points: " + p2.getPoints());

        getHashmapStuff();
        lstDefinitions.setDisable(false);

//        turn is a random number: either 0 or 1 which represents the first or second player
//        turn = (int) Math.round(Math.random()*1);
//        lblTurn.setText("It is Player " + (turn%2) + "'s Turn!" );
        btnStart.setDisable(true);
        for (int i = 0; i < boardSpotsBTN.length; i++) {
            for (int j = 0; j < boardSpotsBTN.length; j++) {
                //initializes each of the indexes in the Button array
                boardSpotsBTN[i][j] = new Button();
                //sets each Buttons text
                boardSpotsBTN[i][j].setText(" ");
                //sets size of buttons//perfered height abd width
                boardSpotsBTN[i][j].setMinHeight(35);
                boardSpotsBTN[i][j].setMinWidth(35);
                boardSpotsBTN[i][j].setPrefHeight(30);
                boardSpotsBTN[i][j].setPrefWidth(30);
                //similar to initializing a new class but for each spot in the array
                board[i][j] = new GridSpot(0);//calls constructor
                //Parameters:  object, columns, rows
                //adds each of the ImageViews to the GridPane in javafx at a specific spot
                //Paramters:  object, columns, rows
                gdpPlayGrid.add(boardSpotsBTN[i][j], j, i);
                gdpPlayGrid.setStyle("-fx-background-color: #000000;");

            }
        }

        //this is the mouse event: same as if you were adding it in scenebuilder but this lets you do it dynamically
        //for button uits a button event for mouse its a mouse event.
        EventHandler z = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                //gets the row index of which button you clicked on
                //casting itno a button
                rowIndex = GridPane.getRowIndex(((Button) t.getSource()));
                //gets the column index of which button you clicked on
                colIndex = GridPane.getColumnIndex(((Button) t.getSource()));
//                System.out.println(rowIndex);
//                System.out.println(colIndex);
//                lblTaken.setText(" ");
                display(rowIndex, colIndex, t);
//                if(!checkDone()) {
////                    System.out.println(turn % 2);
////                    lblResult.setText("Result:");
////                    switchTurn();
//                }else{
//                    reset();
//                }

            }
        };
//
//        System.out.println(blah[0].length);

        for (int i = 0; i < boardSpotsBTN.length; i++) {
            for (int j = 0; j < boardSpotsBTN.length; j++) {
                //setting the onMouseClicked property for each of the buttons to call z (the event handler)
                boardSpotsBTN[i][j].setStyle("-fx-background-color: #ccfffe;");
                boardSpotsBTN[i][j].setOnAction(z);
            }
        }


        if (p1.getGenreChosen().equals("Candyland")){
            for (int i = 0; i < candyLandWords.size(); i++) {
//                lstListOfWords.getItems().add(candyLandWords.get(i));
                displayWord(candyLandWords.get(i),dimention1,board,boardSpotsBTN,candyLandWords);
                checkSpot();
            }
        }

        if (p1.getGenreChosen().equals("AnimalTopia")){
            for (int i = 0; i < animalTopiaWords.size(); i++) {
//                lstListOfWords.getItems().add(animalTopiaWords.get(i));
                displayWord(animalTopiaWords.get(i),dimention1,board,boardSpotsBTN,animalTopiaWords);
                checkSpot();
            }
        }

        if (p1.getGenreChosen().equals("Holidays")){
            for (int i = 0; i < holidayWords.size(); i++) {
//                lstWordListTwo.getItems().add(holidayWords.get(i));
                displayWord(holidayWords.get(i),dimention1,board,boardSpotsBTN,holidayWords);
                checkSpot();
            }
        }

        if (p1.getGenreChosen().equals("Billionares")){
            for (int i = 0; i < billionareWords.size(); i++) {
//                lstWordListTwo.getItems().add(billionareWords.get(i));
                displayWord(billionareWords.get(i),dimention1,board,boardSpotsBTN,billionareWords);
                checkSpot();
            }
        }

        if (p1.getGenreChosen().equals("Countries")){
            for (int i = 0; i < countryWords.size(); i++) {
//                lstWordListTwo.getItems().add(billionareWords.get(i));
                displayWord(countryWords.get(i),dimention1,board,boardSpotsBTN,countryWords);
                checkSpot();
            }
        }

        if (p1.getGenreChosen().equals("GreekMythology")){
            for (int i = 0; i < greekMythologyWords.size(); i++) {
//                lstWordListTwo.getItems().add(billionareWords.get(i));
                displayWord(greekMythologyWords.get(i),dimention1,board,boardSpotsBTN,greekMythologyWords);
                checkSpot();
            }
        }

//        for (int i = 0; i < faultyWords.length; i++) {
//            displayWord(faultyWords[i],dimention1,board,boardSpotsBTN);
//            checkSpot();
//        }

        fillInTheRestOfTheWordSearch(board,boardSpotsBTN);

        directions.clear();
        resetDirectionArray();

        for (int i = 0; i < directions.size(); i++) {
            System.out.println(directions.get(i));
        }

//        displayWord(theWord);

    }

    //fill in the rest of the words
    public void fillInTheRestOfTheWordSearch(GridSpot[][] boardTest, Button[][] boardButtons){

        for(int i = 0; i < 26; i++){
            letters[i] = (char)(97 + i);
        }



        for (int i = 0; i < boardButtons.length;i++){
            for (int j = 0; j < boardButtons.length;j++){
                int pickRandSpot = (int)(Math.random()*letters.length);
                if (boardTest[i][j].getSpot() == 0 || boardTest[i][j].getSpot() == 2){
                    boardButtons[i][j].setText(letters[pickRandSpot]+"");
                }

            }
        }

    }

    //shows the 0s and 1s in the sout.
    public void checkSpot(){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j].getSpot());

            }
            System.out.println(" ");
        }
    }

    public void resetDirectionArray(){
        //1 is right
        directions.add(1);
        //2 is left
        directions.add(2);
        //3 is down
        directions.add(3);
        //4 is up
        directions.add(4);
        //5 is down right
        directions.add(5);
        //6 is down left
        directions.add(6);
        //7 is up left
        directions.add(7);
        //8 is up right
        directions.add(8);
    }

    //put the main words in
    public void displayWord(String subedWord,int dimention, GridSpot[][] boardTest, Button[][] boardButtons, ArrayList arraySearch){

        directions.clear();

        resetDirectionArray();

        int lengthWord = subedWord.length();
        int rCor = (int) (Math.random() * dimention);
        int cCor = (int) (Math.random() * dimention);

        int directionCor = (int)((Math.random()*directions.size()));
        int chosenDirection = directions.get(directionCor);

        boolean foundSpot = false;
        boolean canPutItIn = true;

        boolean continuePutting = false;

        //chnage this to the times when it isnt a square grid.
        int totalSpots = dimention*dimention;

        System.out.println("rCor 1 : " + rCor);
        System.out.println("cCor1 : " + cCor);
        System.out.println("dimention1 : "+ dimention);
        System.out.println("length Check1 : " + (dimention-lengthWord));

        int count = 1;


        //check for the direction the word is gonna be put, check if the spot is 0 or 1. If it is 1, check the letter at that 1 spot, if that word matches the word that is being put in, find
        //another spot, if it does not put the word in.


        while (foundSpot == false) {

                System.out.println("runned " + chosenDirection + " for word " + subedWord);

                //go right
                if (chosenDirection == 1) {

                    if (cCor <= (dimention - lengthWord)) {

                        System.out.println("if demo runs");

                        for (int i = 0; i < lengthWord; i++) {

                            if (boardTest[rCor][i + cCor].getSpot() == 0){
                                continuePutting = true;

                            }else{
                                continuePutting =false;
                                break;
                            }

                        }
                    }

                    if (continuePutting == true){


                        if (cCor <= (dimention - lengthWord)) {

                            System.out.println("if 1 runs");

                            for (int i = 0; i < lengthWord; i++) {

                                System.out.println("for runs");
                                boardButtons[rCor][i + cCor].setText(subedWord.charAt(i) + "");
                                boardTest[rCor][i + cCor].setSpot(1);

                            }

                            for (int i = 0; i < arraySearch.size(); i++) {
                                if (arraySearch.get(i).equals(subedWord)){
                                    lstListOfWords.getItems().add(subedWord);
                                    wordsUsedInWordsearch.add(subedWord);
                                }
                            }

                            foundSpot = true;

                        }else{
                            canPutItIn = false;
                        }



                    }else{
                        canPutItIn = false;
                    }


                }else if (chosenDirection == 2) {//go left

                    if (cCor >= (subedWord.length())-1) {

                        System.out.println("if demo runs");

                        for (int i = 0; i < lengthWord; i++) {

                            if (boardTest[rCor][cCor - i].getSpot() == 0){
                                continuePutting = true;

                            }else{
                                continuePutting =false;
                                break;
                            }

                        }
                    }

                    //            while (foundSpot == false && totalSpots > 0){
                    if (continuePutting == true){
                        if (cCor >= (subedWord.length())-1) {
                            System.out.println("if 1 runs");
                            for (int i = 0; i < subedWord.length(); i++) {


                                boardButtons[rCor][cCor - i].setText(subedWord.charAt(i) + "");
                                boardTest[rCor][cCor - i].setSpot(1);
                                System.out.println("for runs");
                            }

                            for (int i = 0; i < arraySearch.size(); i++) {
                                if (arraySearch.get(i).equals(subedWord)){
                                    lstListOfWords.getItems().add(subedWord);
                                    wordsUsedInWordsearch.add(subedWord);
                                }
                            }

                            foundSpot = true;
                        }else{
                            canPutItIn = false;
                        }
                    }else{
                        canPutItIn = false;
                    }


                }else if (chosenDirection == 3) {//go down

                    if (rCor <= (dimention - lengthWord)) {

                        System.out.println("if demo runs");

                        for (int i = 0; i < lengthWord; i++) {

                            if (boardTest[rCor + i][cCor].getSpot() == 0){
                                continuePutting = true;

                            }else{
                                continuePutting =false;
                                break;
                            }

                        }
                    }

                    if (continuePutting == true){
                        if (rCor <= (dimention - lengthWord)) {
                            System.out.println("if 1 runs");
                            for (int i = 0; i < lengthWord; i++) {

                                System.out.println("for 2 runs");
                                boardButtons[rCor + i][cCor].setText(subedWord.charAt(i) + "");
                                boardTest[i + rCor][cCor].setSpot(1);

                            }

                            for (int i = 0; i < arraySearch.size(); i++) {
                                if (arraySearch.get(i).equals(subedWord)){
                                    lstListOfWords.getItems().add(subedWord);
                                    wordsUsedInWordsearch.add(subedWord);
                                }
                            }

                            foundSpot = true;
                        }else{
                            canPutItIn = false;
                        }
                    }else{
                        canPutItIn = false;
                    }

            }else if (chosenDirection == 4) {//go up

                //            while (foundSpot == false && totalSpots > 0){
                if (rCor >= (lengthWord)) {

                    System.out.println("if demo runs");

                    for (int i = 0; i < lengthWord; i++) {

                        if (boardTest[rCor - i][cCor].getSpot() == 0){
                            continuePutting = true;

                        }else{
                            continuePutting =false;
                            break;
                        }

                    }
                }

                if (continuePutting == true){
                    if (rCor >= (lengthWord)) {
                        System.out.println("if 1 runs");

                        for (int i = 0; i < lengthWord; i++) {
                            System.out.println("for 2 runs");

                            boardButtons[rCor - i][cCor].setText(subedWord.charAt(i) + "");
                            boardTest[rCor - i][cCor].setSpot(1);

                        }

                        for (int i = 0; i < arraySearch.size(); i++) {
                            if (arraySearch.get(i).equals(subedWord)){
                                lstListOfWords.getItems().add(subedWord);
                                wordsUsedInWordsearch.add(subedWord);
                            }
                        }

                        foundSpot = true;
                    }else{
                        canPutItIn = false;
                    }
                }else{
                    canPutItIn = false;
                }


            }else if (chosenDirection == 5) {//go down right


                //            while (foundSpot == false && totalSpots > 0){
                if (rCor <= (dimention - lengthWord) && cCor <= (dimention - lengthWord)) {

                    System.out.println("if demo runs");

                    for (int i = 0; i < lengthWord; i++) {

                        if (boardTest[rCor + i][cCor + i].getSpot() == 0){
                            continuePutting = true;

                        }else{
                            continuePutting =false;
                            break;
                        }

                    }
                }

                if (continuePutting == true){
                    if (rCor <= (dimention - lengthWord) && cCor <= (dimention - lengthWord)) {

                        System.out.println("if 1 runs");

                        for (int i = 0; i < lengthWord; i++) {

                            System.out.println("for 2 runs");

                            boardButtons[rCor + i][cCor + i].setText(subedWord.charAt(i) + "");
                            boardTest[rCor + i][cCor + i].setSpot(1);

                        }

                        for (int i = 0; i < arraySearch.size(); i++) {
                            if (arraySearch.get(i).equals(subedWord)){
                                lstListOfWords.getItems().add(subedWord);
                                wordsUsedInWordsearch.add(subedWord);
                            }
                        }

                        foundSpot = true;
                    }else{
                        canPutItIn = false;
                    }
                }else{
                    canPutItIn = false;
                }



            }else if (chosenDirection == 6) {//go down left

                    if (rCor <= (dimention - lengthWord) && cCor >= (lengthWord)) {

                        System.out.println("if demo runs");

                        for (int i = 0; i < lengthWord; i++) {

                            if (boardTest[rCor + i][cCor - i].getSpot() == 0){
                                continuePutting = true;

                            }else{
                                continuePutting =false;
                                break;
                            }

                        }
                    }

                //            while (foundSpot == false && totalSpots > 0){

                    if (continuePutting == true){
                        if (rCor <= (dimention - lengthWord) && cCor >= (lengthWord)) {

                            System.out.println("if 1 runs");

                            for (int i = 0; i < lengthWord; i++) {

                                System.out.println("for 2 runs");

                                boardButtons[rCor + i][cCor - i].setText(subedWord.charAt(i) + "");
                                boardTest[rCor+i][cCor - i].setSpot(1);

                            }

                            for (int i = 0; i < arraySearch.size(); i++) {
                                if (arraySearch.get(i).equals(subedWord)){
                                    lstListOfWords.getItems().add(subedWord);
                                    wordsUsedInWordsearch.add(subedWord);
                                }
                            }

                            foundSpot = true;
                        }else{
                            canPutItIn = false;
                        }
                    }else{
                        canPutItIn = false;
                    }


                }else if (chosenDirection == 7) {//go up left

                    if (rCor >= (lengthWord) && cCor >= (lengthWord)) {

                        System.out.println("if demo runs");

                        for (int i = 0; i < lengthWord; i++) {

                            if (boardTest[rCor - i][cCor - i].getSpot() == 0){
                                continuePutting = true;

                            }else{
                                continuePutting =false;
                                break;
                            }

                        }
                    }

                //            while (foundSpot == false && totalSpots > 0){
                    if (continuePutting == true){
                        if (rCor >= (lengthWord) && cCor >= (lengthWord)) {
                            System.out.println("if 1 runs");

                            for (int i = 0; i < lengthWord; i++) {

                                System.out.println("for 2 runs");

                                boardButtons[rCor - i][cCor - i].setText(subedWord.charAt(i) + "");
                                boardTest[rCor - i][cCor - i].setSpot(1);
                            }

                            for (int i = 0; i < arraySearch.size(); i++) {
                                if (arraySearch.get(i).equals(subedWord)){
                                    lstListOfWords.getItems().add(subedWord);
                                    wordsUsedInWordsearch.add(subedWord);
                                }
                            }

                            foundSpot = true;
                        }else{
                            canPutItIn = false;
                        }
                    }else{
                        canPutItIn = false;
                    }


                }else if (chosenDirection == 8) {//go up right

                    if (rCor >= (lengthWord) && cCor <= (dimention - lengthWord)) {

                        System.out.println("if demo runs");

                        for (int i = 0; i < lengthWord; i++) {

                            if (boardTest[rCor - i][cCor + i].getSpot() == 0){
                                continuePutting = true;

                            }else{
                                continuePutting =false;
                                break;
                            }

                        }
                    }

                //            while (foundSpot == false && totalSpots > 0){
                    if (continuePutting == true){
                        if (rCor >= (lengthWord) && cCor <= (dimention - lengthWord)) {

                            System.out.println("if 1 runs");

                            for (int i = 0; i < lengthWord; i++) {

                                System.out.println("for 2 runs");

                                boardButtons[rCor - i][cCor + i].setText(subedWord.charAt(i) + "");
                                boardTest[rCor - i][cCor + i].setSpot(1);

                            }

                            for (int i = 0; i < arraySearch.size(); i++) {
                                if (arraySearch.get(i).equals(subedWord)){
                                    lstListOfWords.getItems().add(subedWord);
                                    wordsUsedInWordsearch.add(subedWord);
                                }
                            }

                            foundSpot = true;
                        }else{
                            canPutItIn = false;
                        }
                    }else{
                        canPutItIn = false;
                    }


                }

            if (canPutItIn == false){
                System.out.println("else runned");

                directions.remove(directionCor);

                if (directions.size() != 0){
                    System.out.println("This runs 1");
                    directionCor = (int)((Math.random()*directions.size()-1));
                    System.out.println("This runs 2");
                    chosenDirection = directions.get(directionCor);
                    System.out.println("This runs 3");
//                    totalSpots--;
//                    System.out.println("This runs 4");

                    System.out.println("Total spots: " + totalSpots);
                }

                if (totalSpots <= 0){
                    System.out.println("Word Cant Fit " + subedWord);
                    foundSpot = true;
                }


                if (directions.size() == 0 && totalSpots > 0) {

                    totalSpots--;
                    System.out.println("This runs 4");

                    System.out.println("direction if runned");

                    rCor = (int) (Math.random() * dimention);
                    cCor = (int) (Math.random() * dimention);

                    foundSpot = false;

                    //                    foundSpot = true;
                    resetDirectionArray();
//
//                    directionCor = (int)((Math.random()*directions.size()-1));
//                    chosenDirection = directions.get(directionCor);

                }

            }
        }

    }

    //size of button, text and girdpane all play togther.

    public boolean checkDone(){

        return false;

    }

    //gets the starting pressed spot and ending pressed spot
    public void display(int row, int col, ActionEvent t){

        if (board[row][col].getSpot() == 2){
            p1.setPoints(-10);
            lstPlayerOne.getItems().set(2, "Points: " + p1.getPoints());
        }

        if (clickOnChangeLetterButton == true){

            boardSpotsBTN[row][col].setText(subLetter);
            clickOnChangeLetterButton = false;

        }else if (clickOnChangeLetterButton == false){
            //        System.out.println("clicked on Spot with the letter: " + boardSpotsBTN[row][col].getText());
            spotCheck++;

            if (spotCheck == 1){
                wordSelectedStart += boardSpotsBTN[row][col].getText();
                startNum[0] = row;
                startNum[1] = col;
                lblStartLetter.setText(wordSelectedStart);
//            System.out.println("clicked on Spot with the letter: " + boardSpotsBTN[row][col].getText());
            }else if (spotCheck == 2){
                wordSelectedEnd += boardSpotsBTN[row][col].getText();
                endNum[0] = row;
                endNum[1] = col;
                lblStartLetter.setText(wordSelectedStart);
                lblEndLetter.setText(wordSelectedEnd);
                findWordFromStartAndEndWordsClicked(boardSpotsBTN);
                spotCheck = 0;
                wordSelectedStart = "";
                wordSelectedEnd = "";
//            System.out.println("clicked on Spot with the letter: " + boardSpotsBTN[row][col].getText());
            }

            System.out.println("Starting Spot: " + wordSelectedStart);
            System.out.println("Ending Spot: " + wordSelectedEnd);

        }

    }

    //uses the info from the previous function ^^^^ and finds the words in between.
    public void findWordFromStartAndEndWordsClicked(Button[][] boardButtons){
        System.out.println("System runs");

        boolean foundTheDirection = false;
        boolean findEnd = false;
        boolean foundWord = false;
        int checkRow = 0;
        int checkCol = 0;
        int count = 0;

        String checkWord = "";


        while (findEnd == false){

            //check if going right
            if (endNum[1] > startNum[1] && endNum[0] == startNum[0]){

                while (startNum[1] + count != endNum[1]){
//                    if (startNum[1] + count >= 10){
//                        findEnd = true;
//                    }
                    System.out.println(boardButtons[startNum[0]][startNum[1] + count].getText());
                    checkWord += boardButtons[startNum[0]][startNum[1] + count].getText();

                    lblWordSelected.setText(checkWord);




//                    boardSpotsBTN[startNum[0]][startNum[1] + count].setStyle("-fx-background-color: #ffff00; -fx-border-color: #7c00cf; -fx-border-width: 2px;");


                    System.out.println("stuff 1");

                    count++;

                    if (startNum[1] + count == endNum[1]){
                        System.out.println(boardButtons[startNum[0]][startNum[1] + count].getText());

//                        boardSpotsBTN[startNum[0]][startNum[1] + count].setStyle("-fx-background-color: #ffff00; -fx-border-color: #7c00cf; -fx-border-width: 2px;");


                        System.out.println("stuff 2");
                        checkWord += boardButtons[startNum[0]][startNum[1] + count].getText();
                        lblWordSelected.setText(checkWord);
                        findEnd = true;
                        System.out.println("Found Path right");

                        checkSelectedWordFromListOfWords(checkWord,"right",boardButtons);

                    }

                }

//                System.out.println("Found Path right");
            }else if (endNum[1] < startNum[1] && endNum[0] == startNum[0]){// going left

                while (startNum[1] - count != endNum[1]){
//                    if (startNum[1] + count >= 10){
//                        findEnd = true;
//                    }
                    System.out.println(boardButtons[startNum[0]][startNum[1] - count].getText());
                    checkWord += boardButtons[startNum[0]][startNum[1] - count].getText();
                    lblWordSelected.setText(checkWord);
                    count++;

                    if (startNum[1] - count == endNum[1]){
                        System.out.println(boardButtons[startNum[0]][startNum[1] - count].getText());
                        checkWord += boardButtons[startNum[0]][startNum[1] - count].getText();

                        lblWordSelected.setText(checkWord);

                        findEnd = true;
                        System.out.println("Found Path left");

                        checkSelectedWordFromListOfWords(checkWord,"left",boardButtons);
                    }

                }

            }else if (endNum[0] < startNum[0] && endNum[1] == startNum[1]){//going up
                while (startNum[0] - count != endNum[0]){
//                    if (startNum[1] + count >= 10){
//                        findEnd = true;
//                    }
                    System.out.println(boardButtons[startNum[0] - count][startNum[1]].getText());
                    checkWord += boardButtons[startNum[0] - count][startNum[1]].getText();
                    lblWordSelected.setText(checkWord);
                    count++;

                    if (startNum[0] - count == endNum[0]){
                        System.out.println(boardButtons[startNum[0] - count][startNum[1]].getText());
                        checkWord += boardButtons[startNum[0] - count][startNum[1]].getText();

                        lblWordSelected.setText(checkWord);

                        findEnd = true;
                        System.out.println("Found Path Up");
                        checkSelectedWordFromListOfWords(checkWord,"Up",boardButtons);
                    }

                }
            }else if (endNum[0] > startNum[0] && endNum[1] == startNum[1]){//going down
                while (startNum[0] + count != endNum[0]){
//                    if (startNum[1] + count >= 10){
//                        findEnd = true;
//                    }
                    System.out.println(boardButtons[startNum[0] + count][startNum[1]].getText());
                    checkWord += boardButtons[startNum[0] + count][startNum[1]].getText();
                    lblWordSelected.setText(checkWord);
                    count++;

                    if (startNum[0] + count == endNum[0]){
                        System.out.println(boardButtons[startNum[0] + count][startNum[1]].getText());
                        checkWord += boardButtons[startNum[0] + count][startNum[1]].getText();

                        lblWordSelected.setText(checkWord);

                        findEnd = true;
                        System.out.println("Found Path Down");
                        checkSelectedWordFromListOfWords(checkWord,"Down",boardButtons);
                    }

                }
            }else if (endNum[0] < startNum[0] && endNum[1] > startNum[1]) {//up and right

                //GOING UP
                while (startNum[0] - count != endNum[0] && startNum[1] + count != endNum[1]) {
//                    if (startNum[1] + count >= 10){
//                        findEnd = true;
//                    }
                    System.out.println(boardButtons[startNum[0] - count][startNum[1] + count].getText());
                    checkWord += boardButtons[startNum[0] - count][startNum[1] + count].getText();
                    lblWordSelected.setText(checkWord);
                    count++;

                    if (startNum[0] - count == endNum[0] && startNum[1] + count == endNum[1]) {
                        System.out.println(boardButtons[startNum[0] - count][startNum[1] + count].getText());
                        checkWord += boardButtons[startNum[0] - count][startNum[1] + count].getText();

                        lblWordSelected.setText(checkWord);

                        findEnd = true;
                        System.out.println("Found Path Up and Right");
                        checkSelectedWordFromListOfWords(checkWord,"Up and Right",boardButtons);
                    }

                }
            }else if (endNum[0] < startNum[0] && endNum[1] < startNum[1]) {//up and left
                //GOING UP
                while (startNum[0] - count != endNum[0] && startNum[1] - count != endNum[1]) {
//                    if (startNum[1] + count >= 10){
//                        findEnd = true;
//                    }
                    System.out.println(boardButtons[startNum[0] - count][startNum[1] - count].getText());
                    checkWord += boardButtons[startNum[0] - count][startNum[1] - count].getText();
                    lblWordSelected.setText(checkWord);
                    count++;

                    if (startNum[0] - count == endNum[0] && startNum[1] - count == endNum[1]) {
                        System.out.println(boardButtons[startNum[0] - count][startNum[1] - count].getText());
                        checkWord += boardButtons[startNum[0] - count][startNum[1] - count].getText();

                        lblWordSelected.setText(checkWord);

                        findEnd = true;
                        System.out.println("Found Path Up and Left");
                        checkSelectedWordFromListOfWords(checkWord,"Up and Left",boardButtons);
                    }

                }
            }else if (endNum[0] > startNum[0] && endNum[1] > startNum[1]) {//down and right

                //GOING UP
                while (startNum[0] + count != endNum[0] && startNum[1] + count != endNum[1]) {
//                    if (startNum[1] + count >= 10){
//                        findEnd = true;
//                    }
                    System.out.println(boardButtons[startNum[0] + count][startNum[1] + count].getText());
                    checkWord += boardButtons[startNum[0] + count][startNum[1] + count].getText();
                    lblWordSelected.setText(checkWord);
                    count++;

                    if (startNum[0] + count == endNum[0] && startNum[1] + count == endNum[1]) {
                        System.out.println(boardButtons[startNum[0] + count][startNum[1] + count].getText());
                        checkWord += boardButtons[startNum[0] + count][startNum[1] + count].getText();

                        lblWordSelected.setText(checkWord);

                        findEnd = true;
                        System.out.println("Found Path Down and Right");
                        checkSelectedWordFromListOfWords(checkWord,"Down and Right",boardButtons);
                    }

                }
            }else if (endNum[0] > startNum[0] && endNum[1] < startNum[1]) {//down and left

                //GOING UP
                while (startNum[0] + count != endNum[0] && startNum[1] - count != endNum[1]) {
//                    if (startNum[1] + count >= 10){
//                        findEnd = true;
//                    }
                    System.out.println(boardButtons[startNum[0] + count][startNum[1] - count].getText());
                    checkWord += boardButtons[startNum[0] + count][startNum[1] - count].getText();
                    lblWordSelected.setText(checkWord);
                    count++;

                    if (startNum[0] + count == endNum[0] && startNum[1] - count == endNum[1]) {
                        System.out.println(boardButtons[startNum[0] + count][startNum[1] - count].getText());
                        checkWord += boardButtons[startNum[0] + count][startNum[1] - count].getText();

                        lblWordSelected.setText(checkWord);


                        findEnd = true;
                        System.out.println("Found Path Down and Left");
                        checkSelectedWordFromListOfWords(checkWord,"Down and Left",boardButtons);
                    }

                }
            }

        }

//        checkSelectedWordFromListOfWords(checkWord);

    }

    ////////////////////////////////////////////////////////////////////////////////////////
    public void putWordsInHighlightButtons(String direction, Button[][] boardButtons){
        int count = 0;

        if (direction.equals("right")){

            while (startNum[1] + count != endNum[1]){
                boardButtons[startNum[0]][startNum[1] + count].setStyle("-fx-background-color: #ffff00; -fx-border-color: #7c00cf; -fx-border-width: 2px;");

                count++;

                if (startNum[1] + count == endNum[1]){
                    boardButtons[startNum[0]][startNum[1] + count].setStyle("-fx-background-color: #ffff00; -fx-border-color: #7c00cf; -fx-border-width: 2px;");
                }


            }

        }


        if (direction.equals("left")){
            while (startNum[1] - count != endNum[1]){
                boardButtons[startNum[0]][startNum[1] - count].setStyle("-fx-background-color: #ffff00; -fx-border-color: #7c00cf; -fx-border-width: 2px;");
                count++;

                if (startNum[1] - count == endNum[1]){
                    boardButtons[startNum[0]][startNum[1] - count].setStyle("-fx-background-color: #ffff00; -fx-border-color: #7c00cf; -fx-border-width: 2px;");
                }

            }
        }


        if (direction.equals("Up")){
            while (startNum[0] - count != endNum[0]){

                boardButtons[startNum[0] - count][startNum[1]].setStyle("-fx-background-color: #ffff00; -fx-border-color: #7c00cf; -fx-border-width: 2px;");
                count++;
                if (startNum[0] - count == endNum[0]){
                    boardButtons[startNum[0] - count][startNum[1]].setStyle("-fx-background-color: #ffff00; -fx-border-color: #7c00cf; -fx-border-width: 2px;");
                }

            }
        }


        if (direction.equals("Down")){
            while (startNum[0] + count != endNum[0]){

                boardButtons[startNum[0] + count][startNum[1]].setStyle("-fx-background-color: #ffff00; -fx-border-color: #7c00cf; -fx-border-width: 2px;");
                count++;
                if (startNum[0] + count == endNum[0]){
                    boardButtons[startNum[0] + count][startNum[1]].setStyle("-fx-background-color: #ffff00; -fx-border-color: #7c00cf; -fx-border-width: 2px;");
                }

            }
        }


        if (direction.equals("Down and Right")){
            while (startNum[0] + count != endNum[0] && startNum[1] + count != endNum[1]){

                boardButtons[startNum[0] + count][startNum[1] + count].setStyle("-fx-background-color: #ffff00; -fx-border-color: #7c00cf; -fx-border-width: 2px;");
                count++;
                if (startNum[0] + count == endNum[0] && startNum[1] + count == endNum[1]) {
                    boardButtons[startNum[0] + count][startNum[1] + count].setStyle("-fx-background-color: #ffff00; -fx-border-color: #7c00cf; -fx-border-width: 2px;");
                }

            }
        }

        if (direction.equals("Down and Left")){
            while (startNum[0] + count != endNum[0] && startNum[1] - count != endNum[1]){

                boardButtons[startNum[0] + count][startNum[1] - count].setStyle("-fx-background-color: #ffff00; -fx-border-color: #7c00cf; -fx-border-width: 2px;");
                count++;
                if (startNum[0] + count == endNum[0] && startNum[1] - count == endNum[1]) {
                    boardButtons[startNum[0] + count][startNum[1] - count].setStyle("-fx-background-color: #ffff00; -fx-border-color: #7c00cf; -fx-border-width: 2px;");
                }

            }
        }

        if (direction.equals("Up and Right")){
            while (startNum[0] - count != endNum[0] && startNum[1] + count != endNum[1]){

                boardButtons[startNum[0] - count][startNum[1] + count].setStyle("-fx-background-color: #ffff00; -fx-border-color: #7c00cf; -fx-border-width: 2px;");
                count++;
                if (startNum[0] - count == endNum[0] && startNum[1] + count == endNum[1]) {
                    boardButtons[startNum[0] - count][startNum[1] + count].setStyle("-fx-background-color: #ffff00; -fx-border-color: #7c00cf; -fx-border-width: 2px;");
                }

            }
        }

        if (direction.equals("Up and Left")){
            while (startNum[0] - count != endNum[0] && startNum[1] - count != endNum[1]){

                boardButtons[startNum[0] - count][startNum[1] - count].setStyle("-fx-background-color: #ffff00; -fx-border-color: #7c00cf; -fx-border-width: 2px;");
                count++;
                if (startNum[0] - count == endNum[0] && startNum[1] - count == endNum[1]) {
                    boardButtons[startNum[0] - count][startNum[1] - count].setStyle("-fx-background-color: #ffff00; -fx-border-color: #7c00cf; -fx-border-width: 2px;");
                }

            }
        }

    }

    public void checkSelectedWordFromListOfWords(String selectedWord,String direction, Button[][] boardButtons){

        boolean foundQuote = false;


        for (int i = 0; i < wordsUsedInWordsearch.size(); i++) {

            if (wordsUsedInWordsearch.get(i).equals(selectedWord)){

                lstListOfWords.getItems().remove(selectedWord);

                for (int j = 0; j < selectedWord.length(); j++) {
                    putWordsInHighlightButtons(direction,boardButtons);
                }


                lstWordsFound.getItems().add(selectedWord);


                wordsUsedInWordsearch.set(i," ");

                if (p1.getGenreChosen().equals("Billionares")){
                    if (!numberMapping.get(selectedWord).equals(null)){
                        lstDefinitions.getItems().add(selectedWord + ": " + numberMapping.get(selectedWord));
                    }else{
                        System.out.println("Nada");
                    }
                }

            }
        }

        for (int i = 0; i < wordsUsedInWordsearch.size(); i++) {

            if (wordsUsedInWordsearch.get(i).equals(" ")){

                foundQuote = true;

            }else{
                foundQuote = false;
                break;
            }
        }

        if (foundQuote == true){

            timeEnd = secondsPassed;
            timePassed = timeEnd - timeStart;

            lblWinOrLose.setText("You Found ALL The Words!! You spent: " + timePassed + " seconds passed.");

            System.out.println("You Won!!!");
            p1.setGenreChosen(" ");
            p1.setPoints(100);


            lstPlayerOne.getItems().set(2, "Points: " + p1.getPoints());

        }

    }


    @FXML
    public void btnEnterPlayerOneName(){
        p1.setName(txtEnterPlayerOneName.getText());
    }

    @FXML
    public void btnEnterPlayerOneAge(){
        p1.setAge(Integer.parseInt(txtEnterPlayerOneAge.getText()));
    }

    @FXML
    public void butChooseCandyLand(){
        level = 1;
        p1.setGenreChosen("Candyland");

        try {
            FileInputStream input = new FileInputStream("src/main/resources/pics/candyland.jpg");
            imgBackground.setImage(new Image(input));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void butChooseAnimalTopia(){
        level = 1;
        p1.setGenreChosen("AnimalTopia");

        try {
            FileInputStream input = new FileInputStream("src/main/resources/pics/animals.jpg");
            imgBackground.setImage(new Image(input));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void btnHolidays(){
        level = 2;
        p1.setGenreChosen("Holidays");

        try {
            FileInputStream input = new FileInputStream("src/main/resources/pics/bigHolidays.png");
            imgBackground.setImage(new Image(input));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void btnBillionares(){
        level = 2;
        p1.setGenreChosen("Billionares");

        try {
            FileInputStream input = new FileInputStream("src/main/resources/pics/Billionares.png");
            imgBackground.setImage(new Image(input));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void btnGreekMythology(){
        level = 3;
        p1.setGenreChosen("GreekMythology");

        try {
            FileInputStream input = new FileInputStream("src/main/resources/pics/greek Mythology.jpg");
            imgBackground.setImage(new Image(input));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void btnCountries(){
        level = 3;
        p1.setGenreChosen("Countries");

        try {
            FileInputStream input = new FileInputStream("src/main/resources/pics/countries.jpg");
            imgBackground.setImage(new Image(input));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void btnSetDimentions(){
        dimention1 = Integer.parseInt(txtSetDimention.getText());

        board = new GridSpot[dimention1][dimention1];
        //Creates a 2D array of Buttons

        boardSpotsBTN = new Button[dimention1][dimention1];//you can change these for dynamic.

    }

    @FXML
    public void btnResetPuzzle(){

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j].setSpot(0);
            }
        }

        for (int i = 0; i < boardSpotsBTN.length; i++) {
            for (int j = 0; j < boardSpotsBTN[0].length; j++) {
                boardSpotsBTN[i][j].setStyle("-fx-background-color: #ccfffe;");
                boardSpotsBTN[i][j].setText(" ");
            }
        }

        lstListOfWords.getItems().clear();
        lstWordsFound.getItems().clear();

        lblStartLetter.setText("_____");
        lblEndLetter.setText("_____");
        lblWinOrLose.setText("_________");
        lblNotice.setText("________");
        lblLetterChosen.setText("________");


        lstPlayerOne.getItems().set(2, "Points: " + p1.getPoints());

        checkToDisableButtons();


    }
    @FXML
    public void btnReplay(){
        if (p1.getGenreChosen().equals("Candyland")){
            for (int i = 0; i < candyLandWords.size(); i++) {
//                lstListOfWords.getItems().add(candyLandWords.get(i));
                displayWord(candyLandWords.get(i),dimention1,board,boardSpotsBTN,candyLandWords);
                checkSpot();
            }
        }

        if (p1.getGenreChosen().equals("AnimalTopia")){
            for (int i = 0; i < animalTopiaWords.size(); i++) {
//                lstListOfWords.getItems().add(animalTopiaWords.get(i));
                displayWord(animalTopiaWords.get(i),dimention1,board,boardSpotsBTN,animalTopiaWords);
                checkSpot();
            }
        }

        if (p1.getGenreChosen().equals("Holidays")){
            for (int i = 0; i < holidayWords.size(); i++) {
//                lstWordListTwo.getItems().add(holidayWords.get(i));
                displayWord(holidayWords.get(i),dimention1,board,boardSpotsBTN,holidayWords);
                checkSpot();
            }
        }

        if (p1.getGenreChosen().equals("Billionares")){
            for (int i = 0; i < billionareWords.size(); i++) {
//                lstWordListTwo.getItems().add(billionareWords.get(i));
                displayWord(billionareWords.get(i),dimention1,board,boardSpotsBTN,billionareWords);
                checkSpot();
            }

//            lstDefinitions.setDisable(false);

        }

        if (p1.getGenreChosen().equals("Countries")){
            for (int i = 0; i < countryWords.size(); i++) {
//                lstWordListTwo.getItems().add(billionareWords.get(i));
                displayWord(countryWords.get(i),dimention1,board,boardSpotsBTN,countryWords);
                checkSpot();
            }

//            lstDefinitions.setDisable(false);

        }

        if (p1.getGenreChosen().equals("GreekMythology")){
            for (int i = 0; i < greekMythologyWords.size(); i++) {
//                lstWordListTwo.getItems().add(billionareWords.get(i));
                displayWord(greekMythologyWords.get(i),dimention1,board,boardSpotsBTN,greekMythologyWords);
                checkSpot();
            }

//            lstDefinitions.setDisable(false);

        }

        setRandomSpotsToStars(board);
        fillInTheRestOfTheWordSearch(board,boardSpotsBTN);

        checkSpot();

        directions.clear();
        resetDirectionArray();

        for (int i = 0; i < directions.size(); i++) {
            System.out.println(directions.get(i));
        }

        timeStart = secondsPassed;
        checkToDisableButtons();


    }

    public void checkToDisableButtons(){
        if (p1.getPoints() >= 100){
            btnHandleHolidays.setDisable(false);
            btnHandleBillionares.setDisable(false);
        }

        if (p1.getPoints() >= 400){
            btnHandleGreekMythology.setDisable(false);
            btnHandleCountries.setDisable(false);
        }
    }

    @FXML
    public void btnChooseLetters(){

        btnFxLetterChange.setDisable(true);

        for(int i = 0; i < 26; i++){
            letters[i] = (char)(97 + i);
            lstOfLetters.getItems().add(letters[i]);
        }




//        getHashmapStuff();
    }

    @FXML
    public void lstClickedOnListOfLetters(){
        if (p1.getPoints() >= 100){
            subLetter = lstOfLetters.getSelectionModel().getSelectedItem().toString();
            System.out.println(subLetter);
            clickOnChangeLetterButton = true;

            lblLetterChosen.setText(subLetter);
            lblNotice.setText(" ");
            p1.setPoints(-100);
            lstPlayerOne.getItems().set(2, "Points: " + p1.getPoints());

        }else{
            lblNotice.setText("Not enough points");
            lblLetterChosen.setText(" ");
        }


    }

    //This is the only thing that I googled but I eamiled Mr.Cortez about it and this is for an extra feature.
    //"jeffbezoz","musk","gates","zuckerberg","buffet","ellision","page","brin","ambani"
    public void getHashmapStuff(){
        numberMapping.clear();
                // Adding key-value pairs to a HashMap
        numberMapping.put("jeffbezoz", "The founder and chief executive officer of e-commerce giant Amazon. He is an entrepreneur, investor and media proprietor.");
        numberMapping.put("musk", "The co-founder, CEO, and leader of Tesla, SpaceX, Neuralink, and The Boring Company. He is the richest man on earth!");
        numberMapping.put("gates", "He is the American computer programmer and entrepeneur who cofounded Microsoft Corporation, the world's largest personal-computer software company.");
        numberMapping.put("zuckerberg", "He is the co-founder and CEO of the sociel networking website Facebook, as well as one of the world's youngest billionaires!");
        numberMapping.put("buffet", "He is known as the 'Oracle of Omaha' and is the most successful investor of all time! He runs Berkshire Hathaway which owns more than 60 companies which includes insurer Geico, battery maker Duracell and restaurant chain Dairy Queen.");
        numberMapping.put("ellision", "He is an American buisnessman and investor who's the co-founder, executive chairman, chief technology officer (CTO) and former CEO of Oracle Corporation.");
        numberMapping.put("page", "He is a computer scientist internet entrepreneur, co-founding Google co-founding Alphabet Inc. Co-creator PageRank.");
        numberMapping.put("brin", "He is a computer scientist internet entrepreneur, co-founding Google co-founding Alphabet Inc. Co-creator PageRank.");
        numberMapping.put("ambani", "He is the richest person in Asia and is the chairman of India's Reliance Industries. ");


        // Add a new key-value pair only if the key does not exist in the HashMap, or is mapped to `null`
        System.out.println(numberMapping.get("musk"));
//        System.out.println(numberMapping.get();
    }

    public void setRandomSpotsToStars(GridSpot[][] boardTest){
        int randomRow = 0;
        int randomCol = 0;

        for (int i = 0; i < 5; i++) {
            randomRow = (int)(Math.random()*dimention1);
            randomCol = (int)(Math.random()*dimention1);

            if (boardTest[randomRow][randomCol].getSpot() == 0){
                boardTest[randomRow][randomCol].setSpot(2);
            }


        }

    }

    @FXML
    public void btnFindLetter(){
        if (p1.getPoints() >= 100){
            String letter = txtFindLetter.getText();

            for (int i = 0; i < boardSpotsBTN.length; i++) {
                for (int j = 0; j < boardSpotsBTN[0].length; j++) {

                    if (boardSpotsBTN[i][j].getText().equals(letter)){
                        boardSpotsBTN[i][j].setStyle("-fx-background-color: #00ff44;");
                    }

                }
            }

            p1.setPoints(-100);
            lstPlayerOne.getItems().set(2, "Points: " + p1.getPoints());
        }

    }

    @FXML
    public void btnCheatExperience(){

        p1.setPoints(1000);
        lstPlayerOne.getItems().set(2, "Points: " + p1.getPoints());
    }

    @FXML
    public void btnGambleForPoints(){
        randSpot = (int)(Math.random()*testWords.size());

        wordSelected = testWords.get(randSpot);

        lblWordCheck.setText(wordSelected);

        lblShowMatching.setText("________");

    }

    @FXML
    public void lstClickedOnGambleForPoints(){
        int clickedIndex = lstDefinitionForTestWords.getSelectionModel().getSelectedIndex();
        System.out.println(clickedIndex);
        if (clickedIndex == randSpot){
            lblShowMatching.setText("You matched correctly! You get 50 points!!!");
            p1.setPoints(50);
            lstPlayerOne.getItems().set(2, "Points: " + p1.getPoints());
        }

    }

}