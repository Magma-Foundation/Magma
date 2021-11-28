package com.meti.app;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            var source = Paths.get(".", "index.mgf");
            var application = new Application(new NIOPath(source));
            application.run();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
}
