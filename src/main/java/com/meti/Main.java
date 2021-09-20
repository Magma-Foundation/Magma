package com.meti;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        var root = Paths.get(".", "src");
        var source = new DirectorySource(root);
        var application = new Application(source);
        try {
            application.run();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
}
