package com.meti;

public class Main {
    public static void main(String[] args) {
        try {
            Application.run();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
}
