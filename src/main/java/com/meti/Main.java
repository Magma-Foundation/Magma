package com.meti;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            new Application(Paths.get(".", "index.mg")).run();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
}
