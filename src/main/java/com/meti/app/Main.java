package com.meti.app;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            new Application(Paths.get(".", "index.mgf")).run();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
}
