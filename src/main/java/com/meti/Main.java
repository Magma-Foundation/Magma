package com.meti;

public class Main {
    public static void main(String[] args) {
        try {
            new Application().run();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
}
