package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;

public class Player {

    String name;
    int age;
    String genreChosen;
    int points;

    public Player(String n, int a, String gC, int pts){

        name = n;
        age = a;
        genreChosen = gC;
        points = pts;

    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points += points;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGenreChosen(String genreChosen) {
        this.genreChosen = genreChosen;
    }

    public String getGenreChosen() {
        return genreChosen;
    }

}
