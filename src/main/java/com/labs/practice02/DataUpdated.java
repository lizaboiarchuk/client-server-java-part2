package com.labs.practice02;

public class DataUpdated {
    private int state=1;

    public int getState() { return state; }

    public void Tic(){
        System.out.print("Tic-");
        state = 2;
    }
    public void Tak(){
        System.out.print("Tak-");
        state = 3;
    }

    public void Toe(){
        System.out.println("Toe");
        state = 1;
    }
}