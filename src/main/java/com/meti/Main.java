package com.meti;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        var src = new DirectoryGateway(Paths.get(".", "src"));
        var target = new DirectoryGateway(Paths.get(".", "target"));

        try {
            new Application(src, target).run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
