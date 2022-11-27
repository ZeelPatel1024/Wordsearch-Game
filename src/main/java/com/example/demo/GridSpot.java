package com.example.demo;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GridSpot {
//    Image o,x,back;
//
//    Image pick;
//    //represents the turn of the player which is their spot
    int spot;
    private String btnText = " ";


    public GridSpot(int place){
        btnText = " ";

        spot = place;

//        FileInputStream oo,xx,backk;
//        try {
//            oo = new FileInputStream("src/main/Pictures/o.png");
//            o = new Image(oo);
//            xx= new FileInputStream("src/main/Pictures/x.png");
//            x = new Image(xx);
//            backk = new FileInputStream("src/main/Pictures/liteYellow.png");
//            back = new Image(backk);
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    public void setSpot(int spot) {
        this.spot = spot;
    }

    public int getSpot() {
        return spot;
    }

    //    public void clickedSpot(int turn){
//        //turn is passed in as a 0 or a 1
//        if(turn == 0){
//            //the picture would be an x for player 1
//            btnText = "X";
//        }else{
//            //the picture would be an 0 for player 2
//            btnText = "O";
//        }
//    }


    public String getBtnText() {
        return btnText;
    }

//    public int getSpot(){
//        return spot;
//    }
//    public void setZero(){
//        spot = 0;
//    }
//    public Image getResourceLink() {
//        return pick;
//    }
}
