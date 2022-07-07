package com.labs.practice02;

public class Main {
    public static void main(String[] args) throws InterruptedException {


        System.out.println("1. Fixed Tik-Tak:");

        Data d = new Data();
        Worker w1 = new Worker(1, d);
        Worker w2 = new Worker( 2, d);
        w1.join();

        System.out.println("\n2. Tik-Tak-Toy:");

        DataUpdated ud = new DataUpdated();
        WorkerUpdated uw1 = new WorkerUpdated(2, ud, 2);
        WorkerUpdated uw2 = new WorkerUpdated(3, ud, 3);
        WorkerUpdated uw3 = new WorkerUpdated(1, ud, 1);
        uw1.join();
        uw2.join();

        System.out.println("end of main...");
    }
}
