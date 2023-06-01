package com.meti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        var path = Paths.get(".", "Main.java");
        var s = Files.readString(path);


        var path1 = Paths.get(".", "Main.mgs");
        Files.writeString(path1, s);
    }
}
